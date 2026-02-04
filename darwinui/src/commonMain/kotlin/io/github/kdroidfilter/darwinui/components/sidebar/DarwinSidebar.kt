package io.github.kdroidfilter.darwinui.components.sidebar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.icons.DarwinIcon
import io.github.kdroidfilter.darwinui.icons.LucideChevronsLeft
import io.github.kdroidfilter.darwinui.icons.LucideLogOut
import io.github.kdroidfilter.darwinui.theme.Blue500
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.Zinc100
import io.github.kdroidfilter.darwinui.theme.Zinc200
import io.github.kdroidfilter.darwinui.theme.Zinc400
import io.github.kdroidfilter.darwinui.theme.Zinc500
import io.github.kdroidfilter.darwinui.theme.Zinc600
import io.github.kdroidfilter.darwinui.theme.Zinc700
import io.github.kdroidfilter.darwinui.theme.Zinc900
import io.github.kdroidfilter.darwinui.theme.darwinTween

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
 * @param label Display text for the item.
 * @param onClick Callback invoked when this item is clicked.
 * @param icon Optional Lucide-style [ImageVector] rendered before the label.
 * @param group Optional group header text. When any item has a non-null group,
 *   items are rendered in groups with a header label above each group.
 * @param id Unique identifier used for active-item matching. Defaults to [label].
 */
data class DarwinSidebarItem(
    val label: String,
    val onClick: () -> Unit,
    val icon: ImageVector? = null,
    val group: String? = null,
    val id: String = label,
)

// =============================================================================
// DarwinSidebar — main component
// =============================================================================

