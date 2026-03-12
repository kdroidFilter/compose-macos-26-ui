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
import androidx.compose.runtime.remember
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
import io.github.kdroidfilter.darwinui.icons.LucideChevronsLeft
import io.github.kdroidfilter.darwinui.icons.LucideLogOut
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import androidx.compose.ui.layout.layout
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
// SidebarIconSize — mirrors macOS "Taille des icônes de la barre latérale"
// =============================================================================

/**
 * Icon size preset for the sidebar, matching macOS System Settings option
 * "Taille des icônes de la barre latérale" (Small / Medium / Large).
 */
enum class SidebarIconSize(
    val iconDp: Dp,
    val itemHeight: Dp,
    val hPadding: Dp,
    val iconGap: Dp,
    val itemSpacing: Dp,
    val groupHeaderMaxHeight: Dp,
) {
    Small(
        iconDp = 14.dp,
        itemHeight = 26.dp,
        hPadding = 6.dp,
        iconGap = 5.dp,
        itemSpacing = 0.dp,
        groupHeaderMaxHeight = 30.dp,
    ),
    Medium(
        iconDp = 16.dp,
        itemHeight = 30.dp,
        hPadding = 8.dp,
        iconGap = 6.dp,
        itemSpacing = 1.dp,
        groupHeaderMaxHeight = 32.dp,
    ),
    Large(
        iconDp = 22.dp,
        itemHeight = 36.dp,
        hPadding = 10.dp,
        iconGap = 8.dp,
        itemSpacing = 2.dp,
        groupHeaderMaxHeight = 30.dp,
    ),
}

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
class SidebarItem(
    val label: String,
    val onClick: () -> Unit,
    val icon: ImageVector? = null,
    val group: String? = null,
    val id: String = label,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SidebarItem) return false
        return id == other.id && label == other.label && icon == other.icon && group == other.group
    }

    override fun hashCode(): Int {
        var result = label.hashCode()
        result = 31 * result + (icon?.hashCode() ?: 0)
        result = 31 * result + (group?.hashCode() ?: 0)
        result = 31 * result + id.hashCode()
        return result
    }
}

// =============================================================================
// Sidebar — main component
// =============================================================================

