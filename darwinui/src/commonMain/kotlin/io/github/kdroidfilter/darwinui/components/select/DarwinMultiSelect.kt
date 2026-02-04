package io.github.kdroidfilter.darwinui.components.select

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.glassOrDefault
import io.github.kdroidfilter.darwinui.theme.glassBorderOrDefault

/**
 * Darwin UI multiple-selection dropdown.
 *
 * Mirrors the React darwin-ui MultiSelect component with macOS-inspired styling.
 * Displays selected items as tags/chips inside the trigger, or a count summary
 * when the selection exceeds [maxDisplayCount].
 *
 * @param options          Available options to choose from.
 * @param selectedValues   Currently selected option values.
 * @param onValuesChange   Callback invoked with the updated list of selected values.
 * @param placeholder      Text displayed when nothing is selected.
 * @param enabled          Whether the component is interactive.
 * @param glass            Whether to apply the glass-morphism effect.
 * @param showTags         Whether to display individual tags or always show a count.
 * @param maxDisplayCount  Maximum number of tags to display before collapsing to a count.
 * @param label            Optional label text displayed above the trigger.
 * @param modifier         Modifier applied to the root layout.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DarwinMultiSelect(
    options: List<DarwinSelectOption>,
    selectedValues: List<String>,
    onValuesChange: (List<String>) -> Unit,
    placeholder: String = "Select...",
    enabled: Boolean = true,
    glass: Boolean = false,
    showTags: Boolean = true,
    maxDisplayCount: Int = 3,
    label: String? = null,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes
    val typography = DarwinTheme.typography

    var expanded by remember { mutableStateOf(false) }
    var highlightedIndex by remember { mutableStateOf(-1) }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }

    val selectedOptions = remember(selectedValues, options) {
        options.filter { it.value in selectedValues }
    }

    val borderColor = when {
        isFocused || expanded -> colors.inputFocusBorder
        else -> glassBorderOrDefault(glass, colors.inputBorder)
    }

    val backgroundColor = glassOrDefault(glass, colors.inputBackground)

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

        // Trigger
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 40.dp)
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
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                // Content area
                Box(modifier = Modifier.weight(1f)) {
                    if (selectedValues.isEmpty()) {
                        // Placeholder
                        DarwinText(
                            text = placeholder,
                            style = typography.bodyMedium,
                            color = colors.textTertiary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.align(Alignment.CenterStart),
                        )
                    } else if (!showTags || selectedValues.size > maxDisplayCount) {
                        // Count summary
                        DarwinText(
                            text = "${selectedValues.size} selected",
                            style = typography.bodyMedium,
                            color = colors.textPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.align(Alignment.CenterStart),
                        )
                    } else {
                        // Tags
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            selectedOptions.forEach { option ->
                                SelectTag(
                                    label = option.label,
                                    enabled = enabled,
                                    onRemove = {
                                        toggleValue(option.value)
                                    },
                                )
                            }
                        }
                    }
                }

                // Chevron icon
                DarwinText(
                    text = "\u25BC",
                    style = typography.labelSmall,
                    color = colors.textTertiary,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .rotate(chevronRotation),
                )
            }

            // Dropdown menu
            DarwinDropdownPopup(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(colors.card, shapes.large)
                    .border(1.dp, colors.border, shapes.large)
                    .heightIn(max = 280.dp),
            ) {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(vertical = 4.dp),
                ) {
                    options.forEachIndexed { index, option ->
                        val isSelected = option.value in selectedValues
                        val isHighlighted = index == highlightedIndex
                        val itemBackgroundColor = when {
                            isHighlighted -> colors.accent.copy(alpha = 0.06f)
                            isSelected -> colors.accent.copy(alpha = 0.10f)
                            else -> Color.Transparent
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(36.dp)
                                .then(
                                    if (!option.disabled) {
                                        Modifier.clickable {
                                            toggleValue(option.value)
                                        }
                                    } else Modifier
                                )
                                .background(itemBackgroundColor)
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            // Checkbox indicator
                            CheckboxIndicator(
                                checked = isSelected,
                                enabled = !option.disabled,
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            DarwinText(
                                text = option.label,
                                style = typography.bodyMedium,
                                color = when {
                                    option.disabled -> colors.textQuaternary
                                    else -> colors.textPrimary
                                },
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Small tag/chip displayed inside the multi-select trigger for selected items.
 */
@Composable
private fun SelectTag(
    label: String,
    enabled: Boolean,
    onRemove: () -> Unit,
) {
    val colors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes
    val typography = DarwinTheme.typography

    Row(
        modifier = Modifier
            .height(24.dp)
            .clip(shapes.small)
            .background(colors.accent.copy(alpha = 0.15f))
            .padding(start = 8.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DarwinText(
            text = label,
            style = typography.labelSmall,
            color = colors.accent,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.widthIn(max = 100.dp),
        )

        if (enabled) {
            DarwinText(
                text = "\u2715",
                style = typography.labelSmall,
                color = colors.accent.copy(alpha = 0.7f),
                modifier = Modifier
                    .padding(start = 2.dp)
                    .size(16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        onRemove()
                    },
            )
        }
    }
}

/**
 * Simple checkbox indicator drawn with text characters for cross-platform compatibility.
 */
@Composable
private fun CheckboxIndicator(
    checked: Boolean,
    enabled: Boolean,
) {
    val colors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes
    val typography = DarwinTheme.typography

    val bgColor = when {
        checked && enabled -> colors.accent
        checked && !enabled -> colors.accent.copy(alpha = 0.4f)
        else -> Color.Transparent
    }

    val borderCol = when {
        checked -> Color.Transparent
        enabled -> colors.inputBorder
        else -> colors.textQuaternary
    }

    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(shapes.extraSmall)
            .background(bgColor)
            .border(
                width = if (checked) 0.dp else 1.5.dp,
                color = borderCol,
                shape = shapes.extraSmall,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (checked) {
            DarwinText(
                text = "\u2713",
                style = typography.labelSmall,
                color = colors.onAccent,
            )
        }
    }
}
