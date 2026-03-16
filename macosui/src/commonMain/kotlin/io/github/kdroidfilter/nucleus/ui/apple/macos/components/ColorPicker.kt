package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import kotlin.math.roundToInt

// ===========================================================================
// ColorPickerDot
// ===========================================================================

/**
 * A circular color-picker button. When [color] is `null` it shows a hue
 * wheel; when a colour is set it displays the colour inside a gray ring.
 *
 * The size adapts to [LocalControlSize].
 */
@Composable
fun ColorPickerDot(
    color: Color?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val dotSize = LocalControlSize.current.pickerDotSize()

    Canvas(
        modifier = modifier
            .size(dotSize)
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onClick,
            ),
    ) {
        val radius = size.minDimension / 2f
        val center = Offset(size.width / 2f, size.height / 2f)

        if (color == null) {
            // Hue wheel — angular gradient
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
            // White radial fade
            drawCircle(
                brush = Brush.radialGradient(
                    colorStops = arrayOf(
                        0.00f to Color.White,
                        0.08f to Color.White,
                        0.97f to Color.Transparent,
                        1.00f to Color.Transparent,
                    ),
                    center = center,
                    radius = radius,
                ),
                radius = radius,
                center = center,
                alpha = if (enabled) 1f else 0.5f,
            )
        } else {
            // Selected colour inside a gray ring
            val borderWidth = radius * 0.214f  // ~3dp at 28dp
            val innerRadius = radius - borderWidth - radius * 0.07f

            // Gray ring (border)
            drawCircle(
                color = Color(0xFF979797),
                radius = radius - borderWidth / 2f,
                center = center,
                style = Stroke(borderWidth),
                alpha = if (enabled) 1f else 0.5f,
            )
            // Colour fill
            drawCircle(
                color = color,
                radius = innerRadius,
                center = center,
                alpha = if (enabled) 1f else 0.5f,
            )
        }
    }
}

// ===========================================================================
// ColorGrid
// ===========================================================================

/**
 * A macOS-style color swatch grid — 12 columns x 10 rows of solid-color
 * cells. Clicking a cell calls [onColorSelected] with the chosen color.
 *
 * The cell size adapts to [LocalControlSize].
 * The selected cell is highlighted with a white inset ring.
 *
 * @param selectedColor  The currently active color (or `null` for none).
 * @param onColorSelected Called when the user picks a color.
 */
@Composable
fun ColorGrid(
    selectedColor: Color?,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    val cellSize = LocalControlSize.current.gridCellSize()
    Column(modifier = modifier) {
        ColorGridPalette.forEach { row ->
            Row {
                row.forEach { color ->
                    ColorGridCell(
                        color = color,
                        isSelected = selectedColor != null && colorsMatch(selectedColor, color),
                        onClick = { onColorSelected(color) },
                        size = cellSize,
                    )
                }
            }
        }
    }
}

// ===========================================================================
// Grid cell
// ===========================================================================

@Composable
private fun ColorGridCell(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    size: Dp,
) {
    Canvas(
        modifier = Modifier
            .size(size)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Button,
                onClick = onClick,
            ),
    ) {
        drawRect(color = color)

        if (isSelected) {
            val inset = 3.dp.toPx()
            val bw = 2.dp.toPx()
            val half = bw / 2f
            val r = 2.dp.toPx()
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(inset + half, inset + half),
                size = Size(
                    this.size.width - (inset + half) * 2,
                    this.size.height - (inset + half) * 2,
                ),
                cornerRadius = CornerRadius(r),
                style = Stroke(bw),
            )
        }
    }
}

// ===========================================================================
// OpacitySlider
// ===========================================================================

/**
 * A macOS-style opacity slider — a pill-shaped track showing a
 * checkerboard-to-color gradient, a circular thumb, and a percentage field.
 *
 * The dimensions adapt to [LocalControlSize].
 *
 * @param opacity         Current opacity value in `0f..1f`.
 * @param onOpacityChange Called when the user drags the thumb or taps.
 * @param color           The base colour (used at full opacity for the gradient).
 */
