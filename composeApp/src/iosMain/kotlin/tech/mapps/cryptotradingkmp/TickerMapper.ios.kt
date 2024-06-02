package tech.mapps.cryptotradingkmp

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter

private val numberFormatter = NSNumberFormatter().apply {
    maximumFractionDigits = 2u
    minimumFractionDigits = 2u
    decimalSeparator = "."
}

actual fun Float.roundToTwoDecimalsWithCurrency(currency: String): String =
    roundToTwoDecimals() + " $currency"

actual fun Float.roundToTwoDecimals(): String =
    numberFormatter.stringFromNumber(NSNumber(this))!!

actual val Float.percentage: String
    get() = numberFormatter.stringFromNumber(NSNumber(this * 100))!!