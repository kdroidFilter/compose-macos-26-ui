package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.VerticalScrollbar
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TrackClickBehavior
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.rememberScrollbarState
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icons
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideChevronsLeft
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.SystemIcon
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SpringPreset
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.GlassMaterialSize
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.rememberLiquidState
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.GlassType
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalGlassType
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalSidebarResize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalTitleBarHeight
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalWindowActive
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SidebarStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalSidebarHide
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalSidebarVisible
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalSidebarWidth
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosDuration
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.liquidGlassFade
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosGlassMaterial
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosSpring
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosTween
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.runtime.Immutable
import androidx.compose.ui.tooling.preview.Preview

// =============================================================================
// SidebarItemColors — customizable colors for sidebar items
// =============================================================================

@Immutable
class SidebarItemColors(
    val activeBackgroundColor: Color,
    val inactiveBackgroundColor: Color,
    val activeContentColor: Color,
    val inactiveContentColor: Color,
) {
    fun copy(
        activeBackgroundColor: Color = this.activeBackgroundColor,
        inactiveBackgroundColor: Color = this.inactiveBackgroundColor,
        activeContentColor: Color = this.activeContentColor,
        inactiveContentColor: Color = this.inactiveContentColor,
    ) = SidebarItemColors(activeBackgroundColor, inactiveBackgroundColor, activeContentColor, inactiveContentColor)
}

// =============================================================================
// SidebarDefaults
// =============================================================================

object SidebarDefaults {

    @Composable
    fun itemColors(
        activeBackgroundColor: Color = with(MacosTheme.colorScheme) {
            if (isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.11f)
        },
        inactiveBackgroundColor: Color = Color.Transparent,
        activeContentColor: Color = MacosTheme.colorScheme.accent,
        inactiveContentColor: Color = MacosTheme.colorScheme.textPrimary,
    ) = SidebarItemColors(activeBackgroundColor, inactiveBackgroundColor, activeContentColor, inactiveContentColor)
}

// =============================================================================
// Animation helper — critically damped spring (no overshoot) with Smooth stiffness
// =============================================================================

/** Sidebar spring: same stiffness as [SpringPreset.Smooth] but critically
 *  damped (ratio=1.0) so animations settle progressively without any bounce. */
private fun <T> sidebarSpring() = spring<T>(
    dampingRatio = 1.0f,
    stiffness = SpringPreset.Smooth.stiffness,
)

// =============================================================================
// Data
// =============================================================================

/**
 * Data for a single sidebar navigation item.
 *
 * [onClick] is intentionally excluded from [equals]/[hashCode] to keep
 * [SidebarItem] stable for Compose's skipping mechanism (lambdas don't
 * implement structural equality).
 *
 * @param label Display text for the item.
 * @param onClick Callback invoked when this item is clicked.
 * @param icon Optional Lucide-style [ImageVector] rendered before the label.
 * @param group Optional group header text. When any item has a non-null group,
 *   items are rendered in groups with a header label above each group.
 * @param id Unique identifier used for active-item matching. Defaults to [label].
 */
/**
 * Data for a single sidebar navigation item.
 *
 * [onClick] is intentionally excluded from [equals]/[hashCode] to keep
 * [SidebarItem] stable for Compose's skipping mechanism (lambdas don't
 * implement structural equality).
 *
 * @param label Display text for the item.
 * @param onClick Callback invoked when this item is clicked.
 * @param icon Optional Lucide-style [ImageVector] rendered before the label.
 * @param group Optional group header text. When any item has a non-null group,
 *   items are rendered in groups with a header label above each group.
 * @param id Unique identifier used for active-item matching. Defaults to [label].
 * @param children Child items displayed in a collapsible disclosure section.
 *   When non-empty, a disclosure chevron is shown that toggles visibility of children.
 */
class SidebarItem(
    val label: String,
    val onClick: () -> Unit,
    val icon: ImageVector? = null,
    val group: String? = null,
    val id: String = label,
    val children: List<SidebarItem> = emptyList(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SidebarItem) return false
        return id == other.id && label == other.label && icon == other.icon
            && group == other.group && children == other.children
    }

    override fun hashCode(): Int {
        var result = label.hashCode()
        result = 31 * result + (icon?.hashCode() ?: 0)
        result = 31 * result + (group?.hashCode() ?: 0)
        result = 31 * result + id.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }
}

