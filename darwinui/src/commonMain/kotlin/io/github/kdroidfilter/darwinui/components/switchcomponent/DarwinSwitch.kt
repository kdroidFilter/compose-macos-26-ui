package io.github.kdroidfilter.darwinui.components.switchcomponent

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.Zinc300
import io.github.kdroidfilter.darwinui.theme.Zinc700
import io.github.kdroidfilter.darwinui.theme.darwinSpring

/**
 * A macOS-inspired toggle switch component for Darwin UI.
 *
 * Features an animated thumb that slides between on and off positions
 * using a snappy spring animation. The track changes color based on state.
 *
 * @param checked Whether the switch is currently in the "on" position.
 * @param onCheckedChange Callback invoked when the user toggles the switch.
 * @param modifier Modifier applied to the root row container.
 * @param label Optional text label displayed to the right of the switch.
 * @param enabled Whether the switch is interactive.
 * @param glass When true, applies a glass-morphism style to the switch track.
 */
@Composable
fun DarwinSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
    glass: Boolean = false,
) {
    val colors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes
    val typography = DarwinTheme.typography

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Track dimensions
    val trackWidth = 44.dp
    val trackHeight = 24.dp
    val thumbSize = 20.dp
    val thumbPadding = 2.dp

    // Thumb offset: slides from left (unchecked) to right (checked)
    // Unchecked position: thumbPadding = 2dp
    // Checked position: trackWidth - thumbSize - thumbPadding = 44 - 20 - 2 = 22dp
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) (trackWidth - thumbSize - thumbPadding) else thumbPadding,
        animationSpec = darwinSpring(
            preset = DarwinSpringPreset.Snappy,
        ),
        label = "switchThumbOffset",
    )

    val disabledAlpha = if (enabled) 1f else 0.5f

    // Track color
    val trackColor = when {
        glass && checked -> colors.glassBackground
        checked -> colors.accent
        glass -> colors.glassBackground
        colors.isDark -> Zinc700
        else -> Zinc300
    }

    // Track border
    val trackBorderColor = when {
        isFocused -> colors.ring
        glass -> colors.glassBorder
        checked -> colors.accent
        colors.isDark -> Zinc700
        else -> Zinc300
    }

    // Focus ring
    val focusRingColor = if (isFocused) colors.ring.copy(alpha = 0.5f) else Color.Transparent

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .alpha(disabledAlpha)
            .toggleable(
                value = checked,
                onValueChange = { newValue ->
                    if (enabled) onCheckedChange(newValue)
                },
                enabled = enabled,
                role = Role.Switch,
                interactionSource = interactionSource,
                indication = null,
            ),
    ) {
        // Focus ring wrapper
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(width = trackWidth + 4.dp, height = trackHeight + 4.dp),
        ) {
            // Focus ring
            if (isFocused) {
                Box(
                    modifier = Modifier
                        .size(width = trackWidth + 4.dp, height = trackHeight + 4.dp)
                        .clip(shapes.full)
                        .border(
                            width = 2.dp,
                            color = focusRingColor,
                            shape = shapes.full,
                        )
                )
            }

            // Track
            Box(
                modifier = Modifier
                    .size(width = trackWidth, height = trackHeight)
                    .clip(shapes.full)
                    .background(trackColor, shapes.full)
                    .border(
                        width = if (glass) 1.dp else 0.dp,
                        color = if (glass) trackBorderColor else Color.Transparent,
                        shape = shapes.full,
                    ),
            ) {
                // Thumb
                Box(
                    modifier = Modifier
                        .offset(x = thumbOffset)
                        .align(Alignment.CenterStart)
                        .size(thumbSize)
                        .shadow(
                            elevation = 2.dp,
                            shape = CircleShape,
                            clip = false,
                        )
                        .clip(CircleShape)
                        .background(Color.White, CircleShape),
                )
            }
        }

        // Optional label
        if (label != null) {
            Spacer(modifier = Modifier.width(8.dp))
            BasicText(
                text = label,
                style = typography.bodyMedium.merge(
                    TextStyle(color = colors.textPrimary)
                ),
            )
        }
    }
}
