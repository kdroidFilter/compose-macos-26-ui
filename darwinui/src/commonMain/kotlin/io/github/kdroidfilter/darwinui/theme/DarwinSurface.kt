package io.github.kdroidfilter.darwinui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

/**
 * Surface appearance mode for Darwin UI components.
 *
 * macOS 26 defines two surface variants that affect component rendering:
 * - [ContentArea]: Standard opaque surface — used in regular content areas.
 * - [OverGlass]: Translucent/glass surface — used on vibrancy/blur backgrounds.
 *   Components use additional translucent fill layers for depth on glass.
 *
 * Propagated via [LocalDarwinSurface] and the [DarwinSurface] wrapper composable.
 * Components read the current surface and adapt their fills/alphas accordingly.
 */
enum class DarwinSurface {
    ContentArea,
    OverGlass,
}

val LocalDarwinSurface = compositionLocalOf { DarwinSurface.ContentArea }

/**
 * Propagates a [DarwinSurface] to all descendant Darwin UI components.
 *
 * ```kotlin
 * DarwinSurface(DarwinSurface.OverGlass) {
 *     Switch(...)    // renders with over-glass appearance
 *     Checkbox(...)  // renders with over-glass appearance
 * }
 * ```
 */
@Composable
fun DarwinSurface(surface: DarwinSurface, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalDarwinSurface provides surface) { content() }
}
