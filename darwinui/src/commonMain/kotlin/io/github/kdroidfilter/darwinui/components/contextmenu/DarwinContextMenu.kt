@file:OptIn(ExperimentalComposeUiApi::class)

package io.github.kdroidfilter.darwinui.components.contextmenu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.icons.LucideCheck
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.Red500
import io.github.kdroidfilter.darwinui.theme.Zinc100
import io.github.kdroidfilter.darwinui.theme.Zinc300
import io.github.kdroidfilter.darwinui.theme.Zinc400
import io.github.kdroidfilter.darwinui.theme.Zinc500
import io.github.kdroidfilter.darwinui.theme.Zinc700
import io.github.kdroidfilter.darwinui.theme.Zinc900

// CompositionLocal to allow items to auto-close the menu
internal val LocalContextMenuClose = staticCompositionLocalOf<() -> Unit> { {} }

@Composable
fun DarwinContextMenu(
    modifier: Modifier = Modifier,
    trigger: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes

    var isOpen by remember { mutableStateOf(false) }
    var clickOffset by remember { mutableStateOf(IntOffset.Zero) }

    // bg-white/95 dark:bg-zinc-900/95
    val bgColor = if (colors.isDark) Zinc900.copy(alpha = 0.95f) else Color.White.copy(alpha = 0.95f)

    // border-black/10 dark:border-white/10
    val borderColor = if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

    val shape = shapes.large // rounded-xl = 12dp

    Box(modifier = modifier) {
        // Trigger with right-click detection
        Box(
            modifier = Modifier.pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        if (event.type == PointerEventType.Press &&
                            event.buttons.isSecondaryPressed
                        ) {
                            val pos = event.changes.first().position
                            clickOffset = IntOffset(pos.x.toInt(), pos.y.toInt())
                            isOpen = true
                            event.changes.forEach { it.consume() }
                        }
                    }
                }
            }
        ) {
            trigger()
        }

        if (isOpen) {
            Popup(
                alignment = Alignment.TopStart,
                offset = clickOffset,
                onDismissRequest = { isOpen = false },
                properties = PopupProperties(focusable = true),
            ) {
                AnimatedVisibility(
                    visible = isOpen,
                    enter = fadeIn(animationSpec = tween(150)) +
                            scaleIn(
                                initialScale = 0.95f,
                                transformOrigin = TransformOrigin(0f, 0f), // top-left
                                animationSpec = tween(150),
                            ),
                    exit = fadeOut(animationSpec = tween(100)) +
                            scaleOut(
                                targetScale = 0.95f,
                                transformOrigin = TransformOrigin(0f, 0f),
                                animationSpec = tween(100),
                            ),
                ) {
                    CompositionLocalProvider(
                        LocalContextMenuClose provides { isOpen = false },
                    ) {
                        Column(
                            modifier = Modifier
                                .width(IntrinsicSize.Max)
                                .widthIn(min = 180.dp) // min-w-45 = 180dp
                                .shadow(elevation = 8.dp, shape = shape)
                                .clip(shape)
                                .background(bgColor, shape)
                                .border(1.dp, borderColor, shape)
                                .padding(vertical = 4.dp), // p-1 vertical only (separator extends full width)
                            content = content,
                        )
                    }
                }
            }
        }
    }
}

// =============================================================================
// Context Menu Item
// =============================================================================

