@file:OptIn(ExperimentalComposeUiApi::class)

package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.LucideCheck
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.Red500
import io.github.kdroidfilter.darwinui.theme.darwinGlass

// CompositionLocal to allow items to auto-close the menu
internal val LocalContextMenuClose = staticCompositionLocalOf<() -> Unit> { {} }

@Composable
fun ContextMenu(
    modifier: Modifier = Modifier,
    trigger: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = DarwinTheme.colorScheme

    var isOpen by remember { mutableStateOf(false) }
    var clickOffset by remember { mutableStateOf(IntOffset.Zero) }

    val fallbackBg = if (colors.isDark) Color(0xFF262626) else Color(0xFFF5F5F5)
    val menuShape = RoundedCornerShape(13.dp)

    Box(modifier = modifier) {
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
                                transformOrigin = TransformOrigin(0f, 0f),
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
                                .widthIn(min = 200.dp)
                                .shadow(
                                    elevation = 25.dp,
                                    shape = menuShape,
                                    ambientColor = Color.Black.copy(alpha = 0.16f),
                                    spotColor = Color.Black.copy(alpha = 0.16f),
                                )
                                .darwinGlass(shape = menuShape, fallbackColor = fallbackBg)
                                .padding(vertical = 5.dp),
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
fun ContextMenuItem(
    onSelect: () -> Unit,
    enabled: Boolean = true,
    destructive: Boolean = false,
    modifier: Modifier = Modifier,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colorScheme
    val closeMenu = LocalContextMenuClose.current
    val accentColor = colors.accent

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHighlighted = isHovered || isPressed

    val itemBg = when {
        !enabled -> Color.Transparent
        destructive && isHighlighted -> Red500
        isHighlighted -> accentColor
        else -> Color.Transparent
    }

    val textColor = when {
        isHighlighted -> Color.White
        destructive -> Red500
        else -> if (colors.isDark) Color.White.copy(alpha = 0.85f)
        else Color(0xFF1A1A1A)
    }

    val disabledTextColor = if (colors.isDark) Color.White.copy(alpha = 0.35f)
    else Color(0xFFBFBFBF)

    val effectiveTextColor = if (enabled) textColor else disabledTextColor

    val itemShape = RoundedCornerShape(12.dp)
    val contentStyle = TextStyle(
        fontSize = 13.sp,
        color = effectiveTextColor,
        letterSpacing = 0.sp,
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .height(24.dp)
            .clip(itemShape)
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
            .padding(
                start = if (leadingContent != null) 8.dp else 12.dp,
                end = 12.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalDarwinTextStyle provides contentStyle,
            LocalDarwinContentColor provides effectiveTextColor,
        ) {
            if (leadingContent != null) {
                Box(
                    modifier = Modifier.size(12.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    leadingContent()
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            Box(modifier = Modifier.weight(1f)) {
                content()
            }

            if (trailingContent != null) {
                Spacer(modifier = Modifier.width(8.dp))
                trailingContent()
            }
        }
    }
}

// =============================================================================
// Context Menu Checkbox Item
// =============================================================================

@Composable
fun ContextMenuCheckboxItem(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colorScheme
    val closeMenu = LocalContextMenuClose.current
    val accentColor = colors.accent

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHighlighted = isHovered || isPressed

    val itemBg = when {
        !enabled -> Color.Transparent
        isHighlighted -> accentColor
        else -> Color.Transparent
    }

    val textColor = when {
        isHighlighted -> Color.White
        else -> if (colors.isDark) Color.White.copy(alpha = 0.85f)
        else Color(0xFF1A1A1A)
    }

    val disabledTextColor = if (colors.isDark) Color.White.copy(alpha = 0.35f)
    else Color(0xFFBFBFBF)

    val effectiveTextColor = if (enabled) textColor else disabledTextColor

    val itemShape = RoundedCornerShape(12.dp)
    val contentStyle = TextStyle(
        fontSize = 13.sp,
        color = effectiveTextColor,
        letterSpacing = 0.sp,
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .height(24.dp)
            .clip(itemShape)
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
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.size(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (checked) {
                androidx.compose.foundation.Image(
                    imageVector = LucideCheck,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(effectiveTextColor),
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))

        Box(modifier = Modifier.weight(1f)) {
            CompositionLocalProvider(
                LocalDarwinTextStyle provides contentStyle,
                LocalDarwinContentColor provides effectiveTextColor,
            ) {
                content()
            }
        }
    }
}

// =============================================================================
// Context Menu Label
// =============================================================================

@Composable
fun ContextMenuLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colorScheme

    val labelColor = if (colors.isDark) Color.White.copy(alpha = 0.45f)
    else Color(0xFF727272)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .height(24.dp)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        BasicText(
            text = text,
            style = TextStyle(
                fontSize = 13.sp,
                color = labelColor,
                letterSpacing = 0.sp,
            ),
        )
    }
}

// =============================================================================
// Context Menu Separator
// =============================================================================

@Composable
fun ContextMenuSeparator(modifier: Modifier = Modifier) {
    val colors = DarwinTheme.colorScheme

    val separatorColor = if (colors.isDark) Color.White.copy(alpha = 0.08f)
    else Color(0xFFE6E6E6)

    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 11.dp)
            .padding(vertical = 5.dp)
            .height(1.dp)
            .background(separatorColor),
    )
}

// =============================================================================
// Context Menu Shortcut
// =============================================================================

@Composable
fun ContextMenuShortcut(
    text: String,
    modifier: Modifier = Modifier,
) {
    // Inherits the text style (including color) from the parent ContextMenuItem
    // via LocalDarwinTextStyle, so shortcut color matches the item text color
    val parentStyle = LocalDarwinTextStyle.current

    BasicText(
        text = text,
        style = parentStyle.merge(
            TextStyle(letterSpacing = 0.sp)
        ),
        modifier = modifier,
    )
}

@Preview
@Composable
private fun ContextMenuPreview() {
    DarwinTheme {
        ContextMenu(
            trigger = { Text("Right-click me") },
        ) {
            ContextMenuItem(onSelect = {}) { Text("Copy") }
            ContextMenuSeparator()
            ContextMenuItem(onSelect = {}, destructive = true) { Text("Delete") }
        }
    }
}
