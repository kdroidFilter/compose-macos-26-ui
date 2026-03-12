package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideChevronDown
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import io.github.kdroidfilter.darwinui.theme.darwinGlass
import io.github.kdroidfilter.darwinui.theme.darwinTween

// ===========================================================================
// PulldownButton — macOS-native pulldown/popup button (flat bezel style)
// ===========================================================================

/**
 * macOS-native pulldown button: 24dp tall, rounded corners (rx≈9.6dp), subtle 5% background.
 * Displays content + up/down chevron arrows. Matches NSPopUpButton flat bezel style.
 */
@Composable
fun PulldownButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
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
    val shape = RoundedCornerShape(9.6.dp)

    CompositionLocalProvider(
        LocalDarwinContentColor provides contentColor,
        LocalDarwinTextStyle provides DarwinTheme.typography.caption1,
    ) {
        Box(
            modifier = modifier
                .scale(scale)
                .alpha(alpha)
                .height(24.dp)
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
                .padding(horizontal = 12.dp),
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

/**
 * macOS-native pulldown button with frosted glass background: 36dp tall, capsule shape,
 * drop shadow. Displays content + up/down chevron arrows.
 */
@Composable
fun GlassPulldownButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
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
        LocalDarwinTextStyle provides DarwinTheme.typography.caption1,
    ) {
        Box(
            modifier = modifier
                .scale(scale)
                .alpha(alpha)
                .shadow(
                    elevation = 4.dp,
                    shape = capsuleShape,
                    spotColor = Color.Black.copy(alpha = 0.12f),
                    ambientColor = Color.Black.copy(alpha = 0.06f),
                )
                .darwinGlass(
                    shape = capsuleShape,
                    fallbackColor = fallbackColor,
                )
                .border(BorderStroke(0.5.dp, borderColor), capsuleShape)
                .background(hoverOverlay, capsuleShape)
                .hoverable(interactionSource = interactionSource, enabled = enabled)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick,
                )
                .defaultMinSize(minHeight = 36.dp)
                .padding(horizontal = 16.dp),
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

/**
 * macOS-native disclosure button: circular 24dp button with a chevron that rotates
 * when [expanded] changes. Matches NSDisclosureTriangle style.
 */
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
// PushButton — macOS-native compact push button (bezel/rounded style)
// ===========================================================================

/**
 * macOS-native push button: 24dp tall, very rounded corners (rx≈9.6dp), subtle 5% dark
 * background. Matches the NSButton "rounded" bezel style at regular size.
 */
@Composable
fun PushButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    val isDark = DarwinTheme.colorScheme.isDark
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "push_scale",
    )
    val alpha by animateFloatAsState(
        targetValue = if (!enabled) 0.5f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "push_alpha",
    )
    val hoverOverlay by animateColorAsState(
        targetValue = when {
            !isHovered || !enabled -> Color.Transparent
            isDark -> Color.White.copy(alpha = 0.05f)
            else -> Color.Black.copy(alpha = 0.04f)
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "push_hover",
    )

    val bgColor = if (isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.05f)
    val contentColor = if (isDark) Color.White else Color(0xFF1A1A1A)
    val shape = RoundedCornerShape(9.6.dp)

    CompositionLocalProvider(
        LocalDarwinContentColor provides contentColor,
        LocalDarwinTextStyle provides DarwinTheme.typography.caption1,
    ) {
        Box(
            modifier = modifier
                .scale(scale)
                .alpha(alpha)
                .height(24.dp)
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
                .padding(horizontal = 12.dp),
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

@Composable
fun PushButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    PushButton(onClick, modifier, enabled, interactionSource) {
        Text(text)
    }
}

// ===========================================================================
// ArrowButton — macOS-native stepper button (up + down chevrons)
// ===========================================================================

/**
 * macOS-native arrow/stepper button: circular 24dp button with an up chevron on the top half
 * and a down chevron on the bottom half. Matches NSArrowButton style.
 */
@Composable
fun ArrowButton(
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val isDark = DarwinTheme.colorScheme.isDark
    val bgColor = if (isDark) Color.White.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.05f)
    val iconColor = if (isDark) Color.White.copy(alpha = 0.85f) else Color.Black.copy(alpha = 0.85f)
    val circleShape = RoundedCornerShape(50)

    val topSource = remember { MutableInteractionSource() }
    val bottomSource = remember { MutableInteractionSource() }
    val isTopPressed by topSource.collectIsPressedAsState()
    val isBottomPressed by bottomSource.collectIsPressedAsState()

    val alpha by animateFloatAsState(
        targetValue = if (!enabled) 0.5f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "arrow_alpha",
    )

    Box(
        modifier = modifier
            .size(24.dp)
            .alpha(alpha)
            .clip(circleShape)
            .background(bgColor, circleShape),
    ) {
        // Top half — increment (chevron up)
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(width = 24.dp, height = 12.dp)
                .background(
                    if (isTopPressed && enabled) (if (isDark) Color.White else Color.Black).copy(alpha = 0.08f)
                    else Color.Transparent
                )
                .hoverable(interactionSource = topSource, enabled = enabled)
                .clickable(
                    interactionSource = topSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onIncrement,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = LucideChevronDown,
                modifier = Modifier.size(12.dp).rotate(180f),
                tint = iconColor,
            )
        }

        // Bottom half — decrement (chevron down)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(width = 24.dp, height = 12.dp)
                .background(
                    if (isBottomPressed && enabled) (if (isDark) Color.White else Color.Black).copy(alpha = 0.08f)
                    else Color.Transparent
                )
                .hoverable(interactionSource = bottomSource, enabled = enabled)
                .clickable(
                    interactionSource = bottomSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onDecrement,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = LucideChevronDown,
                modifier = Modifier.size(12.dp),
                tint = iconColor,
            )
        }
    }
}

