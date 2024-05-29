package tech.mapps.cryptotradingkmp

data class Ticker(
    val ticker: String,
    val price: String,
    val change24hPercentage: Float,
)