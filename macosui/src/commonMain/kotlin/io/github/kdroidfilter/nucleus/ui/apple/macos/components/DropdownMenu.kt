package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.geometry.Offset
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.PushButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icons
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import kotlinx.coroutines.delay
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalTextStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosGlass

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
 * A dropdown menu component for macOS UI.
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
    placement: MenuPlacement = MenuPlacement.Below,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = MacosTheme.colorScheme
    val isDark = colors.isDark

    val fallbackBg = if (isDark) Color(0xFF18181B).copy(alpha = 0.95f) else Color(0xFFFAFAFA)
    val borderColor = if (isDark) Color.White.copy(alpha = 0.12f) else Color.Black.copy(alpha = 0.08f)
    val menuShape = RoundedCornerShape(13.dp)

    // Capture the anchor's window position for full-screen popup positioning
    var anchorPosition by remember { mutableStateOf(IntOffset.Zero) }
    var anchorSize by remember { mutableStateOf(IntSize.Zero) }
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var menuSize by remember { mutableStateOf(IntSize.Zero) }
    Spacer(
        modifier = Modifier.onGloballyPositioned { coordinates ->
            val windowPos = coordinates.localToWindow(Offset.Zero)
            anchorPosition = IntOffset(windowPos.x.toInt(), windowPos.y.toInt())
            anchorSize = coordinates.size
        },
    )

    val density = LocalDensity.current
    val gapPx = with(density) { 4.dp.roundToPx() }
    val origin = placement.transformOrigin(centerX = true)

    if (expanded) {
        val keyboardState = remember { DropdownMenuKeyboardState() }
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
                ) + offset.y
                val clampedOffset = IntOffset(
                    x = (anchorPosition.x + offset.x).coerceIn(0, (containerSize.width - menuSize.width).coerceAtLeast(0)),
                    y = rawY.coerceIn(0, (containerSize.height - menuSize.height).coerceAtLeast(0)),
                )
                AnimatedVisibility(
                    visible = expanded,
                    modifier = Modifier
                        .onSizeChanged { menuSize = it }
                        .offset { clampedOffset },
                    enter = fadeIn(tween(150)) +
                            scaleIn(
                                initialScale = 0.95f,
                                transformOrigin = origin,
                                animationSpec = tween(150),
                            ),
                    exit = fadeOut(tween(100)) +
                            scaleOut(
                                targetScale = 0.95f,
                                transformOrigin = origin,
                                animationSpec = tween(100),
                            ),
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
                                .widthIn(min = 200.dp)
                                .shadow(
                                    elevation = 25.dp,
                                    shape = menuShape,
                                    ambientColor = Color.Black.copy(alpha = 0.10f),
                                    spotColor = Color.Black.copy(alpha = 0.16f),
                                )
                                .macosGlass(shape = menuShape, fallbackColor = fallbackBg)
                                .border(0.5.dp, borderColor, menuShape)
                                .heightIn(max = 360.dp)
                                .verticalScroll(scrollState)
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
    val colors = MacosTheme.colorScheme
    val isDark = colors.isDark
    val accentColor = colors.accent

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

    val itemBackground = when {
        !enabled -> Color.Transparent
        destructive && isHighlighted -> colors.destructive
        isHighlighted -> accentColor
        else -> Color.Transparent
    }

    val textColor = when {
        isHighlighted && enabled -> Color.White
        destructive -> colors.destructive
        else -> if (isDark) Color.White.copy(alpha = 0.85f) else Color(0xFF1A1A1A)
    }

    val itemShape = RoundedCornerShape(8.dp)
    val contentStyle = TextStyle(fontSize = 13.sp, color = textColor, letterSpacing = (-0.2).sp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .height(24.dp)
            .clip(itemShape)
            .background(itemBackground, itemShape)
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
            .padding(horizontal = 10.dp)
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
                LocalTextStyle provides contentStyle,
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
    val colors = MacosTheme.colorScheme
    val isDark = colors.isDark
    val accentColor = colors.accent

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

    val submenuOpenBg = if (isDark) Color.White.copy(alpha = 0.10f) else Color(0xFFE6E6E6)

    val itemBackground = when {
        !enabled -> Color.Transparent
        isTriggerHovered -> accentColor
        submenuExpanded -> submenuOpenBg
        else -> Color.Transparent
    }

    val textColor = when {
        isTriggerHovered && enabled -> Color.White
        else -> if (isDark) Color.White.copy(alpha = 0.85f) else Color(0xFF1A1A1A)
    }

    val chevronColor = when {
        isTriggerHovered && enabled -> Color.White
        else -> if (isDark) Color(0xFFA1A1AA) else Color(0xFF4C4C4C)
    }

    val itemShape = RoundedCornerShape(8.dp)
    val contentStyle = TextStyle(fontSize = 13.sp, color = textColor, letterSpacing = (-0.2).sp)

    // Callback for children to signal they're active
    val keepAliveCallback = remember<(Boolean) -> Unit> { { active -> childActive = active } }

    Box {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned { itemWidthPx = it.size.width }
                .padding(horizontal = 5.dp)
                .height(24.dp)
                .clip(itemShape)
                .background(itemBackground, itemShape)
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
                .padding(horizontal = 10.dp)
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
                    LocalTextStyle provides contentStyle,
                ) {
                    content()
                }
            }

            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                icon = Icons.ChevronRight,
                modifier = Modifier.size(12.dp),
                tint = chevronColor,
            )
        }

        if (submenuExpanded) {
            val submenuFallbackBg = if (isDark) Color(0xFF18181B).copy(alpha = 0.95f) else Color(0xFFFAFAFA)
            val submenuBorderColor = if (isDark) Color.White.copy(alpha = 0.12f) else Color.Black.copy(alpha = 0.08f)
            val submenuShape = RoundedCornerShape(13.dp)

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
                            .widthIn(min = 200.dp)
                            .shadow(
                                elevation = 25.dp,
                                shape = submenuShape,
                                ambientColor = Color.Black.copy(alpha = 0.10f),
                                spotColor = Color.Black.copy(alpha = 0.16f),
                            )
                            .macosGlass(shape = submenuShape, fallbackColor = submenuFallbackBg)
                            .border(0.5.dp, submenuBorderColor, submenuShape)
                            .heightIn(max = 360.dp)
                            .verticalScroll(scrollState)
                            .padding(vertical = 5.dp),
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
                        icon = Icons.Check,
                        modifier = Modifier.size(12.dp),
                        tint = if (MacosTheme.colorScheme.isDark) Color(0xFFD4D4D8) else Color(0xFF3F3F46),
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
    val isDark = MacosTheme.colorScheme.isDark

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .height(21.dp)
            .padding(horizontal = 13.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        BasicText(
            text = text,
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.Black,
                color = if (isDark) Color(0xFFA1A1AA) else Color(0xFF727272),
                letterSpacing = (-0.15).sp,
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
    val isDark = MacosTheme.colorScheme.isDark

    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .padding(vertical = 5.dp)
            .height(1.dp)
            .background(
                if (isDark) Color.White.copy(alpha = 0.08f)
                else Color(0xFFE6E6E6)
            ),
    )
}


@Preview
@Composable
private fun DropdownMenuPreview() {
    MacosTheme {
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