// ===========================================================================
// macOS-native panel button styles — NSSavePanel / sheet footer buttons
// ===========================================================================

/**
 * macOS-native accent panel button for save dialogs and sheet footers.
 * 24dp tall, squarish rounded corners (rx≈7.69dp), solid blue (#0088FF) background.
 */
@Composable
fun PanelAccentButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fillWidth: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isDark = DarwinTheme.colorScheme.isDark

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

    val shape = RoundedCornerShape(7.69.dp)

    Box(
        modifier = modifier
            .scale(scale)
            .then(if (fillWidth) Modifier.fillMaxWidth() else Modifier.defaultMinSize(minWidth = 76.dp))
            .height(24.dp)
            .clip(shape)
            .background(Color(0xFF0088FF), shape)
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
            LocalDarwinTextStyle provides DarwinTheme.typography.caption1.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                fontSize = 13.sp,
            ),
        ) {
            Text(text)
        }
    }
}

/**
 * macOS-native destructive panel button for save dialogs and sheet footers.
 * 24dp tall, squarish rounded corners (rx≈7.69dp), red tinted background (#FF383C at 25%).
 */
@Composable
fun PanelDestructiveButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fillWidth: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isDark = DarwinTheme.colorScheme.isDark

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "panel_dest_scale",
    )
    val hoverOverlay by animateColorAsState(
        targetValue = when {
            !isHovered || !enabled -> Color.Transparent
            isDark -> Color(0xFFFF383C).copy(alpha = 0.08f)
            else -> Color(0xFFFF383C).copy(alpha = 0.06f)
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "panel_dest_hover",
    )

    val shape = RoundedCornerShape(7.69.dp)

    Box(
        modifier = modifier
            .scale(scale)
            .then(if (fillWidth) Modifier.fillMaxWidth() else Modifier.defaultMinSize(minWidth = 76.dp))
            .height(24.dp)
            .clip(shape)
            .background(Color(0xFFFF383C).copy(alpha = 0.25f), shape)
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
            LocalDarwinContentColor provides Color(0xFFFF383C),
            LocalDarwinTextStyle provides DarwinTheme.typography.caption1.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                fontSize = 13.sp,
            ),
        ) {
            Text(text)
        }
    }
}

/**
 * macOS-native secondary panel button for save dialogs and sheet footers.
 * 24dp tall, squarish rounded corners (rx≈7.69dp), subtle 5% black background.
 */
@Composable
fun PanelSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fillWidth: Boolean = false,
) {
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

    val shape = RoundedCornerShape(7.69.dp)

    Box(
        modifier = modifier
            .scale(scale)
            .then(if (fillWidth) Modifier.fillMaxWidth() else Modifier.defaultMinSize(minWidth = 76.dp))
            .height(24.dp)
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
            LocalDarwinTextStyle provides DarwinTheme.typography.caption1.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                fontSize = 13.sp,
            ),
        ) {
            Text(text)
        }
    }
}

// ===========================================================================
// macOS-native alert pill button styles
// ===========================================================================

/**
 * macOS-native accent pill button for alert dialogs.
 * Full-width, 28dp tall, pill-shaped (rx=14) with solid blue (#0088FF) background.
 */
@Composable
fun MacNativeAccentButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fillWidth: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "mac_btn_scale",
    )

    val pillShape = RoundedCornerShape(14.dp)

    Box(
        modifier = modifier
            .scale(scale)
            .then(if (fillWidth) Modifier.fillMaxWidth() else Modifier.defaultMinSize(minWidth = 72.dp))
            .height(28.dp)
            .clip(pillShape)
            .background(Color(0xFF0088FF), pillShape)
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
            LocalDarwinTextStyle provides DarwinTheme.typography.caption1.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                fontSize = 13.sp,
            ),
        ) {
            Text(text)
        }
    }
}

/**
 * macOS-native destructive pill button for alert dialogs.
 * Full-width, 28dp tall, pill-shaped with red tinted background (#FF383C at 23%).
 */
@Composable
fun MacNativeDestructiveButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fillWidth: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "mac_dest_btn_scale",
    )

    val pillShape = RoundedCornerShape(14.dp)

    Box(
        modifier = modifier
            .scale(scale)
            .then(if (fillWidth) Modifier.fillMaxWidth() else Modifier.defaultMinSize(minWidth = 72.dp))
            .height(28.dp)
            .clip(pillShape)
            .background(Color(0xFFFF383C).copy(alpha = 0.23f), pillShape)
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
            LocalDarwinContentColor provides Color(0xFFFF383C),
            LocalDarwinTextStyle provides DarwinTheme.typography.caption1.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                fontSize = 13.sp,
            ),
        ) {
            Text(text)
        }
    }
}

/**
 * macOS-native secondary/cancel pill button for alert dialogs.
 * Full-width, 28dp tall, pill-shaped with neutral gray background (#E6E6E6 light / white 12% dark).
 */
@Composable
fun MacNativeSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fillWidth: Boolean = true,
) {
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

    val pillShape = RoundedCornerShape(14.dp)

    Box(
        modifier = modifier
            .scale(scale)
            .then(if (fillWidth) Modifier.fillMaxWidth() else Modifier.defaultMinSize(minWidth = 72.dp))
            .height(28.dp)
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
            LocalDarwinTextStyle provides DarwinTheme.typography.caption1.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                fontSize = 13.sp,
            ),
        ) {
            Text(text)
        }
    }
}
