package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.components.PushButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideCheck
import io.github.kdroidfilter.darwinui.icons.LucideChevronRight
import kotlinx.coroutines.delay
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.Red500
import io.github.kdroidfilter.darwinui.theme.Zinc100
import io.github.kdroidfilter.darwinui.theme.Zinc300
import io.github.kdroidfilter.darwinui.theme.Zinc400
import io.github.kdroidfilter.darwinui.theme.Zinc500
import io.github.kdroidfilter.darwinui.theme.Zinc700
import io.github.kdroidfilter.darwinui.theme.Zinc900
import io.github.kdroidfilter.darwinui.theme.darwinGlass

// =============================================================================
// Keyboard navigation state
// =============================================================================

internal class DropdownMenuKeyboardState {
    var focusedIndex by mutableIntStateOf(-1)
    private val items = mutableListOf<DropdownMenuItemRegistration>()

    fun register(registration: DropdownMenuItemRegistration): Int {
        val index = items.size
        items.add(registration)
        return index
    }

    fun moveFocus(delta: Int) {
        if (items.isEmpty()) return
        val enabledIndices = items.indices.filter { items[it].enabled }
        if (enabledIndices.isEmpty()) return

        val currentPos = enabledIndices.indexOf(focusedIndex)
        val nextPos = when {
            currentPos == -1 && delta > 0 -> 0
            currentPos == -1 && delta < 0 -> enabledIndices.lastIndex
            else -> (currentPos + delta).coerceIn(0, enabledIndices.lastIndex)
        }
        focusedIndex = enabledIndices[nextPos]
    }

    fun activateFocused() {
        if (focusedIndex in items.indices && items[focusedIndex].enabled) {
            items[focusedIndex].onClick()
        }
    }
}

internal data class DropdownMenuItemRegistration(
    val enabled: Boolean,
    val onClick: () -> Unit,
)

internal val LocalDropdownMenuKeyboardState =
    compositionLocalOf<DropdownMenuKeyboardState?> { null }

// =============================================================================
// DropdownMenu — main component
// =============================================================================

/**
 * A dropdown menu component for Darwin UI.
 *
 * Displays a list of actions or options anchored to an external trigger element.
 * The menu appears when [expanded] is true with a scale and fade animation, and
 * dismisses when the user clicks outside of it.
 *
 * Wrap the trigger composable and this [DropdownMenu] together in a [Box] to anchor
 * the popup relative to your trigger element.
 *
 * @param expanded Whether the dropdown menu is currently visible.
 * @param onDismissRequest Callback invoked when the user clicks outside the menu.
 * @param modifier Modifier applied to the menu container.
 * @param content The menu content.
 */
@Composable
fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: IntOffset = IntOffset(0, 0),
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = DarwinTheme.colorScheme
    val isDark = colors.isDark
    val shapes = DarwinTheme.shapes

    val fallbackBg = if (isDark) Zinc900.copy(alpha = 0.95f) else Color.White.copy(alpha = 0.95f)
    val borderColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

    if (expanded) {
        val keyboardState = remember { DropdownMenuKeyboardState() }
        Popup(
            offset = offset,
            onDismissRequest = onDismissRequest,
            properties = PopupProperties(focusable = true),
        ) {
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(tween(150)) +
                        scaleIn(
                            initialScale = 0.95f,
                            transformOrigin = TransformOrigin(0.5f, 0f),
                            animationSpec = tween(150),
                        ) +
                        slideInVertically(tween(150)) { -20 },
                exit = fadeOut(tween(100)) +
                        scaleOut(
                            targetScale = 0.95f,
                            transformOrigin = TransformOrigin(0.5f, 0f),
                            animationSpec = tween(100),
                        ) +
                        slideOutVertically(tween(100)) { -20 },
            ) {
                val scrollState = rememberScrollState()

                CompositionLocalProvider(LocalDropdownMenuKeyboardState provides keyboardState) {
                    Column(
                        modifier = modifier
                            .onKeyEvent { event ->
                                if (event.type != KeyEventType.KeyDown) return@onKeyEvent false
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
                                        keyboardState.activateFocused()
                                        true
                                    }
                                    Key.Escape -> {
                                        onDismissRequest()
                                        true
                                    }
                                    else -> false
                                }
                            }
                            .width(IntrinsicSize.Max)
                            .widthIn(min = 180.dp)
                            .shadow(elevation = 8.dp, shape = shapes.large)
                            .darwinGlass(shape = shapes.large, fallbackColor = fallbackBg)
                            .border(1.dp, borderColor, shapes.large)
                            .heightIn(max = 360.dp)
                            .verticalScroll(scrollState)
                            .padding(vertical = 4.dp), // p-1 top/bottom
                        content = content,
                    )
                }
            }
        }
    }
}