// =============================================================================
// Sidebar — main component
// =============================================================================

/**
 * macOS-style sidebar navigation component.
 *
 * @param items Navigation items to display.
 * @param activeItem The [SidebarItem.id] of the currently selected item.
 * @param modifier Modifier applied to the root container.
 * @param width Width of the sidebar when expanded.
 * @param collapsedWidth Width of the sidebar when collapsed (icon-only mode).
 * @param onLogout Optional logout callback. When non-null, a logout button is shown at the bottom.
 * @param logoutLabel Label for the logout button.
 * @param collapsed Whether the sidebar is in collapsed (icon-only) mode.
 * @param onCollapsedChange Called when the collapse toggle is clicked.
 * @param collapsible Whether the collapse toggle is shown.
 *   **Note:** Collapse mode is designed for secondary panels and web layouts
 *   where an icon-only sidebar is useful. Do not use it for the primary macOS
 *   app sidebar inside a [Scaffold] — the collapsed rendering breaks the
 *   glass material highlight alignment. Use [Scaffold]'s
 *   [onColumnVisibilityChange][Scaffold] to hide/show the sidebar instead.
 * @param showBorder Whether to show a right border on the sidebar.
 * @param scrollbarTrackClickBehavior Scrollbar track click behavior.
 * @param header Optional composable header above the items.
 * @param pinnedItems Items pinned to the bottom of the sidebar.
 * @param onHideSidebar Optional callback to hide the sidebar. When non-null (or when
 *   [LocalSidebarHide] is provided by a [Scaffold]), a hide button is shown in the
 *   top-right corner of the sidebar header area.
 */