@Composable
fun OpacitySlider(
    opacity: Float,
    onOpacityChange: (Float) -> Unit,
    color: Color,
    modifier: Modifier = Modifier,
) {
    val controlSize = LocalControlSize.current
    val trackHeight = controlSize.opacityTrackHeight()
    val thumbDiameter = controlSize.opacityThumbDiameter()
    val thumbBorderWidth = controlSize.opacityThumbBorder()
    val fieldWidth = controlSize.opacityFieldWidth()
    val fontSize = controlSize.opacityFontSize()
    val gap = controlSize.opacityGap()
    val thumbInset = (trackHeight - thumbDiameter) / 2

    val isDark = MacosTheme.colorScheme.isDark
    val density = LocalDensity.current
    var trackWidthPx by remember { mutableFloatStateOf(0f) }
    val thumbDiameterPx = with(density) { thumbDiameter.toPx() }

    Row(
        modifier = modifier.height(trackHeight),
        horizontalArrangement = Arrangement.spacedBy(gap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Track + thumb
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(percent = 50))
                .onSizeChanged { trackWidthPx = it.width.toFloat() }
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        if (trackWidthPx > thumbDiameterPx) {
                            val raw = (offset.x - thumbDiameterPx / 2f) /
                                (trackWidthPx - thumbDiameterPx)
                            onOpacityChange(raw.coerceIn(0f, 1f))
                        }
                    }
                }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, _ ->
                        change.consume()
                        if (trackWidthPx > thumbDiameterPx) {
                            val raw = (change.position.x - thumbDiameterPx / 2f) /
                                (trackWidthPx - thumbDiameterPx)
                            onOpacityChange(raw.coerceIn(0f, 1f))
                        }
                    }
                },
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCheckerboard()
                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            color.copy(alpha = 0f),
                            color.copy(alpha = 1f),
                        ),
                    ),
                )
            }

            val thumbOffsetPx = (opacity * (trackWidthPx - thumbDiameterPx)).roundToInt()
            Box(
                modifier = Modifier
                    .offset { IntOffset(thumbOffsetPx, with(density) { thumbInset.roundToPx() }) }
                    .size(thumbDiameter)
                    .shadow(3.dp, CircleShape, ambientColor = Color.Black.copy(alpha = 0.10f))
                    .clip(CircleShape)
                    .drawBehind {
                        drawCircle(color = color.copy(alpha = 1f))
                        val bw = thumbBorderWidth.toPx()
                        drawCircle(
                            color = Color.White,
                            radius = size.minDimension / 2f - bw / 2f,
                            style = Stroke(bw),
                        )
                    },
            )
        }

        // Percentage field
        Box(
            modifier = Modifier
                .size(fieldWidth, trackHeight)
                .clip(RoundedCornerShape(percent = 50))
                .drawBehind {
                    drawRect(if (isDark) Color.Black else Color.White)
                },
            contentAlignment = Alignment.Center,
        ) {
            androidx.compose.foundation.text.BasicText(
                text = "${(opacity * 100).roundToInt()}%",
                style = androidx.compose.ui.text.TextStyle(
                    color = if (isDark) Color.White else Color.Black,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                ),
            )
        }
    }
}

// ===========================================================================
// Checkerboard drawing
// ===========================================================================

private fun DrawScope.drawCheckerboard() {
    val cellPx = CheckerCellSize.toPx()
    val cols = (size.width / cellPx).toInt() + 1
    val rows = (size.height / cellPx).toInt() + 1
    drawRect(color = Color(0xFFD9D9D9))
    for (r in 0 until rows) {
        for (c in 0 until cols) {
            if ((r + c) % 2 == 0) {
                drawRect(
                    color = Color(0xFFB0B0B0),
                    topLeft = Offset(c * cellPx, r * cellPx),
                    size = Size(cellPx, cellPx),
                )
            }
        }
    }
}

