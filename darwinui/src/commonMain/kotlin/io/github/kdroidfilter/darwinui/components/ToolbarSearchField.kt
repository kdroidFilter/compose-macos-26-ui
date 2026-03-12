package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideSearch
import io.github.kdroidfilter.darwinui.icons.LucideX
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.Zinc400
import io.github.kdroidfilter.darwinui.theme.Zinc500
import io.github.kdroidfilter.darwinui.theme.Zinc900
import io.github.kdroidfilter.darwinui.theme.darwinGlass
import io.github.kdroidfilter.darwinui.theme.darwinTween

// =============================================================================
// ToolbarSearchField — macOS Finder-style expandable search
// =============================================================================

/**
 * macOS Finder-style expandable search field for toolbars.
 *
 * In its collapsed state, displays a circular search icon button.
 * When expanded, animates into a pill-shaped search input with an optional
 * categorized suggestions dropdown below (matching Finder's predictive search).
 *
 * @param value Current search text.
 * @param onValueChange Called when the search text changes.
 * @param modifier Modifier applied to the outer container.
 * @param placeholder Placeholder text shown when [value] is empty.
 * @param expanded Whether the search field is currently expanded.
 * @param onExpandedChange Called when the expanded state should change.
 * @param expandedWidth Width of the search field when expanded.
 * @param onSearch Called when the user presses Enter/Go. Pass `null` to disable.
 * @param suggestions Optional dropdown content shown below the field when
 *   expanded and [value] is not empty. Use [SearchSuggestionHeader],
 *   [SearchSuggestionItem], and [SearchSuggestionSeparator] inside.
 */
@Composable
fun ToolbarSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search",
    expanded: Boolean = false,
    onExpandedChange: (Boolean) -> Unit = {},
    expandedWidth: Dp = 200.dp,
    onSearch: ((String) -> Unit)? = null,
    suggestions: (@Composable ColumnScope.() -> Unit)? = null,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(expanded) {
        if (expanded) focusRequester.requestFocus()
    }

    Box(modifier = modifier, contentAlignment = Alignment.CenterEnd) {
        // Collapsed: icon button
        AnimatedVisibility(
            visible = !expanded,
            enter = fadeIn(darwinTween(DarwinDuration.Normal)),
            exit = fadeOut(darwinTween(DarwinDuration.Fast)),
        ) {
            IconButton(onClick = { onExpandedChange(true) }) {
                Icon(LucideSearch, modifier = Modifier.size(14.dp))
            }
        }

        // Expanded: search field
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(darwinTween(DarwinDuration.Normal)) +
                    expandHorizontally(
                        animationSpec = darwinTween(DarwinDuration.Normal),
                        expandFrom = Alignment.End,
                    ),
            exit = fadeOut(darwinTween(DarwinDuration.Fast)) +
                    shrinkHorizontally(
                        animationSpec = darwinTween(DarwinDuration.Fast),
                        shrinkTowards = Alignment.End,
                    ),
        ) {
            ExpandedField(
                value = value,
                onValueChange = onValueChange,
                placeholder = placeholder,
                expandedWidth = expandedWidth,
                focusRequester = focusRequester,
                onSearch = onSearch,
                onClose = {
                    onValueChange("")
                    onExpandedChange(false)
                },
            )
        }

        // Suggestions popup
        if (expanded && suggestions != null && value.isNotEmpty()) {
            SearchSuggestionsPopup(
                expandedWidth = expandedWidth,
                content = suggestions,
            )
        }
    }
}

// =============================================================================
// Expanded field — internal
// =============================================================================

@Composable
private fun ExpandedField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    expandedWidth: Dp,
    focusRequester: FocusRequester,
    onSearch: ((String) -> Unit)?,
    onClose: () -> Unit,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography
    val isDark = colors.isDark

    val shape = DarwinTheme.shapes.full
    val bgColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.055f)
    val ringColor = colors.ring.copy(alpha = 0.5f)
    val textColor = colors.textPrimary
    val placeholderColor = colors.textTertiary
    val iconColor = colors.textTertiary

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.focusRequester(focusRequester),
        singleLine = true,
        textStyle = typography.bodySmall.copy(color = textColor),
        cursorBrush = SolidColor(colors.accent),
        keyboardActions = KeyboardActions(
            onSearch = onSearch?.let { { it(value) } },
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .width(expandedWidth)
                    .height(28.dp)
                    .clip(shape)
                    .background(bgColor, shape)
                    .border(1.5.dp, ringColor, shape)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                // Search icon
                Icon(
                    imageVector = LucideSearch,
                    tint = iconColor,
                    modifier = Modifier.size(14.dp),
                )

                // Text field
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        CompositionLocalProvider(
                            LocalDarwinTextStyle provides typography.bodySmall.copy(
                                color = placeholderColor,
                            ),
                        ) {
                            Text(placeholder)
                        }
                    }
                    innerTextField()
                }

                // Close button
                CloseCircleButton(onClick = onClose, isDark = isDark)
            }
        },
    )
}

