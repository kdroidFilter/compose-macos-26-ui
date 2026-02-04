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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
 * Option model for DarwinSelect and DarwinMultiSelect.
 */
data class DarwinSelectOption(
    val value: String,
    val label: String,
    val disabled: Boolean = false,
)

/**
 * Darwin UI single-selection dropdown.
 *
 * Mirrors the React darwin-ui Select component with macOS-inspired styling.
 *
 * @param options         Available options to choose from.
 * @param selectedValue   Currently selected option value, or null if nothing is selected.
 * @param onValueChange   Callback invoked when the user selects an option.
 * @param placeholder     Text displayed when no option is selected.
 * @param enabled         Whether the component is interactive.
 * @param glass           Whether to apply the glass-morphism effect.
 * @param label           Optional label text displayed above the trigger.
 * @param modifier        Modifier applied to the root layout.
 */
@Composable
fun DarwinSelect(
    options: List<DarwinSelectOption>,
    selectedValue: String?,
    onValueChange: (String) -> Unit,
    placeholder: String = "Select...",
    enabled: Boolean = true,
    glass: Boolean = false,
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

    val selectedOption = remember(selectedValue, options) {
        options.firstOrNull { it.value == selectedValue }
    }

    val borderColor = when {
        isFocused || expanded -> colors.inputFocusBorder
        else -> glassBorderOrDefault(glass, colors.inputBorder)
    }

    val backgroundColor = glassOrDefault(glass, colors.inputBackground)

    val chevronRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
    )

    // Reset highlighted index when dropdown opens
    LaunchedEffect(expanded) {
        if (expanded) {
            highlightedIndex = options.indexOfFirst { it.value == selectedValue }
        }
    }

    val enabledOptions = remember(options) {
        options.mapIndexedNotNull { index, option ->
            if (!option.disabled) index else null
        }
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
                    .height(40.dp)
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
                                        Key.Enter -> {
                                            if (expanded && highlightedIndex in options.indices) {
                                                val option = options[highlightedIndex]
                                                if (!option.disabled) {
                                                    onValueChange(option.value)
                                                    expanded = false
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
                    )
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                // Selected label or placeholder
                DarwinText(
                    text = selectedOption?.label ?: placeholder,
                    style = typography.bodyMedium,
                    color = if (selectedOption != null) colors.textPrimary else colors.textTertiary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )

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
                        val isSelected = option.value == selectedValue
                        val isHighlighted = index == highlightedIndex
                        val itemBackgroundColor = when {
                            isSelected -> colors.accent.copy(alpha = 0.10f)
                            isHighlighted -> colors.accent.copy(alpha = 0.06f)
                            else -> Color.Transparent
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(36.dp)
                                .then(
                                    if (!option.disabled) {
                                        Modifier.clickable {
                                            onValueChange(option.value)
                                            expanded = false
                                        }
                                    } else Modifier
                                )
                                .background(itemBackgroundColor)
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            DarwinText(
                                text = option.label,
                                style = typography.bodyMedium,
                                color = when {
                                    option.disabled -> colors.textQuaternary
                                    isSelected -> colors.accent
                                    else -> colors.textPrimary
                                },
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f),
                            )

                            if (isSelected) {
                                DarwinText(
                                    text = "\u2713",
                                    style = typography.bodyMedium,
                                    color = colors.accent,
                                    modifier = Modifier.padding(start = 8.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
