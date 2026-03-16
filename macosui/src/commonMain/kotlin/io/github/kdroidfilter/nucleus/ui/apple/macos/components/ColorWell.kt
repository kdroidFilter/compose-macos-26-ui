package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalSurface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Surface

// ===========================================================================
// ColorWellStyle
// ===========================================================================

/** Visual style of a [ColorWell]. */
enum class ColorWellStyle {
    /** Bordered swatch with padding inside a subtle container. No hover feedback. */
    Standard,

    /** Same layout as [Standard]. Hover and press reveal a chevron menu indicator. */
    Minimal,

    /** Full-bleed swatch paired with a color-wheel button on the trailing side. */
    Expanded,
}

// ===========================================================================
// ColorWellColors
// ===========================================================================

@Immutable
class ColorWellColors internal constructor(
    internal val containerSelectedIdle: Color,
    internal val containerUnselectedIdle: Color,
    internal val containerSelectedPressed: Color,
    internal val containerUnselectedPressed: Color,
    internal val containerSelectedDisabled: Color,
    internal val containerUnselectedDisabled: Color,
    internal val swatchBorder: Color,
    internal val buttonSelectedIdle: Color,
    internal val buttonUnselectedIdle: Color,
    internal val buttonPressed: Color,
    internal val buttonDisabled: Color,
) {
    internal fun containerColor(
        selected: Boolean,
        pressed: Boolean,
        enabled: Boolean,
    ): Color = when {
        !enabled -> if (selected) containerSelectedDisabled else containerUnselectedDisabled
        pressed -> if (selected) containerSelectedPressed else containerUnselectedPressed
        else -> if (selected) containerSelectedIdle else containerUnselectedIdle
    }

    internal fun buttonColor(
        selected: Boolean,
        pressed: Boolean,
        enabled: Boolean,
    ): Color = when {
        !enabled -> buttonDisabled
        pressed -> buttonPressed
        else -> if (selected) buttonSelectedIdle else buttonUnselectedIdle
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ColorWellColors) return false
        return containerSelectedIdle == other.containerSelectedIdle &&
            containerUnselectedIdle == other.containerUnselectedIdle &&
            containerSelectedPressed == other.containerSelectedPressed &&
            containerUnselectedPressed == other.containerUnselectedPressed &&
            containerSelectedDisabled == other.containerSelectedDisabled &&
            containerUnselectedDisabled == other.containerUnselectedDisabled &&
            swatchBorder == other.swatchBorder &&
            buttonSelectedIdle == other.buttonSelectedIdle &&
            buttonUnselectedIdle == other.buttonUnselectedIdle &&
            buttonPressed == other.buttonPressed &&
            buttonDisabled == other.buttonDisabled
    }

    override fun hashCode(): Int {
        var result = containerSelectedIdle.hashCode()
        result = 31 * result + containerUnselectedIdle.hashCode()
        result = 31 * result + containerSelectedPressed.hashCode()
        result = 31 * result + containerUnselectedPressed.hashCode()
        result = 31 * result + containerSelectedDisabled.hashCode()
        result = 31 * result + containerUnselectedDisabled.hashCode()
        result = 31 * result + swatchBorder.hashCode()
        result = 31 * result + buttonSelectedIdle.hashCode()
        result = 31 * result + buttonUnselectedIdle.hashCode()
        result = 31 * result + buttonPressed.hashCode()
        result = 31 * result + buttonDisabled.hashCode()
        return result
    }
}

// ===========================================================================
// ColorWellDefaults
// ===========================================================================

object ColorWellDefaults {

    @Composable
    fun colors(): ColorWellColors {
        val isDark = MacosTheme.colorScheme.isDark
        val overGlass = LocalSurface.current == Surface.OverGlass
        // Light mode: translucent black overlays — dark mode: translucent white
        val base = if (isDark) Color.White else Color.Black
        return if (overGlass) {
            ColorWellColors(
                containerSelectedIdle = base.copy(alpha = if (isDark) 0.18f else 0.13f),
                containerUnselectedIdle = base.copy(alpha = if (isDark) 0.08f else 0.05f),
                containerSelectedPressed = base.copy(alpha = if (isDark) 0.28f else 0.20f),
                containerUnselectedPressed = base.copy(alpha = if (isDark) 0.20f else 0.15f),
                containerSelectedDisabled = base.copy(alpha = if (isDark) 0.05f else 0.03f),
                containerUnselectedDisabled = base.copy(alpha = if (isDark) 0.05f else 0.03f),
                swatchBorder = base.copy(alpha = 0.10f),
                buttonSelectedIdle = base.copy(alpha = if (isDark) 0.18f else 0.13f),
                buttonUnselectedIdle = base.copy(alpha = if (isDark) 0.08f else 0.05f),
                buttonPressed = base.copy(alpha = if (isDark) 0.28f else 0.20f),
                buttonDisabled = base.copy(alpha = if (isDark) 0.05f else 0.03f),
            )
        } else {
            ColorWellColors(
                containerSelectedIdle = base.copy(alpha = if (isDark) 0.15f else 0.10f),
                containerUnselectedIdle = base.copy(alpha = if (isDark) 0.08f else 0.05f),
                containerSelectedPressed = base.copy(alpha = if (isDark) 0.22f else 0.17f),
                containerUnselectedPressed = base.copy(alpha = if (isDark) 0.18f else 0.13f),
                containerSelectedDisabled = base.copy(alpha = if (isDark) 0.08f else 0.05f),
                containerUnselectedDisabled = base.copy(alpha = if (isDark) 0.05f else 0.03f),
                swatchBorder = base.copy(alpha = 0.10f),
                buttonSelectedIdle = base.copy(alpha = if (isDark) 0.15f else 0.10f),
                buttonUnselectedIdle = base.copy(alpha = if (isDark) 0.08f else 0.05f),
                buttonPressed = base.copy(alpha = if (isDark) 0.22f else 0.17f),
                buttonDisabled = base.copy(alpha = if (isDark) 0.08f else 0.05f),
            )
        }
    }
}

