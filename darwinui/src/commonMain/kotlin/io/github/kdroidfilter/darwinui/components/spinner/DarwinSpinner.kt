package io.github.kdroidfilter.darwinui.components.spinner

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A small indeterminate circular spinner drawn with Canvas.
 *
 * Replaces `material3.CircularProgressIndicator` for use in loading states
 * (e.g., the [DarwinButton] loading spinner).
 *
 * @param modifier Modifier applied to the canvas.
 * @param color The color of the spinning arc.
 * @param size Diameter of the spinner.
 * @param strokeWidth Width of the arc stroke.
 */
@Composable
fun DarwinSpinner(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    size: Dp = 16.dp,
    strokeWidth: Dp = 2.dp,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
    )

    Canvas(modifier = modifier.size(size)) {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            cap = StrokeCap.Round,
        )
        val arcSize = androidx.compose.ui.geometry.Size(
            width = this.size.width - stroke.width,
            height = this.size.height - stroke.width,
        )
        val topLeft = androidx.compose.ui.geometry.Offset(
            x = stroke.width / 2f,
            y = stroke.width / 2f,
        )

        drawArc(
            color = color,
            startAngle = rotation - 90f,
            sweepAngle = 270f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = stroke,
        )
    }
}