@Composable
fun Sidebar(
    items: List<SidebarItem>,
    activeItem: String,
    modifier: Modifier = Modifier,
    width: Dp = 240.dp,
    collapsedWidth: Dp = 56.dp,
    onLogout: (() -> Unit)? = null,
    logoutLabel: String = "Logout",
    collapsed: Boolean = false,
    onCollapsedChange: ((Boolean) -> Unit)? = null,
    collapsible: Boolean = false,
    showBorder: Boolean = false,
    scrollbarTrackClickBehavior: TrackClickBehavior = TrackClickBehavior.Jump,
    header: (@Composable () -> Unit)? = null,
    pinnedItems: List<SidebarItem> = emptyList(),
    onHideSidebar: (() -> Unit)? = null,
    itemColors: SidebarItemColors = SidebarDefaults.itemColors(),
) {
    val controlSize = LocalControlSize.current
    val sidebarMetrics = MacosTheme.componentStyling.sidebar.metrics
    val colors = MacosTheme.colorScheme
    val isDark = colors.isDark
    val glassType = LocalGlassType.current

    // Resolve hide callback: explicit parameter takes precedence, then CompositionLocal
    val scaffoldHide = LocalSidebarHide.current
    val effectiveHide = onHideSidebar ?: scaffoldHide

    // Scaffold-provided width takes precedence over the parameter
    val scaffoldWidth = LocalSidebarWidth.current
    val effectiveWidth = if (scaffoldWidth != Dp.Unspecified) scaffoldWidth else width

    val hasGroups = remember(items) { items.any { it.group != null } }
    val groupedItems = remember(items) { if (hasGroups) items.groupBy { it.group } else null }

    // Animated width: collapsed=collapsedWidth, expanded=effectiveWidth
    val animatedWidth by animateDpAsState(
        targetValue = if (collapsed) collapsedWidth else effectiveWidth,
        animationSpec = sidebarSpring(),
    )

    // Animated padding: collapsed=6dp, expanded=6dp
    val animatedPadding by animateDpAsState(
        targetValue = if (collapsed) 6.dp else 6.dp,
        animationSpec = sidebarSpring(),
    )

    val animatedTrackStartPadding by animateDpAsState(
        targetValue = if (collapsed) 4.dp else 4.dp,
        animationSpec = sidebarSpring(),
    )
    val animatedTrackEndPadding by animateDpAsState(
        targetValue = if (collapsed) 4.dp else 13.dp,
        animationSpec = sidebarSpring(),
    )


    val hasBottomSection = collapsible || pinnedItems.isNotEmpty() || onLogout != null

    val sidebarContentShape = MacosTheme.shapes.extraLarge

    // Window active state: macOS instantly removes glass vibrancy from inactive windows
    val isWindowActive = LocalWindowActive.current

    val sidebarBorderColor = if (isWindowActive) {
        if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.08f)
    } else {
        if (isDark) Color.White.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.04f)
    }

    // Opaque background that covers the glass material when the window is inactive
    val inactiveOverlay = if (isWindowActive) {
        Color.Transparent
    } else {
        if (isDark) Color(0xFF282828) else Color(0xFFF4F4F4)
    }

    // Tinted glass darkens the entire sidebar content background
    val tintedOverlay = if (glassType == GlassType.Tinted && isWindowActive) {
        if (isDark) Color.Black.copy(alpha = 0.25f) else Color.Black.copy(alpha = 0.08f)
    } else {
        Color.Transparent
    }

    Box(
        modifier = modifier
            .width(animatedWidth)
            .fillMaxHeight()
            .clipToBounds(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(animatedWidth)
                .padding(animatedPadding)
                .macosGlassMaterial(
                    shape = sidebarContentShape,
                    materialSize = GlassMaterialSize.Large,
                    drawShadow = false,
                )
                .background(inactiveOverlay, sidebarContentShape)
                .border(1.dp, sidebarBorderColor, sidebarContentShape),
        ) {
            // ---- Scrollable content ----
            val itemsScrollState = rememberScrollState()
            val titleBarHeight = LocalTitleBarHeight.current
            val sidebarGlassState = rememberLiquidState()
            Box(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .liquefiable(sidebarGlassState)
                        .background(if (isWindowActive) MacosTheme.colorScheme.background else inactiveOverlay)
                        .background(tintedOverlay),
                ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(itemsScrollState),
                ) {
                    // Spacer matching the title bar height minus the sidebar padding
                    if (effectiveHide != null) {
                        Spacer(Modifier.height((titleBarHeight - animatedPadding).coerceAtLeast(0.dp)))
                    }

                    // ---- Header (scrolls with content) ----
                    if (header != null) {
                        val headerFraction by animateFloatAsState(
                            targetValue = if (collapsed) 0f else 1f,
                            animationSpec = sidebarSpring(),
                        )
                        Box(
                            modifier = Modifier
                                .clipToBounds()
                                .graphicsLayer { alpha = headerFraction }
                                .layout { measurable, constraints ->
                                    val placeable = measurable.measure(constraints)
                                    val h = (placeable.height * headerFraction).toInt()
                                    layout(placeable.width, h) {
                                        placeable.placeRelative(0, 0)
                                    }
                                },
                        ) {
                            header()
                        }
                    }

                    // ---- Items ----
                    Column(
                        modifier = Modifier
                            .padding(start = animatedTrackStartPadding, end = animatedTrackEndPadding, top = 6.dp, bottom = 2.dp),
                        verticalArrangement = Arrangement.spacedBy(sidebarMetrics.itemSpacingFor(controlSize)),
                    ) {
                        if (hasGroups && groupedItems != null) {
                            for ((group, groupItems) in groupedItems) {
                                if (group != null) {
                                    GroupHeader(text = group, isCollapsed = collapsed, controlSize = controlSize, sidebarMetrics = sidebarMetrics)
                                }
                                groupItems.forEach { item ->
                                    key(item.id) {
                                        SidebarItemWithVisibility(
                                            item = item,
                                            activeItem = activeItem,
                                            collapsed = collapsed,
                                            controlSize = controlSize,
                                            sidebarMetrics = sidebarMetrics,
                                            itemColors = itemColors,
                                        )
                                    }
                                }
                            }
                        } else {
                            items.forEach { item ->
                                key(item.id) {
                                    SidebarItemWithVisibility(
                                        item = item,
                                        activeItem = activeItem,
                                        collapsed = collapsed,
                                        controlSize = controlSize,
                                        sidebarMetrics = sidebarMetrics,
                                        itemColors = itemColors,
                                    )
                                }
                            }
                        }
                    }
                }
                }

                if (!collapsed) {
                    val scrollbarInset = if (effectiveHide != null) {
                        (titleBarHeight - animatedPadding).coerceAtLeast(0.dp)
                    } else 0.dp
                    VerticalScrollbar(
                        state = rememberScrollbarState(itemsScrollState),
                        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                        trackClickBehavior = scrollbarTrackClickBehavior,
                        topInset = scrollbarInset,
                    )
                }

                // ---- Fixed liquid glass overlay at top (items scroll under it) ----
                if (effectiveHide != null) {
                    val glassHeight = (titleBarHeight - animatedPadding).coerceAtLeast(0.dp)
                    // Liquid glass with progressive fade (no content inside)
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .height(glassHeight)
                            .liquidGlassFade(
                                state = sidebarGlassState,
                                shape = RoundedCornerShape(
                                    topStart = 20.dp, topEnd = 20.dp,
                                    bottomStart = 0.dp, bottomEnd = 0.dp,
                                ),
                            ),
                    )
                    // Hide button (separate, not affected by the fade mask)
                    val hideFraction by animateFloatAsState(
                        targetValue = if (collapsed) 0f else 1f,
                        animationSpec = sidebarSpring(),
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .height(glassHeight)
                            .clipToBounds()
                            .graphicsLayer { alpha = hideFraction }
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(constraints)
                                val h = (placeable.height * hideFraction).toInt()
                                layout(placeable.width, h) {
                                    placeable.placeRelative(0, 0)
                                }
                            },
                        contentAlignment = Alignment.CenterEnd,
                    ) {
                        SidebarHideButton(onClick = effectiveHide)
                    }
                }
            }

            // ---- Bottom section ----
            if (hasBottomSection) {
                Column(
                    modifier = Modifier
                        .background(if (isWindowActive) MacosTheme.colorScheme.background else inactiveOverlay)
                        .background(tintedOverlay)
                        .padding(top = 6.dp, start = 4.dp, end = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(sidebarMetrics.itemSpacingFor(controlSize)),
                ) {
                    if (collapsible) {
                        CollapseToggle(
                            isCollapsed = collapsed,
                            onClick = { onCollapsedChange?.invoke(!collapsed) },
                            controlSize = controlSize,
                            sidebarMetrics = sidebarMetrics,
                        )
                    }

                    pinnedItems.forEach { item ->
                        key(item.id) {
                            SidebarItemWithVisibility(
                                item = item,
                                activeItem = activeItem,
                                collapsed = collapsed,
                                controlSize = controlSize,
                                sidebarMetrics = sidebarMetrics,
                                itemColors = itemColors,
                            )
                        }
                    }

                    if (onLogout != null) {
                        SidebarItemRow(
                            label = logoutLabel,
                            onClick = onLogout,
                            active = false,
                            icon = Icons.LogOut,
                            isCollapsed = collapsed,
                            controlSize = controlSize,
                            sidebarMetrics = sidebarMetrics,
                            itemColors = itemColors,
                        )
                    }
                }
            }
        }

        // Right border
        if (showBorder) {
            val borderColor = colors.border
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(borderColor),
            )
        }

        // Right-edge drag handle for resizing
        val resizeCallbacks = LocalSidebarResize.current
        if (resizeCallbacks != null && !collapsed) {
            val density = LocalDensity.current
            val dragInteractionSource = remember { MutableInteractionSource() }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(8.dp)
                    .fillMaxHeight()
                    .hoverable(dragInteractionSource)
                    .pointerHoverIcon(PointerIcon.Crosshair) // col-resize cursor
                    .draggable(
                        orientation = Orientation.Horizontal,
                        state = rememberDraggableState { deltaPx ->
                            with(density) { resizeCallbacks.onDrag(deltaPx.toDp()) }
                        },
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(onDoubleTap = { resizeCallbacks.onReset() })
                    },
            )
        }
    }
}

