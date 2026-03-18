package io.github.kdroidfilter.nucleus.ui.apple.macos.util

import java.time.DayOfWeek
import java.time.Month as JavaMonth
import java.time.format.TextStyle
import java.util.Locale

internal actual fun localizedMonthNames(): List<String> =
    (1..12).map { JavaMonth.of(it).getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()) }

internal actual fun localizedMonthShortNames(): List<String> =
    (1..12).map { JavaMonth.of(it).getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault()) }

internal actual fun localizedWeekdayShortNames(): List<String> {
    val order = listOf(
        DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY,
    )
    return order.map { it.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault()) }
}