// =============================================================================
// DropdownMenuItem
// =============================================================================

/**
 * A clickable menu item within a [DropdownMenu].
 *
 * @param onClick Callback invoked when the item is clicked.
 * @param enabled Whether the item is interactive.
 * @param destructive When true, renders the item in red for dangerous actions.
 * @param modifier Modifier applied to the item row.
 * @param leadingIcon Optional composable displayed before the label.
 * @param trailingContent Optional composable displayed after the label.
 * @param content The item label content.
 */
@Composable
fun DropdownMenuItem(
    onClick: () -> Unit,
    enabled: Boolean = true,
    destructive: Boolean = false,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val isDark = DarwinTheme.colorScheme.isDark
    val typography = DarwinTheme.typography
    val shapes = DarwinTheme.shapes

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    // Register for keyboard navigation
    val keyboardState = LocalDropdownMenuKeyboardState.current
    val itemIndex = remember {
        keyboardState?.register(
            DropdownMenuItemRegistration(enabled = enabled, onClick = onClick),
        ) ?: -1
    }
    val isKeyboardFocused = keyboardState != null && keyboardState.focusedIndex == itemIndex

    // Clear keyboard focus when mouse enters any item
    if (isHovered && keyboardState != null && keyboardState.focusedIndex != -1) {
        keyboardState.focusedIndex = -1
    }

    val isHighlighted = isHovered || isKeyboardFocused

    // Destructive: hover:bg-red-500/10
    val itemBackground = when {
        !enabled -> Color.Transparent
        destructive && isHighlighted -> Red500.copy(alpha = 0.10f)
        isHighlighted -> if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.05f)
        else -> Color.Transparent
    }

    // Destructive: text-red-500 (always)
    val textColor = when {
        destructive -> Red500
        isHighlighted -> if (isDark) Zinc100 else Zinc900
        else -> if (isDark) Zinc300 else Zinc700
    }

    val contentStyle = typography.subheadline.merge(TextStyle(color = textColor))

    // Outer padding(horizontal=4.dp) simulates the p-1 container inset
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp) // container p-1 inset
            .clip(shapes.small) // rounded-lg = 8dp
            .background(itemBackground, shapes.small)
            .then(
                if (enabled) {
                    Modifier
                        .hoverable(interactionSource)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = onClick,
                        )
                } else {
                    Modifier
                }
            )
            .padding(horizontal = 8.dp, vertical = 6.dp) // px-2 py-1.5
            .alpha(if (enabled) 1f else 0.5f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        if (leadingIcon != null) {
            leadingIcon()
            Spacer(modifier = Modifier.width(8.dp))
        }

        Box(modifier = Modifier.weight(1f)) {
            CompositionLocalProvider(
                LocalDarwinTextStyle provides contentStyle,
            ) {
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
// Submenu parent signaling
// =============================================================================

/**
 * CompositionLocal that allows a child submenu to signal its parent that it
 * is still active (expanded), preventing the parent from closing while a
 * descendant submenu is open.
 */
private val LocalParentSubmenuKeepAlive = compositionLocalOf<((Boolean) -> Unit)?> { null }

// =============================================================================
// DropdownMenuSubMenu
// =============================================================================

/**
 * A menu item that opens a submenu to the right when hovered or clicked.
 * Matches macOS native submenu behavior with a chevron-right indicator.
 * Supports arbitrary nesting depth.
 *
 * @param enabled Whether the item is interactive.
 * @param modifier Modifier applied to the item row.
 * @param leadingIcon Optional composable displayed before the label.
 * @param submenuContent The submenu content, using the same [ColumnScope] as [DropdownMenu].
 * @param content The item label content.
 */
@Composable
fun DropdownMenuSubMenu(
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    submenuContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit,
) {
    val isDark = DarwinTheme.colorScheme.isDark
    val typography = DarwinTheme.typography
    val shapes = DarwinTheme.shapes

    val triggerInteractionSource = remember { MutableInteractionSource() }
    val isTriggerHovered by triggerInteractionSource.collectIsHoveredAsState()
    val submenuInteractionSource = remember { MutableInteractionSource() }
    val isSubmenuHovered by submenuInteractionSource.collectIsHoveredAsState()
    var submenuExpanded by remember { mutableStateOf(false) }
    var childActive by remember { mutableStateOf(false) }
    var itemWidthPx by remember { mutableStateOf(0) }

    // Signal parent that we're active
    val parentKeepAlive = LocalParentSubmenuKeepAlive.current
    LaunchedEffect(submenuExpanded) {
        parentKeepAlive?.invoke(submenuExpanded)
    }

    val isAnyHovered = isTriggerHovered || isSubmenuHovered || childActive

    // Open immediately on hover, close with delay only when nothing is hovered
    LaunchedEffect(isAnyHovered) {
        if (isAnyHovered && enabled) {
            submenuExpanded = true
        } else if (!isAnyHovered) {
            delay(300)
            submenuExpanded = false
        }
    }

    val isActive = isTriggerHovered || submenuExpanded

    val itemBackground = when {
        !enabled -> Color.Transparent
        isActive -> if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.05f)
        else -> Color.Transparent
    }

    val textColor = when {
        isActive -> if (isDark) Zinc100 else Zinc900
        else -> if (isDark) Zinc300 else Zinc700
    }

    val contentStyle = typography.subheadline.merge(TextStyle(color = textColor))

    // Callback for children to signal they're active
    val keepAliveCallback = remember<(Boolean) -> Unit> { { active -> childActive = active } }

    Box {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned { itemWidthPx = it.size.width }
                .padding(horizontal = 4.dp)
                .clip(shapes.small)
                .background(itemBackground, shapes.small)
                .then(
                    if (enabled) {
                        Modifier
                            .hoverable(triggerInteractionSource)
                            .clickable(
                                interactionSource = triggerInteractionSource,
                                indication = null,
                                onClick = { submenuExpanded = !submenuExpanded },
                            )
                    } else {
                        Modifier
                    }
                )
                .padding(horizontal = 8.dp, vertical = 6.dp)
                .alpha(if (enabled) 1f else 0.5f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            if (leadingIcon != null) {
                leadingIcon()
                Spacer(modifier = Modifier.width(8.dp))
            }

            Box(modifier = Modifier.weight(1f)) {
                CompositionLocalProvider(
                    LocalDarwinTextStyle provides contentStyle,
                ) {
                    content()
                }
            }

            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                LucideChevronRight,
                modifier = Modifier.size(12.dp),
                tint = if (isDark) Zinc400 else Zinc500,
            )
        }

        if (submenuExpanded) {
            val fallbackBg = if (isDark) Zinc900.copy(alpha = 0.95f) else Color.White.copy(alpha = 0.95f)
            val borderColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

            Popup(
                offset = IntOffset(itemWidthPx, 0),
                properties = PopupProperties(focusable = false),
            ) {
                val scrollState = rememberScrollState()

                CompositionLocalProvider(LocalParentSubmenuKeepAlive provides keepAliveCallback) {
                    Column(
                        modifier = Modifier
                            .hoverable(submenuInteractionSource)
                            .width(IntrinsicSize.Max)
                            .widthIn(min = 180.dp)
                            .shadow(elevation = 8.dp, shape = shapes.large)
                            .darwinGlass(shape = shapes.large, fallbackColor = fallbackBg)
                            .border(1.dp, borderColor, shapes.large)
                            .heightIn(max = 360.dp)
                            .verticalScroll(scrollState)
                            .padding(vertical = 4.dp),
                        content = submenuContent,
                    )
                }
            }
        }
    }
}

// =============================================================================
// DropdownMenuCheckboxItem
// =============================================================================

/**
 * A menu item with a checkbox indicator within a [DropdownMenu].
 *
 * @param checked Whether the checkbox is currently checked.
 * @param onCheckedChange Callback invoked when the user toggles the item.
 * @param enabled Whether the item is interactive.
 * @param modifier Modifier applied to the item row.
 * @param content The item label content.
 */
@Composable
fun DropdownMenuCheckboxItem(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    DropdownMenuItem(
        onClick = { onCheckedChange(!checked) },
        enabled = enabled,
        modifier = modifier,
        leadingIcon = {

            Box(
                modifier = Modifier.size(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                if (checked) {
                    Icon(
                        imageVector = LucideCheck,
                        modifier = Modifier.size(12.dp),
                        tint = if (DarwinTheme.colorScheme.isDark) Zinc300 else Zinc700,
                    )
                }
            }
        },
        content = content,
    )
}

// =============================================================================
// DropdownMenuLabel
// =============================================================================

/**
 * A non-clickable label or section header within a [DropdownMenu].
 *
 * @param text The label text to display.
 * @param modifier Modifier applied to the label container.
 */
@Composable
fun DropdownMenuLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    val isDark = DarwinTheme.colorScheme.isDark
    val typography = DarwinTheme.typography

    // Outer horizontal padding 4dp (container p-1 inset) + inner px-2 (8dp)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp) // container p-1 inset
            .padding(horizontal = 8.dp, vertical = 6.dp), // px-2 py-1.5
    ) {
        BasicText(
            text = text,
            style = typography.caption1.merge(
                TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDark) Zinc400 else Zinc500,
                )
            ),
        )
    }
}

