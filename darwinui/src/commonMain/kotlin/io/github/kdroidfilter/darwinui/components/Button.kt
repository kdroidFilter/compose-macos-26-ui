package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideChevronDown
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinSurface
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.ControlSize
import io.github.kdroidfilter.darwinui.theme.LocalControlSize
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinSurface
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import io.github.kdroidfilter.darwinui.theme.darwinGlass
import io.github.kdroidfilter.darwinui.theme.darwinTween
import io.github.kdroidfilter.darwinui.theme.iconGap
import io.github.kdroidfilter.darwinui.theme.iconSize
import io.github.kdroidfilter.darwinui.theme.labelStyle

// ===========================================================================
// PushButtonStyle — macOS 26 push button visual styles
//
// Matches the Sketch Buttons page categories:
//   Push Button / Content Area / {style} / {size} / {state}
// ===========================================================================

/**
 * Visual style for [PushButton], matching macOS 26 Sketch specifications.
 */
enum class PushButtonStyle {
    /** Solid accent-filled button — primary action (e.g. "OK", "Save"). */
    Default,

    /** Solid accent-filled toggle button — visually identical to [Default] but supports on/off. */
    Accent,

    /** Red-tinted background — destructive action (e.g. "Delete"). */
    Destructive,

    /** Accent-tinted background with accent text — secondary action, supports on/off. */
    Secondary,

    /** Transparent background, accent text — no visual on/off feedback, caller changes the label. */
    Borderless,

    /** Transparent when off, accent-filled bezel when on — hover/press shows overlays. */
    BorderlessBezel,

    /** Gray background, neutral text — supports on/off with slightly darker fill when selected. */
    Neutral,
}

// ===========================================================================
// PushButton — unified macOS push button
// ===========================================================================

/**
 * macOS 26 push button matching Apple Sketch specifications.
 *
 * Supports all visual styles via [style] parameter, optional toggle state
 * via [selected], and all five control sizes via [ControlSize].
 *
 * @param onClick Called when the button is clicked.
 * @param style Visual style of the button.
 * @param selected Toggle state for styles that support it (Accent, Secondary, Borderless, Neutral).
 *   Null means the button is not toggleable.
 * @param enabled Whether the button is interactive.
 */
@Composable
fun PushButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: PushButtonStyle = PushButtonStyle.Default,
    selected: Boolean = false,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    val controlSize = LocalControlSize.current
    val metrics = DarwinTheme.componentStyling.defaultButton.metrics
    val isDark = DarwinTheme.colorScheme.isDark
    val accent = DarwinTheme.colorScheme.accent
    val surface = LocalDarwinSurface.current

    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "push_scale",
    )

    val colors = resolvePushButtonColors(style, selected, isDark, accent, surface)

    val bgColor by animateColorAsState(
        targetValue = when {
            !enabled -> colors.backgroundDisabled
            isPressed -> colors.backgroundPressed
            selected -> colors.backgroundSelected
            else -> colors.background
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "push_bg",
    )
    val contentColor by animateColorAsState(
        targetValue = when {
            !enabled -> colors.contentDisabled
            isPressed && colors.contentPressed != Color.Unspecified -> colors.contentPressed
            selected && (style == PushButtonStyle.Borderless || style == PushButtonStyle.BorderlessBezel) -> colors.contentSelected
            else -> colors.content
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "push_content",
    )
    val overlayColor by animateColorAsState(
        targetValue = when {
            isPressed && enabled -> colors.pressOverlay
            isHovered && enabled -> colors.hoverOverlay
            else -> Color.Transparent
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "push_overlay",
    )

    val shape = RoundedCornerShape(metrics.cornerSizeFor(controlSize))

    CompositionLocalProvider(
        LocalDarwinContentColor provides contentColor,
        LocalDarwinTextStyle provides controlSize.labelStyle(),
    ) {
        Box(
            modifier = modifier
                .scale(scale)
                .height(metrics.minHeightFor(controlSize))
                .clip(shape)
                .background(bgColor, shape)
                .background(overlayColor, shape)
                .hoverable(interactionSource = interactionSource, enabled = enabled)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick,
                )
                .padding(horizontal = metrics.horizontalPaddingFor(controlSize)),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                content = content,
            )
        }
    }
}

