package io.github.kdroidfilter.darwinui.components

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideChevronsLeft
import io.github.kdroidfilter.darwinui.icons.LucideLogOut
import androidx.compose.ui.unit.Dp
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.Zinc500
import io.github.kdroidfilter.darwinui.theme.Zinc600
import io.github.kdroidfilter.darwinui.theme.Zinc700
import io.github.kdroidfilter.darwinui.theme.Zinc900
import io.github.kdroidfilter.darwinui.theme.darwinTween
import kotlin.math.roundToInt

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
    val collapsedIconDp: Dp,
    val itemHeight: Dp,
    val hPadding: Dp,
    val collapsedHPadding: Dp,
    val iconGap: Dp,
    val itemSpacing: Dp,
    val groupHeaderMaxHeight: Dp,
) {
    Small(
        iconDp = 16.dp,
        collapsedIconDp = 18.dp,
        itemHeight = 26.dp,
        hPadding = 8.dp,
        collapsedHPadding = 8.dp,
        iconGap = 6.dp,
        itemSpacing = 1.dp,
        groupHeaderMaxHeight = 24.dp,
    ),
    Medium(
        iconDp = 18.dp,
        collapsedIconDp = 20.dp,
        itemHeight = 30.dp,
        hPadding = 10.dp,
        collapsedHPadding = 10.dp,
        iconGap = 8.dp,
        itemSpacing = 2.dp,
        groupHeaderMaxHeight = 28.dp,
    ),
    Large(
        iconDp = 26.dp,
        collapsedIconDp = 26.dp,
        itemHeight = 42.dp,
        hPadding = 12.dp,
        collapsedHPadding = 12.dp,
        iconGap = 10.dp,
        itemSpacing = 3.dp,
        groupHeaderMaxHeight = 32.dp,
    ),
}

// =============================================================================
// Data
// =============================================================================

/**
 * Data for a single sidebar navigation item.
 *
 * @param label Display text for the item.
 * @param onClick Callback invoked when this item is clicked.
 * @param icon Optional Lucide-style [ImageVector] rendered before the label.
 * @param group Optional group header text. When any item has a non-null group,
 *   items are rendered in groups with a header label above each group.
 * @param id Unique identifier used for active-item matching. Defaults to [label].
 */
