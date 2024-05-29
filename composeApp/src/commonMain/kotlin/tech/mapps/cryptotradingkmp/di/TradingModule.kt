package tech.mapps.cryptotradingkmp.di

import androidx.lifecycle.SavedStateHandle
import org.koin.dsl.module
import tech.mapps.cryptotradingkmp.TradingViewModel

val tradingModule = module {
    single { TradingViewModel(SavedStateHandle(), get(), get(), get()) }
}