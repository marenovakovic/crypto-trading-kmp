package tech.mapps.cryptotradingkmp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import tech.mapps.cryptotradingkmp.di.androidModule
import tech.mapps.cryptotradingkmp.di.appModule
import tech.mapps.cryptotradingkmp.di.tradingModule
import tech.mapps.cryptotradingkmp.http.httpModule

class AndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AndroidApp)
            androidLogger()
            modules(androidModule, appModule, httpModule, tradingModule)
        }
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            App()
        }
    }
}