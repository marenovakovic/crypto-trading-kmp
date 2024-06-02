package tech.mapps.cryptotradingkmp

import tech.mapps.cryptotradingkmp.api.TickerDto

fun TickerDto.toTicker() =
    Ticker(
        ticker = symbol.tickerSymbol,
        price = lastTradePrice.roundToTwoDecimalsWithCurrency(),
        change24hPercentage = dailyChangePercentage.percentage.toFloatOrNull() ?: 0f,
    )

private val String.tickerSymbol
    get() = trim('"').split(currency).first().drop(1).trimEnd(':')

private const val currency = "USD"

expect fun Float.roundToTwoDecimalsWithCurrency(currency: String = "USD"): String
expect fun Float.roundToTwoDecimals(): String
expect val Float.percentage: String