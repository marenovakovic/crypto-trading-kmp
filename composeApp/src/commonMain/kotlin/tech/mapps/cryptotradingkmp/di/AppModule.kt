package tech.mapps.cryptotradingkmp.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module
import tech.mapps.cryptotradingkmp.ConnectivityStatusTracker
import tech.mapps.cryptotradingkmp.ConnectivityStatusTrackerFactory
import tech.mapps.cryptotradingkmp.ObserveTickers
import tech.mapps.cryptotradingkmp.ObserveTickersImpl
import tech.mapps.cryptotradingkmp.api.BitfinexTradingApi
import tech.mapps.cryptotradingkmp.api.CryptoTradingApi

val appModule = module {
    factory { CoroutineScope(Dispatchers.Main.immediate + SupervisorJob()) }
    single<ConnectivityStatusTracker> { get<ConnectivityStatusTrackerFactory>().create() }
    single<CryptoTradingApi> { BitfinexTradingApi(get()) }
    single<ObserveTickers> { ObserveTickersImpl(get()) }
}