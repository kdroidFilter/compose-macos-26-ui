package io.github.kdroidfilter.darwinui.components

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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import io.github.kdroidfilter.darwinui.components.VerticalScrollbar
import io.github.kdroidfilter.darwinui.components.TrackClickBehavior
import io.github.kdroidfilter.darwinui.components.rememberScrollbarState
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideChevronRight
import io.github.kdroidfilter.darwinui.icons.LucideChevronsLeft
import io.github.kdroidfilter.darwinui.icons.LucideLogOut
import io.github.kdroidfilter.darwinui.theme.ControlSize
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.GlassMaterialSize
import io.github.kdroidfilter.darwinui.theme.LocalControlSize
import io.github.kdroidfilter.darwinui.theme.LocalSidebarResize
import io.github.kdroidfilter.darwinui.theme.SidebarStyle
import io.github.kdroidfilter.darwinui.theme.LocalSidebarWidth
import io.github.kdroidfilter.darwinui.theme.darwinGlassMaterial
import io.github.kdroidfilter.darwinui.theme.darwinSpring
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
import androidx.compose.ui.tooling.preview.Preview

// =============================================================================
// Animation helper — critically damped spring (no overshoot) with Smooth stiffness
// =============================================================================

/** Sidebar spring: same stiffness as [DarwinSpringPreset.Smooth] but critically
 *  damped (ratio=1.0) so animations settle progressively without any bounce. */
