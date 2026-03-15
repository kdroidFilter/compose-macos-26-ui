package io.github.kdroidfilter.nucleus.ui.apple.macos.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * macOS UI shape system — mirrors Material3's Shapes class with 5 standard levels.
 */
@Immutable
data class Shapes(
    /** 4dp — small elements: badges, indicators */
    val extraSmall: Shape = RoundedCornerShape(4.dp),

    /** 8dp — buttons small, inputs small */
    val small: Shape = RoundedCornerShape(8.dp),

    /** 10dp — default inputs, selects */
    val medium: Shape = RoundedCornerShape(10.dp),

    /** 12dp — cards, dialogs, default buttons */
    val large: Shape = RoundedCornerShape(12.dp),

    /** 16dp — large cards, modals */
    val extraLarge: Shape = RoundedCornerShape(16.dp),

    /** Full rounded: pills, avatar, circular — macOS extension (not in M3). */
    val full: Shape = RoundedCornerShape(50),
)

val LocalShapes = staticCompositionLocalOf { Shapes() }
