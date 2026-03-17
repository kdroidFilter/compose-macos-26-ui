package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icons
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosDuration
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalContentColor
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalTextStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Zinc400
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Zinc500
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Zinc900
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosGlass
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosTween

// =============================================================================
// Search suggestion keyboard navigation
// =============================================================================

internal class SearchKeyboardState {
    var focusedIndex by mutableStateOf(-1)
    private val items = mutableListOf<() -> Unit>()

    /** Called by the parent to allow suggestion clicks to cancel a pending focus-loss close. */
    var onInteraction: (() -> Unit)? = null

    fun register(onClick: () -> Unit): Int {
        val index = items.size
        items.add(onClick)
        return index
    }

    fun moveFocus(delta: Int) {
        if (items.isEmpty()) return
        val next = when {
            focusedIndex == -1 && delta > 0 -> 0
            focusedIndex == -1 && delta < 0 -> items.lastIndex
            else -> (focusedIndex + delta).coerceIn(0, items.lastIndex)
        }
        focusedIndex = next
    }

    fun activateFocused(): Boolean {
        if (focusedIndex in items.indices) {
            items[focusedIndex]()
            return true
        }
        return false
    }
}

internal val LocalSearchKeyboardState = compositionLocalOf<SearchKeyboardState?> { null }

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
    colors: SearchFieldColors? = null,
    suggestions: (@Composable ColumnScope.() -> Unit)? = null,
) {
    val focusRequester = remember { FocusRequester() }
    val showSuggestions = expanded && suggestions != null && value.isNotEmpty()
    // Create a fresh keyboard state for each search query so items re-register
    val keyboardState = remember(value) { SearchKeyboardState() }
    val style = LocalTitleBarStyle.current

    // Delay focus-loss collapse so that mouse clicks on suggestions can be
    // processed before the popup disappears.
    val scope = rememberCoroutineScope()
    var focusLostJob by remember { mutableStateOf<Job?>(null) }

    fun cancelPendingClose() {
        focusLostJob?.cancel()
        focusLostJob = null
    }

    fun closeSearch() {
        cancelPendingClose()
        onValueChange("")
        onExpandedChange(false)
    }

    // Let suggestion clicks cancel the pending close
    LaunchedEffect(keyboardState) {
        keyboardState.onInteraction = { cancelPendingClose() }
    }

    LaunchedEffect(expanded) {
        if (expanded) focusRequester.requestFocus()
    }

    Box(modifier = modifier, contentAlignment = Alignment.CenterEnd) {
        // Collapsed: toolbar button
        AnimatedVisibility(
            visible = !expanded,
            enter = fadeIn(macosTween(MacosDuration.Normal)),
            exit = fadeOut(macosTween(MacosDuration.Fast)),
        ) {
            TitleBarButtonGroup {
                TitleBarGroupButton(onClick = { onExpandedChange(true) }) {
                    Icon(icon = Icons.Search, modifier = Modifier.size(style.iconSize + 2.dp))
                }
            }
        }

        // Expanded: search field
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(macosTween(MacosDuration.Normal)) +
                    expandHorizontally(
                        animationSpec = macosTween(MacosDuration.Normal),
                        expandFrom = Alignment.End,
                    ),
            exit = fadeOut(macosTween(MacosDuration.Fast)) +
                    shrinkHorizontally(
                        animationSpec = macosTween(MacosDuration.Fast),
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
                onClose = { closeSearch() },
                onFocusLost = {
                    if (showSuggestions) {
                        // Delay closing to let click events on suggestions propagate
                        focusLostJob = scope.launch {
                            delay(200)
                            onValueChange("")
                            onExpandedChange(false)
                        }
                    } else {
                        onValueChange("")
                        onExpandedChange(false)
                    }
                },
                keyboardState = if (showSuggestions) keyboardState else null,
                colors = colors,
            )
        }

        // Suggestions popup
        if (showSuggestions) {
            SearchSuggestionsPopup(
                expandedWidth = expandedWidth,
                fieldHeight = style.buttonHeight,
                keyboardState = keyboardState,
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
    onFocusLost: () -> Unit = {},
    keyboardState: SearchKeyboardState? = null,
    colors: SearchFieldColors? = null,
) {
    val scheme = MacosTheme.colorScheme
    val typography = MacosTheme.typography
    val isDark = scheme.isDark
    val style = LocalTitleBarStyle.current

    // Adapt dimensions to title bar style
    val fieldHeight = style.buttonHeight
    val searchIconSize = style.iconSize + 2.dp
    val closeButtonSize = when (style) {
        TitleBarStyle.UnifiedCompact -> 14.dp
        else -> 16.dp
    }
    val closeIconSize = when (style) {
        TitleBarStyle.UnifiedCompact -> 8.dp
        else -> 10.dp
    }
    val textStyle = when (style) {
        TitleBarStyle.UnifiedCompact -> typography.caption2
        else -> typography.caption1
    }
    val hPadding = when (style) {
        TitleBarStyle.UnifiedCompact -> 6.dp
        else -> 8.dp
    }
    val itemSpacing = when (style) {
        TitleBarStyle.UnifiedCompact -> 4.dp
        else -> 6.dp
    }

    val shape = MacosTheme.shapes.full
    val bgColor = if (colors?.backgroundColor != null && colors.backgroundColor != Color.Unspecified) {
        colors.backgroundColor
    } else {
        if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.055f)
    }
    val ringColor = scheme.accent.copy(alpha = 0.4f)
    val textColor = colors?.textColor ?: scheme.textPrimary
    val placeholderColor = colors?.placeholderColor ?: scheme.textTertiary
    val iconColor = colors?.iconColor ?: scheme.textTertiary

    // Track whether the field has received focus at least once to avoid
    // triggering onFocusLost during initial composition
    var hasBeenFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                if (focusState.hasFocus) {
                    hasBeenFocused = true
                } else if (hasBeenFocused) {
                    onFocusLost()
                }
            }
            .onPreviewKeyEvent { event ->
                if (event.type != KeyEventType.KeyDown || keyboardState == null) return@onPreviewKeyEvent false
                when (event.key) {
                    Key.DirectionDown -> {
                        keyboardState.moveFocus(1)
                        true
                    }
                    Key.DirectionUp -> {
                        keyboardState.moveFocus(-1)
                        true
                    }
                    Key.Enter -> {
                        if (keyboardState.focusedIndex >= 0) {
                            keyboardState.activateFocused()
                            true
                        } else false
                    }
                    Key.Escape -> {
                        onClose()
                        true
                    }
                    else -> false
                }
            },
        singleLine = true,
        textStyle = textStyle.copy(color = textColor),
        cursorBrush = SolidColor(scheme.accent),
        keyboardActions = KeyboardActions(
            onSearch = onSearch?.let { { it(value) } },
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .width(expandedWidth)
                    .height(fieldHeight)
                    .shadow(
                        elevation = 4.dp,
                        shape = shape,
                        clip = false,
                        ambientColor = scheme.accent.copy(alpha = 0.25f),
                        spotColor = scheme.accent.copy(alpha = 0.25f),
                    )
                    .clip(shape)
                    .background(bgColor, shape)
                    .border(1.5.dp, ringColor, shape)
                    .padding(horizontal = hPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(itemSpacing),
            ) {
                // Search icon
                Icon(
                    icon = Icons.Search,
                    tint = iconColor,
                    modifier = Modifier.size(searchIconSize),
                )

                // Text field
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        CompositionLocalProvider(
                            LocalTextStyle provides textStyle.copy(
                                color = placeholderColor,
                            ),
                        ) {
                            Text(placeholder)
                        }
                    }
                    innerTextField()
                }

                // Close button
                CloseCircleButton(
                    onClick = onClose,
                    isDark = isDark,
                    buttonSize = closeButtonSize,
                    iconSize = closeIconSize,
                )
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
    buttonSize: Dp = 16.dp,
    iconSize: Dp = 10.dp,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val bgColor by animateColorAsState(
        targetValue = when {
            isHovered -> if (isDark) Color.White.copy(alpha = 0.35f) else Color.Black.copy(alpha = 0.30f)
            else -> if (isDark) Color.White.copy(alpha = 0.25f) else Color.Black.copy(alpha = 0.20f)
        },
        animationSpec = macosTween(MacosDuration.Fast),
        label = "close_circle_bg",
    )
    val iconColor by animateColorAsState(
        targetValue = when {
            isHovered -> if (isDark) Color.Black.copy(alpha = 0.8f) else Color.White.copy(alpha = 0.9f)
            else -> if (isDark) Color.Black.copy(alpha = 0.65f) else Color.White.copy(alpha = 0.8f)
        },
        animationSpec = macosTween(MacosDuration.Fast),
        label = "close_circle_icon",
    )

    Box(
        modifier = Modifier
            .size(buttonSize)
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
            icon = Icons.X,
            tint = iconColor,
            modifier = Modifier.size(iconSize),
        )
    }
}