private fun <T> sidebarSpring() = spring<T>(
    dampingRatio = 1.0f,
    stiffness = DarwinSpringPreset.Smooth.stiffness,
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
 * @param showBorder Whether to show a right border on the sidebar.
 * @param scrollbarTrackClickBehavior Scrollbar track click behavior.
 * @param header Optional composable header above the items.
 * @param pinnedItems Items pinned to the bottom of the sidebar.
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
) {
    val controlSize = LocalControlSize.current
    val sidebarMetrics = DarwinTheme.componentStyling.sidebar.metrics
    val colors = DarwinTheme.colorScheme
    val isDark = colors.isDark

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

    // Animated padding: collapsed=4dp, expanded=4dp
    val animatedPadding by animateDpAsState(
        targetValue = if (collapsed) 4.dp else 4.dp,
        animationSpec = sidebarSpring(),
    )

    val animatedTrackStartPadding by animateDpAsState(
        targetValue = if (collapsed) 4.dp else 9.dp,
        animationSpec = sidebarSpring(),
    )
    val animatedTrackEndPadding by animateDpAsState(
        targetValue = if (collapsed) 4.dp else 9.dp,
        animationSpec = sidebarSpring(),
    )


    val hasBottomSection = collapsible || pinnedItems.isNotEmpty() || onLogout != null

    val sidebarContentShape = DarwinTheme.shapes.extraLarge
    val sidebarBorderColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.08f)

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
                .drawBehind {
                    // Soft outer shadow: concentric stroke rings, no fill
                    val steps = 5
                    val maxExpand = 6.dp.toPx()
                    val baseAlpha = if (isDark) 0.02f else 0.03f
                    val cornerRad = 16.dp.toPx()
                    val strokeWidth = maxExpand / steps
                    for (i in 1..steps) {
                        val expand = maxExpand * (i.toFloat() / steps)
                        val alpha = baseAlpha * ((steps - i + 1).toFloat() / steps)
                        drawRoundRect(
                            color = Color.Black.copy(alpha = alpha),
                            topLeft = Offset(-expand, -expand),
                            size = Size(size.width + expand * 2, size.height + expand * 2),
                            cornerRadius = CornerRadius(cornerRad + expand),
                            style = Stroke(width = strokeWidth),
                        )
                    }
                }
                .darwinGlassMaterial(
                    shape = sidebarContentShape,
                    materialSize = GlassMaterialSize.Large,
                )
                .border(1.dp, sidebarBorderColor, sidebarContentShape),
        ) {
            // ---- Pinned header (height fraction + alpha, stays in tree) ----
            if (header != null) {
                val headerFraction by animateFloatAsState(
                    targetValue = if (collapsed) 0f else 1f,
                    animationSpec = sidebarSpring(),
                )

                // layout{} reads headerFraction during the layout phase, which is
                // acceptable here: it is an animated State so Compose only re-lays
                // this subtree (not the whole sidebar) on each animation frame.
                // graphicsLayer confines the alpha read to the draw phase.
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

            // ---- Scrollable items ----
            val itemsScrollState = rememberScrollState()
            Box(modifier = Modifier.weight(1f)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(itemsScrollState)
                        .padding(start = animatedTrackStartPadding, end = animatedTrackEndPadding, top = 2.dp, bottom = 2.dp),
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
                                )
                            }
                        }
                    }
                }

                if (!collapsed) {
                    VerticalScrollbar(
                        state = rememberScrollbarState(itemsScrollState),
                        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                        trackClickBehavior = scrollbarTrackClickBehavior,
                    )
                }
            }

            // ---- Bottom section ----
            if (hasBottomSection) {
                Column(
                    modifier = Modifier.padding(top = 6.dp, start = 4.dp, end = 4.dp),
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
                            )
                        }
                    }

                    if (onLogout != null) {
                        SidebarItemRow(
                            label = logoutLabel,
                            onClick = onLogout,
                            active = false,
                            icon = LucideLogOut,
                            isCollapsed = collapsed,
                            controlSize = controlSize,
                            sidebarMetrics = sidebarMetrics,
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
    indentLevel: Int = 0,
) {
    if (item.children.isNotEmpty()) {
        DisclosureItem(
            item = item,
            activeItem = activeItem,
            collapsed = collapsed,
            controlSize = controlSize,
            sidebarMetrics = sidebarMetrics,
            indentLevel = indentLevel,
        )
        return
    }

    val indentPadding = sidebarMetrics.indentFor(indentLevel)

    if (item.icon != null) {
        SidebarItemRow(
            label = item.label,
            onClick = item.onClick,
            active = activeItem == item.id,
            icon = item.icon,
            isCollapsed = collapsed,
            controlSize = controlSize,
            sidebarMetrics = sidebarMetrics,
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
    indentLevel: Int = 0,
) {
    var expanded by remember { mutableStateOf(true) }
    val chevronRotation by animateFloatAsState(
        targetValue = if (expanded) 90f else 0f,
        animationSpec = sidebarSpring(),
    )

    val indentPadding = sidebarMetrics.indentFor(indentLevel)

    // Parent item row with chevron
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = indentPadding)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                if (item.onClick != {}) item.onClick() else expanded = !expanded
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Disclosure chevron (hidden when sidebar is collapsed)
        val chevronAlpha by animateFloatAsState(
            targetValue = if (collapsed) 0f else 1f,
            animationSpec = sidebarSpring(),
        )
        Box(
            modifier = Modifier
                .size(sidebarMetrics.itemHeightFor(controlSize))
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
            // Sketch: disclosure chevron 11sp, #00000040
            Icon(
                LucideChevronRight,
                modifier = Modifier.size(12.dp),
                tint = Color.Black.copy(alpha = 0.25f),
            )
        }

        // Item content
        Box(modifier = Modifier.weight(1f)) {
            SidebarItemRow(
                label = item.label,
                onClick = { expanded = !expanded },
                active = activeItem == item.id,
                icon = item.icon,
                isCollapsed = collapsed,
                controlSize = controlSize,
                sidebarMetrics = sidebarMetrics,
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
    val typography = DarwinTheme.typography

    val alpha by animateFloatAsState(
        targetValue = if (isCollapsed) 0f else 1f,
        animationSpec = sidebarSpring(),
    )
    val targetHeight = sidebarMetrics.groupHeaderHeightFor(controlSize)
    val maxHeight by animateDpAsState(
        targetValue = if (isCollapsed) 0.dp else targetHeight,
        animationSpec = sidebarSpring(),
    )

    // Sketch: header text color = #00000080
    val headerColor = Color.Black.copy(alpha = 0.50f)
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
            modifier = Modifier.padding(start = sidebarMetrics.hPaddingFor(controlSize) + 8.dp, end = 8.dp, bottom = 4.dp),
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
    icon: ImageVector?,
    isCollapsed: Boolean,
    controlSize: ControlSize,
    sidebarMetrics: SidebarStyle.Metrics,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    iconContentDescription: String? = null,
) {
    val colors = DarwinTheme.colorScheme

    val interactionSource = remember { MutableInteractionSource() }

    val isDark = colors.isDark
    // Sketch: selected background — light: #0000001c, dark: use white overlay
    val backgroundColor by animateColorAsState(
        targetValue = if (active) {
            if (isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.11f)
        } else {
            Color.Transparent
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
    )
    val contentColor by animateColorAsState(
        targetValue = if (active) colors.accent else colors.textPrimary,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
    )

    val itemShape = DarwinTheme.shapes.small

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
    val typography = DarwinTheme.typography
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
                imageVector = icon,
                tint = contentColor,
                // Describe the icon when collapsed (icon is the only visible label);
                // null when expanded (the text label is present and the icon is decorative).
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
        // Tween intentional here: rotation arc reads better with an easing curve
        // than a spring. Label fade stays on sidebarSpring() inside SidebarItemRow.
        animationSpec = sidebarSpring(),
    )

    SidebarItemRow(
        label = "Collapse",
        onClick = onClick,
        active = false,
        icon = LucideChevronsLeft,
        isCollapsed = isCollapsed,
        controlSize = controlSize,
        sidebarMetrics = sidebarMetrics,
        iconModifier = Modifier.graphicsLayer { rotationZ = iconRotation },
        iconContentDescription = if (isCollapsed) "Expand sidebar" else "Collapse sidebar",
    )
}

@Preview
@Composable
private fun SidebarPreview() {
    DarwinTheme {
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
