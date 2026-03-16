package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Horizontal inset for the native window controls (traffic lights on macOS).
 *
 * When provided by a platform window composable (e.g. `MacosWindow`), the
 * [Scaffold] uses this value to pad its built-in sidebar toggle so it doesn't
 * overlap with the native window controls.
 *
 * Defaults to [Dp.Unspecified] (no inset).
 */
val LocalWindowControlInset = compositionLocalOf { Dp.Unspecified }

/**
 * Callback to force re-application of native titlebar Auto Layout constraints.
 *
 * Provided by `MacosWindow` when the JNI bridge is available. The [Scaffold]
 * calls this after sidebar show/hide animations complete to work around macOS
 * occasionally invalidating the titlebar view hierarchy during content relayout.
 *
 * Defaults to `null` (no-op on non-macOS platforms or when JNI is unavailable).
 */
val LocalTitleBarRevalidate = compositionLocalOf<(() -> Unit)?> { null }