// =============================================================================
// CloseCircleButton — filled circle with × icon
// =============================================================================

@Composable
private fun CloseCircleButton(
    onClick: () -> Unit,
    isDark: Boolean,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val bgColor by animateColorAsState(
        targetValue = when {
            isHovered -> if (isDark) Color.White.copy(alpha = 0.35f) else Color.Black.copy(alpha = 0.30f)
            else -> if (isDark) Color.White.copy(alpha = 0.25f) else Color.Black.copy(alpha = 0.20f)
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "close_circle_bg",
    )
    val iconColor by animateColorAsState(
        targetValue = when {
            isHovered -> if (isDark) Color.Black.copy(alpha = 0.8f) else Color.White.copy(alpha = 0.9f)
            else -> if (isDark) Color.Black.copy(alpha = 0.65f) else Color.White.copy(alpha = 0.8f)
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "close_circle_icon",
    )

    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(bgColor, CircleShape)
            .hoverable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = LucideX,
            tint = iconColor,
            modifier = Modifier.size(10.dp),
        )
    }
}

// =============================================================================
// SearchSuggestionsPopup — dropdown with categorized suggestions
// =============================================================================

@Composable
private fun SearchSuggestionsPopup(
    expandedWidth: Dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = DarwinTheme.colors
    val isDark = colors.isDark
    val shapes = DarwinTheme.shapes
    val density = androidx.compose.ui.platform.LocalDensity.current

    val fallbackBg = if (isDark) Zinc900.copy(alpha = 0.95f) else Color.White.copy(alpha = 0.95f)
    val borderColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

    // 28dp field height + 4dp gap, converted to pixels
    val offsetY = with(density) { 32.dp.roundToPx() }

    Popup(
        alignment = Alignment.TopEnd,
        offset = IntOffset(0, offsetY),
        properties = PopupProperties(focusable = false),
    ) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .width(expandedWidth)
                .widthIn(min = 200.dp)
                .shadow(elevation = 8.dp, shape = shapes.large)
                .darwinGlass(shape = shapes.large, fallbackColor = fallbackBg)
                .border(1.dp, borderColor, shapes.large)
                .heightIn(max = 400.dp)
                .verticalScroll(scrollState)
                .padding(vertical = 4.dp),
            content = content,
        )
    }
}

// =============================================================================
// SearchSuggestionHeader — section header in suggestions
// =============================================================================

/**
 * A non-clickable section header within a [ToolbarSearchField] suggestions dropdown.
 * Matches the Finder search suggestion category headers.
 *
 * @param text The section title to display.
 * @param modifier Modifier applied to the header container.
 */
@Composable
fun SearchSuggestionHeader(
    text: String,
    modifier: Modifier = Modifier,
) {
    val isDark = DarwinTheme.colors.isDark
    val typography = DarwinTheme.typography

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Text(
            text = text,
            style = typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = if (isDark) Zinc400 else Zinc500,
            ),
        )
    }
}

// =============================================================================
// SearchSuggestionItem — clickable suggestion
// =============================================================================

/**
 * A clickable suggestion item within a [ToolbarSearchField] suggestions dropdown.
 * Matches the Finder search suggestion item style with hover feedback.
 *
 * @param onClick Called when the item is clicked.
 * @param modifier Modifier applied to the item row.
 * @param enabled Whether the item is interactive.
 * @param content The item label content.
 */
@Composable
fun SearchSuggestionItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    val isDark = DarwinTheme.colors.isDark
    val typography = DarwinTheme.typography
    val shapes = DarwinTheme.shapes

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val bgColor = when {
        !enabled -> Color.Transparent
        isHovered -> if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.05f)
        else -> Color.Transparent
    }

    val textColor = if (isDark) Color.White.copy(alpha = 0.85f) else Color.Black.copy(alpha = 0.85f)

    val contentAlpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.5f,
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "suggestion_item_alpha",
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .clip(shapes.small)
            .background(bgColor, shapes.small)
            .then(
                if (enabled) {
                    Modifier
                        .hoverable(interactionSource)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = onClick,
                        )
                } else Modifier
            )
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalDarwinTextStyle provides typography.bodyMedium.copy(color = textColor),
            LocalDarwinContentColor provides textColor,
        ) {
            content()
        }
    }
}

// =============================================================================
// SearchSuggestionSeparator — thin divider between sections
// =============================================================================

/**
 * A horizontal separator line between sections in a [ToolbarSearchField]
 * suggestions dropdown.
 *
 * @param modifier Modifier applied to the separator.
 */
@Composable
fun SearchSuggestionSeparator(modifier: Modifier = Modifier) {
    val isDark = DarwinTheme.colors.isDark

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .height(0.5.dp)
            .background(
                if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f),
            ),
    )
}
