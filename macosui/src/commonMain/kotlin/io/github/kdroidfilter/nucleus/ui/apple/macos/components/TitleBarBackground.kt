package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Background style for a [TitleBar].
 *
 * Controls whether the title bar renders with frosted glass blur,
 * an opaque background, or a fully transparent background.
 */
sealed class TitleBarBackground {
    /** Glass when inside a scaffold, opaque otherwise (default). */
    data object Automatic : TitleBarBackground()

    /** Always opaque — disables glass blur even inside a scaffold. */
    data object Visible : TitleBarBackground()

    /** Fully transparent — no background, no border. Buttons remain interactive. */
    data object Hidden : TitleBarBackground()

    /**
     * Custom glass material with explicit parameters.
     *
     * @param frost Blur radius for the frost effect.
     * @param saturation Saturation multiplier.
     * @param tint Optional tint color applied over the blurred background.
     */
    data class Material(
        val frost: Dp = 16.dp,
        val saturation: Float = 1.05f,
        val tint: Color? = null,
    ) : TitleBarBackground()
}
