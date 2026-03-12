package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.fletchmckee.liquid.liquid
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.LocalToolbarGlassState
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import io.github.kdroidfilter.darwinui.theme.darwinTween

// ===========================================================================
// IconButton — macOS-native circular icon button
// ===========================================================================

/**
 * macOS-native circular icon button. Matches NSButton with a circular bezel style.
 *
 * When placed inside a [DarwinScaffold] title bar area, automatically renders
 * with a frosted liquid glass background for a native macOS toolbar look.
 */
@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = 30.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val isDark = DarwinTheme.colorScheme.isDark
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val toolbarGlassState = LocalToolbarGlassState.current

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.93f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "icon_btn_scale",
    )
    val alpha by animateFloatAsState(
        targetValue = if (!enabled) 0.5f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "icon_btn_alpha",
    )

    // Hover/press overlay (used on top of glass or as standalone bg)
    val overlayColor by animateColorAsState(
        targetValue = when {
            isPressed && enabled -> if (isDark) Color.White.copy(alpha = 0.20f) else Color.Black.copy(alpha = 0.10f)
            isHovered && enabled -> if (isDark) Color.White.copy(alpha = 0.14f) else Color.Black.copy(alpha = 0.06f)
            else -> Color.Transparent
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "icon_btn_overlay",
    )

    val bgModifier = if (toolbarGlassState != null) {
        // No background in toolbar — only hover/press overlay
        Modifier.clip(CircleShape)
    } else {
        // Plain background fallback outside toolbar
        val bgColor = when {
            isPressed && enabled -> if (isDark) Color.White.copy(alpha = 0.20f) else Color.Black.copy(alpha = 0.12f)
            isHovered && enabled -> if (isDark) Color.White.copy(alpha = 0.14f) else Color.Black.copy(alpha = 0.10f)
            else -> if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.07f)
        }
        Modifier
            .clip(CircleShape)
            .background(bgColor, CircleShape)
    }

    val contentColor = if (isDark) Color.White.copy(alpha = 0.85f) else Color.Black.copy(alpha = 0.75f)

    val borderColor = if (isDark) Color.White.copy(alpha = 0.12f) else Color.Black.copy(alpha = 0.12f)

    CompositionLocalProvider(
        LocalDarwinContentColor provides contentColor,
        LocalDarwinTextStyle provides DarwinTheme.typography.caption1,
    ) {
        Box(
            modifier = modifier
                .size(size)
                .scale(scale)
                .alpha(alpha)
                .then(bgModifier)
                .then(
                    if (toolbarGlassState != null) {
                        Modifier
                            .border(0.5.dp, borderColor, CircleShape)
                            .background(overlayColor, CircleShape)
                    } else {
                        Modifier
                    }
                )
                .hoverable(interactionSource = interactionSource, enabled = enabled)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick,
                ),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}

// ===========================================================================
// HelpButton — macOS-native help button (NSButton bezelStyle .helpButton)
// ===========================================================================

/**
 * macOS-native help button: circular 24dp button with "?" label.
 * Matches NSButton with bezelStyle = .helpButton.
 */
@Composable
fun HelpButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        size = 24.dp,
        interactionSource = interactionSource,
    ) {
        Text("?")
    }
}
