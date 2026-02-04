package io.github.kdroidfilter.darwinui.components.progress

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.theme.*

// ==================== Enums ====================

/**
 * Size variants for the linear progress bar.
 *
 * @property height Track height in dp.
 */
enum class DarwinProgressSize(val height: Dp) {
    Sm(4.dp),
    Md(8.dp),
    Lg(12.dp),
}

/**
 * Color variants for both linear and circular progress indicators.
 */
enum class DarwinProgressVariant {
    /** Uses the theme accent color (blue). */
    Default,

    /** Green color indicating success. */
    Success,

    /** Amber color indicating a warning. */
    Warning,

    /** Red color indicating danger or error. */
    Danger,

    /** Blue-to-purple linear gradient. */
    Gradient,
}

// ==================== Color Helpers ====================

/**
 * Resolves the solid fill color for a given variant from theme colors.
 */
@Composable
private fun variantColor(variant: DarwinProgressVariant): Color {
    val colors = DarwinTheme.colors
    return when (variant) {
        DarwinProgressVariant.Default -> colors.accent
        DarwinProgressVariant.Success -> colors.success
        DarwinProgressVariant.Warning -> colors.warning
        DarwinProgressVariant.Danger -> colors.destructive
        DarwinProgressVariant.Gradient -> colors.accent // fallback; gradient handled separately
    }
}

/**
 * Whether this variant uses a gradient fill.
 */
private fun isGradientVariant(variant: DarwinProgressVariant): Boolean =
    variant == DarwinProgressVariant.Gradient

// ==================== Linear Progress ====================

/**
 * Darwin UI Linear Progress -- a horizontal progress bar.
 *
 * Supports determinate and indeterminate modes, multiple color variants,
 * an optional glass effect, and an optional percentage label.
 *
 * @param value Current progress value (0 to [max]).
 * @param max Maximum progress value.
 * @param size Size variant controlling bar height.
 * @param variant Color variant for the filled portion.
 * @param indeterminate When true, plays an infinite sliding animation ignoring [value].
 * @param showValue When true, displays the percentage to the right of the bar.
 * @param glass When true, uses a semi-transparent glass background for the track.
 * @param modifier Modifier applied to the root layout.
 */
