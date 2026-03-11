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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import io.github.kdroidfilter.darwinui.icons.LucideCheck
import io.github.kdroidfilter.darwinui.icons.LucideChevronDown
import io.github.kdroidfilter.darwinui.icons.LucideX
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

/**
 * A multi-select combo box with index-based selection.
 *
 * @param items The list of string items to display.
 * @param selectedIndices The currently selected indices.
 * @param onSelectionChange Callback with the updated list of selected indices.
 * @param modifier Modifier applied to the component.
 * @param header Optional header/label text above the component.
 * @param placeholder Optional placeholder text when nothing is selected.
 * @param disabled Whether the component is non-interactive.
 * @param showTags Whether to show removable tag chips below the trigger.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MultiSelectComboBox(
    items: List<String>,
    selectedIndices: List<Int>,
    onSelectionChange: (List<Int>) -> Unit,
    modifier: Modifier = Modifier,
    header: String? = null,
    placeholder: String = "Select...",
    disabled: Boolean = false,
    showTags: Boolean = true,
) {
    val colors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes
    val typography = DarwinTheme.typography
    val enabled = !disabled

    var expanded by remember { mutableStateOf(false) }
    var highlightedIndex by remember { mutableStateOf(-1) }
    var triggerWidthPx by remember { mutableStateOf(0) }
    var triggerHeightPx by remember { mutableStateOf(0) }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isTriggerHovered by interactionSource.collectIsHoveredAsState()
    val focusRequester = remember { FocusRequester() }

    val borderColor = when {
        isFocused || expanded -> colors.inputFocusBorder
        else -> colors.inputBorder
    }

    val backgroundColor = when {
        isTriggerHovered -> if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.04f)
        else -> colors.inputBackground
    }

    val chevronRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
    )

    LaunchedEffect(expanded) {
        if (expanded) {
            highlightedIndex = -1
        }
    }

    fun toggleIndex(index: Int) {
        val updated = if (index in selectedIndices) {
            selectedIndices - index
        } else {
            selectedIndices + index
        }
        onSelectionChange(updated)
    }

    val displayText = when {
        selectedIndices.isEmpty() -> null
        selectedIndices.size == 1 -> items.getOrNull(selectedIndices.first()) ?: "1 selected"
        else -> "${selectedIndices.size} selected"
    }

    Column(modifier = modifier) {
        if (header != null) {
            Text(
                text = header,
                style = typography.labelMedium,
                color = colors.textPrimary,
                modifier = Modifier.padding(bottom = 6.dp),
            )
        }

        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .onGloballyPositioned { coordinates ->
                        triggerWidthPx = coordinates.size.width
                        triggerHeightPx = coordinates.size.height
                    }
                    .clip(shapes.medium)
                    .background(backgroundColor)
                    .border(
                        width = if (isFocused || expanded) 2.dp else 1.dp,
                        color = borderColor,
                        shape = shapes.medium,
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
                                        Key.Enter, Key.Spacebar -> {
                                            if (expanded && highlightedIndex in items.indices) {
                                                toggleIndex(highlightedIndex)
                                            } else if (!expanded) {
                                                expanded = true
                                            }
                                            true
                                        }
                                        Key.Escape -> {
                                            expanded = false
                                            true
                                        }
                                        else -> false
                                    }
                                }
                        } else Modifier
                    )
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = displayText ?: placeholder,
                    style = typography.bodyMedium,
                    color = if (displayText != null) colors.textPrimary else colors.textTertiary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )

                Icon(
                    imageVector = LucideChevronDown,
                    tint = colors.textTertiary,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .rotate(chevronRotation),
                )
            }

            DropdownPopup(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                anchorWidthPx = triggerWidthPx,
                anchorHeightPx = triggerHeightPx,
                matchAnchorWidth = false,
                modifier = Modifier
                    .clip(shapes.large)
                    .background(
                        if (colors.isDark) Color(0xFF171717) else Color.White,
                        shapes.large,
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
                        .padding(vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    items.forEachIndexed { index, label ->
                        val isSelected = index in selectedIndices
                        val isHighlighted = index == highlightedIndex
                        val itemInteractionSource = remember { MutableInteractionSource() }
                        val isItemHovered by itemInteractionSource.collectIsHoveredAsState()

                        val itemBackgroundColor = when {
                            isHighlighted || isItemHovered ->
                                if (colors.isDark) Color.White.copy(alpha = 0.10f)
                                else Color.Black.copy(alpha = 0.035f)
                            else -> Color.Transparent
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp)
                                .clip(shapes.medium)
                                .hoverable(itemInteractionSource)
                                .clickable(
                                    interactionSource = itemInteractionSource,
                                    indication = null,
                                ) {
                                    toggleIndex(index)
                                }
                                .background(itemBackgroundColor)
                                .padding(start = 8.dp, end = 8.dp, top = 6.dp, bottom = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier.size(16.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = LucideCheck,
                                        tint = colors.accent,
                                        modifier = Modifier.size(12.dp),
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = label,
                                style = typography.bodyMedium,
                                color = when {
                                    isSelected -> colors.textPrimary
                                    else -> if (colors.isDark) Color(0xFFD4D4D8) else Color(0xFF3F3F46)
                                },
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }

        if (showTags && selectedIndices.isNotEmpty()) {
            FlowRow(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                selectedIndices.forEach { index ->
                    val label = items.getOrNull(index) ?: return@forEach
                    Row(
                        modifier = Modifier
                            .clip(shapes.medium)
                            .background(
                                if (colors.isDark) Color.White.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.05f),
                            )
                            .border(
                                1.dp,
                                if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f),
                                shapes.medium,
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = label,
                            style = typography.bodySmall.copy(fontSize = 12.sp),
                            color = if (colors.isDark) Color(0xFFD4D4D8) else Color(0xFF3F3F46),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )

                        if (enabled) {
                            Spacer(modifier = Modifier.width(6.dp))
                            val removeInteraction = remember { MutableInteractionSource() }
                            val isRemoveHovered by removeInteraction.collectIsHoveredAsState()
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(shapes.small)
                                    .hoverable(removeInteraction)
                                    .background(
                                        if (isRemoveHovered) {
                                            if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.05f)
                                        } else Color.Transparent,
                                    )
                                    .clickable(
                                        interactionSource = removeInteraction,
                                        indication = null,
                                    ) {
                                        toggleIndex(index)
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    imageVector = LucideX,
                                    tint = if (isRemoveHovered) {
                                        if (colors.isDark) Color(0xFFE4E4E7) else Color(0xFF3F3F46)
                                    } else {
                                        if (colors.isDark) Color(0xFF71717A) else Color(0xFFA1A1AA)
                                    },
                                    modifier = Modifier.size(12.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MultiSelectComboBoxPreview() {
    DarwinTheme {
        var selected by remember { mutableStateOf(emptyList<Int>()) }
        MultiSelectComboBox(
            items = listOf("Apple", "Banana", "Cherry"),
            selectedIndices = selected,
            onSelectionChange = { selected = it },
            header = "Fruits",
        )
    }
}