// =============================================================================
// SearchSuggestionsPopup — dropdown with categorized suggestions
// =============================================================================

@Composable
private fun SearchSuggestionsPopup(
    expandedWidth: Dp,
    fieldHeight: Dp,
    keyboardState: SearchKeyboardState,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = MacosTheme.colorScheme
    val isDark = colors.isDark
    val shapes = MacosTheme.shapes
    val density = androidx.compose.ui.platform.LocalDensity.current

    val fallbackBg = if (isDark) Zinc900.copy(alpha = 0.95f) else Color.White.copy(alpha = 0.95f)
    val borderColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

    // Field height + 4dp gap, converted to pixels
    val offsetY = with(density) { (fieldHeight + 4.dp).roundToPx() }

    Popup(
        alignment = Alignment.TopEnd,
        offset = IntOffset(0, offsetY),
        properties = PopupProperties(focusable = false),
    ) {
        val scrollState = rememberScrollState()

        CompositionLocalProvider(LocalSearchKeyboardState provides keyboardState) {
            Column(
                modifier = Modifier
                    .width(expandedWidth)
                    .widthIn(min = 200.dp)
                    .shadow(elevation = 8.dp, shape = shapes.large)
                    .macosGlass(shape = shapes.large, fallbackColor = fallbackBg)
                    .border(1.dp, borderColor, shapes.large)
                    .heightIn(max = 400.dp)
                    .verticalScroll(scrollState)
                    .padding(vertical = 4.dp),
                content = content,
            )
        }
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
    val isDark = MacosTheme.colorScheme.isDark
    val typography = MacosTheme.typography

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Text(
            text = text,
            style = typography.caption1.copy(
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
    val isDark = MacosTheme.colorScheme.isDark
    val typography = MacosTheme.typography
    val shapes = MacosTheme.shapes

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    // Register for keyboard navigation
    val keyboardState = LocalSearchKeyboardState.current
    val itemIndex = remember(keyboardState) {
        if (enabled) keyboardState?.register(onClick) ?: -1 else -1
    }
    val isKeyboardFocused = keyboardState != null && keyboardState.focusedIndex == itemIndex

    // Clear keyboard focus on mouse hover
    if (isHovered && keyboardState != null && keyboardState.focusedIndex != -1) {
        keyboardState.focusedIndex = -1
    }

    val isHighlighted = isHovered || isKeyboardFocused

    val bgColor = when {
        !enabled -> Color.Transparent
        isHighlighted -> if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.05f)
        else -> Color.Transparent
    }

    val textColor = if (isDark) Color.White.copy(alpha = 0.85f) else Color.Black.copy(alpha = 0.85f)

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
                            onClick = {
                                keyboardState?.onInteraction?.invoke()
                                onClick()
                            },
                        )
                } else Modifier
            )
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides typography.subheadline.copy(color = textColor),
            LocalContentColor provides textColor,
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
    val isDark = MacosTheme.colorScheme.isDark

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
