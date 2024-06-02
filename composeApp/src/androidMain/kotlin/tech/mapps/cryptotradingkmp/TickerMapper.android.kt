package tech.mapps.cryptotradingkmp

import java.math.RoundingMode

actual fun Float.roundToTwoDecimalsWithCurrency(currency: String): String =
    roundToTwoDecimals().toString() + " $currency"

actual fun Float.roundToTwoDecimals(): Float =
    toBigDecimal().setScale(2, RoundingMode.HALF_DOWN).toFloat()

actual val Float.percentage: Float
    get() = "%.2f".format(this * 100).toFloat()