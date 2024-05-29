package tech.mapps.cryptotradingkmp

import arrow.core.Either
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import tech.mapps.cryptotradingkmp.api.CryptoTradingApi
import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.Duration.Companion.seconds

fun interface ObserveTickers : () -> Flow<Either<Unit, ImmutableList<Ticker>>>

class ObserveTickersImpl(
    private val api: CryptoTradingApi,
    private val refreshInterval: Duration = 5.seconds,
) : ObserveTickers {
    override fun invoke(): Flow<Either<Unit, ImmutableList<Ticker>>> =
        tickerFlow(period = refreshInterval, initialDelay = ZERO)
            .mapLatest { getTickers() }

    private suspend fun getTickers() =
        Either
            .catch { api.getTickers() }
            .map { dtos -> dtos.map { it.toTicker() }.toImmutableList() }
            .mapLeft {}
}