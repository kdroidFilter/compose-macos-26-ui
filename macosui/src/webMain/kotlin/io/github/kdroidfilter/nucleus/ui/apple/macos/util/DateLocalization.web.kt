package io.github.kdroidfilter.nucleus.ui.apple.macos.util

// Locale-aware formatting via the browser Intl API requires platform-specific JS interop
// (js() blocks for Kotlin/JS, @JsFun for Kotlin/WASM) that cannot be shared in a common
// webMain source set. English names are returned as a fallback.
// TODO: split into jsMain / wasmJsMain source sets when the build is ready for it.

internal actual fun localizedMonthNames(): List<String> = listOf(
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December",
)

internal actual fun localizedMonthShortNames(): List<String> = listOf(
    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
)

internal actual fun localizedWeekdayShortNames(): List<String> =
    listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
