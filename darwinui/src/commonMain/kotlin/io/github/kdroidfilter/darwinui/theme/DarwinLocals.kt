package io.github.kdroidfilter.darwinui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

/**
 * CompositionLocal for providing a default [TextStyle] to descendant composables.
 *
 * Replaces `androidx.compose.material3.LocalTextStyle`.
 */
val LocalDarwinTextStyle = compositionLocalOf { TextStyle.Default }

/**
 * CompositionLocal for providing a default content [Color] to descendant composables.
 *
 * Replaces `androidx.compose.material3.LocalContentColor`.
 */
val LocalDarwinContentColor = compositionLocalOf { Color.Unspecified }
