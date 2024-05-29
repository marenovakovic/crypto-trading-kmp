package tech.mapps.cryptotradingkmp.http

import org.koin.dsl.module

val httpModule = module {
    single { httpClient }
}