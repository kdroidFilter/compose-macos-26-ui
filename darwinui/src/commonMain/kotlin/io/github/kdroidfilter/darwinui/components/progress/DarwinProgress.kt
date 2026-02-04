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

enum class DarwinProgressSize(val height: Dp) {
    Sm(4.dp),   // h-1 = 4dp
    Md(8.dp),   // h-2 = 8dp
    Lg(12.dp),  // h-3 = 12dp
}

enum class DarwinProgressVariant {
    Default,
    Success,
    Warning,
    Danger,
    Gradient,
}

// ==================== Color Helpers ====================

private fun variantColor(variant: DarwinProgressVariant): Color = when (variant) {
    DarwinProgressVariant.Default -> Blue500       // bg-blue-500
    DarwinProgressVariant.Success -> Emerald500    // bg-emerald-500
    DarwinProgressVariant.Warning -> Amber500      // bg-amber-500
    DarwinProgressVariant.Danger -> Red500         // bg-red-500
    DarwinProgressVariant.Gradient -> Blue500      // fallback; gradient handled separately
}

private fun gradientBrush(width: Float): Brush = Brush.linearGradient(
    colors = listOf(Blue500, Violet500, Red500),
    start = Offset.Zero,
    end = Offset(width, 0f),
)

// ==================== Linear Progress ====================

@Composable
fun DarwinLinearProgress(
    value: Float = 0f,
    max: Float = 100f,
    size: DarwinProgressSize = DarwinProgressSize.Md,
    variant: DarwinProgressVariant = DarwinProgressVariant.Default,
    indeterminate: Boolean = false,
    showValue: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val isDark = DarwinTheme.colors.isDark

    // Track color: bg-black/10 dark:bg-white/10
    val trackColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

    val fillColor = variantColor(variant)
    val useGradient = variant == DarwinProgressVariant.Gradient

    // CSS ease-out = cubic-bezier(0, 0, 0.58, 1)
    val targetFraction = if (indeterminate) 0f else (value / max).coerceIn(0f, 1f)
    val animatedFraction by animateFloatAsState(
        targetValue = targetFraction,
        animationSpec = tween(
            durationMillis = 400,
            easing = CubicBezierEasing(0f, 0f, 0.58f, 1f), // CSS ease-out
        ),
    )

    // Indeterminate: w-1/3 bar, x: ["-100%", "400%"], 1.5s easeInOut
    // Bar width = 1/3 of track. Translate -100% to 400% of bar width.
    // CSS translate(-100%) = -barWidth, translate(400%) = 4*barWidth
    // So bar left edge goes from -barWidth to 4*barWidth.
    // Normalized: animate from -1 to 4 (fractions of barWidth)
    val infiniteTransition = rememberInfiniteTransition(label = "progress_indeterminate")
    val indeterminateOffset by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = FastOutSlowInEasing, // CSS easeInOut
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
            val cornerRadius = CornerRadius(this.size.height / 2f) // rounded-full

            // Background track
            drawRoundRect(
                color = trackColor,
                cornerRadius = cornerRadius,
                size = this.size,
            )

            if (indeterminate) {
                // Bar is 1/3 of track width
                val barWidth = this.size.width / 3f
                // startX = indeterminateOffset * barWidth
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
                // Determinate fill
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

        // Percentage label: mt-1 text-right text-xs text-zinc-600 dark:text-zinc-400
        if (showValue && !indeterminate) {
            Spacer(modifier = Modifier.height(4.dp)) // mt-1 = 4dp
            DarwinText(
                text = "$percentage%",
                style = DarwinTheme.typography.labelSmall, // text-xs
                color = if (isDark) Zinc400 else Zinc600,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

// ==================== Circular Progress ====================

@Composable
fun DarwinCircularProgress(
    value: Float = 0f,
    max: Float = 100f,
    size: Dp = 40.dp,
    strokeWidth: Dp = 4.dp,
    variant: DarwinProgressVariant = DarwinProgressVariant.Default,
    indeterminate: Boolean = false,
    showValue: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val isDark = DarwinTheme.colors.isDark

    // Track color: text-black/10 dark:text-white/10
    val trackColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

    val fillColor = variantColor(variant)

    val targetFraction = if (indeterminate) 0f else (value / max).coerceIn(0f, 1f)
    val animatedFraction by animateFloatAsState(
        targetValue = targetFraction,
        animationSpec = tween(
            durationMillis = 400,
            easing = CubicBezierEasing(0f, 0f, 0.58f, 1f), // CSS ease-out
        ),
    )

    // over 2s linear. This creates a drawing/erasing arc that also rotates.
    // We model this with a single 0→1 progress that maps to startAngle + sweepAngle.
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

            // Background circle track
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

                //   SVG parent: -rotate-90 (path starts at top)
                //   strokeDasharray: C, strokeDashoffset: [C, -C]
                //   rotate: [0, 360], duration: 2s linear
                //
                // Phase 1 (t<0.5): head races forward, tail follows slowly → arc grows
                // Phase 2 (t≥0.5): tail races forward, head slows down → arc shrinks
                val t = indeterminateProgress
                val startAngle: Float
                val sweepAngle: Float
                if (t < 0.5f) {
                    // Tail: -90+360t, Head: -90+1080t → sweep = 720t
                    startAngle = -90f + 360f * t
                    sweepAngle = 720f * t
                } else {
                    // Tail: -450+1080t, Head: 270+360t → sweep = 720(1-t)
                    startAngle = -450f + 1080f * t
                    sweepAngle = 720f * (1f - t)
                }

                drawArc(
                    color = fillColor,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle.coerceIn(0f, 360f),
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = stroke,
                )
            } else {
                // Determinate arc: starts from top (-90°)
                val sweepAngle = animatedFraction * 360f
                if (sweepAngle > 0f) {
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

        // Center label: text-xs font-medium text-zinc-700 dark:text-zinc-300
        if (showValue && !indeterminate) {
            DarwinText(
                text = "$percentage%",
                style = DarwinTheme.typography.labelSmall,
                color = if (isDark) Zinc300 else Zinc700,
                textAlign = TextAlign.Center,
            )
        }
    }
}