private val CheckerCellSize = 8.dp

// ===========================================================================
// Size metrics (per ControlSize)
// ===========================================================================

// -- ColorPickerDot ----------------------------------------------------------

private fun ControlSize.pickerDotSize(): Dp = when (this) {
    ControlSize.Mini -> 16.dp
    ControlSize.Small -> 20.dp
    ControlSize.Regular -> 24.dp
    ControlSize.Large -> 28.dp
    ControlSize.ExtraLarge -> 34.dp
}

// -- ColorGrid ---------------------------------------------------------------

private fun ControlSize.gridCellSize(): Dp = when (this) {
    ControlSize.Mini -> 12.dp
    ControlSize.Small -> 16.dp
    ControlSize.Regular -> 20.dp
    ControlSize.Large -> 24.dp
    ControlSize.ExtraLarge -> 28.dp
}

// -- OpacitySlider -----------------------------------------------------------

private fun ControlSize.opacityTrackHeight(): Dp = when (this) {
    ControlSize.Mini -> 12.dp
    ControlSize.Small -> 14.dp
    ControlSize.Regular -> 18.dp
    ControlSize.Large -> 22.dp
    ControlSize.ExtraLarge -> 26.dp
}

private fun ControlSize.opacityThumbDiameter(): Dp = when (this) {
    ControlSize.Mini -> 10.dp
    ControlSize.Small -> 12.dp
    ControlSize.Regular -> 16.dp
    ControlSize.Large -> 20.dp
    ControlSize.ExtraLarge -> 24.dp
}

private fun ControlSize.opacityThumbBorder(): Dp = when (this) {
    ControlSize.Mini -> 1.dp
    ControlSize.Small -> 1.5.dp
    ControlSize.Regular -> 1.5.dp
    ControlSize.Large -> 2.dp
    ControlSize.ExtraLarge -> 2.dp
}

private fun ControlSize.opacityFieldWidth(): Dp = when (this) {
    ControlSize.Mini -> 30.dp
    ControlSize.Small -> 34.dp
    ControlSize.Regular -> 42.dp
    ControlSize.Large -> 48.dp
    ControlSize.ExtraLarge -> 56.dp
}

private fun ControlSize.opacityFontSize(): TextUnit = when (this) {
    ControlSize.Mini -> 9.sp
    ControlSize.Small -> 10.sp
    ControlSize.Regular -> 11.sp
    ControlSize.Large -> 13.sp
    ControlSize.ExtraLarge -> 15.sp
}

private fun ControlSize.opacityGap(): Dp = when (this) {
    ControlSize.Mini -> 3.dp
    ControlSize.Small -> 4.dp
    ControlSize.Regular -> 6.dp
    ControlSize.Large -> 7.dp
    ControlSize.ExtraLarge -> 8.dp
}

// ===========================================================================
// Helpers
// ===========================================================================

/** Compares two colors ignoring minor floating-point drift. */
private fun colorsMatch(a: Color, b: Color): Boolean =
    (a.red - b.red).let { it * it } +
        (a.green - b.green).let { it * it } +
        (a.blue - b.blue).let { it * it } < 0.001f

// ===========================================================================
// Palette  (extracted from Sketch — 12 columns x 10 rows)
// ===========================================================================