// ===========================================================================
// ColorWell
// ===========================================================================

/**
 * A macOS-style color well control that displays the currently selected color.
 *
 * The size adapts to [LocalControlSize]; the surface style (content-area vs
 * over-glass) is read from [LocalSurface].
 *
 * @param color     The currently selected color shown in the swatch.
 * @param onClick   Called when the user taps the swatch area.
 * @param onButtonClick Called when the user taps the color-wheel button
 *   ([ColorWellStyle.Expanded] only; defaults to [onClick]).
 * @param selected  Whether the well is in the active ("on") state — shows a
 *   slightly darker container background.
 * @param style     Visual variant.
 */
@Composable
fun ColorWell(
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onButtonClick: (() -> Unit)? = null,
    selected: Boolean = false,
    enabled: Boolean = true,
    style: ColorWellStyle = ColorWellStyle.Standard,
    colors: ColorWellColors = ColorWellDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val controlSize = LocalControlSize.current
    val height = controlSize.colorWellHeight()

    when (style) {
        ColorWellStyle.Standard,
        ColorWellStyle.Minimal,
        -> PaddedColorWell(
            color = color,
            onClick = onClick,
            modifier = modifier,
            selected = selected,
            enabled = enabled,
            isMinimal = style == ColorWellStyle.Minimal,
            controlSize = controlSize,
            height = height,
            colors = colors,
            interactionSource = interactionSource,
        )

        ColorWellStyle.Expanded -> ExpandedColorWell(
            color = color,
            onClick = onClick,
            onButtonClick = onButtonClick ?: onClick,
            modifier = modifier,
            selected = selected,
            enabled = enabled,
            controlSize = controlSize,
            height = height,
            colors = colors,
            interactionSource = interactionSource,
        )
    }
}

// ===========================================================================
// Standard / Minimal  (padded swatch inside a container)
// ===========================================================================

@Composable
private fun PaddedColorWell(
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier,
    selected: Boolean,
    enabled: Boolean,
    isMinimal: Boolean,
    controlSize: ControlSize,
    height: Dp,
    colors: ColorWellColors,
    interactionSource: MutableInteractionSource,
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val outerCorner = controlSize.colorWellOuterCorner()
    val swatchCorner = controlSize.colorWellSwatchCorner()
    val containerColor = colors.containerColor(selected, isPressed && enabled, enabled)
    val showBorder = !(isMinimal && selected)
    val showChevron = isMinimal && enabled && (isHovered || isPressed)

    Canvas(
        modifier = modifier
            .size(width = ContainerWidth, height = height)
            .clip(RoundedCornerShape(outerCorner))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onClick,
            ),
    ) {
        val outerR = outerCorner.toPx()
        val swatchR = swatchCorner.toPx()
        val pad = SwatchPadding.toPx()
        val bw = SwatchBorderWidth.toPx()
        val sw = size.width - pad * 2
        val sh = size.height - pad * 2

        // Container background
        drawRoundRect(containerColor, cornerRadius = CornerRadius(outerR))

        // Swatch color fill
        drawRoundRect(
            color = color,
            topLeft = Offset(pad, pad),
            size = Size(sw, sh),
            cornerRadius = CornerRadius(swatchR),
            alpha = if (enabled) 1f else DisabledSwatchAlpha,
        )

        // Swatch border (drawn inside)
        if (showBorder) {
            val half = bw / 2f
            drawRoundRect(
                color = colors.swatchBorder,
                topLeft = Offset(pad + half, pad + half),
                size = Size(sw - bw, sh - bw),
                cornerRadius = CornerRadius((swatchR - half).coerceAtLeast(0f)),
                style = Stroke(bw),
                alpha = if (enabled) 1f else DisabledSwatchAlpha,
            )
        }

        // Chevron indicator (Minimal hover / press)
        if (showChevron) {
            drawChevronIndicator(
                center = Offset(x = pad + sw - sh * 0.6f, y = pad + sh / 2f),
                chevronSize = sh * 0.45f,
            )
        }
    }
}

