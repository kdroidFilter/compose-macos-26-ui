package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SpringPreset
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosSpring

// ===========================================================================
// ColorWellColors
// ===========================================================================

@Immutable
class ColorWellColors(
    val borderColor: Color,
    val disabledBorderColor: Color,
) {
    fun copy(
        borderColor: Color = this.borderColor,
        disabledBorderColor: Color = this.disabledBorderColor,
    ) = ColorWellColors(borderColor, disabledBorderColor)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ColorWellColors) return false
        return borderColor == other.borderColor && disabledBorderColor == other.disabledBorderColor
    }

    override fun hashCode() = 31 * borderColor.hashCode() + disabledBorderColor.hashCode()
}

// ===========================================================================
// ColorWellDefaults
// ===========================================================================

object ColorWellDefaults {
    val Size = 28.dp

    @Composable
    fun colors(
        borderColor: Color = MacosTheme.colorScheme.borderStrong.copy(alpha = 0.35f),
        disabledBorderColor: Color = MacosTheme.colorScheme.borderStrong.copy(alpha = 0.15f),
    ) = ColorWellColors(borderColor, disabledBorderColor)
}

// ===========================================================================
// ColorWell
// ===========================================================================

/**
 * A macOS-style color well — a circular control that displays the currently
 * selected color. When no color is provided, the hue wheel is shown as a
 * visual hint for the color picker affordance.
 *
 * @param color The currently selected color, or null to show the hue wheel.
 * @param onClick Called when the user taps the well (e.g., to open a picker).
 */
@Composable
fun ColorWell(
    color: Color?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = ColorWellDefaults.Size,
    colors: ColorWellColors = ColorWellDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = when {
            isPressed && enabled -> 0.92f
            isHovered && enabled -> 1.05f
            else -> 1f
        },
        animationSpec = macosSpring(SpringPreset.Snappy),
        label = "colorWellScale",
    )

    val borderColor = if (enabled) colors.borderColor else colors.disabledBorderColor

    Canvas(
        modifier = modifier
            .size(size)
            .scale(scale)
            .alpha(if (enabled) 1f else 0.4f)
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onClick,
            ),
    ) {
        val radius = this.size.minDimension / 2f
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val arcSize = Size(radius * 2f, radius * 2f)
        val arcTopLeft = Offset(center.x - radius, center.y - radius)

        if (color == null) {
            // Draw the hue wheel: 360 arc slices, red at top going clockwise
            for (i in 0..359) {
                val hue = i.toFloat()
                drawArc(
                    color = Color.hsv(hue, 1f, 1f),
                    startAngle = hue - 90f,
                    sweepAngle = 1.5f,
                    useCenter = true,
                    topLeft = arcTopLeft,
                    size = arcSize,
                )
            }
            // White radial fade from center (matches the SVG's radialGradient overlay)
            drawCircle(
                brush = Brush.radialGradient(
                    colorStops = arrayOf(
                        0.04f to Color.White,
                        0.41f to Color.White.copy(alpha = 0.66f),
                        0.74f to Color.White.copy(alpha = 0.33f),
                        0.96f to Color.Transparent,
                    ),
                    center = center,
                    radius = radius,
                ),
                radius = radius,
                center = center,
            )
        } else {
            drawCircle(color = color, radius = radius, center = center)
        }

        // Subtle border ring
        drawCircle(
            color = borderColor,
            radius = radius - 0.5f,
            center = center,
            style = Stroke(width = 1.dp.toPx()),
        )
    }
}