@Composable
fun DarwinSidebar(
    items: List<DarwinSidebarItem>,
    activeItem: String,
    modifier: Modifier = Modifier,
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

    // Animated width: collapsed=56dp, expanded=200dp
    val animatedWidth by animateDpAsState(
        targetValue = if (collapsed) 56.dp else 200.dp,
        animationSpec = sidebarSpring(),
    )

    // Animated padding: collapsed=8dp(p-2), expanded=12dp(p-3)
    val animatedPadding by animateDpAsState(
        targetValue = if (collapsed) 8.dp else 12.dp,
        animationSpec = sidebarSpring(),
    )

    // Bottom section border
    val bottomBorderColor = if (isDark) Color.White.copy(alpha = 0.10f) else Zinc200

    val hasBottomSection = collapsible || settingsItem != null || onLogout != null

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
                .padding(animatedPadding),
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

            // ---- Scrollable items (flex-1, space-y-1) ----
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                if (hasGroups) {
                    // Grouped rendering: preserve insertion order
                    val groups = topItems.groupBy { it.group }
                    for ((group, groupItems) in groups) {
                        if (group != null) {
                            GroupHeader(text = group, isCollapsed = collapsed)
                        }
                        groupItems.forEach { item ->
                            SidebarItemWithVisibility(
                                item = item,
                                activeItem = activeItem,
                                collapsed = collapsed,
                            )
                        }
                    }
                } else {
                    topItems.forEach { item ->
                        SidebarItemWithVisibility(
                            item = item,
                            activeItem = activeItem,
                            collapsed = collapsed,
                        )
                    }
                }
            }

            // ---- Bottom section: border-t + pt-3, space-y-1 ----
            if (hasBottomSection) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(bottomBorderColor),
                )

                Column(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    // Collapse toggle
                    if (collapsible) {
                        CollapseToggle(
                            isCollapsed = collapsed,
                            onClick = { onCollapsedChange?.invoke(!collapsed) },
                        )
                    }

                    // Settings item (fade out if no icon)
                    if (settingsItem != null) {
                        SidebarItemWithVisibility(
                            item = settingsItem,
                            activeItem = activeItem,
                            collapsed = collapsed,
                        )
                    }

                    // Logout (always has icon)
                    if (onLogout != null) {
                        SidebarItemRow(
                            label = "Logout",
                            onClick = onLogout,
                            active = false,
                            icon = LucideLogOut,
                            isCollapsed = collapsed,
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
    item: DarwinSidebarItem,
    activeItem: String,
    collapsed: Boolean,
) {
    if (item.icon != null) {
        SidebarItemRow(
            label = item.label,
            onClick = item.onClick,
            active = activeItem == item.id,
            icon = item.icon,
            isCollapsed = collapsed,
        )
    } else {
        val alpha by animateFloatAsState(
            targetValue = if (collapsed) 0f else 1f,
            animationSpec = sidebarSpring(),
        )
        val itemHeight by animateDpAsState(
            targetValue = if (collapsed) 0.dp else 40.dp,
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
private fun GroupHeader(text: String, isCollapsed: Boolean) {
    val typography = DarwinTheme.typography
    val colors = DarwinTheme.colors

    val alpha by animateFloatAsState(
        targetValue = if (isCollapsed) 0f else 1f,
        animationSpec = sidebarSpring(),
    )
    // 40dp is a generous upper bound (content is ~28dp with padding).
    // When expanded, the content is smaller so 40dp doesn't constrain it.
    // When collapsing, it smoothly shrinks and clips the content to 0.
    val maxHeight by animateDpAsState(
        targetValue = if (isCollapsed) 0.dp else 40.dp,
        animationSpec = sidebarSpring(),
    )

    Box(
        modifier = Modifier
            .heightIn(max = maxHeight)
            .clipToBounds()
            .graphicsLayer { this.alpha = alpha },
    ) {
        DarwinText(
            text = text,
            style = typography.labelSmall,
            color = colors.textTertiary,
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 4.dp),
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
) {
    val isDark = DarwinTheme.colors.isDark
    val shapes = DarwinTheme.shapes
    val typography = DarwinTheme.typography

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val backgroundColor = when {
        active -> Blue500
        isHovered -> if (isDark) Color.White.copy(alpha = 0.10f) else Zinc100
        else -> Color.Transparent
    }

    val textColor = when {
        active -> Color.White
        isHovered -> if (isDark) Zinc100 else Zinc900
        else -> if (isDark) Zinc400 else Zinc600
    }

    val iconColor = when {
        active -> Color.White
        isHovered -> if (isDark) Zinc200 else Zinc700
        else -> if (isDark) Zinc400 else Zinc500
    }

    // All item animations use the same Smooth spring as the sidebar width
    // so everything moves in one unified, progressive motion.
    val iconSize by animateDpAsState(
        targetValue = if (isCollapsed) 20.dp else 16.dp,
        animationSpec = sidebarSpring(),
    )

    val hPadding by animateDpAsState(
        targetValue = if (isCollapsed) 10.dp else 12.dp,
        animationSpec = sidebarSpring(),
    )

    val iconLabelGap by animateDpAsState(
        targetValue = if (isCollapsed) 0.dp else 12.dp,
        animationSpec = sidebarSpring(),
    )

    val labelAlpha by animateFloatAsState(
        targetValue = if (isCollapsed) 0f else 1f,
        animationSpec = sidebarSpring(),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(shapes.extraLarge) // rounded-xl = 16dp
            .background(backgroundColor, shapes.extraLarge)
            .hoverable(interactionSource = interactionSource)
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
            DarwinIcon(
                imageVector = icon,
                size = iconSize,
                tint = iconColor,
            )
            Spacer(modifier = Modifier.width(iconLabelGap))
        }

        DarwinText(
            text = label,
            style = typography.bodyMedium.merge(
                TextStyle(fontWeight = FontWeight.Medium)
            ),
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
) {
    val isDark = DarwinTheme.colors.isDark
    val shapes = DarwinTheme.shapes
    val typography = DarwinTheme.typography

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val textColor = when {
        isHovered -> if (isDark) Zinc200 else Zinc700
        else -> if (isDark) Zinc400 else Zinc500
    }

    val backgroundColor = when {
        isHovered -> if (isDark) Color.White.copy(alpha = 0.10f) else Zinc100
        else -> Color.Transparent
    }

    // ChevronsLeft rotates 180° when collapsed
    val iconRotation by animateFloatAsState(
        targetValue = if (isCollapsed) 180f else 0f,
        animationSpec = darwinTween(DarwinDuration.Slow),
    )

    val iconSize by animateDpAsState(
        targetValue = if (isCollapsed) 20.dp else 16.dp,
        animationSpec = sidebarSpring(),
    )
    val hPadding by animateDpAsState(
        targetValue = if (isCollapsed) 10.dp else 12.dp,
        animationSpec = sidebarSpring(),
    )

    val iconLabelGap by animateDpAsState(
        targetValue = if (isCollapsed) 0.dp else 12.dp,
        animationSpec = sidebarSpring(),
    )

    val labelAlpha by animateFloatAsState(
        targetValue = if (isCollapsed) 0f else 1f,
        animationSpec = sidebarSpring(),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(shapes.extraLarge)
            .background(backgroundColor, shapes.extraLarge)
            .hoverable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = hPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DarwinIcon(
            imageVector = LucideChevronsLeft,
            size = iconSize,
            tint = textColor,
            modifier = Modifier.graphicsLayer { rotationZ = iconRotation },
        )

        Spacer(modifier = Modifier.width(iconLabelGap))

        DarwinText(
            text = "Collapse",
            style = typography.bodyMedium.merge(
                TextStyle(fontWeight = FontWeight.Medium)
            ),
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Clip,
            modifier = Modifier.graphicsLayer { alpha = labelAlpha },
        )
    }
}
