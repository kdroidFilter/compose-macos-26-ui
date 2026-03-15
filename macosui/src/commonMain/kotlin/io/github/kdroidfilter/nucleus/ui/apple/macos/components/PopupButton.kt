package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icons
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SpringPreset
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalContentColor
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalTextStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosGlass
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosSpring

// ===========================================================================
// PopupButton — macOS 26 pop-up button (NSPopUpButton equivalent)
//
// Matches Sketch: Pop-up Button / Content Area & Over-glass / {size} / {state}
//
// Sketch specs (light mode):
//   Idle:     bg #0000000D (5%)   text #1A1A1A   chevron #000000D9 (85%)
//   Clicked:  bg #00000026 (15%)  text #1A1A1A   chevron #000000D9 (85%)
//   Disabled: bg #00000008 (3%)   text #BFBFBF   chevron #00000040 (25%)
//
// Sizes:  Mn=16dp/r4  Sm=20dp/r5  Md=24dp/r6  Lg=28dp/r14  XL=36dp/r18
// Font:   Mn=10sp     Sm=11sp     Md=13sp     Lg=13sp      XL=13sp
// ===========================================================================

/**
 * macOS 26 pop-up button for selecting from a list of options.
 *
 * Displays the currently selected option with a double-chevron indicator,
 * and opens a dropdown menu when clicked. Matches NSPopUpButton behavior.
 *
 * @param items The list of items to display in the dropdown.
 * @param selectedIndex The index of the currently selected item.
 * @param onSelectedChange Called when a new item is selected.
 * @param itemText Transforms an item into its display string.
 * @param enabled Whether the button is interactive.
 */
@Composable
fun <T> PopupButton(
    items: List<T>,
    selectedIndex: Int,
    onSelectedChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placement: MenuPlacement = MenuPlacement.Below,
    itemText: (T) -> String = { it.toString() },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val controlSize = LocalControlSize.current
    val metrics = MacosTheme.componentStyling.defaultButton.metrics
    val isDark = MacosTheme.colorScheme.isDark

    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    var expanded by remember { mutableStateOf(false) }
    var anchorPosition by remember { mutableStateOf(IntOffset.Zero) }
    var anchorSize by remember { mutableStateOf(IntSize.Zero) }

    val base = if (isDark) Color.White else Color.Black

    // Background — Sketch: #0000000D idle, #00000026 clicked, #00000008 disabled
    val bgColor = when {
        !enabled -> base.copy(alpha = if (isDark) 0.04f else 0.031f)
        isPressed || expanded -> base.copy(alpha = 0.15f)
        isHovered -> base.copy(alpha = 0.08f)
        else -> base.copy(alpha = 0.051f)
    }

    // Text — always use the "enabled" color; disabled is handled by overall alpha
    val contentColor = if (isDark) Color.White else Color(0xFF1A1A1A)

    // Chevron — always use the "enabled" tint; disabled is handled by overall alpha
    val chevronColor = base.copy(alpha = 0.85f)

    // Press scale like PushButton
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1f,
        animationSpec = macosSpring(SpringPreset.Snappy),
        label = "popup_scale",
    )

    val buttonHeight = metrics.minHeightFor(controlSize)
    val cornerRadius = metrics.cornerSizeFor(controlSize)
    val shape = RoundedCornerShape(cornerRadius)
    val leftPadding = metrics.horizontalPaddingFor(controlSize)

    // Chevron box fills the button height (square area at the right)
    val chevronBoxSize = buttonHeight
    val chevronIconSize = when (controlSize) {
        ControlSize.Mini -> 8.dp
        ControlSize.Small -> 10.dp
        ControlSize.Regular -> 12.dp
        ControlSize.Large -> 13.dp
        ControlSize.ExtraLarge -> 14.dp
    }

    val selectedText = items.getOrNull(selectedIndex)?.let(itemText) ?: ""

    val labelFontSize = when (controlSize) {
        ControlSize.Mini -> 10.sp
        ControlSize.Small -> 11.sp
        ControlSize.Regular, ControlSize.Large, ControlSize.ExtraLarge -> 13.sp
    }

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    val windowPos = coordinates.localToWindow(Offset.Zero)
                    anchorPosition = IntOffset(windowPos.x.toInt(), windowPos.y.toInt())
                }
                .onSizeChanged { anchorSize = it }
                .scale(scale)
                .height(buttonHeight)
                .clip(shape)
                .background(bgColor, shape)
                .then(
                    if (enabled) {
                        Modifier
                            .hoverable(interactionSource = interactionSource)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                role = Role.DropdownList,
                                onClick = { expanded = !expanded },
                            )
                    } else {
                        Modifier.alpha(0.45f)
                    }
                )
                .padding(start = leftPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = selectedText,
                color = contentColor,
                fontSize = labelFontSize,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            // Double chevron indicator (square area at right edge)
            Box(
                modifier = Modifier.size(chevronBoxSize),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    icon = Icons.ChevronsUpDown,
                    modifier = Modifier.size(chevronIconSize),
                    tint = chevronColor,
                )
            }
        }

        // Dropdown menu
        if (expanded) {
            PopupButtonMenu(
                items = items,
                selectedIndex = selectedIndex,
                onSelectedChange = { index ->
                    onSelectedChange(index)
                    expanded = false
                },
                onDismissRequest = { expanded = false },
                anchorPosition = anchorPosition,
                anchorSize = anchorSize,
                placement = placement,
                itemText = itemText,
            )
        }
    }
}

