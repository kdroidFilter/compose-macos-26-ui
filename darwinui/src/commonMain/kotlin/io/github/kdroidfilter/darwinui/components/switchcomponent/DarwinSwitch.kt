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

// Emerald-500 — the React darwin-ui switch "on" color
private val Emerald500 = Color(0xFF10B981)

/**
 * A macOS-inspired toggle switch matching the React darwin-ui Switch.
 *
 * React dimensions: track w-9 h-5 (36×20px), thumb h-4 w-4 (16px),
 * track padding px-0.5 (2px), thumb travel 16px.
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

    // React: w-9 h-5 = 36×20px
    val trackWidth = 36.dp
    val trackHeight = 20.dp
    // React: h-4 w-4 = 16px
    val thumbSize = 16.dp
    // React: px-0.5 = 2px
    val thumbPadding = 2.dp

    // React: animate x from 0 to 16
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) 16.dp else 0.dp,
        animationSpec = darwinSpring(preset = DarwinSpringPreset.Snappy),
        label = "switchThumbOffset",
    )

    val disabledAlpha = if (enabled) 1f else 0.5f

    // Track color — React:
    // checked: bg-emerald-500
    // glass unchecked: bg-white/60 dark:bg-zinc-900/60
    // default unchecked: bg-zinc-600 (same for light and dark)
    val trackColor = when {
        checked -> Emerald500
        glass -> if (colors.isDark) Color(0x99181818) else Color(0x99FFFFFF)
        else -> Zinc600
    }

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
            // Thumb — React: h-4 w-4 rounded-full bg-white shadow-sm
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

        // Optional label — React: gap-2 text-[13px]
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