@Composable
fun DarwinLinearProgress(
    value: Float = 0f,
    max: Float = 100f,
    size: DarwinProgressSize = DarwinProgressSize.Md,
    variant: DarwinProgressVariant = DarwinProgressVariant.Default,
    indeterminate: Boolean = false,
    showValue: Boolean = false,
    glass: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colors

    val trackColor = when {
        glass -> colors.glassBackground
        colors.isDark -> Zinc800
        else -> Zinc200
    }

    val fillColor = variantColor(variant)
    val useGradient = isGradientVariant(variant)

    // Determinate: animate the fill fraction smoothly
    val targetFraction = if (indeterminate) 0f else (value / max).coerceIn(0f, 1f)
    val animatedFraction by animateFloatAsState(
        targetValue = targetFraction,
        animationSpec = darwinSpring(DarwinSpringPreset.Smooth),
    )

    // Indeterminate: sliding shimmer animation
    val infiniteTransition = rememberInfiniteTransition()
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -0.3f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart,
        ),
    )

    val percentage = ((value / max) * 100f).coerceIn(0f, 100f).toInt()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Canvas(
            modifier = Modifier
                .weight(1f)
                .height(size.height),
        ) {
            val cornerRadius = CornerRadius(this.size.height / 2f)

            // Background track
            drawRoundRect(
                color = trackColor,
                cornerRadius = cornerRadius,
                size = this.size,
            )

            if (indeterminate) {
                // Shimmer bar: a sliding bar of ~30% width
                val barWidth = this.size.width * 0.3f
                val startX = shimmerOffset * (this.size.width + barWidth) - barWidth
                val clippedStart = startX.coerceAtLeast(0f)
                val clippedEnd = (startX + barWidth).coerceAtMost(this.size.width)
                val clippedWidth = (clippedEnd - clippedStart).coerceAtLeast(0f)

                if (clippedWidth > 0f) {
                    if (useGradient) {
                        drawRoundRect(
                            brush = Brush.linearGradient(
                                colors = listOf(Blue500, Purple500),
                                start = Offset.Zero,
                                end = Offset(clippedWidth, 0f),
                            ),
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
                // Determinate fill
                val fillWidth = animatedFraction * this.size.width
                if (fillWidth > 0f) {
                    if (useGradient) {
                        drawRoundRect(
                            brush = Brush.linearGradient(
                                colors = listOf(Blue500, Purple500),
                                start = Offset.Zero,
                                end = Offset(fillWidth, 0f),
                            ),
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

        // Percentage label
        if (showValue && !indeterminate) {
            Spacer(modifier = Modifier.width(8.dp))
            DarwinText(
                text = "$percentage%",
                style = DarwinTheme.typography.bodySmall,
                color = colors.textSecondary,
                textAlign = TextAlign.End,
                modifier = Modifier.widthIn(min = 36.dp),
            )
        }
    }
}

// ==================== Circular Progress ====================

/**
 * Darwin UI Circular Progress -- a circular arc progress indicator.
 *
 * Supports determinate and indeterminate modes, multiple color variants,
 * and an optional percentage label drawn in the center.
 *
 * @param value Current progress value (0 to [max]).
 * @param max Maximum progress value.
 * @param size Diameter of the circular indicator.
 * @param strokeWidth Width of the arc stroke.
 * @param variant Color variant for the progress arc.
 * @param indeterminate When true, plays an infinite rotating animation ignoring [value].
 * @param showValue When true, displays the percentage in the center of the circle.
 * @param modifier Modifier applied to the canvas.
 */
@Composable
fun DarwinCircularProgress(
    value: Float = 0f,
    max: Float = 100f,
    size: Dp = 48.dp,
    strokeWidth: Dp = 4.dp,
    variant: DarwinProgressVariant = DarwinProgressVariant.Default,
    indeterminate: Boolean = false,
    showValue: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colors

    val trackColor = if (colors.isDark) Zinc800 else Zinc200
    val fillColor = variantColor(variant)

    // Determinate: animated sweep angle
    val targetFraction = if (indeterminate) 0f else (value / max).coerceIn(0f, 1f)
    val animatedFraction by animateFloatAsState(
        targetValue = targetFraction,
        animationSpec = darwinSpring(DarwinSpringPreset.Smooth),
    )

    // Indeterminate: rotation animation
    val infiniteTransition = rememberInfiniteTransition()
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
    )

    val percentage = ((value / max) * 100f).coerceIn(0f, 100f).toInt()

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stroke = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round,
            )
            val arcSize = Size(
                width = this.size.width - stroke.width,
                height = this.size.height - stroke.width,
            )
            val topLeft = Offset(stroke.width / 2f, stroke.width / 2f)

            // Background circle
            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke,
            )

            if (indeterminate) {
                // Rotating arc covering ~90 degrees
                drawArc(
                    color = fillColor,
                    startAngle = rotationAngle - 90f,
                    sweepAngle = 90f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = stroke,
                )
            } else {
                // Determinate arc
                val sweepAngle = animatedFraction * 360f
                if (sweepAngle > 0f) {
                    if (variant == DarwinProgressVariant.Gradient) {
                        val brush = Brush.sweepGradient(
                            colors = listOf(Blue500, Purple500, Blue500),
                        )
                        drawArc(
                            brush = brush,
                            startAngle = -90f,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            topLeft = topLeft,
                            size = arcSize,
                            style = stroke,
                        )
                    } else {
                        drawArc(
                            color = fillColor,
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

        // Center label
        if (showValue && !indeterminate) {
            DarwinText(
                text = "$percentage%",
                style = DarwinTheme.typography.labelSmall,
                color = colors.textPrimary,
                textAlign = TextAlign.Center,
            )
        }
    }
}