/**
 * Renders a single sidebar item with appropriate animation based on
 * whether the item has an icon or not.
 *
 * Icon items: always present, label clips naturally via the sidebar width.
 * Text-only items: height + alpha animate to 0 when collapsed. The element
 * stays in the composition tree so [Arrangement.spacedBy] gaps don't snap.
 *
 * Items with [SidebarItem.children] show a disclosure chevron and render
 * their children in an animated collapsible section.
 */
@Composable
private fun SidebarItemWithVisibility(
    item: SidebarItem,
    activeItem: String,
    collapsed: Boolean,
    controlSize: ControlSize,
    sidebarMetrics: SidebarStyle.Metrics,
    itemColors: SidebarItemColors,
    indentLevel: Int = 0,
) {
    if (item.children.isNotEmpty()) {
        DisclosureItem(
            item = item,
            activeItem = activeItem,
            collapsed = collapsed,
            controlSize = controlSize,
            sidebarMetrics = sidebarMetrics,
            itemColors = itemColors,
            indentLevel = indentLevel,
        )
        return
    }

    // Leaf items inside a disclosure group add disclosureWidth so their content aligns
    // with disclosure parents. Top-level items (indentLevel == 0) get no extra indent.
    val extraIndent = if (indentLevel > 0) sidebarMetrics.disclosureWidthFor(controlSize) else 0.dp
    val indentPadding by animateDpAsState(
        targetValue = if (collapsed) 0.dp else sidebarMetrics.indentFor(indentLevel, controlSize) + extraIndent,
        animationSpec = sidebarSpring(),
    )

    if (item.icon != null) {
        SidebarItemRow(
            label = item.label,
            onClick = item.onClick,
            active = activeItem == item.id,
            icon = item.icon?.let { SystemIcon(it) },
            isCollapsed = collapsed,
            controlSize = controlSize,
            sidebarMetrics = sidebarMetrics,
            itemColors = itemColors,
            modifier = Modifier.padding(start = indentPadding),
        )
    } else {
        val alpha by animateFloatAsState(
            targetValue = if (collapsed) 0f else 1f,
            animationSpec = sidebarSpring(),
        )
        val itemHeight by animateDpAsState(
            targetValue = if (collapsed) 0.dp else sidebarMetrics.itemHeightFor(controlSize),
            animationSpec = sidebarSpring(),
        )

        Box(
            modifier = Modifier
                .height(itemHeight)
                .clipToBounds()
                .graphicsLayer { this.alpha = alpha },
        ) {
            SidebarItemRow(
                label = item.label,
                onClick = item.onClick,
                active = activeItem == item.id,
                icon = null,
                isCollapsed = false,
                controlSize = controlSize,
                sidebarMetrics = sidebarMetrics,
                itemColors = itemColors,
                modifier = Modifier.padding(start = indentPadding),
            )
        }
    }
}

