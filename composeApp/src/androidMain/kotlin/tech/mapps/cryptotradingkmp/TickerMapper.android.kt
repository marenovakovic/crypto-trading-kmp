package tech.mapps.cryptotradingkmp

import java.math.RoundingMode

actual fun Float.roundToTwoDecimalsWithCurrency(currency: String): String =
    roundToTwoDecimals() + " $currency"

actual fun Float.roundToTwoDecimals(): String =
    toBigDecimal().setScale(2, RoundingMode.HALF_DOWN).toString()

actual val Float.percentage: String
    get() = "%.2f".format(this * 100)