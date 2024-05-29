package tech.mapps.cryptotradingkmp

internal expect fun roundToTwoDecimals(f: Float): Float

internal expect fun roundToTwoDecimalsWithCurrency(f: Float, currency: String = "USD"): String

internal expect fun percentage(f: Float): Float