/**
 * A sidebar item with a collapsible disclosure section for its children.
 * Shows a rotating chevron that toggles child visibility.
 */
@Composable
private fun DisclosureItem(
    item: SidebarItem,
    activeItem: String,
    collapsed: Boolean,
    controlSize: ControlSize,
    sidebarMetrics: SidebarStyle.Metrics,
    itemColors: SidebarItemColors,
    indentLevel: Int = 0,
) {
    var expanded by remember { mutableStateOf(true) }
    val chevronRotation by animateFloatAsState(
        targetValue = if (expanded) 90f else 0f,
        animationSpec = sidebarSpring(),
    )

    val disclosureWidth = sidebarMetrics.disclosureWidthFor(controlSize)
    // Animate indent and chevron width to 0 when collapsed so the icon can center.
    val animatedIndent by animateDpAsState(
        targetValue = if (collapsed) 0.dp else sidebarMetrics.indentFor(indentLevel, controlSize),
        animationSpec = sidebarSpring(),
    )
    val animatedDisclosureWidth by animateDpAsState(
        targetValue = if (collapsed) 0.dp else disclosureWidth,
        animationSpec = sidebarSpring(),
    )

    // Parent item row with chevron
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = animatedIndent),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Disclosure chevron (hidden when sidebar is collapsed)
        val chevronAlpha by animateFloatAsState(
            targetValue = if (collapsed) 0f else 1f,
            animationSpec = sidebarSpring(),
        )
        Box(
            modifier = Modifier
                .width(animatedDisclosureWidth)
                .height(sidebarMetrics.itemHeightFor(controlSize))
                .graphicsLayer {
                    alpha = chevronAlpha
                    rotationZ = chevronRotation
                }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) { expanded = !expanded },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                icon = Icons.ChevronRight,
                modifier = Modifier.size(12.dp),
                tint = if (MacosTheme.colorScheme.isDark) Color.White.copy(alpha = 0.25f)
                    else Color.Black.copy(alpha = 0.25f),
            )
        }

        // Item content
        Box(modifier = Modifier.weight(1f)) {
            SidebarItemRow(
                label = item.label,
                onClick = item.onClick,
                active = activeItem == item.id,
                icon = item.icon?.let { SystemIcon(it) },
                isCollapsed = collapsed,
                controlSize = controlSize,
                sidebarMetrics = sidebarMetrics,
                itemColors = itemColors,
            )
        }
    }

    // Children with animated visibility
    val childAlpha by animateFloatAsState(
        targetValue = if (expanded && !collapsed) 1f else 0f,
        animationSpec = sidebarSpring(),
    )
    val childFraction by animateFloatAsState(
        targetValue = if (expanded && !collapsed) 1f else 0f,
        animationSpec = sidebarSpring(),
    )

    Box(
        modifier = Modifier
            .clipToBounds()
            .graphicsLayer { alpha = childAlpha }
            .layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)
                val h = (placeable.height * childFraction).toInt()
                layout(placeable.width, h) { placeable.placeRelative(0, 0) }
            },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(sidebarMetrics.itemSpacingFor(controlSize)),
        ) {
            item.children.forEach { child ->
                key(child.id) {
                    SidebarItemWithVisibility(
                        item = child,
                        activeItem = activeItem,
                        collapsed = collapsed,
                        controlSize = controlSize,
                        sidebarMetrics = sidebarMetrics,
                        itemColors = itemColors,
                        indentLevel = indentLevel + 1,
                    )
                }
            }
        }
    }
}

