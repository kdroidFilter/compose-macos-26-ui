package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalControlSize

// ==================== Linear Progress ====================

/**
 * macOS-style linear progress indicator.
 *
 * @param value Current progress value.
 * @param max Maximum progress value.
 * @param enabled Active (accent colored) or inactive (gray).
 * @param indeterminate Whether to display the looping indeterminate animation.
 * @param modifier Modifier applied to the component.
 */
@Composable
fun LinearProgress(
    value: Float = 0f,
    max: Float = 100f,
    enabled: Boolean = true,
    indeterminate: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val controlSize = LocalControlSize.current
    val height = MacosTheme.componentStyling.progress.metrics.heightFor(controlSize)
    val accentColor = MacosTheme.colorScheme.accent

    // Determinate animation
    val targetFraction = if (indeterminate) 0f else (value / max).coerceIn(0f, 1f)
    val animatedFraction by animateFloatAsState(
        targetValue = targetFraction,
        animationSpec = tween(400, easing = CubicBezierEasing(0f, 0f, 0.58f, 1f)),
    )

    // Indeterminate: continuous sweep 0→1, bar slides from off-left to off-right
    val infiniteTransition = rememberInfiniteTransition(label = "progress_indeterminate")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = CubicBezierEasing(0.4f, 0f, 0.6f, 1f)),
            repeatMode = RepeatMode.Restart,
        ),
        label = "indeterminate_phase",
    )

    Canvas(
        modifier = modifier.fillMaxWidth().height(height),
    ) {
        val trackCornerRadius = CornerRadius(size.height / 2f)

        // Track fill
        drawRoundRect(
            color = Color.Black.copy(alpha = 0.06f),
            cornerRadius = trackCornerRadius,
            size = size,
        )

        // Inner shadow approximation
        drawRoundRect(
            color = Color.Black.copy(alpha = 0.04f),
            cornerRadius = trackCornerRadius,
            size = size,
            style = Stroke(width = 1f),
        )

        if (indeterminate) {
            drawIndeterminateBar(phase, accentColor, enabled)
        } else {
            val fillWidth = animatedFraction * size.width
            if (fillWidth > 0f) {
                drawDeterminateBar(fillWidth, accentColor, enabled)
            }
        }
    }
}

private fun DrawScope.drawDeterminateBar(
    width: Float,
    accentColor: Color,
    enabled: Boolean,
) {
    val cr = CornerRadius(size.height / 2f)
    val barSize = Size(width, size.height)

    if (enabled) {
        // 4 layered fills matching macOS (bottom to top)
        drawRoundRect(color = accentColor, cornerRadius = cr, size = barSize)
        drawRoundRect(color = Color.Black.copy(alpha = 0.10f), cornerRadius = cr, size = barSize)
        drawRoundRect(color = accentColor.copy(alpha = 0.50f), cornerRadius = cr, size = barSize)
        drawRoundRect(color = accentColor.copy(alpha = 0.50f), cornerRadius = cr, size = barSize)
    } else {
        drawRoundRect(color = Color.Gray.copy(alpha = 0.3f), cornerRadius = cr, size = barSize)
        drawRoundRect(color = Color.Gray.copy(alpha = 0.2f), cornerRadius = cr, size = barSize)
    }
}

private fun DrawScope.drawIndeterminateBar(
    phase: Float,
    accentColor: Color,
    enabled: Boolean,
) {
    val barFraction = 0.34f
    val barWidth = size.width * barFraction
    // Bar travels from fully off-left to fully off-right
    val totalTravel = size.width + barWidth
    val barX = -barWidth + phase * totalTravel

    // Clip to track shape so the bar merges with rounded edges naturally
    val trackPath = Path().apply {
        addRoundRect(
            RoundRect(
                left = 0f,
                top = 0f,
                right = size.width,
                bottom = size.height,
                cornerRadius = CornerRadius(size.height / 2f),
            )
        )
    }

    val baseColor = if (enabled) accentColor else Color.Gray.copy(alpha = 0.3f)

    // Gradient: transparent on trailing (left) edge, solid on leading (right) edge
    val colorStops = arrayOf(
        0.00f to baseColor.copy(alpha = 0f),
        0.30f to baseColor.copy(alpha = 0.20f),
        0.60f to baseColor.copy(alpha = 0.55f),
        0.85f to baseColor.copy(alpha = 0.85f),
        1.00f to baseColor,
    )

    clipPath(trackPath) {
        drawRect(
            brush = Brush.linearGradient(
                colorStops = colorStops,
                start = Offset(barX, 0f),
                end = Offset(barX + barWidth, 0f),
            ),
            topLeft = Offset(barX, 0f),
            size = Size(barWidth, size.height),
        )
    }
}


@Preview
@Composable
private fun ProgressPreview() {
    MacosTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            LinearProgress(value = 60f)
            LinearProgress(value = 60f, enabled = false)
            LinearProgress(indeterminate = true)
        }
    }
}
