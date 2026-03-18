package io.github.kdroidfilter.nucleus.ui.apple.macos.util

/** Returns the 12 full month names in the system locale, in calendar order (January first). */
internal expect fun localizedMonthNames(): List<String>

/** Returns the 12 abbreviated month names in the system locale, in calendar order (Jan first). */
internal expect fun localizedMonthShortNames(): List<String>

/**
 * Returns the 7 short weekday names in the system locale, starting from Sunday.
 * Suitable for use as column headers in a calendar grid.
 */
internal expect fun localizedWeekdayShortNames(): List<String>
