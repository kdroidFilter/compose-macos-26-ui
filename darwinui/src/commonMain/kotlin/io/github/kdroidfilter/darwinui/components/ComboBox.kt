package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideChevronDown
import io.github.kdroidfilter.darwinui.theme.ControlSize
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalControlSize
import io.github.kdroidfilter.darwinui.theme.Outline
import io.github.kdroidfilter.darwinui.theme.darwinGlass
import io.github.kdroidfilter.darwinui.theme.focusOrValidationOutline

/**
 * A combo box / select component with index-based selection.
 *
 * @param items The list of string items to display.
 * @param selected The currently selected index, or null for no selection.
 * @param onSelectionChange Callback with (index, item) when selection changes.
 * @param modifier Modifier applied to the component.
 * @param header Optional header/label text above the combo box.
 * @param placeholder Optional placeholder text when nothing is selected.
 * @param disabled Whether the combo box is non-interactive.
 */
@Composable
fun ComboBox(
    items: List<String>,
    selected: Int?,
    onSelectionChange: (index: Int, item: String) -> Unit,
    modifier: Modifier = Modifier,
    header: String? = null,
    placeholder: String? = null,
    disabled: Boolean = false,
    outline: Outline = Outline.None,
    placement: MenuPlacement = MenuPlacement.Below,
) {
    val controlSize = LocalControlSize.current
    val comboMetrics = DarwinTheme.componentStyling.comboBox.metrics
    val colors = DarwinTheme.colorScheme
    val shapes = DarwinTheme.shapes
    val typography = DarwinTheme.typography
    val outlines = DarwinTheme.globalColors.outlines
    val enabled = !disabled

    var expanded by remember { mutableStateOf(false) }
    var highlightedIndex by remember { mutableStateOf(-1) }
    var triggerWidthPx by remember { mutableStateOf(0) }
    var triggerHeightPx by remember { mutableStateOf(0) }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isTriggerHovered by interactionSource.collectIsHoveredAsState()
    val focusRequester = remember { FocusRequester() }

    val selectedLabel = selected?.let { items.getOrNull(it) }

    val isDark = colors.isDark
    val backgroundColor = when {
        !enabled -> if (isDark) Color.White.copy(alpha = 0.04f) else Color.White.copy(alpha = 0.50f)
        else -> if (isDark) Color.White.copy(alpha = 0.08f) else Color.White
    }
    val borderColor = when {
        !enabled -> if (isDark) Color.White.copy(alpha = 0.04f) else Color.Black.copy(alpha = 0.04f)
        isFocused || expanded -> colors.inputFocusBorder
        else -> if (isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.08f)
    }
    val borderWidth = if (enabled && (isFocused || expanded)) 2.dp else 1.dp
    val chevronAlpha = when {
        !enabled -> 0.06f
        expanded -> 0.20f
        else -> 0.13f
    }
    val chevronBg = if (isDark) Color.White.copy(alpha = chevronAlpha) else Color.Black.copy(alpha = chevronAlpha)
    val chevronTintAlpha = if (enabled) 0.85f else 0.25f
    val chevronTint = if (isDark) Color.White.copy(alpha = chevronTintAlpha) else Color.Black.copy(alpha = chevronTintAlpha)

    val comboFontSize = when (controlSize) {
        ControlSize.Mini -> 10.sp
        ControlSize.Small, ControlSize.Regular -> 11.sp
        ControlSize.Large, ControlSize.ExtraLarge -> 13.sp
    }

    val chevronRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
    )

    LaunchedEffect(expanded) {
        if (expanded) {
            highlightedIndex = selected ?: -1
        }
    }

    Column(modifier = modifier) {
        if (header != null) {
            Text(
                text = header,
                style = typography.caption1,
                color = colors.textPrimary,
                modifier = Modifier.padding(bottom = 6.dp),
            )
        }

        Box {
            // Outer unclipped box carries the focus/validation outline ring (Phase 1.4)
            Box(
                modifier = Modifier
                    .widthIn(min = comboMetrics.minWidth)
                    .fillMaxWidth()
                    .height(comboMetrics.minHeightFor(controlSize))
                    .focusOrValidationOutline(isFocused || expanded, outline, shapes.small, outlines),
            ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        triggerWidthPx = coordinates.size.width
                        triggerHeightPx = coordinates.size.height
                    }
                    .clip(shapes.small)
                    .background(backgroundColor)
                    .border(
                        width = borderWidth,
                        color = borderColor,
                        shape = shapes.small,
                    )
                    .then(
                        if (enabled) {
                            Modifier
                                .hoverable(interactionSource)
                                .focusRequester(focusRequester)
                                .focusable(interactionSource = interactionSource)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                ) {
                                    expanded = !expanded
                                }
                                .onKeyEvent { event ->
                                    if (event.type != KeyEventType.KeyDown) return@onKeyEvent false
                                    when (event.key) {
                                        Key.DirectionDown -> {
                                            if (!expanded) {
                                                expanded = true
                                            } else {
                                                highlightedIndex = (highlightedIndex + 1).coerceAtMost(items.size - 1)
                                            }
                                            true
                                        }
                                        Key.DirectionUp -> {
                                            if (expanded) {
                                                highlightedIndex = (highlightedIndex - 1).coerceAtLeast(0)
                                            }
                                            true
                                        }
                                        Key.Enter -> {
                                            if (expanded && highlightedIndex in items.indices) {
                                                onSelectionChange(highlightedIndex, items[highlightedIndex])
                                                expanded = false
                                            } else if (!expanded) {
                                                expanded = true
                                            }
                                            true
                                        }
                                        Key.Escape -> {
                                            expanded = false
                                            true
                                        }
                                        Key.Spacebar -> {
                                            if (!expanded) {
                                                expanded = true
                                                true
                                            } else false
                                        }
                                        else -> false
                                    }
                                }
                        } else Modifier
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = selectedLabel ?: placeholder ?: "Select...",
                    style = typography.subheadline,
                    fontSize = comboFontSize,
                    color = if (selectedLabel != null) colors.textPrimary else colors.textTertiary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f).padding(start = 12.dp),
                )

                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .width(16.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(chevronBg),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = LucideChevronDown,
                        tint = chevronTint,
                        modifier = Modifier
                            .size(10.dp)
                            .rotate(chevronRotation),
                    )
                }
            } // Row
            } // outline Box

            DropdownPopup(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                anchorWidthPx = triggerWidthPx,
                anchorHeightPx = triggerHeightPx,
                matchAnchorWidth = false,
                placement = placement,
                modifier = Modifier
                    .darwinGlass(
                        shape = shapes.large,
                        fallbackColor = if (colors.isDark) Color(0xFF171717) else Color.White,
                    )
                    .border(
                        1.dp,
                        if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f),
                        shapes.large,
                    )
                    .heightIn(max = 280.dp),
            ) {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(vertical = 5.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                ) {
                    items.forEachIndexed { index, label ->
                        val isSelected = index == selected
                        val isHighlighted = index == highlightedIndex
                        val itemInteractionSource = remember { MutableInteractionSource() }
                        val isItemHovered by itemInteractionSource.collectIsHoveredAsState()

                        val itemBackgroundColor = when {
                            isSelected -> colors.accent
                            isHighlighted || isItemHovered ->
                                if (colors.isDark) Color.White.copy(alpha = 0.08f)
                                else Color.Black.copy(alpha = 0.05f)
                            else -> Color.Transparent
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(comboMetrics.minHeightFor(controlSize))
                                .hoverable(itemInteractionSource)
                                .clickable(
                                    interactionSource = itemInteractionSource,
                                    indication = null,
                                ) {
                                    onSelectionChange(index, label)
                                    expanded = false
                                },
                        ) {
                            if (itemBackgroundColor != Color.Transparent) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 5.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(itemBackgroundColor),
                                )
                            }
                            Text(
                                text = label,
                                style = typography.subheadline,
                                fontSize = comboFontSize,
                                color = when {
                                    isSelected -> colors.onAccent
                                    else -> colors.textPrimary
                                },
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(horizontal = 13.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ComboBoxPreview() {
    DarwinTheme {
        var selected by remember { mutableStateOf<Int?>(null) }
        ComboBox(
            items = listOf("Apple", "Banana", "Cherry"),
            selected = selected,
            onSelectionChange = { index, _ -> selected = index },
            header = "Fruit",
        )
    }
}
