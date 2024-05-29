package tech.mapps.cryptotradingkmp

import tech.mapps.cryptotradingkmp.api.CryptoTradingApi
import tech.mapps.cryptotradingkmp.api.TickerDto

@Suppress("TestFunctionName")
fun StubCryptoTradingApi(getTickers: () -> List<TickerDto>) = object : CryptoTradingApi {
    override suspend fun getTickers() = getTickers()
}