/** Convenience overload with text label and optional leading icon. */
@Composable
fun PushButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    style: PushButtonStyle = PushButtonStyle.Default,
    selected: Boolean = false,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    PushButton(onClick, modifier, style, selected, enabled, interactionSource) {
        if (icon != null) {
            val controlSize = LocalControlSize.current
            Icon(
                imageVector = icon,
                modifier = Modifier.size(controlSize.iconSize()),
                tint = LocalDarwinContentColor.current,
            )
            Spacer(Modifier.width(controlSize.iconGap()))
        }
        Text(text)
    }
}

/** Icon-only convenience overload — no text label. */
@Composable
fun PushButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: PushButtonStyle = PushButtonStyle.Default,
    selected: Boolean = false,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    PushButton(onClick, modifier, style, selected, enabled, interactionSource) {
        val controlSize = LocalControlSize.current
        Icon(
            imageVector = icon,
            modifier = Modifier.size(controlSize.iconSize()),
            tint = LocalDarwinContentColor.current,
        )
    }
}

// ===========================================================================
// PushButton color resolution — from Sketch specs
// ===========================================================================

private data class PushButtonResolvedColors(
    val background: Color,
    val backgroundPressed: Color,
    val backgroundDisabled: Color,
    val backgroundSelected: Color,
    val content: Color,
    val contentPressed: Color = Color.Unspecified,
    val contentDisabled: Color,
    val contentSelected: Color,
    /** Overlay applied on press (layered on top of background). */
    val pressOverlay: Color = Color.Transparent,
    val hoverOverlay: Color,
)

@Composable
private fun resolvePushButtonColors(
    style: PushButtonStyle,
    selected: Boolean,
    isDark: Boolean,
    accent: Color,
    surface: DarwinSurface,
): PushButtonResolvedColors {
    val overGlass = surface == DarwinSurface.OverGlass
    return when (style) {
        PushButtonStyle.Default -> PushButtonResolvedColors(
            background = accent,
            backgroundPressed = accent,
            backgroundDisabled = accent.copy(alpha = if (overGlass) 0.50f else 0.40f),
            backgroundSelected = accent,
            content = Color.White,
            contentDisabled = Color.White.copy(alpha = 0.50f),
            contentSelected = Color.White,
            // Over-glass: stronger press overlay (0x26 vs 0x14 from Sketch)
            pressOverlay = Color.Black.copy(alpha = if (overGlass) 0.15f else 0.08f),
            hoverOverlay = Color.White.copy(alpha = 0.10f),
        )

        PushButtonStyle.Accent -> PushButtonResolvedColors(
            background = accent,
            backgroundPressed = accent,
            backgroundDisabled = accent.copy(alpha = if (overGlass) 0.50f else 0.50f),
            backgroundSelected = accent,
            content = Color.White,
            contentDisabled = Color.White.copy(alpha = 0.50f),
            contentSelected = Color.White,
            pressOverlay = Color.Black.copy(alpha = if (overGlass) 0.15f else 0.15f),
            hoverOverlay = Color.White.copy(alpha = 0.10f),
        )

        PushButtonStyle.Destructive -> {
            val destructive = DarwinTheme.colorScheme.destructive
            PushButtonResolvedColors(
                background = destructive.copy(alpha = 0.25f),
                backgroundPressed = destructive.copy(alpha = 0.25f),
                backgroundDisabled = destructive.copy(alpha = 0.25f),
                backgroundSelected = destructive.copy(alpha = 0.25f),
                content = destructive,
                contentDisabled = destructive.copy(alpha = 0.40f),
                contentSelected = destructive,
                pressOverlay = Color.Black.copy(alpha = 0.15f),
                hoverOverlay = destructive.copy(alpha = 0.08f),
            )
        }

        PushButtonStyle.Secondary -> PushButtonResolvedColors(
            background = accent.copy(alpha = 0.10f),
            backgroundPressed = accent.copy(alpha = 0.10f),
            backgroundDisabled = accent.copy(alpha = 0.10f),
            backgroundSelected = accent.copy(alpha = 0.10f),
            content = accent,
            contentDisabled = accent.copy(alpha = 0.40f),
            contentSelected = accent,
            pressOverlay = Color.Black.copy(alpha = 0.08f),
            hoverOverlay = Color.Black.copy(alpha = if (isDark) 0.10f else 0.08f),
        )

        PushButtonStyle.Borderless -> {
            // Sketch: Clicked state has text fills accent + black 20% = darkened accent
            val pressedAccent = lerp(accent, Color.Black, 0.20f)
            PushButtonResolvedColors(
                background = Color.Transparent,
                backgroundPressed = Color.Transparent,
                backgroundDisabled = Color.Transparent,
                backgroundSelected = Color.Transparent,
                content = accent,
                contentPressed = pressedAccent,
                contentDisabled = accent.copy(alpha = 0.40f),
                contentSelected = accent,
                pressOverlay = Color.Transparent,
                hoverOverlay = Color.Transparent,
            )
        }

        PushButtonStyle.BorderlessBezel -> {
            val base = if (isDark) Color.White else Color.Black
            PushButtonResolvedColors(
                background = Color.Transparent,
                backgroundPressed = Color.Transparent,
                backgroundDisabled = if (selected) accent.copy(alpha = 0.50f) else Color.Transparent,
                backgroundSelected = if (selected) accent else Color.Transparent,
                content = accent,
                contentDisabled = if (selected) Color.White else accent.copy(alpha = 0.40f),
                contentSelected = if (selected) Color.White else accent,
                pressOverlay = if (selected) {
                    // On + pressed: accent fill + black 15%
                    Color.Black.copy(alpha = 0.15f)
                } else {
                    // Off + pressed: black 15%
                    base.copy(alpha = 0.15f)
                },
                hoverOverlay = base.copy(alpha = 0.08f),
            )
        }

        PushButtonStyle.Neutral -> {
            val base = if (isDark) Color.White else Color.Black
            val textColor = if (isDark) Color.White else Color(0xFF1A1A1A)
            val disabledTextColor = if (isDark) Color(0xFF636366) else Color(0xFFBFBFBF)
            PushButtonResolvedColors(
                background = base.copy(alpha = 0.05f),
                backgroundPressed = base.copy(alpha = 0.15f),
                backgroundDisabled = base.copy(alpha = if (overGlass) 0.03f else 0.04f),
                backgroundSelected = base.copy(alpha = 0.13f),
                content = textColor,
                contentDisabled = disabledTextColor,
                contentSelected = textColor,
                pressOverlay = Color.Transparent,
                hoverOverlay = base.copy(alpha = 0.04f),
            )
        }
    }
}

