package io.github.kdroidfilter.darwinui.components.window

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.closebutton.DarwinCloseButton
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

// Traffic light button colors
private val TrafficLightYellow = Color(0xFFFEBC2E)
private val TrafficLightGreen = Color(0xFF28C840)

/**
 * macOS-style window container with a title bar.
 *
 * Provides a window-like container featuring the iconic macOS traffic light
 * buttons (close, minimize, maximize) in the title bar, a centered title,
 * and a content area below.
 *
 * Only the close button is functional; the yellow (minimize) and green
 * (maximize) buttons are purely decorative.
 *
 * @param title The text displayed in the center of the title bar.
 * @param onClose Callback invoked when the close button is clicked.
 *   When null, no close button action is provided but the traffic
 *   light dots are still displayed.
 * @param modifier Modifier to be applied to the outer window container.
 * @param content The composable content displayed below the title bar.
 */
@Composable
fun DarwinWindow(
    title: String = "",
    onClose: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes
    val typography = DarwinTheme.typography

    val shape = shapes.extraLarge
    val backgroundColor = colors.card
    val borderColor = colors.border

    Column(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = shape,
                clip = false,
            )
            .clip(shape)
            .background(backgroundColor, shape)
            .border(width = 1.dp, color = borderColor, shape = shape),
    ) {
        // ---- Title bar ----
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 12.dp),
        ) {
            // Left side: traffic light buttons
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Close button (functional)
                if (onClose != null) {
                    DarwinCloseButton(
                        onClick = onClose,
                        size = 12.dp,
                    )
                } else {
                    // Decorative red dot when no callback
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFF5F57), CircleShape),
                    )
                }

                // Minimize button (decorative)
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(TrafficLightYellow, CircleShape),
                )

                // Maximize button (decorative)
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(TrafficLightGreen, CircleShape),
                )
            }

            // Center: title text
            if (title.isNotEmpty()) {
                DarwinText(
                    text = title,
                    style = typography.labelMedium,
                    color = colors.textSecondary,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }

        // ---- Bottom border of title bar ----
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colors.borderSubtle),
        )

        // ---- Content area ----
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            content()
        }
    }
}
