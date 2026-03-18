package io.github.kdroidfilter.nucleus.ui.apple.macos.util

import java.util.Calendar
import java.util.Locale

// java.time.Month.getDisplayName requires API 26; min SDK is 24, so we use Calendar
// which is available on all Android versions and returns standalone forms correctly.

internal actual fun localizedMonthNames(): List<String> =
    (0 until 12).map { month ->
        Calendar.getInstance().apply { set(Calendar.MONTH, month) }
            .getDisplayName(Calendar.MONTH, Calendar.LONG_STANDALONE, Locale.getDefault())
            ?: MONTH_NAMES_FALLBACK[month]
    }

internal actual fun localizedMonthShortNames(): List<String> =
    (0 until 12).map { month ->
        Calendar.getInstance().apply { set(Calendar.MONTH, month) }
            .getDisplayName(Calendar.MONTH, Calendar.SHORT_STANDALONE, Locale.getDefault())
            ?: MONTH_SHORT_NAMES_FALLBACK[month]
    }

internal actual fun localizedWeekdayShortNames(): List<String> =
    // Calendar.DAY_OF_WEEK: 1 = Sunday, 2 = Monday, …, 7 = Saturday
    (Calendar.SUNDAY..Calendar.SATURDAY).map { day ->
        Calendar.getInstance().apply { set(Calendar.DAY_OF_WEEK, day) }
            .getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT_STANDALONE, Locale.getDefault())
            ?: WEEKDAY_SHORT_NAMES_FALLBACK[day - 1]
    }

private val MONTH_NAMES_FALLBACK = listOf(
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December",
)

private val MONTH_SHORT_NAMES_FALLBACK = listOf(
    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
)

private val WEEKDAY_SHORT_NAMES_FALLBACK = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