data class SidebarItem(
    val label: String,
    val onClick: () -> Unit,
    val icon: ImageVector? = null,
    val group: String? = null,
    val id: String = label,
)

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
    collapsed: Boolean = false,
    onCollapsedChange: ((Boolean) -> Unit)? = null,
    collapsible: Boolean = onCollapsedChange != null,
    showBorder: Boolean = false,
    header: (@Composable () -> Unit)? = null,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography
    val isDark = colors.isDark

    val topItems = items.filter { it.label != "Settings" }
    val settingsItem = items.find { it.label == "Settings" }
    val hasGroups = items.any { it.group != null }

    // Animated width: collapsed=56dp, expanded=272dp (matches macOS sidebar)
    val animatedWidth by animateDpAsState(
        targetValue = if (collapsed) 56.dp else 272.dp,
        animationSpec = sidebarSpring(),
    )

    // Animated padding: collapsed=6dp, expanded=8dp (macOS-like tight padding)
    val animatedPadding by animateDpAsState(
        targetValue = if (collapsed) 6.dp else 8.dp,
        animationSpec = sidebarSpring(),
    )

    val hasBottomSection = collapsible || settingsItem != null || onLogout != null

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

                Box(
                    modifier = Modifier
                        .clipToBounds()
                        .graphicsLayer { alpha = headerFraction }
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            val h = (placeable.height * headerFraction).roundToInt()
                            layout(placeable.width, h) {
                                placeable.placeRelative(0, 0)
                            }
                        },
                ) {
                    header()
                }
            }

            // ---- Scrollable items ----
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 6.dp),
                verticalArrangement = Arrangement.spacedBy(iconSize.itemSpacing),
            ) {
                if (hasGroups) {
                    val groups = topItems.groupBy { it.group }
                    for ((group, groupItems) in groups) {
                        if (group != null) {
                            GroupHeader(text = group, isCollapsed = collapsed, sidebarIconSize = iconSize)
                        }
                        groupItems.forEach { item ->
                            SidebarItemWithVisibility(
                                item = item,
                                activeItem = activeItem,
                                collapsed = collapsed,
                                sidebarIconSize = iconSize,
                            )
                        }
                    }
                } else {
                    topItems.forEach { item ->
                        SidebarItemWithVisibility(
                            item = item,
                            activeItem = activeItem,
                            collapsed = collapsed,
                            sidebarIconSize = iconSize,
                        )
                    }
                }
            }

            // ---- Bottom section ----
            if (hasBottomSection) {
                Column(
                    modifier = Modifier.padding(top = 8.dp, start = 6.dp, end = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(iconSize.itemSpacing),
                ) {
                    if (collapsible) {
                        CollapseToggle(
                            isCollapsed = collapsed,
                            onClick = { onCollapsedChange?.invoke(!collapsed) },
                            sidebarIconSize = iconSize,
                        )
                    }

                    if (settingsItem != null) {
                        SidebarItemWithVisibility(
                            item = settingsItem,
                            activeItem = activeItem,
                            collapsed = collapsed,
                            sidebarIconSize = iconSize,
                        )
                    }

                    if (onLogout != null) {
                        SidebarItemRow(
                            label = "Logout",
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
    val colors = DarwinTheme.colors

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
            style = typography.labelSmall,
            color = colors.textTertiary,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 10.dp, bottom = 2.dp),
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
) {
    val isDark = DarwinTheme.colors.isDark
    val typography = DarwinTheme.typography

    val interactionSource = remember { MutableInteractionSource() }

    val backgroundColor = if (active) DarwinTheme.colors.accent else Color.Transparent

    val textColor = if (active) Color.White else if (isDark) Color(0xFFD4D4D8) else Zinc600
    val iconColor = if (active) Color.White else if (isDark) Color(0xFFD4D4D8) else Zinc500

    val itemShape = DarwinTheme.shapes.small

    // When collapsed, always use Small dimensions so icons fit the fixed 56dp width
    val collapsedSize = SidebarIconSize.Small
    val iconSize = if (isCollapsed) collapsedSize.collapsedIconDp else sidebarIconSize.iconDp
    val hPadding = if (isCollapsed) collapsedSize.collapsedHPadding else sidebarIconSize.hPadding
    val iconLabelGap = if (isCollapsed) 0.dp else sidebarIconSize.iconGap

    val labelAlpha by animateFloatAsState(
        targetValue = if (isCollapsed) 0f else 1f,
        animationSpec = sidebarSpring(),
    )

    val textStyle = when (sidebarIconSize) {
        SidebarIconSize.Small -> typography.bodySmall
        SidebarIconSize.Medium -> typography.bodyMedium
        SidebarIconSize.Large -> typography.bodyLarge
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
                tint = iconColor,
                modifier = Modifier.size(iconSize),
            )
            Spacer(modifier = Modifier.width(iconLabelGap))
        }

        Text(
            text = label,
            style = textStyle,
            color = textColor,
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
    val isDark = DarwinTheme.colors.isDark
    val typography = DarwinTheme.typography

    val interactionSource = remember { MutableInteractionSource() }

    val textColor = if (isDark) Color(0xFFD4D4D8) else Zinc500

    val itemShape = DarwinTheme.shapes.small

    val iconRotation by animateFloatAsState(
        targetValue = if (isCollapsed) 180f else 0f,
        animationSpec = darwinTween(DarwinDuration.Slow),
    )

    val collapsedSize = SidebarIconSize.Small
    val iconSize = if (isCollapsed) collapsedSize.collapsedIconDp else sidebarIconSize.iconDp
    val hPadding = if (isCollapsed) collapsedSize.collapsedHPadding else sidebarIconSize.hPadding
    val iconLabelGap = if (isCollapsed) 0.dp else sidebarIconSize.iconGap

    val labelAlpha by animateFloatAsState(
        targetValue = if (isCollapsed) 0f else 1f,
        animationSpec = sidebarSpring(),
    )

    val textStyle = when (sidebarIconSize) {
        SidebarIconSize.Small -> typography.bodySmall
        SidebarIconSize.Medium -> typography.bodyMedium
        SidebarIconSize.Large -> typography.bodyLarge
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(sidebarIconSize.itemHeight)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = hPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = LucideChevronsLeft,
            tint = textColor,
            modifier = Modifier.size(iconSize).graphicsLayer { rotationZ = iconRotation },
        )

        Spacer(modifier = Modifier.width(iconLabelGap))

        Text(
            text = "Collapse",
            style = textStyle,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Clip,
            modifier = Modifier.graphicsLayer { alpha = labelAlpha },
        )
    }
}

@Preview
@Composable
private fun SidebarPreview() {
    DarwinTheme {
        Sidebar(
            items = listOf(
                SidebarItem(label = "Home", onClick = {}),
                SidebarItem(label = "Settings", onClick = {}),
            ),
            activeItem = "Home",
        )
    }
}
