package tech.mapps.cryptotradingkmp.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import tech.mapps.cryptotradingkmp.AndroidConnectivityStatusTrackerFactory
import tech.mapps.cryptotradingkmp.ConnectivityStatusTrackerFactory

val androidModule = module {
    single<ConnectivityStatusTrackerFactory> {
        AndroidConnectivityStatusTrackerFactory(androidApplication())
    }
}