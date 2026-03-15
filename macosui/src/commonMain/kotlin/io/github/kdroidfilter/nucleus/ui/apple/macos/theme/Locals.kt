package io.github.kdroidfilter.nucleus.ui.apple.macos.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

/**
 * CompositionLocal for providing a default [TextStyle] to descendant composables.
 *
 * Replaces `androidx.compose.material3.LocalTextStyle`.
 */
val LocalTextStyle = compositionLocalOf { TextStyle.Default }

/**
 * CompositionLocal for providing a default content [Color] to descendant composables.
 *
 * Replaces `androidx.compose.material3.LocalContentColor`.
 */
val LocalContentColor = compositionLocalOf { Color.Unspecified }

/**
 * Whether the host window is currently active (foreground).
 *
 * When `false`, controls render in their "inactive" appearance — macOS grays
 * out controls in background windows. Defaults to `true`.
 *
 * Platform integrations should provide this from the actual window focus state.
 */
val LocalWindowActive = compositionLocalOf { true }
