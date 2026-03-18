package io.github.kdroidfilter.nucleus.ui.apple.macos.util

import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale

internal actual fun localizedMonthNames(): List<String> {
    val formatter = NSDateFormatter().apply { locale = NSLocale.currentLocale() }
    @Suppress("UNCHECKED_CAST")
    return formatter.standaloneMonthSymbols as List<String>
}

internal actual fun localizedMonthShortNames(): List<String> {
    val formatter = NSDateFormatter().apply { locale = NSLocale.currentLocale() }
    @Suppress("UNCHECKED_CAST")
    return formatter.shortStandaloneMonthSymbols as List<String>
}

internal actual fun localizedWeekdayShortNames(): List<String> {
    val formatter = NSDateFormatter().apply { locale = NSLocale.currentLocale() }
    @Suppress("UNCHECKED_CAST")
    // shortStandaloneWeekdaySymbols returns Sunday-first: ["Sun", "Mon", ..., "Sat"]
    return formatter.shortStandaloneWeekdaySymbols as List<String>
}