// ===========================================================================
// Expanded  (full-bleed swatch + color-wheel button)
// ===========================================================================

@Composable
private fun ExpandedColorWell(
    color: Color,
    onClick: () -> Unit,
    onButtonClick: () -> Unit,
    modifier: Modifier,
    selected: Boolean,
    enabled: Boolean,
    controlSize: ControlSize,
    height: Dp,
    colors: ColorWellColors,
    interactionSource: MutableInteractionSource,
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val buttonInteractionSource = remember { MutableInteractionSource() }
    val isButtonPressed by buttonInteractionSource.collectIsPressedAsState()

    val corner = controlSize.colorWellExpandedCorner()
    val wheelSize = controlSize.colorWellWheelSize()
    val buttonColor = colors.buttonColor(selected, isButtonPressed && enabled, enabled)
    val showChevron = enabled && (isHovered || isPressed)

    Row(modifier = modifier.height(height)) {
        // Swatch
        Canvas(
            modifier = Modifier
                .size(ExpandedSwatchWidth, height)
                .clip(RoundedCornerShape(corner))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick,
                ),
        ) {
            val r = corner.toPx()

            drawRoundRect(
                color = color,
                cornerRadius = CornerRadius(r),
                alpha = if (enabled) 1f else DisabledSwatchAlpha,
            )

            if (showChevron) {
                drawChevronIndicator(
                    center = Offset(size.width / 2f, size.height / 2f),
                    chevronSize = size.height * 0.45f,
                )
            }
        }

        Spacer(Modifier.width(ExpandedGap))

        // Color-wheel button
        Box(
            modifier = Modifier
                .size(height)
                .clip(RoundedCornerShape(corner))
                .drawBehind {
                    drawRoundRect(buttonColor, cornerRadius = CornerRadius(corner.toPx()))
                }
                .clickable(
                    interactionSource = buttonInteractionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onButtonClick,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Canvas(
                modifier = Modifier
                    .size(wheelSize)
                    .alpha(if (enabled) 1f else DisabledSwatchAlpha),
            ) {
                drawHueWheel()
            }
        }
    }
}

// ===========================================================================
// Drawing helpers
// ===========================================================================

private fun DrawScope.drawHueWheel() {
    val radius = size.minDimension / 2f
    val center = Offset(size.width / 2f, size.height / 2f)
    val arcSize = Size(radius * 2f, radius * 2f)
    val arcTopLeft = Offset(center.x - radius, center.y - radius)

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
}

private fun DrawScope.drawChevronIndicator(center: Offset, chevronSize: Float) {
    val halfW = chevronSize * 0.36f
    val halfH = chevronSize * 0.2f
    val path = Path().apply {
        moveTo(center.x - halfW, center.y - halfH)
        lineTo(center.x, center.y + halfH)
        lineTo(center.x + halfW, center.y - halfH)
    }
    drawPath(
        path = path,
        color = Color.White,
        style = Stroke(
            width = chevronSize * 0.16f,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round,
        ),
    )
}

// ===========================================================================
// Metrics  (values extracted from Sketch)
// ===========================================================================

private fun ControlSize.colorWellHeight(): Dp = when (this) {
    ControlSize.Mini -> 16.dp
    ControlSize.Small -> 20.dp
    ControlSize.Regular -> 24.dp
    ControlSize.Large -> 28.dp
    ControlSize.ExtraLarge -> 36.dp
}

private fun ControlSize.colorWellOuterCorner(): Dp = when (this) {
    ControlSize.Mini -> 4.dp
    ControlSize.Small -> 5.dp
    ControlSize.Regular -> 6.dp
    ControlSize.Large -> 14.dp
    ControlSize.ExtraLarge -> 18.dp
}

private fun ControlSize.colorWellSwatchCorner(): Dp = when (this) {
    ControlSize.Mini -> 1.5.dp
    ControlSize.Small -> 2.5.dp
    ControlSize.Regular -> 3.5.dp
    ControlSize.Large -> 15.dp
    ControlSize.ExtraLarge -> 15.dp
}

private fun ControlSize.colorWellExpandedCorner(): Dp = when (this) {
    ControlSize.Mini -> 4.dp
    ControlSize.Small -> 5.dp
    ControlSize.Regular -> 6.dp
    ControlSize.Large -> 7.dp
    ControlSize.ExtraLarge -> 9.dp
}

private fun ControlSize.colorWellWheelSize(): Dp = when (this) {
    ControlSize.Mini -> 10.dp
    ControlSize.Small -> 12.dp
    ControlSize.Regular -> 16.dp
    ControlSize.Large -> 18.dp
    ControlSize.ExtraLarge -> 24.dp
}

// ===========================================================================
// Constants
// ===========================================================================

private val ContainerWidth = 60.dp
private val ExpandedSwatchWidth = 59.dp
private val ExpandedGap = 1.dp
private val SwatchPadding = 3.dp
private val SwatchBorderWidth = 0.5.dp
private const val DisabledSwatchAlpha = 0.5f
