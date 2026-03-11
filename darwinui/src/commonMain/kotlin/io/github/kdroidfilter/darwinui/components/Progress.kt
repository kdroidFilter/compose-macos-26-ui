package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.theme.*

// ==================== Enums ====================

enum class ProgressSize(val height: Dp) {
    Sm(4.dp),
    Md(8.dp),
    Lg(12.dp),
}

enum class ProgressVariant {
    Default,
    Success,
    Warning,
    Danger,
    Gradient,
}

// ==================== ProgressRing sizes ====================

object ProgressRingSize {
    val Large = 64.dp
    val Medium = 32.dp
    val Small = 16.dp
}

// ==================== Color Helpers ====================

@Composable
private fun variantColor(variant: ProgressVariant): Color = when (variant) {
    ProgressVariant.Default -> DarwinTheme.colors.accent
    ProgressVariant.Success -> Emerald500
    ProgressVariant.Warning -> Amber500
    ProgressVariant.Danger -> Red500
    ProgressVariant.Gradient -> DarwinTheme.colors.accent
}

private fun gradientBrush(width: Float): Brush = Brush.linearGradient(
    colors = listOf(Blue500, Violet500, Red500),
    start = Offset.Zero,
    end = Offset(width, 0f),
)

// ==================== Linear Progress ====================

@Composable
fun LinearProgress(
    value: Float = 0f,
    max: Float = 100f,
    size: ProgressSize = ProgressSize.Md,
    variant: ProgressVariant = ProgressVariant.Default,
    indeterminate: Boolean = false,
    showValue: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val isDark = DarwinTheme.colors.isDark

    val trackColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

    val fillColor = variantColor(variant)
    val useGradient = variant == ProgressVariant.Gradient

    val targetFraction = if (indeterminate) 0f else (value / max).coerceIn(0f, 1f)
    val animatedFraction by animateFloatAsState(
        targetValue = targetFraction,
        animationSpec = tween(
            durationMillis = 400,
            easing = CubicBezierEasing(0f, 0f, 0.58f, 1f),
        ),
    )

    val infiniteTransition = rememberInfiniteTransition(label = "progress_indeterminate")
    val indeterminateOffset by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = FastOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "indeterminate_offset",
    )

    val percentage = ((value / max) * 100f).coerceIn(0f, 100f).toInt()

    Column(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(size.height),
        ) {
            val cornerRadius = CornerRadius(this.size.height / 2f)

            drawRoundRect(
                color = trackColor,
                cornerRadius = cornerRadius,
                size = this.size,
            )

            if (indeterminate) {
                val barWidth = this.size.width / 3f
                val startX = indeterminateOffset * barWidth
                val clippedStart = startX.coerceAtLeast(0f)
                val clippedEnd = (startX + barWidth).coerceAtMost(this.size.width)
                val clippedWidth = (clippedEnd - clippedStart).coerceAtLeast(0f)

                if (clippedWidth > 0f) {
                    if (useGradient) {
                        drawRoundRect(
                            brush = gradientBrush(clippedWidth),
                            topLeft = Offset(clippedStart, 0f),
                            size = Size(clippedWidth, this.size.height),
                            cornerRadius = cornerRadius,
                        )
                    } else {
                        drawRoundRect(
                            color = fillColor,
                            topLeft = Offset(clippedStart, 0f),
                            size = Size(clippedWidth, this.size.height),
                            cornerRadius = cornerRadius,
                        )
                    }
                }
            } else {
                val fillWidth = animatedFraction * this.size.width
                if (fillWidth > 0f) {
                    if (useGradient) {
                        drawRoundRect(
                            brush = gradientBrush(fillWidth),
                            cornerRadius = cornerRadius,
                            size = Size(fillWidth, this.size.height),
                        )
                    } else {
                        drawRoundRect(
                            color = fillColor,
                            cornerRadius = cornerRadius,
                            size = Size(fillWidth, this.size.height),
                        )
                    }
                }
            }
        }

        if (showValue && !indeterminate) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$percentage%",
                style = DarwinTheme.typography.labelSmall,
                color = if (isDark) Zinc400 else Zinc600,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

// ==================== ProgressRing (circular) ====================

/**
 * Determinate circular progress ring.
 *
 * @param progress Progress value from 0f to 1f.
 * @param modifier Modifier applied to the component.
 * @param size Diameter of the ring.
 * @param width Stroke width of the ring.
 * @param color Fill color for the progress arc.
 */
