package tech.mapps.cryptotradingkmp

import java.math.RoundingMode

internal actual fun roundToTwoDecimals(f: Float) =
    f.toBigDecimal().setScale(2, RoundingMode.HALF_DOWN).toFloat()

internal actual fun roundToTwoDecimalsWithCurrency(f: Float, currency: String) =
    roundToTwoDecimals(f).toString() + " $currency"

internal actual fun percentage(f: Float) =
    "%.2f".format(f * 100).toFloat()