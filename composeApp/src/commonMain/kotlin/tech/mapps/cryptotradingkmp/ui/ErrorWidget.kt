package tech.mapps.cryptotradingkmp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import crypto_trading_kmp.composeapp.generated.resources.Res
import crypto_trading_kmp.composeapp.generated.resources.error_occurred
import crypto_trading_kmp.composeapp.generated.resources.internet_connection_is_unavailable
import crypto_trading_kmp.composeapp.generated.resources.you_are_seeing_old_data
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tech.mapps.cryptotradingkmp.ConnectivityStatus
import tech.mapps.cryptotradingkmp.Ticker
import tech.mapps.cryptotradingkmp.TradingState
import tech.mapps.cryptotradingkmp.theme.AppTheme

@Composable
internal fun ErrorWidget(
    tradingState: TradingState,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.error),
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides LocalTextStyle.current.copy(color = Color.White)
        ) {
            when {
                tradingState.connectivityStatus == ConnectivityStatus.Unavailable ->
                    Text(text = stringResource(Res.string.internet_connection_is_unavailable))
                tradingState.error != null ->
                    Text(text = stringResource(Res.string.error_occurred))
            }
            if (tradingState.tickers.isNotEmpty())
                Text(text = stringResource(Res.string.you_are_seeing_old_data))
        }
    }
}

@Preview
@Composable
private fun ErrorWidgetNoInternetPreview() {
    AppTheme {
        ErrorWidget(
            tradingState = TradingState(
                connectivityStatus = ConnectivityStatus.Unavailable,
            ),
        )
    }
}

@Preview
@Composable
private fun ErrorWidgetNoInternetOldDataPreview() {
    val tickers = persistentListOf(
        Ticker(
            ticker = "BTC",
            price = "69362",
            change24hPercentage = 1.9f,
        ),
    )
    AppTheme {
        ErrorWidget(
            tradingState = TradingState(
                connectivityStatus = ConnectivityStatus.Unavailable,
                allTickers = tickers,
            ),
        )
    }
}

@Preview
@Composable
private fun ErrorWidgetGenericErrorPreview() {
    AppTheme {
        ErrorWidget(
            tradingState = TradingState(
                connectivityStatus = ConnectivityStatus.Available,
                error = Unit,
            ),
        )
    }
}

@Preview
@Composable
private fun ErrorWidgetGenericErrorOldDataPreview() {
    val tickers = persistentListOf(
        Ticker(
            ticker = "BTC",
            price = "69362",
            change24hPercentage = 1.9f,
        ),
    )
    AppTheme {
        ErrorWidget(
            tradingState = TradingState(
                connectivityStatus = ConnectivityStatus.Available,
                allTickers = tickers,
                error = Unit,
            ),
        )
    }
}