// ===========================================================================
// ArrowButton — macOS-native popup chevron button
// ===========================================================================

/**
 * macOS 26 arrow/popup button: circular button with a chevron (popup indicator).
 * Matches the Sketch "Arrow Button / Content Area" specs.
 */
@Composable
fun ArrowButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val controlSize = LocalControlSize.current
    val isDark = DarwinTheme.colorScheme.isDark
    val surface = LocalDarwinSurface.current
    val overGlass = surface == DarwinSurface.OverGlass
    val isPressed by interactionSource.collectIsPressedAsState()

    val base = if (isDark) Color.White else Color.Black
    val bgColor by animateColorAsState(
        targetValue = when {
            !enabled -> base.copy(alpha = if (overGlass) 0.03f else 0.04f)
            isPressed -> base.copy(alpha = 0.15f)
            else -> base.copy(alpha = 0.05f)
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "arrow_bg",
    )
    val iconColor = when {
        !enabled -> base.copy(alpha = 0.25f)
        else -> base.copy(alpha = 0.85f)
    }

    val size = when (controlSize) {
        ControlSize.Mini -> 16.dp
        ControlSize.Small -> 20.dp
        ControlSize.Regular -> 24.dp
        ControlSize.Large -> 28.dp
        ControlSize.ExtraLarge -> 36.dp
    }
    val iconSize = when (controlSize) {
        ControlSize.Mini -> 10.dp
        ControlSize.Small -> 12.dp
        ControlSize.Regular -> 14.dp
        ControlSize.Large -> 16.dp
        ControlSize.ExtraLarge -> 20.dp
    }
    val circleShape = RoundedCornerShape(50)

    Box(
        modifier = modifier
            .size(size)
            .clip(circleShape)
            .background(bgColor, circleShape)
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
        Icon(
            imageVector = LucideChevronDown,
            modifier = Modifier.size(iconSize),
            tint = iconColor,
        )
    }
}

