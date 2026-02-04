package io.github.kdroidfilter.darwinui.components.closebutton

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * macOS-style red close button (traffic light button).
 *
 * Displays a circular red button that shows an X icon on hover,
 * mimicking the native macOS window close button behavior.
 *
 * @param onClick Called when the button is clicked.
 * @param size The diameter of the circular button. Defaults to 12dp.
 * @param modifier Modifier to be applied to the button.
 */
@Composable
fun DarwinCloseButton(
    onClick: () -> Unit,
    size: Dp = 12.dp,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val normalColor = Color(0xFFFF5F57)
    val hoverColor = Color(0xFFE0443E)

    val backgroundColor = if (isHovered) hoverColor else normalColor

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor, CircleShape)
            .hoverable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (isHovered) {
            Canvas(modifier = Modifier.size(size * 0.5f)) {
                val strokeWidth = size.toPx() * 0.1f
                val padding = 0f
                drawLine(
                    color = Color.White,
                    start = Offset(padding, padding),
                    end = Offset(this.size.width - padding, this.size.height - padding),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round,
                )
                drawLine(
                    color = Color.White,
                    start = Offset(this.size.width - padding, padding),
                    end = Offset(padding, this.size.height - padding),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round,
                )
            }
        }
    }
}