@Composable
fun Sidebar(
    items: List<SidebarItem>,
    activeItem: String,
    modifier: Modifier = Modifier,
    iconSize: SidebarIconSize = SidebarIconSize.Medium,
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
    val colors = DarwinTheme.colorScheme
    val isDark = colors.isDark

    val hasGroups = remember(items) { items.any { it.group != null } }
    val groupedItems = remember(items) { if (hasGroups) items.groupBy { it.group } else null }

    // Animated width: collapsed=56dp, expanded=240dp (matches macOS Finder sidebar)
    val animatedWidth by animateDpAsState(
        targetValue = if (collapsed) 56.dp else 240.dp,
        animationSpec = sidebarSpring(),
    )

    // Animated padding: collapsed=4dp, expanded=6dp (macOS-like tight padding)
    val animatedPadding by animateDpAsState(
        targetValue = if (collapsed) 4.dp else 6.dp,
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
                .clip(sidebarContentShape)
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
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    verticalArrangement = Arrangement.spacedBy(iconSize.itemSpacing),
                ) {
                    if (hasGroups && groupedItems != null) {
                        for ((group, groupItems) in groupedItems) {
                            if (group != null) {
                                GroupHeader(text = group, isCollapsed = collapsed, sidebarIconSize = iconSize)
                            }
                            groupItems.forEach { item ->
                                key(item.id) {
                                    SidebarItemWithVisibility(
                                        item = item,
                                        activeItem = activeItem,
                                        collapsed = collapsed,
                                        sidebarIconSize = iconSize,
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
                                    sidebarIconSize = iconSize,
                                )
                            }
                        }
                    }
                }

                VerticalScrollbar(
                    state = rememberScrollbarState(itemsScrollState),
                    modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                    trackClickBehavior = scrollbarTrackClickBehavior,
                )
            }

            // ---- Bottom section ----
            if (hasBottomSection) {
                Column(
                    modifier = Modifier.padding(top = 6.dp, start = 4.dp, end = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(iconSize.itemSpacing),
                ) {
                    if (collapsible) {
                        CollapseToggle(
                            isCollapsed = collapsed,
                            onClick = { onCollapsedChange?.invoke(!collapsed) },
                            sidebarIconSize = iconSize,
                        )
                    }

                    pinnedItems.forEach { item ->
                        key(item.id) {
                            SidebarItemWithVisibility(
                                item = item,
                                activeItem = activeItem,
                                collapsed = collapsed,
                                sidebarIconSize = iconSize,
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
                            sidebarIconSize = iconSize,
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
    }
}

/**
 * Renders a single sidebar item with appropriate animation based on
 * whether the item has an icon or not.
 *
 * Icon items: always present, label clips naturally via the sidebar width.
 * Text-only items: height + alpha animate to 0 when collapsed. The element
 * stays in the composition tree so [Arrangement.spacedBy] gaps don't snap.
 */
@Composable
private fun SidebarItemWithVisibility(
    item: SidebarItem,
    activeItem: String,
    collapsed: Boolean,
    sidebarIconSize: SidebarIconSize = SidebarIconSize.Medium,
) {
    if (item.icon != null) {
        SidebarItemRow(
            label = item.label,
            onClick = item.onClick,
            active = activeItem == item.id,
            icon = item.icon,
            isCollapsed = collapsed,
            sidebarIconSize = sidebarIconSize,
        )
    } else {
        val alpha by animateFloatAsState(
            targetValue = if (collapsed) 0f else 1f,
            animationSpec = sidebarSpring(),
        )
        val itemHeight by animateDpAsState(
            targetValue = if (collapsed) 0.dp else sidebarIconSize.itemHeight,
            animationSpec = sidebarSpring(),
        )

        // The outer Box handles visibility for text-only items.
        // isCollapsed=false is passed to SidebarItemRow so its internal
        // labelAlpha always targets 1f — the Box controls visibility, not the row.
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
                sidebarIconSize = sidebarIconSize,
            )
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
private fun GroupHeader(text: String, isCollapsed: Boolean, sidebarIconSize: SidebarIconSize = SidebarIconSize.Medium) {
    val typography = DarwinTheme.typography
    val colors = DarwinTheme.colorScheme

    val alpha by animateFloatAsState(
        targetValue = if (isCollapsed) 0f else 1f,
        animationSpec = sidebarSpring(),
    )
    val maxHeight by animateDpAsState(
        targetValue = if (isCollapsed) 0.dp else sidebarIconSize.groupHeaderMaxHeight,
        animationSpec = sidebarSpring(),
    )

    Box(
        modifier = Modifier
            .heightIn(max = maxHeight)
            .clipToBounds()
            .graphicsLayer { this.alpha = alpha },
    ) {
        Text(
            text = text,
            style = typography.caption2,
            color = colors.textTertiary,
            modifier = Modifier.padding(start = sidebarIconSize.hPadding, end = 8.dp, top = 12.dp, bottom = 2.dp),
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
    sidebarIconSize: SidebarIconSize = SidebarIconSize.Medium,
    iconModifier: Modifier = Modifier,
    iconContentDescription: String? = null,
) {
    val colors = DarwinTheme.colorScheme
    val typography = DarwinTheme.typography

    val interactionSource = remember { MutableInteractionSource() }

    // macOS Finder style: subtle translucent background + accent text/icon when active
    val backgroundColor by animateColorAsState(
        targetValue = if (active) colors.textPrimary.copy(alpha = 0.11f) else Color.Transparent,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
    )
    val contentColor by animateColorAsState(
        targetValue = if (active) colors.accent else colors.textPrimary,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
    )

    val itemShape = DarwinTheme.shapes.small

    // Collapse fraction drives all collapse animations: 0f = expanded, 1f = collapsed.
    // When sidebarIconSize changes (without collapse), the fraction stays at its current
    // value and the new Dp values take effect instantly — no unwanted size animation.
    val collapseFraction by animateFloatAsState(
        targetValue = if (isCollapsed) 1f else 0f,
        animationSpec = sidebarSpring(),
    )

    // When collapsed: center the icon within 56dp total sidebar width.
    // Total horizontal insets = animatedPadding(4dp) + scrollColumn padding(4dp) + hPadding.
    // To center: each side = (56dp - iconDp) / 2, so hPadding = (56dp - iconDp) / 2 - 8dp.
    val collapsedHPadding = ((56.dp - sidebarIconSize.iconDp) / 2 - 8.dp).coerceAtLeast(0.dp)
    val hPadding = lerp(sidebarIconSize.hPadding, collapsedHPadding, collapseFraction)
    val iconLabelGap = lerp(sidebarIconSize.iconGap, 0.dp, collapseFraction)
    val labelAlpha = 1f - collapseFraction

    val textStyle = when (sidebarIconSize) {
        SidebarIconSize.Small -> typography.caption1
        SidebarIconSize.Medium -> typography.subheadline
        SidebarIconSize.Large -> typography.callout
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(sidebarIconSize.itemHeight)
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
                modifier = Modifier.size(sidebarIconSize.iconDp).then(iconModifier),
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
    sidebarIconSize: SidebarIconSize = SidebarIconSize.Medium,
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
        sidebarIconSize = sidebarIconSize,
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
