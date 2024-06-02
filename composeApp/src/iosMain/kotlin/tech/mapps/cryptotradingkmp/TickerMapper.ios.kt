package tech.mapps.cryptotradingkmp

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter

private val numberFormatter = NSNumberFormatter().apply {
    maximumFractionDigits = 2u
    minimumFractionDigits = 2u
}

actual fun Float.roundToTwoDecimalsWithCurrency(currency: String): String =
    roundToTwoDecimals().toString() + " $currency"

actual fun Float.roundToTwoDecimals(): Float =
    numberFormatter.stringFromNumber(NSNumber(this))!!.toFloat()

actual val Float.percentage: Float
    get() = numberFormatter.stringFromNumber(NSNumber(this * 100))!!.toFloat()