// ===========================================================================
// PulldownButton — macOS-native pulldown/popup button (flat bezel style)
// ===========================================================================

/**
 * macOS-native pulldown button with content + up/down chevron arrows.
 * Matches NSPopUpButton flat bezel style.
 */
@Composable
fun PulldownButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    val controlSize = LocalControlSize.current
    val metrics = DarwinTheme.componentStyling.defaultButton.metrics
    val isDark = DarwinTheme.colorScheme.isDark
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "pulldown_scale",
    )
    val alpha by animateFloatAsState(
        targetValue = if (!enabled) 0.5f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "pulldown_alpha",
    )
    val hoverOverlay by animateColorAsState(
        targetValue = when {
            !isHovered || !enabled -> Color.Transparent
            isDark -> Color.White.copy(alpha = 0.05f)
            else -> Color.Black.copy(alpha = 0.04f)
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "pulldown_hover",
    )

    val bgColor = if (isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.05f)
    val contentColor = if (isDark) Color.White else Color(0xFF1A1A1A)
    val shape = RoundedCornerShape(metrics.cornerSizeFor(controlSize))

    CompositionLocalProvider(
        LocalDarwinContentColor provides contentColor,
        LocalDarwinTextStyle provides controlSize.labelStyle(),
    ) {
        Box(
            modifier = modifier
                .scale(scale)
                .alpha(alpha)
                .height(metrics.minHeightFor(controlSize))
                .clip(shape)
                .background(bgColor, shape)
                .background(hoverOverlay, shape)
                .hoverable(interactionSource = interactionSource, enabled = enabled)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick,
                )
                .padding(horizontal = metrics.horizontalPaddingFor(controlSize)),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                content()
                Spacer(Modifier.width(4.dp))
                PulldownChevrons(tint = contentColor)
            }
        }
    }
}

@Composable
fun PulldownButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    PulldownButton(onClick, modifier, enabled, interactionSource) {
        Text(text)
    }
}

// ===========================================================================
// GlassPulldownButton — macOS-native pulldown button with glass appearance
// ===========================================================================

@Composable
fun GlassPulldownButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    val controlSize = LocalControlSize.current
    val metrics = DarwinTheme.componentStyling.defaultButton.metrics
    val isDark = DarwinTheme.colorScheme.isDark
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "glass_pulldown_scale",
    )
    val alpha by animateFloatAsState(
        targetValue = if (!enabled) 0.5f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "glass_pulldown_alpha",
    )
    val hoverOverlay by animateColorAsState(
        targetValue = when {
            !isHovered || !enabled -> Color.Transparent
            isDark -> Color.White.copy(alpha = 0.06f)
            else -> Color.Black.copy(alpha = 0.04f)
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "glass_pulldown_hover",
    )

    val fallbackColor = if (isDark) Color.White.copy(alpha = 0.12f) else Color.White
    val borderColor = if (isDark) Color.White.copy(alpha = 0.15f) else Color.Black.copy(alpha = 0.12f)
    val contentColor = if (isDark) Color.White else Color(0xFF1A1A1A)
    val capsuleShape = RoundedCornerShape(50)

    CompositionLocalProvider(
        LocalDarwinContentColor provides contentColor,
        LocalDarwinTextStyle provides controlSize.labelStyle(),
    ) {
        Box(
            modifier = modifier
                .scale(scale)
                .alpha(alpha)
                .darwinGlass(
                    shape = capsuleShape,
                    fallbackColor = fallbackColor,
                )
                .background(hoverOverlay, capsuleShape)
                .hoverable(interactionSource = interactionSource, enabled = enabled)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick,
                )
                .defaultMinSize(minHeight = metrics.minHeightFor(controlSize) + 12.dp)
                .padding(horizontal = metrics.horizontalPaddingFor(controlSize) + 4.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                content()
                Spacer(Modifier.width(6.dp))
                PulldownChevrons(tint = contentColor)
            }
        }
    }
}

@Composable
fun GlassPulldownButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    GlassPulldownButton(onClick, modifier, enabled, interactionSource) {
        Text(text)
    }
}