/**
 * Group header label displayed above a group of sidebar items.
 * Hides with animation when the sidebar is collapsed.
 *
 * Uses manual height + alpha animation instead of [AnimatedVisibility]
 * so the element stays in the composition tree and [Arrangement.spacedBy]
 * gaps don't snap when the exit animation completes.
 */
@Composable
private fun GroupHeader(text: String, isCollapsed: Boolean, controlSize: ControlSize, sidebarMetrics: SidebarStyle.Metrics) {
    val typography = MacosTheme.typography

    val alpha by animateFloatAsState(
        targetValue = if (isCollapsed) 0f else 1f,
        animationSpec = sidebarSpring(),
    )
    val targetHeight = sidebarMetrics.groupHeaderHeightFor(controlSize)
    val maxHeight by animateDpAsState(
        targetValue = if (isCollapsed) 0.dp else targetHeight,
        animationSpec = sidebarSpring(),
    )

    val headerColor = if (MacosTheme.colorScheme.isDark) Color.White.copy(alpha = 0.50f)
        else Color.Black.copy(alpha = 0.50f)
    // Sketch: Large=13sp, Medium/Small=11sp
    val headerStyle = when (controlSize) {
        ControlSize.Large, ControlSize.ExtraLarge -> typography.caption1
        else -> typography.caption2
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = maxHeight)
            .clipToBounds()
            .graphicsLayer { this.alpha = alpha },
        contentAlignment = Alignment.BottomStart,
    ) {
        Text(
            text = text,
            style = headerStyle,
            color = headerColor,
            modifier = Modifier.padding(start = sidebarMetrics.hPaddingFor(controlSize), end = 8.dp, bottom = 4.dp),
        )
    }
}

// =============================================================================
// SidebarItemRow — internal item renderer
// =============================================================================
//
// Uses a single Row layout for both collapsed and expanded states.
// The label is always rendered — the sidebar's clipToBounds naturally
// reveals it from start to end as the width grows (Apple-style push).
// On collapse, the shrinking width clips the label away.
// =============================================================================

