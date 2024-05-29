package tech.mapps.cryptotradingkmp

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter

internal actual fun roundToTwoDecimals(f: Float): Float = 1f
//    NSNumberFormatter().run {
//        minimumFractionDigits = 2u
//        maximumFractionDigits = 2u
//        numberStyle = 1u
//        stringFromNumber(NSNumber(this@roundToTwoDecimals))
//    }
//    toBigDecimal().setScale(2, RoundingMode.HALF_DOWN)

internal actual fun roundToTwoDecimalsWithCurrency(f: Float, currency: String): String =
    roundToTwoDecimals(f).toString() + " $currency"

internal actual fun percentage(f: Float) =
    NSNumberFormatter().run {
        minimumFractionDigits = 2u
        maximumFractionDigits = 2u
        numberStyle = 1u
        stringFromNumber(NSNumber(f))
    }!!.toFloat()