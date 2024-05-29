import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController
import tech.mapps.cryptotradingkmp.App
import tech.mapps.cryptotradingkmp.di.appModule
import tech.mapps.cryptotradingkmp.di.iosModule
import tech.mapps.cryptotradingkmp.di.tradingModule
import tech.mapps.cryptotradingkmp.http.httpModule

fun MainViewController(): UIViewController {
    startKoin {
        modules(iosModule, appModule, httpModule, tradingModule)
    }
    return ComposeUIViewController { App() }
}