// ===========================================================================
// PulldownChevrons — shared up/down chevron pair for pulldown buttons
// ===========================================================================

@Composable
private fun PulldownChevrons(tint: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = LucideChevronDown,
            modifier = Modifier.size(8.dp).rotate(180f),
            tint = tint,
        )
        Icon(
            imageVector = LucideChevronDown,
            modifier = Modifier.size(8.dp),
            tint = tint,
        )
    }
}

// ===========================================================================
// DisclosureButton — macOS-native circular expand/collapse toggle
// ===========================================================================

@Composable
fun DisclosureButton(
    expanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isDark = DarwinTheme.colorScheme.isDark
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.90f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "disclosure_scale",
    )
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 0f else -90f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "disclosure_rotation",
    )
    val alpha by animateFloatAsState(
        targetValue = if (!enabled) 0.5f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "disclosure_alpha",
    )

    val bgColor = if (isDark) Color.White.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.05f)
    val iconColor = if (isDark) Color.White.copy(alpha = 0.85f) else Color.Black.copy(alpha = 0.85f)
    val circleShape = RoundedCornerShape(50)

    Box(
        modifier = modifier
            .size(24.dp)
            .scale(scale)
            .alpha(alpha)
            .clip(circleShape)
            .background(bgColor, circleShape)
            .hoverable(interactionSource = interactionSource, enabled = enabled)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onToggle,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = LucideChevronDown,
            modifier = Modifier
                .size(16.dp)
                .rotate(rotation),
            tint = iconColor,
        )
    }
}

// ===========================================================================
// macOS-native panel button styles — NSSavePanel / sheet footer buttons
// ===========================================================================

@Composable
fun PanelAccentButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fillWidth: Boolean = false,
) {
    val controlSize = LocalControlSize.current
    val metrics = DarwinTheme.componentStyling.defaultButton.metrics
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "panel_accent_scale",
    )
    val hoverOverlay by animateColorAsState(
        targetValue = when {
            !isHovered || !enabled -> Color.Transparent
            else -> Color.White.copy(alpha = 0.10f)
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "panel_accent_hover",
    )

    val shape = RoundedCornerShape(metrics.cornerSizeFor(controlSize))

    Box(
        modifier = modifier
            .scale(scale)
            .then(if (fillWidth) Modifier.fillMaxWidth() else Modifier.defaultMinSize(minWidth = 76.dp))
            .height(metrics.minHeightFor(controlSize))
            .clip(shape)
            .background(DarwinTheme.colorScheme.accent, shape)
            .background(hoverOverlay, shape)
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
        CompositionLocalProvider(
            LocalDarwinContentColor provides Color.White,
            LocalDarwinTextStyle provides controlSize.labelStyle().copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
            ),
        ) {
            Text(text)
        }
    }
}

@Composable
fun PanelDestructiveButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fillWidth: Boolean = false,
) {
    val controlSize = LocalControlSize.current
    val metrics = DarwinTheme.componentStyling.defaultButton.metrics
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val destructive = DarwinTheme.colorScheme.destructive

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "panel_dest_scale",
    )
    val hoverOverlay by animateColorAsState(
        targetValue = when {
            !isHovered || !enabled -> Color.Transparent
            else -> destructive.copy(alpha = 0.08f)
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "panel_dest_hover",
    )

    val shape = RoundedCornerShape(metrics.cornerSizeFor(controlSize))

    Box(
        modifier = modifier
            .scale(scale)
            .then(if (fillWidth) Modifier.fillMaxWidth() else Modifier.defaultMinSize(minWidth = 76.dp))
            .height(metrics.minHeightFor(controlSize))
            .clip(shape)
            .background(destructive.copy(alpha = 0.25f), shape)
            .background(hoverOverlay, shape)
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
        CompositionLocalProvider(
            LocalDarwinContentColor provides destructive,
            LocalDarwinTextStyle provides controlSize.labelStyle().copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
            ),
        ) {
            Text(text)
        }
    }
}

