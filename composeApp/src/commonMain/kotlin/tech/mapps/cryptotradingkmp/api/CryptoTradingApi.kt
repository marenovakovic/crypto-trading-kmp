package tech.mapps.cryptotradingkmp.api

fun interface CryptoTradingApi {
    suspend fun getTickers(): List<TickerDto>
}