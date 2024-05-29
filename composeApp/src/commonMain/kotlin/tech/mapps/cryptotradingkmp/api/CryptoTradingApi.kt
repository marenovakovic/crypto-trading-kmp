package tech.mapps.cryptotradingkmp.api

interface CryptoTradingApi {
    suspend fun getTickers(): List<TickerDto>
}