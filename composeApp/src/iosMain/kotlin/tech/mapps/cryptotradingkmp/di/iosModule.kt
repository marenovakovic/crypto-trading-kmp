package tech.mapps.cryptotradingkmp.di

import org.koin.dsl.module
import tech.mapps.cryptotradingkmp.ConnectivityStatusTrackerFactory
import tech.mapps.cryptotradingkmp.IosConnectivityStatusTrackerFactory

internal val iosModule = module {
    single<ConnectivityStatusTrackerFactory> { IosConnectivityStatusTrackerFactory() }
}