@Composable
fun PanelSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fillWidth: Boolean = false,
) {
    val controlSize = LocalControlSize.current
    val metrics = DarwinTheme.componentStyling.defaultButton.metrics
    val isDark = DarwinTheme.colorScheme.isDark
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    val backgroundColor = if (isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.05f)
    val textColor = if (isDark) Color.White else Color(0xFF1A1A1A)

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "panel_sec_scale",
    )
    val hoverOverlay by animateColorAsState(
        targetValue = when {
            !isHovered || !enabled -> Color.Transparent
            isDark -> Color.White.copy(alpha = 0.05f)
            else -> Color.Black.copy(alpha = 0.04f)
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "panel_sec_hover",
    )

    val shape = RoundedCornerShape(metrics.cornerSizeFor(controlSize))

    Box(
        modifier = modifier
            .scale(scale)
            .then(if (fillWidth) Modifier.fillMaxWidth() else Modifier.defaultMinSize(minWidth = 76.dp))
            .height(metrics.minHeightFor(controlSize))
            .clip(shape)
            .background(backgroundColor, shape)
            .background(hoverOverlay, shape)
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
        CompositionLocalProvider(
            LocalDarwinContentColor provides textColor,
            LocalDarwinTextStyle provides controlSize.labelStyle().copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
            ),
        ) {
            Text(text)
        }
    }
}

// ===========================================================================
// macOS-native alert pill button styles
// ===========================================================================

@Composable
fun MacNativeAccentButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fillWidth: Boolean = true,
) {
    val controlSize = LocalControlSize.current
    val metrics = DarwinTheme.componentStyling.defaultButton.metrics
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "mac_btn_scale",
    )

    val pillShape = RoundedCornerShape(50)
    val btnHeight = metrics.minHeightFor(controlSize) + 6.dp

    Box(
        modifier = modifier
            .scale(scale)
            .then(if (fillWidth) Modifier.fillMaxWidth() else Modifier.defaultMinSize(minWidth = 72.dp))
            .height(btnHeight)
            .clip(pillShape)
            .background(DarwinTheme.colorScheme.accent, pillShape)
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
        CompositionLocalProvider(
            LocalDarwinContentColor provides Color.White,
            LocalDarwinTextStyle provides controlSize.labelStyle().copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
            ),
        ) {
            Text(text)
        }
    }
}

@Composable
fun MacNativeDestructiveButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fillWidth: Boolean = true,
) {
    val controlSize = LocalControlSize.current
    val metrics = DarwinTheme.componentStyling.defaultButton.metrics
    val destructive = DarwinTheme.colorScheme.destructive
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "mac_dest_btn_scale",
    )

    val pillShape = RoundedCornerShape(50)
    val btnHeight = metrics.minHeightFor(controlSize) + 6.dp

    Box(
        modifier = modifier
            .scale(scale)
            .then(if (fillWidth) Modifier.fillMaxWidth() else Modifier.defaultMinSize(minWidth = 72.dp))
            .height(btnHeight)
            .clip(pillShape)
            .background(destructive.copy(alpha = 0.23f), pillShape)
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
        CompositionLocalProvider(
            LocalDarwinContentColor provides destructive,
            LocalDarwinTextStyle provides controlSize.labelStyle().copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
            ),
        ) {
            Text(text)
        }
    }
}

@Composable
fun MacNativeSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fillWidth: Boolean = true,
) {
    val controlSize = LocalControlSize.current
    val metrics = DarwinTheme.componentStyling.defaultButton.metrics
    val isDark = DarwinTheme.colorScheme.isDark
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor = if (isDark) Color.White.copy(alpha = 0.12f) else Color(0xFFE6E6E6)
    val textColor = if (isDark) Color.White else Color.Black.copy(alpha = 0.85f)

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "mac_sec_btn_scale",
    )

    val pillShape = RoundedCornerShape(50)
    val btnHeight = metrics.minHeightFor(controlSize) + 6.dp

    Box(
        modifier = modifier
            .scale(scale)
            .then(if (fillWidth) Modifier.fillMaxWidth() else Modifier.defaultMinSize(minWidth = 72.dp))
            .height(btnHeight)
            .clip(pillShape)
            .background(backgroundColor, pillShape)
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
        CompositionLocalProvider(
            LocalDarwinContentColor provides textColor,
            LocalDarwinTextStyle provides controlSize.labelStyle().copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
            ),
        ) {
            Text(text)
        }
    }
}