@Composable
fun ProgressRing(
    progress: Float,
    modifier: Modifier = Modifier,
    size: Dp = ProgressRingSize.Medium,
    width: Dp = size * 3 / 32,
    color: Color = DarwinTheme.colors.accent,
) {
    val isDark = DarwinTheme.colors.isDark
    val trackColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

    val targetFraction = progress.coerceIn(0f, 1f)
    val animatedFraction by animateFloatAsState(
        targetValue = targetFraction,
        animationSpec = tween(
            durationMillis = 400,
            easing = CubicBezierEasing(0f, 0f, 0.58f, 1f),
        ),
    )

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stroke = Stroke(width = width.toPx(), cap = StrokeCap.Round)
            val arcSize = Size(
                width = this.size.width - stroke.width,
                height = this.size.height - stroke.width,
            )
            val topLeft = Offset(stroke.width / 2f, stroke.width / 2f)

            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke,
            )

            val sweepAngle = animatedFraction * 360f
            if (sweepAngle > 0f) {
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = stroke,
                )
            }
        }
    }
}

/**
 * Indeterminate circular progress ring.
 *
 * @param modifier Modifier applied to the component.
 * @param size Diameter of the ring.
 * @param width Stroke width of the ring.
 * @param color Fill color for the progress arc.
 */
@Composable
fun ProgressRing(
    modifier: Modifier = Modifier,
    size: Dp = ProgressRingSize.Medium,
    width: Dp = size * 3 / 32,
    color: Color = DarwinTheme.colors.accent,
) {
    val isDark = DarwinTheme.colors.isDark
    val trackColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

    val infiniteTransition = rememberInfiniteTransition(label = "circular_indeterminate")
    val indeterminateProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "circular_progress",
    )

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stroke = Stroke(width = width.toPx(), cap = StrokeCap.Round)
            val arcSize = Size(
                width = this.size.width - stroke.width,
                height = this.size.height - stroke.width,
            )
            val topLeft = Offset(stroke.width / 2f, stroke.width / 2f)

            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke,
            )

            val t = indeterminateProgress
            val startAngle: Float
            val sweepAngle: Float
            if (t < 0.5f) {
                startAngle = -90f + 360f * t
                sweepAngle = 720f * t
            } else {
                startAngle = -450f + 1080f * t
                sweepAngle = 720f * (1f - t)
            }

            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle.coerceIn(0f, 360f),
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke,
            )
        }
    }
}

// ===========================================================================
// M3-named aliases
// ===========================================================================

/**
 * Determinate linear progress indicator — mirrors M3's LinearProgressIndicator.
 */
@Composable
fun LinearProgressIndicator(
    progress: () -> Float,
    modifier: Modifier = Modifier,
    color: Color = DarwinTheme.colors.accent,
    trackColor: Color = if (DarwinTheme.colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f),
    strokeCap: androidx.compose.ui.graphics.StrokeCap = androidx.compose.ui.graphics.StrokeCap.Round,
) {
    LinearProgress(
        value = progress() * 100f,
        max = 100f,
        modifier = modifier,
    )
}

/**
 * Indeterminate linear progress indicator — mirrors M3's LinearProgressIndicator.
 */
@Composable
fun LinearProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = DarwinTheme.colors.accent,
    trackColor: Color = if (DarwinTheme.colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f),
    strokeCap: androidx.compose.ui.graphics.StrokeCap = androidx.compose.ui.graphics.StrokeCap.Round,
) {
    LinearProgress(indeterminate = true, modifier = modifier)
}

/**
 * Determinate circular progress indicator — mirrors M3's CircularProgressIndicator.
 */
@Composable
fun CircularProgressIndicator(
    progress: () -> Float,
    modifier: Modifier = Modifier,
    color: Color = DarwinTheme.colors.accent,
    strokeWidth: Dp = 4.dp,
    trackColor: Color = if (DarwinTheme.colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f),
    strokeCap: androidx.compose.ui.graphics.StrokeCap = androidx.compose.ui.graphics.StrokeCap.Round,
) {
    ProgressRing(progress = progress(), color = color, modifier = modifier)
}

/**
 * Indeterminate circular progress indicator — mirrors M3's CircularProgressIndicator.
 */
@Composable
fun CircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = DarwinTheme.colors.accent,
    strokeWidth: Dp = 4.dp,
    trackColor: Color = if (DarwinTheme.colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f),
    strokeCap: androidx.compose.ui.graphics.StrokeCap = androidx.compose.ui.graphics.StrokeCap.Round,
) {
    ProgressRing(color = color, modifier = modifier)
}

@Preview
@Composable
private fun ProgressPreview() {
    DarwinTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            LinearProgress(value = 60f)
            ProgressRing(progress = 0.75f)
            ProgressRing()
        }
    }
}
