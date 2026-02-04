package io.github.kdroidfilter.darwinui.components.switchcomponent

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.Zinc600
import io.github.kdroidfilter.darwinui.theme.darwinSpring

private val Emerald500 = Color(0xFF10B981)

@Composable
fun DarwinSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
) {
    val colors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes
    val typography = DarwinTheme.typography

    val interactionSource = remember { MutableInteractionSource() }

    val trackWidth = 36.dp
    val trackHeight = 20.dp

    val thumbSize = 16.dp

    val thumbPadding = 2.dp

    val thumbOffset by animateDpAsState(
        targetValue = if (checked) 16.dp else 0.dp,
        animationSpec = darwinSpring(preset = DarwinSpringPreset.Snappy),
        label = "switchThumbOffset",
    )

    val disabledAlpha = if (enabled) 1f else 0.5f

    // checked: bg-emerald-500
    // default unchecked: bg-zinc-600 (same for light and dark)
    val trackColor = if (checked) Emerald500 else Zinc600

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
        // Track
        Box(
            modifier = Modifier
                .size(width = trackWidth, height = trackHeight)
                .clip(shapes.full)
                .background(trackColor, shapes.full),
        ) {

            Box(
                modifier = Modifier
                    .offset(x = thumbOffset + thumbPadding)
                    .align(Alignment.CenterStart)
                    .size(thumbSize)
                    .shadow(
                        elevation = 1.dp,
                        shape = CircleShape,
                        clip = false,
                    )
                    .clip(CircleShape)
                    .background(Color.White, CircleShape),
            )
        }

        if (label != null) {
            Spacer(modifier = Modifier.width(8.dp))
            BasicText(
                text = label,
                style = typography.bodyMedium.merge(
                    TextStyle(
                        color = colors.textPrimary,
                        fontSize = 13.sp,
                    )
                ),
            )
        }
    }
}