// =============================================================================
// DropdownMenuSeparator
// =============================================================================

/**
 * A horizontal separator line within a [DropdownMenu].
 *
 * @param modifier Modifier applied to the separator.
 */
@Composable
fun DropdownMenuSeparator(modifier: Modifier = Modifier) {
    val isDark = DarwinTheme.colorScheme.isDark

    // -mx-1 extends past the container p-1 → edge-to-edge within the border.
    // Since the Column has no horizontal padding, separator fills full width.
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) // my-1
            .height(1.dp)
            .background(
                if (isDark) Color.White.copy(alpha = 0.10f)
                else Color.Black.copy(alpha = 0.10f)
            ),
    )
}

// =============================================================================
// DropdownMenuShortcut
// =============================================================================

/**
 * A right-aligned keyboard shortcut label within a [DropdownMenuItem].
 *
 * @param text The shortcut text to display (e.g., "Cmd+C").
 * @param modifier Modifier applied to the shortcut text.
 */
@Composable
fun DropdownMenuShortcut(
    text: String,
    modifier: Modifier = Modifier,
) {
    val isDark = DarwinTheme.colorScheme.isDark
    val typography = DarwinTheme.typography

    BasicText(
        text = text,
        style = typography.caption1.merge(
            TextStyle(
                color = if (isDark) Zinc400 else Zinc500,
                letterSpacing = 1.2.sp, // tracking-widest ≈ 0.1em at 12sp
            )
        ),
        modifier = modifier,
    )
}

@Preview
@Composable
private fun DropdownMenuPreview() {
    DarwinTheme {
        var expanded by remember { mutableStateOf(false) }
        Box {
            PushButton(onClick = { expanded = !expanded }) { Text("Menu") }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                DropdownMenuItem(onClick = {}) { Text("Item 1") }
                DropdownMenuItem(onClick = {}) { Text("Item 2") }
            }
        }
    }
}