@Composable
private fun SidebarItemRow(
    label: String,
    onClick: () -> Unit,
    active: Boolean,
    icon: SystemIcon?,
    isCollapsed: Boolean,
    controlSize: ControlSize,
    sidebarMetrics: SidebarStyle.Metrics,
    itemColors: SidebarItemColors = SidebarDefaults.itemColors(),
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    iconContentDescription: String? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val backgroundColor = if (active) itemColors.activeBackgroundColor else itemColors.inactiveBackgroundColor
    val contentColor = if (active) itemColors.activeContentColor else itemColors.inactiveContentColor

    val itemShape = MacosTheme.shapes.small

    // Collapse fraction drives all collapse animations: 0f = expanded, 1f = collapsed.
    // When controlSize changes (without collapse), the fraction stays at its current
    // value and the new Dp values take effect instantly — no unwanted size animation.
    val collapseFraction by animateFloatAsState(
        targetValue = if (isCollapsed) 1f else 0f,
        animationSpec = sidebarSpring(),
    )

    // When collapsed: center the icon within the collapsed sidebar width.
    // In collapsed mode: outer padding = 4dp, track padding = 4dp → 8dp per side.
    // To center: hPadding = (collapsedWidth - iconDp) / 2 - 8dp
    val iconDp = sidebarMetrics.iconDpFor(controlSize)
    val collapsedHPadding = ((56.dp - iconDp) / 2 - 8.dp).coerceAtLeast(0.dp)
    val hPadding = lerp(sidebarMetrics.hPaddingFor(controlSize), collapsedHPadding, collapseFraction)
    val iconLabelGap = lerp(sidebarMetrics.iconGapFor(controlSize), 0.dp, collapseFraction)
    val labelAlpha = 1f - collapseFraction

    // Sketch: Large=15sp (body), Medium=13sp (caption1), Small=11sp (caption2)
    val typography = MacosTheme.typography
    val textStyle = when (controlSize) {
        ControlSize.Large, ControlSize.ExtraLarge -> typography.body
        ControlSize.Regular -> typography.caption1
        else -> typography.caption2
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(sidebarMetrics.itemHeightFor(controlSize))
            .clip(itemShape)
            .background(backgroundColor, itemShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Tab,
                onClick = onClick,
            )
            .padding(horizontal = hPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            Icon(
                icon = icon,
                tint = contentColor,
                contentDescription = if (isCollapsed) (iconContentDescription ?: label) else null,
                modifier = Modifier.size(iconDp).then(iconModifier),
            )
            Spacer(modifier = Modifier.width(iconLabelGap))
        }

        Text(
            text = label,
            style = textStyle,
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Clip,
            modifier = Modifier.graphicsLayer { alpha = labelAlpha },
        )
    }
}

// =============================================================================
// CollapseToggle — internal collapse button
// =============================================================================

@Composable
private fun CollapseToggle(
    isCollapsed: Boolean,
    onClick: () -> Unit,
    controlSize: ControlSize,
    sidebarMetrics: SidebarStyle.Metrics,
) {
    val iconRotation by animateFloatAsState(
        targetValue = if (isCollapsed) 180f else 0f,
        animationSpec = sidebarSpring(),
    )

    SidebarItemRow(
        label = "Collapse",
        onClick = onClick,
        active = false,
        icon = SystemIcon(LucideChevronsLeft),
        isCollapsed = isCollapsed,
        controlSize = controlSize,
        sidebarMetrics = sidebarMetrics,
        iconModifier = Modifier.graphicsLayer { rotationZ = iconRotation },
        iconContentDescription = if (isCollapsed) "Expand sidebar" else "Collapse sidebar",
    )
}

// =============================================================================
// SidebarHideButton — icon-only toggle with flattened oval hover
// =============================================================================

private val SidebarHideButtonShape = RoundedCornerShape(32.dp)

/**
 * A minimal sidebar hide button showing only the panel-left icon.
 * On hover, a flattened oval (wider than tall) background appears.
 */
@Composable
private fun SidebarHideButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDark = MacosTheme.colorScheme.isDark
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val bgColor by animateColorAsState(
        targetValue = when {
            isPressed -> if (isDark) Color.White.copy(alpha = 0.15f) else Color.Black.copy(alpha = 0.10f)
            isHovered -> if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.06f)
            else -> Color.Transparent
        },
        animationSpec = macosTween(MacosDuration.Fast),
        label = "sidebar_hide_bg",
    )

    val contentColor = if (isDark) Color.White.copy(alpha = 0.85f) else Color(0xFF1A1A1A)

    Box(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 10.dp)
            .clip(SidebarHideButtonShape)
            .background(bgColor, SidebarHideButtonShape)
            .hoverable(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = onClick,
            )
            .padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon = Icons.PanelLeft, modifier = Modifier.size(22.dp), tint = contentColor)
    }
}

@Preview
@Composable
private fun SidebarPreview() {
    MacosTheme {
        Sidebar(
            items = listOf(
                SidebarItem(label = "Home", onClick = {}),
                SidebarItem(label = "Projects", onClick = {}),
            ),
            pinnedItems = listOf(SidebarItem(label = "Settings", onClick = {})),
            activeItem = "Home",
        )
    }
}