@Composable
fun DarwinContextMenuItem(
    onSelect: () -> Unit,
    enabled: Boolean = true,
    destructive: Boolean = false,
    modifier: Modifier = Modifier,
    trailingContent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography
    val closeMenu = LocalContextMenuClose.current

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHighlighted = isHovered || isPressed

    // hover:bg-black/10 dark:hover:bg-white/10
    // destructive: hover:bg-red-500/10
    val itemBg = when {
        !enabled -> Color.Transparent
        destructive && isHighlighted -> Red500.copy(alpha = 0.10f)
        isHighlighted -> if (colors.isDark) Color.White.copy(alpha = 0.10f)
        else Color.Black.copy(alpha = 0.10f)
        else -> Color.Transparent
    }

    // text-zinc-700 dark:text-zinc-300
    // hover:text-zinc-900 dark:hover:text-zinc-100
    // destructive: text-red-500 (always)
    val textColor = when {
        destructive -> Red500
        isHighlighted -> if (colors.isDark) Zinc100 else Zinc900
        else -> if (colors.isDark) Zinc300 else Zinc700
    }

    val contentStyle = typography.bodyMedium.merge(TextStyle(color = textColor)) // text-sm

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp) // container p-1 horizontal
            .clip(DarwinTheme.shapes.small) // rounded-lg = 8dp
            .then(
                if (enabled) {
                    Modifier
                        .hoverable(interactionSource)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = {
                                onSelect()
                                closeMenu()
                            },
                        )
                } else {
                    Modifier
                }
            )
            .background(itemBg)
            .padding(horizontal = 8.dp, vertical = 6.dp) // px-2 py-1.5
            .alpha(if (enabled) 1f else 0.5f),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.weight(1f)) {
            CompositionLocalProvider(LocalDarwinTextStyle provides contentStyle) {
                content()
            }
        }

        if (trailingContent != null) {
            Spacer(modifier = Modifier.width(8.dp))
            trailingContent()
        }
    }
}

// =============================================================================
// Context Menu Checkbox Item
// =============================================================================

@Composable
fun DarwinContextMenuCheckboxItem(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography
    val closeMenu = LocalContextMenuClose.current

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHighlighted = isHovered || isPressed

    val itemBg = when {
        !enabled -> Color.Transparent
        isHighlighted -> if (colors.isDark) Color.White.copy(alpha = 0.10f)
        else Color.Black.copy(alpha = 0.10f)
        else -> Color.Transparent
    }

    val textColor = when {
        isHighlighted -> if (colors.isDark) Zinc100 else Zinc900
        else -> if (colors.isDark) Zinc300 else Zinc700
    }

    val contentStyle = typography.bodyMedium.merge(TextStyle(color = textColor))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .clip(DarwinTheme.shapes.small) // rounded-lg = 8dp
            .then(
                if (enabled) {
                    Modifier
                        .hoverable(interactionSource)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = { onCheckedChange(!checked) },
                        )
                } else {
                    Modifier
                }
            )
            .background(itemBg)
            .padding(horizontal = 8.dp, vertical = 6.dp) // px-2 py-1.5
            .alpha(if (enabled) 1f else 0.5f),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Check indicator: mr-2 h-4 w-4
        Box(
            modifier = Modifier.size(16.dp), // h-4 w-4
            contentAlignment = Alignment.Center,
        ) {
            if (checked) {
                // h-3 w-3 check icon
                androidx.compose.foundation.Image(
                    imageVector = LucideCheck,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp), // h-3 w-3
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(textColor),
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp)) // mr-2

        Box(modifier = Modifier.weight(1f)) {
            CompositionLocalProvider(LocalDarwinTextStyle provides contentStyle) {
                content()
            }
        }
    }
}

// =============================================================================
// Context Menu Label
// =============================================================================

@Composable
fun DarwinContextMenuLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography

    // text-zinc-500 dark:text-zinc-400
    val labelColor = if (colors.isDark) Zinc400 else Zinc500

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp), // px-2 + 4dp container = 12dp, py-1.5 = 6dp
    ) {
        BasicText(
            text = text,
            style = typography.labelSmall.merge(
                TextStyle(
                    color = labelColor,
                    fontWeight = FontWeight.SemiBold, // font-semibold
                )
            ),
        )
    }
}

// =============================================================================
// Context Menu Separator
// =============================================================================

@Composable
fun DarwinContextMenuSeparator(modifier: Modifier = Modifier) {
    val colors = DarwinTheme.colors

    // bg-black/10 dark:bg-white/10
    val separatorColor = if (colors.isDark) Color.White.copy(alpha = 0.10f)
    else Color.Black.copy(alpha = 0.10f)

    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) // my-1 = 4dp
            .height(1.dp) // h-px
            .background(separatorColor),
    )
}

// =============================================================================
// Context Menu Shortcut
// =============================================================================

@Composable
fun DarwinContextMenuShortcut(
    text: String,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography

    // text-zinc-500 dark:text-zinc-400
    val shortcutColor = if (colors.isDark) Zinc400 else Zinc500

    BasicText(
        text = text,
        style = typography.labelSmall.merge(
            TextStyle(
                color = shortcutColor,
                letterSpacing = 0.1.em, // tracking-widest
            )
        ),
        modifier = modifier,
    )
}
