package tech.mapps.cryptotradingkmp

import androidx.compose.runtime.Composable
import org.koin.compose.KoinApplication
import tech.mapps.cryptotradingkmp.di.appModule
import tech.mapps.cryptotradingkmp.di.tradingModule
import tech.mapps.cryptotradingkmp.http.httpModule
import tech.mapps.cryptotradingkmp.theme.AppTheme
import tech.mapps.cryptotradingkmp.ui.TradingScreen

@Composable
internal fun App() {
    AppTheme {
        TradingScreen()
    }
}