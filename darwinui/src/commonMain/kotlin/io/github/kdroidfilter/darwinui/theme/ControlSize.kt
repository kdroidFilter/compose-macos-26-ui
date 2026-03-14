package io.github.kdroidfilter.darwinui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle

/**
 * Control size presets inspired by SwiftUI's `.controlSize()`.
 *
 * Propagated via [LocalControlSize] and the [ControlSize] wrapper composable.
 * Components read the current size and delegate to their `Metrics.*For(controlSize)` resolvers.
 */
enum class ControlSize {
    Mini,
    Small,
    Regular,
    Large,
    ExtraLarge,
}

val LocalControlSize = compositionLocalOf { ControlSize.Regular }

/**
 * Propagates a [ControlSize] to all descendant Darwin UI components.
 *
 * Equivalent to SwiftUI's `.controlSize()` modifier.
 *
 * ```kotlin
 * ControlSize(ControlSize.Small) {
 *     Button(...)   // small
 *     TextField(...) // small
 * }
 * ```
 */
@Composable
fun ControlSize(size: ControlSize, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalControlSize provides size) { content() }
}

/**
 * Returns the appropriate label [TextStyle] for this control size.
 */
@Composable
@ReadOnlyComposable
fun ControlSize.labelStyle(): TextStyle = when (this) {
    ControlSize.Mini -> DarwinTheme.typography.caption2
    ControlSize.Small -> DarwinTheme.typography.caption1
    ControlSize.Regular -> DarwinTheme.typography.footnote
    ControlSize.Large -> DarwinTheme.typography.subheadline
    ControlSize.ExtraLarge -> DarwinTheme.typography.callout
}
