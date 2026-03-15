package io.github.kdroidfilter.nucleus.ui.apple.macos.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

/**
 * Surface appearance mode for macOS UI components.
 *
 * macOS 26 defines two surface variants that affect component rendering:
 * - [ContentArea]: Standard opaque surface — used in regular content areas.
 * - [OverGlass]: Translucent/glass surface — used on vibrancy/blur backgrounds.
 *   Components use additional translucent fill layers for depth on glass.
 *
 * Propagated via [LocalSurface] and the [Surface] wrapper composable.
 * Components read the current surface and adapt their fills/alphas accordingly.
 */
enum class Surface {
    ContentArea,
    OverGlass,
}

val LocalSurface = compositionLocalOf { Surface.ContentArea }

/**
 * Propagates a [Surface] to all descendant macOS UI components.
 *
 * ```kotlin
 * Surface(Surface.OverGlass) {
 *     Switch(...)    // renders with over-glass appearance
 *     Checkbox(...)  // renders with over-glass appearance
 * }
 * ```
 */
@Composable
fun Surface(surface: Surface, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalSurface provides surface) { content() }
}