@Suppress("LongMethod")
private val ColorGridPalette: List<List<Color>> = listOf(
    // Row 0 — grays (white -> black)
    listOf(
        Color(0xFFFEFFFE), Color(0xFFEBEBEB), Color(0xFFD6D6D6), Color(0xFFC2C2C2),
        Color(0xFFADADAD), Color(0xFF999999), Color(0xFF858585), Color(0xFF707070),
        Color(0xFF5C5C5C), Color(0xFF474747), Color(0xFF333333), Color(0xFF000000),
    ),
    // Row 1
    listOf(
        Color(0xFF00374A), Color(0xFF011D57), Color(0xFF11053B), Color(0xFF2E063D),
        Color(0xFF3C071B), Color(0xFF5C0701), Color(0xFF5A1C00), Color(0xFF583300),
        Color(0xFF563D00), Color(0xFF666100), Color(0xFF4F5504), Color(0xFF263E0F),
    ),
    // Row 2
    listOf(
        Color(0xFF004D65), Color(0xFF012F7B), Color(0xFF1A0A52), Color(0xFF450D59),
        Color(0xFF551029), Color(0xFF831100), Color(0xFF7B2900), Color(0xFF7A4A00),
        Color(0xFF785800), Color(0xFF8D8602), Color(0xFF6F760A), Color(0xFF38571A),
    ),
    // Row 3
    listOf(
        Color(0xFF016E8F), Color(0xFF0042A9), Color(0xFF2C0977), Color(0xFF61187C),
        Color(0xFF791A3D), Color(0xFFB51A00), Color(0xFFAD3E00), Color(0xFFA96800),
        Color(0xFFA67B01), Color(0xFFC4BC00), Color(0xFF9BA50E), Color(0xFF4E7A27),
    ),
    // Row 4
    listOf(
        Color(0xFF008CB4), Color(0xFF0056D6), Color(0xFF371A94), Color(0xFF7A219E),
        Color(0xFF99244F), Color(0xFFE22400), Color(0xFFDA5100), Color(0xFFD38301),
        Color(0xFFD19D01), Color(0xFFF5EC00), Color(0xFFC3D117), Color(0xFF669D34),
    ),
    // Row 5 — vivid
    listOf(
        Color(0xFF00A1D8), Color(0xFF0061FD), Color(0xFF4D22B2), Color(0xFF982ABC),
        Color(0xFFB92D5D), Color(0xFFFF4015), Color(0xFFFF6A00), Color(0xFFFFAB01),
        Color(0xFFFCC700), Color(0xFFFEFB41), Color(0xFFD9EC37), Color(0xFF76BB40),
    ),
    // Row 6
    listOf(
        Color(0xFF01C7FC), Color(0xFF3A87FD), Color(0xFF5E30EB), Color(0xFFBE38F3),
        Color(0xFFE63B7A), Color(0xFFFE6250), Color(0xFFFE8648), Color(0xFFFEB43F),
        Color(0xFFFECB3E), Color(0xFFFFF76B), Color(0xFFE4EF65), Color(0xFF96D35F),
    ),
    // Row 7
    listOf(
        Color(0xFF52D6FC), Color(0xFF74A7FF), Color(0xFF864FFD), Color(0xFFD357FE),
        Color(0xFFEE719E), Color(0xFFFF8C82), Color(0xFFFEA57D), Color(0xFFFEC777),
        Color(0xFFFED977), Color(0xFFFFF994), Color(0xFFEAF28F), Color(0xFFB1DD8B),
    ),
    // Row 8
    listOf(
        Color(0xFF93E3FC), Color(0xFFA7C6FF), Color(0xFFB18CFE), Color(0xFFE292FE),
        Color(0xFFF4A4C0), Color(0xFFFFB5AF), Color(0xFFFFC5AB), Color(0xFFFED9A8),
        Color(0xFFFDE4A8), Color(0xFFFFFBB9), Color(0xFFF1F7B7), Color(0xFFCDE8B5),
    ),
    // Row 9 — pastels
    listOf(
        Color(0xFFCBF0FF), Color(0xFFD2E2FE), Color(0xFFD8C9FE), Color(0xFFEFCAFE),
        Color(0xFFF9D3E0), Color(0xFFFFDAD8), Color(0xFFFFE2D6), Color(0xFFFEECD4),
        Color(0xFFFEF1D5), Color(0xFFFDFBDD), Color(0xFFF6FADB), Color(0xFFDEEED4),
    ),
)