/** Convenience overload for [String] lists. */
@Composable
fun PopupButton(
    items: List<String>,
    selectedIndex: Int,
    onSelectedChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placement: MenuPlacement = MenuPlacement.Below,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    PopupButton(
        items = items,
        selectedIndex = selectedIndex,
        onSelectedChange = onSelectedChange,
        modifier = modifier,
        enabled = enabled,
        placement = placement,
        itemText = { it },
        interactionSource = interactionSource,
    )
}

// ===========================================================================
// PopupButtonMenu — dropdown menu for PopupButton
// ===========================================================================

@Composable
private fun <T> PopupButtonMenu(
    items: List<T>,
    selectedIndex: Int,
    onSelectedChange: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    anchorPosition: IntOffset,
    anchorSize: IntSize,
    placement: MenuPlacement,
    itemText: (T) -> String,
) {
    val colors = MacosTheme.colorScheme
    val density = LocalDensity.current
    val fallbackBg = if (colors.isDark) Color(0xFF262626) else Color(0xFFFAFAFA)
    val borderColor = if (colors.isDark) Color.White.copy(alpha = 0.12f) else Color.Black.copy(alpha = 0.08f)
    val menuShape = RoundedCornerShape(13.dp)
    val minMenuWidth = with(density) { anchorSize.width.toDp() }.coerceAtLeast(150.dp)
    val gapPx = with(density) { 4.dp.roundToPx() }

    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var menuSize by remember { mutableStateOf(IntSize.Zero) }

    val origin = placement.transformOrigin()

    Popup(
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { containerSize = it }
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onDismissRequest,
                ),
        ) {
            val rawY = calculateMenuY(
                placement = placement,
                anchorY = anchorPosition.y,
                anchorHeight = anchorSize.height,
                menuHeight = menuSize.height,
                gapPx = gapPx,
            )
            val clampedOffset = IntOffset(
                x = anchorPosition.x.coerceIn(0, (containerSize.width - menuSize.width).coerceAtLeast(0)),
                y = rawY.coerceIn(0, (containerSize.height - menuSize.height).coerceAtLeast(0)),
            )

            AnimatedVisibility(
                visible = true,
                modifier = Modifier
                    .onSizeChanged { menuSize = it }
                    .offset { clampedOffset },
                enter = fadeIn(animationSpec = tween(150)) +
                        scaleIn(
                            initialScale = 0.95f,
                            transformOrigin = origin,
                            animationSpec = tween(150),
                        ),
                exit = fadeOut(animationSpec = tween(100)) +
                        scaleOut(
                            targetScale = 0.95f,
                            transformOrigin = origin,
                            animationSpec = tween(100),
                        ),
            ) {
                Column(
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .widthIn(min = minMenuWidth)
                        .shadow(
                            elevation = 25.dp,
                            shape = menuShape,
                            ambientColor = Color.Black.copy(alpha = 0.10f),
                            spotColor = Color.Black.copy(alpha = 0.16f),
                        )
                        .macosGlass(shape = menuShape, fallbackColor = fallbackBg)
                        .border(0.5.dp, borderColor, menuShape)
                        .padding(vertical = 5.dp),
                ) {
                    items.forEachIndexed { index, item ->
                        PopupMenuItem(
                            text = itemText(item),
                            selected = index == selectedIndex,
                            onClick = { onSelectedChange(index) },
                        )
                    }
                }
            }
        }
    }
}

// ===========================================================================
// PopupMenuItem — individual item in the popup menu
// ===========================================================================

@Composable
private fun PopupMenuItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val colors = MacosTheme.colorScheme
    val accentColor = colors.accent

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHighlighted = isHovered || isPressed

    val itemBg = if (isHighlighted) accentColor else Color.Transparent

    val textColor = when {
        isHighlighted -> Color.White
        else -> if (colors.isDark) Color.White.copy(alpha = 0.85f) else Color(0xFF1A1A1A)
    }

    val checkColor = if (isHighlighted) Color.White else accentColor

    val itemShape = RoundedCornerShape(8.dp)
    val contentStyle = TextStyle(
        fontSize = 13.sp,
        color = textColor,
        letterSpacing = (-0.2).sp,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .height(24.dp)
            .clip(itemShape)
            .hoverable(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .background(itemBg)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Checkmark for selected item
        Box(
            modifier = Modifier.size(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (selected) {
                Icon(
                    icon = Icons.Check,
                    modifier = Modifier.size(12.dp),
                    tint = checkColor,
                )
            }
        }
        Spacer(modifier = Modifier.width(4.dp))

        CompositionLocalProvider(
            LocalTextStyle provides contentStyle,
            LocalContentColor provides textColor,
        ) {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
