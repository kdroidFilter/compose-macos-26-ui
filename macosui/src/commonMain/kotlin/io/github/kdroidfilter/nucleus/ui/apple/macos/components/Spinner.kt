package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalControlSize
import kotlin.math.floor

private const val SPOKE_COUNT = 8
private const val ROTATION_DURATION_MS = 800

// Opacity values for each spoke relative to the brightest one (index 0 = brightest)
private val SPOKE_OPACITIES = floatArrayOf(1f, 0.87f, 0.75f, 0.63f, 0.51f, 0.39f, 0.27f, 0.15f)

/**
 * macOS-style indeterminate spinner with 8 radial spokes.
 *
 * @param modifier Modifier applied to the component.
 * @param size Diameter of the spinner.
 * @param color Color of the spokes.
 */
@Composable
fun Spinner(
    modifier: Modifier = Modifier,
    size: Dp = MacosTheme.componentStyling.progress.metrics.spinnerSizeFor(LocalControlSize.current),
    color: Color = MacosTheme.colorScheme.textSecondary,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "spinner")
    val animatedStep by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = SPOKE_COUNT.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = ROTATION_DURATION_MS,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "spinner_step",
    )

    Canvas(modifier = modifier.size(size)) {
        val currentStep = floor(animatedStep).toInt() % SPOKE_COUNT
        drawSpokes(color, currentStep)
    }
}

/**
 * macOS-style indeterminate spinner with a text label.
 *
 * @param label Text displayed next to the spinner.
 * @param modifier Modifier applied to the row container.
 * @param spinnerSize Diameter of the spinner.
 * @param color Color of the spokes.
 */
@Composable
fun Spinner(
    label: String,
    modifier: Modifier = Modifier,
    spinnerSize: Dp = MacosTheme.componentStyling.progress.metrics.spinnerSizeFor(LocalControlSize.current),
    color: Color = MacosTheme.colorScheme.textSecondary,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spinner(size = spinnerSize, color = color)
        Text(
            text = label,
            style = MacosTheme.typography.body,
            color = MacosTheme.colorScheme.textSecondary,
        )
    }
}

private fun DrawScope.drawSpokes(color: Color, currentStep: Int) {
    val diameter = size.minDimension
    // Spoke proportions derived from Sketch: small 3/22 wide, 7/22 tall; large 4/30 wide, 10/30 tall
    val spokeWidth = diameter * 4f / 30f
    val spokeHeight = diameter * 10f / 30f
    val cornerRadius = CornerRadius(spokeWidth / 2f, spokeWidth / 2f)
    val center = Offset(size.width / 2f, size.height / 2f)
    // Spoke is drawn from top edge toward center
    val spokeTopLeft = Offset(
        x = center.x - spokeWidth / 2f,
        y = 0f,
    )
    val spokeSize = Size(spokeWidth, spokeHeight)

    for (i in 0 until SPOKE_COUNT) {
        val angle = i * 360f / SPOKE_COUNT
        // Opacity index: spoke 0 is brightest, then fading around
        val opacityIndex = (i - currentStep + SPOKE_COUNT) % SPOKE_COUNT
        val alpha = SPOKE_OPACITIES[opacityIndex]

        rotate(degrees = angle, pivot = center) {
            drawRoundRect(
                color = color.copy(alpha = alpha * color.alpha),
                topLeft = spokeTopLeft,
                size = spokeSize,
                cornerRadius = cornerRadius,
            )
        }
    }
}

@Preview
@Composable
private fun SpinnerPreview() {
    MacosTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Spinner()
            Spinner(label = "Checking for Update\u2026")
        }
    }
}
