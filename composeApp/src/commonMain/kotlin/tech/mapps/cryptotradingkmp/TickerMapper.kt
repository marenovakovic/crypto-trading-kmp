package tech.mapps.cryptotradingkmp

import tech.mapps.cryptotradingkmp.api.TickerDto

fun TickerDto.toTicker() =
    Ticker(
        ticker = symbol.tickerSymbol,
        price = lastTradePrice.roundToTwoDecimalsWithCurrency(),
        change24hPercentage = dailyChangePercentage.percentage,
    )

private val String.tickerSymbol
    get() = trim('"').split(currency).first().drop(1).trimEnd(':')

private const val currency = "USD"

expect fun Float.roundToTwoDecimalsWithCurrency(currency: String = "USD"): String
//    roundToTwoDecimals().toString() + " $currency"

expect fun Float.roundToTwoDecimals(): Float
//    toBigDecimal().setScale(2, RoundingMode.HALF_DOWN)

expect val Float.percentage: Float
//    get() = this
//    get() = "%.2f".format(this * 100).toFloat()