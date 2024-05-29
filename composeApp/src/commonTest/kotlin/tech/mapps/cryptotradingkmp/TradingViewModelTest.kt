@file:OptIn(ExperimentalCoroutinesApi::class)

package tech.mapps.cryptotradingkmp

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.TurbineTestContext
import app.cash.turbine.test
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TradingViewModelTest {

    private val coroutineScope = CoroutineScope(UnconfinedTestDispatcher())

    private fun viewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(),
        coroutineScope: CoroutineScope = this.coroutineScope,
        connectivity: Flow<ConnectivityStatus> = flowOf(ConnectivityStatus.Available),
        tickers: Either<Unit, ImmutableList<Ticker>>? = randomTickers.toImmutableList().right(),
        tickersFlow: Flow<Either<Unit, ImmutableList<Ticker>>>? = null,
    ) =
        TradingViewModel(
            savedStateHandle = savedStateHandle,
            coroutineScope = coroutineScope,
            connectivityStatusTracker = { connectivity },
            observeTickers = { tickersFlow ?: flowOf(tickers!!) },
        )

    @Test
    fun `initial state`() =
        assertEquals(TradingState(), viewModel().state.value)

    @Test
    fun `monitor connectivity status`() = runTest {
        val connectivity = MutableStateFlow<ConnectivityStatus?>(null)

        val viewModel = viewModel(connectivity = connectivity.filterNotNull())

        viewModel.init()

        viewModel.state.test {
            skipItems(1)

            connectivity.update { ConnectivityStatus.Available }
            assertEquals(
                ConnectivityStatus.Available,
                awaitItem().connectivityStatus,
            )

            connectivity.update { ConnectivityStatus.Unavailable }
            assertEquals(
                ConnectivityStatus.Unavailable,
                awaitItem().connectivityStatus,
            )
        }
    }

    @Test
    fun `observe tickers`() = runTest {
        val tickers = randomTickers.toImmutableList()
        val viewModel = viewModel(tickers = tickers.right())

        viewModel.init()

        viewModel.state.test {
            assertEquals(
                TradingState(tickers, ConnectivityStatus.Available),
                awaitItem(),
            )
        }
    }

    @Test
    fun `observe tickers error`() = runTest {
        val viewModel = viewModel(tickers = Unit.left())

        viewModel.init()

        viewModel.state.test {
            assertEquals(
                TradingState(
                    connectivityStatus = ConnectivityStatus.Available,
                    allTickers = persistentListOf(),
                    error = Unit,
                ),
                awaitItem(),
            )
        }
    }

    @Test
    fun `when error occurs still show stale data`() = runTest {
        val tickers = randomTickers.toImmutableList()
        val tickerFlow = MutableStateFlow<Either<Unit, ImmutableList<Ticker>>?>(null)
        val viewModel = viewModel(tickersFlow = tickerFlow.filterNotNull())

        viewModel.init()

        viewModel.state.test {
            skipItems(1)

            tickerFlow.update { tickers.right() }
            assertEquals(
                TradingState(
                    connectivityStatus = ConnectivityStatus.Available,
                    allTickers = tickers,
                    error = null,
                ),
                awaitItem(),
            )

            tickerFlow.update { Unit.left() }
            assertEquals(
                TradingState(
                    connectivityStatus = ConnectivityStatus.Available,
                    allTickers = tickers,
                    error = Unit,
                ),
                awaitItem(),
            )
        }
    }

    @Test
    fun `error is null when tickers are successfully observed`() = runTest {
        val tickers = randomTickers.toImmutableList()
        val tickerFlow = MutableStateFlow<Either<Unit, ImmutableList<Ticker>>?>(null)
        val viewModel = viewModel(tickersFlow = tickerFlow.filterNotNull())

        viewModel.init()

        viewModel.state.test {
            skipItems(1)

            tickerFlow.update { Unit.left() }
            assertEquals(
                TradingState(
                    connectivityStatus = ConnectivityStatus.Available,
                    allTickers = persistentListOf(),
                    error = Unit,
                ),
                awaitItem(),
            )

            tickerFlow.update { tickers.right() }
            assertEquals(
                TradingState(
                    connectivityStatus = ConnectivityStatus.Available,
                    allTickers = tickers,
                    error = null,
                ),
                awaitItem(),
            )
        }
    }

    @Test
    fun `success error success`() = runTest {
        val tickers = randomTickers.toImmutableList()
        val tickerFlow = MutableStateFlow<Either<Unit, ImmutableList<Ticker>>?>(null)
        val viewModel = viewModel(tickersFlow = tickerFlow.filterNotNull())

        viewModel.init()

        viewModel.state.test {
            skipItems(1)

            tickerFlow.update { tickers.right() }
            assertEquals(
                TradingState(
                    connectivityStatus = ConnectivityStatus.Available,
                    allTickers = tickers,
                    error = null,
                ),
                awaitItem(),
            )

            tickerFlow.update { Unit.left() }
            assertEquals(
                TradingState(
                    connectivityStatus = ConnectivityStatus.Available,
                    allTickers = tickers,
                    error = Unit,
                ),
                awaitItem(),
            )

            val newTickers = randomTickers.toImmutableList()
            tickerFlow.update { newTickers.right() }
            assertEquals(
                TradingState(
                    connectivityStatus = ConnectivityStatus.Available,
                    allTickers = newTickers,
                    error = null
                ),
                awaitItem(),
            )
        }
    }

    @Test
    fun `search tickers`() = runTest {
        val tickers = randomTickers.toImmutableList()
        val query = tickers.first().ticker
        val viewModel = viewModel(tickers = tickers.right())

        viewModel.init()

        viewModel.state.test {
            skipItems(1)

            assertSearched(viewModel, query, tickers)
        }
    }

    @Test
    fun `search multiple times`() = runTest {
        val tickers = randomTickers.toImmutableList()
        val viewModel = viewModel(tickers = tickers.right())

        viewModel.init()

        viewModel.state.test {
            skipItems(1)

            assertSearched(viewModel, "a", tickers)
            assertSearched(viewModel, "b", tickers)
            assertSearched(viewModel, "c", tickers)
        }
    }

    private suspend fun TurbineTestContext<TradingState>.assertSearched(
        viewModel: TradingViewModel,
        query: String,
        tickers: ImmutableList<Ticker>,
    ) {
        viewModel.search(query)

        assertEquals(
            tickers.search(query),
            awaitItem().tickers,
        )
    }

    @Test
    fun `restore query after process death`() = runTest {
        val tickers = randomTickers.toImmutableList()
        val query = tickers.first().ticker
        val viewModel = viewModel(tickers = tickers.right())

        viewModel.init()

        viewModel.state.test {
            skipItems(1)

            viewModel.search(query)

            assertEquals(tickers.search(query), awaitItem().tickers)
        }
    }

    private val tickersAscendingPrice =
        persistentListOf(
            Ticker(ticker = "", price = "8.32 USD", change24hPercentage = 1f),
            Ticker(ticker = "", price = "20.86 USD", change24hPercentage = 1f),
            Ticker(ticker = "", price = "100.10 USD", change24hPercentage = 1f),
        )
    private val tickersDescendingPrice = tickersAscendingPrice.reversed().toImmutableList()

    private val tickersAscendingName =
        persistentListOf(
            Ticker(ticker = "a", price = "1 USD", change24hPercentage = 1f),
            Ticker(ticker = "b", price = "2 USD", change24hPercentage = 1f),
            Ticker(ticker = "c", price = "3 USD", change24hPercentage = 1f),
        )
    private val tickersDescendingName = tickersAscendingName.reversed().toImmutableList()

    @Test
    fun `initially tickers are sorted by descending price`() = runTest {
        val viewModel = viewModel(tickers = tickersAscendingPrice.right())

        viewModel.init()

        viewModel.state.test {
            assertEquals(tickersDescendingPrice, awaitItem().tickers)
        }
    }

    @Test
    fun `calling sort by price on initial tickers sorts them by ascending price`() = runTest {
        val viewModel = viewModel(tickers = tickersAscendingPrice.right())

        viewModel.init()
        viewModel.sortByPrice()

        viewModel.state.test {
            assertEquals(tickersAscendingPrice, awaitItem().tickers)
        }
    }

    @Test
    fun `calling sort by price after sorting initial tickers sorts them by descending price`() =
        runTest {
            val viewModel = viewModel(tickers = tickersAscendingPrice.right())

            viewModel.init()
            viewModel.sortByPrice()
            viewModel.sortByPrice()

            viewModel.state.test {
                assertEquals(tickersDescendingPrice, awaitItem().tickers)
            }
        }

    @Test
    fun `calling sort by name on initial tickers sorts them by ascending name`() = runTest {
        val viewModel = viewModel(tickers = tickersAscendingName.right())

        viewModel.init()
        viewModel.sortByName()

        viewModel.state.test {
            assertEquals(tickersAscendingName, awaitItem().tickers)
        }
    }

    @Test
    fun `calling sort by name after sorting initial tickers by name sorts them by descending name`() =
        runTest {
            val viewModel = viewModel(tickers = tickersAscendingName.right())

            viewModel.init()
            viewModel.sortByName()
            viewModel.sortByName()

            viewModel.state.test {
                assertEquals(tickersDescendingName, awaitItem().tickers)
            }
        }
}