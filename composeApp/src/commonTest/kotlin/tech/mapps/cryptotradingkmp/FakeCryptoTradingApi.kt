package tech.mapps.cryptotradingkmp

import tech.mapps.cryptotradingkmp.api.CryptoTradingApi

object FailingCryptoTradingApi : CryptoTradingApi {
    override suspend fun getTickers() = throw Throwable()
}

class FakeCryptoTradingApi : CryptoTradingApi {
    val tickerDtos = randomTickerDtos

    override suspend fun getTickers() = tickerDtos
}