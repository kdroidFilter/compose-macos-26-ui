package io.github.kdroidfilter.darwinui.components.select

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
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.icons.DarwinIcon
import io.github.kdroidfilter.darwinui.icons.LucideCheck
import io.github.kdroidfilter.darwinui.icons.LucideChevronDown
import io.github.kdroidfilter.darwinui.icons.LucideX
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DarwinMultiSelect(
    options: List<DarwinSelectOption>,
    selectedValues: List<String>,
    onValuesChange: (List<String>) -> Unit,
    placeholder: String = "Select...",
    enabled: Boolean = true,
    showTags: Boolean = true,
    label: String? = null,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes
    val typography = DarwinTheme.typography

    var expanded by remember { mutableStateOf(false) }
    var highlightedIndex by remember { mutableStateOf(-1) }
    var triggerWidthPx by remember { mutableStateOf(0) }
    var triggerHeightPx by remember { mutableStateOf(0) }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isTriggerHovered by interactionSource.collectIsHoveredAsState()
    val focusRequester = remember { FocusRequester() }

    val selectedOptions = remember(selectedValues, options) {
        options.filter { it.value in selectedValues }
    }

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

    val enabledOptions = remember(options) {
        options.mapIndexedNotNull { index, option ->
            if (!option.disabled) index else null
        }
    }

    // Reset highlighted index when dropdown opens
    LaunchedEffect(expanded) {
        if (expanded) {
            highlightedIndex = -1
        }
    }

    fun toggleValue(value: String) {
        val updated = if (value in selectedValues) {
            selectedValues - value
        } else {
            selectedValues + value
        }
        onValuesChange(updated)
    }

    // Display text in trigger
    val displayText = when {
        selectedValues.isEmpty() -> null
        selectedValues.size == 1 -> selectedOptions.firstOrNull()?.label ?: "1 selected"
        else -> "${selectedValues.size} selected"
    }

    Column(modifier = modifier) {
        // Label
        if (label != null) {
            DarwinText(
                text = label,
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
                                                val currentPos = enabledOptions.indexOf(highlightedIndex)
                                                val nextPos = (currentPos + 1).coerceAtMost(enabledOptions.size - 1)
                                                highlightedIndex = enabledOptions.getOrElse(nextPos) { highlightedIndex }
                                            }
                                            true
                                        }
                                        Key.DirectionUp -> {
                                            if (expanded) {
                                                val currentPos = enabledOptions.indexOf(highlightedIndex)
                                                val prevPos = (currentPos - 1).coerceAtLeast(0)
                                                highlightedIndex = enabledOptions.getOrElse(prevPos) { highlightedIndex }
                                            }
                                            true
                                        }
                                        Key.Enter, Key.Spacebar -> {
                                            if (expanded && highlightedIndex in options.indices) {
                                                val option = options[highlightedIndex]
                                                if (!option.disabled) {
                                                    toggleValue(option.value)
                                                }
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
                // Display text or placeholder
                DarwinText(
                    text = displayText ?: placeholder,
                    style = typography.bodyMedium,
                    color = if (displayText != null) colors.textPrimary else colors.textTertiary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )

                // Chevron icon
                DarwinIcon(
                    imageVector = LucideChevronDown,
                    tint = colors.textTertiary,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .rotate(chevronRotation),
                )
            }

            // Dropdown menu
            DarwinDropdownPopup(
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
                    options.forEachIndexed { index, option ->
                        val isSelected = option.value in selectedValues
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
                                .then(
                                    if (!option.disabled) {
                                        Modifier
                                            .hoverable(itemInteractionSource)
                                            .clickable(
                                                interactionSource = itemInteractionSource,
                                                indication = null,
                                            ) {
                                                toggleValue(option.value)
                                            }
                                    } else Modifier
                                )
                                .background(itemBackgroundColor)
                                .padding(start = 8.dp, end = 8.dp, top = 6.dp, bottom = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {

                            Box(
                                modifier = Modifier.size(16.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                if (isSelected) {
                                    DarwinIcon(
                                        imageVector = LucideCheck,
                                        tint = colors.accent,
                                        size = 12.dp,
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            DarwinText(
                                text = option.label,
                                style = typography.bodyMedium,
                                color = when {
                                    option.disabled -> colors.textQuaternary
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

        if (showTags && selectedValues.isNotEmpty()) {
            FlowRow(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                selectedOptions.forEach { option ->
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
                        DarwinText(
                            text = option.label,
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
                                        toggleValue(option.value)
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                DarwinIcon(
                                    imageVector = LucideX,
                                    tint = if (isRemoveHovered) {
                                        if (colors.isDark) Color(0xFFE4E4E7) else Color(0xFF3F3F46) // zinc-200 / zinc-700
                                    } else {
                                        if (colors.isDark) Color(0xFF71717A) else Color(0xFFA1A1AA) // zinc-500 / zinc-400
                                    },
                                    size = 12.dp,
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
private fun DarwinMultiSelectPreview() {
    DarwinTheme {
        var selected by remember { mutableStateOf(emptyList<String>()) }
        DarwinMultiSelect(
            options = listOf(
                DarwinSelectOption("a", "Apple"),
                DarwinSelectOption("b", "Banana"),
                DarwinSelectOption("c", "Cherry"),
            ),
            selectedValues = selected,
            onValuesChange = { selected = it },
            label = "Fruits",
        )
    }
}
