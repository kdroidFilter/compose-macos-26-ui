package io.github.kdroidfilter.darwinui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Darwin UI Shape System
 * Based on --radius: 0.75rem (12dp) with calculated variants.
 */
@Immutable
data class DarwinShapes(
    /** 4dp — Small elements: checkboxes, small badges */
    val extraSmall: Shape = RoundedCornerShape(4.dp),

    /** 8dp — calc(var(--radius) - 4px): Buttons small, inputs small */
    val small: Shape = RoundedCornerShape(8.dp),

    /** 10dp — calc(var(--radius) - 2px): Default inputs, selects */
    val medium: Shape = RoundedCornerShape(10.dp),

    /** 12dp — var(--radius): Cards, dialogs, default buttons */
    val large: Shape = RoundedCornerShape(12.dp),

    /** 16dp — calc(var(--radius) + 4px): Large cards, modals */
    val extraLarge: Shape = RoundedCornerShape(16.dp),

    /** 20dp — calc(var(--radius) + 8px): Extra large containers */
    val extraExtraLarge: Shape = RoundedCornerShape(20.dp),

    /** Full rounded: pills, avatar, circular buttons */
    val full: Shape = RoundedCornerShape(50),
)

val LocalDarwinShapes = staticCompositionLocalOf { DarwinShapes() }
