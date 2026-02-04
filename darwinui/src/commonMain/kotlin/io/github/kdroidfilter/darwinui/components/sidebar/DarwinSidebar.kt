package io.github.kdroidfilter.darwinui.components.sidebar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import io.github.kdroidfilter.darwinui.theme.darwinTween

// =============================================================================
// Data
// =============================================================================

/**
 * Data for a single sidebar navigation item.
 *
 * @param label Display text and unique identifier for the item.
 * @param onClick Callback invoked when this item is clicked.
 * @param icon Optional Lucide-style [ImageVector] rendered before the label.
 */
data class DarwinSidebarItem(
    val label: String,
    val onClick: () -> Unit,
    val icon: ImageVector? = null,
)

// =============================================================================
// DarwinSidebar — main component
// =============================================================================

/**
 * macOS-style navigation sidebar matching the React darwin-ui `Sidebar` component.
 *
 * Renders a vertical panel with navigation items, an optional collapse toggle,
 * and a logout button at the bottom. Automatically separates "Settings" from
 * other items, placing it in the bottom section (matching React behaviour).
 *
 * Desktop width animates between 200dp (expanded) and 56dp (collapsed) using
 * a spring animation. Labels are revealed naturally by the growing width
 * (Apple-style push from start to end).
 *
 * @param items List of navigation items. The "Settings" item is automatically
 *   moved to the bottom section.
 * @param activeItem The label of the currently active item.
 * @param onLogout Callback invoked when the logout button is clicked.
 * @param modifier Modifier applied to the sidebar root.
 * @param collapsed Whether the sidebar is in collapsed (icon-only) mode.
 * @param onCollapsedChange Callback invoked when the collapse toggle is clicked.
 *   When non-null, the collapse toggle button is shown by default.
 * @param collapsible Whether to show the collapse toggle button. Defaults to
 *   `true` when [onCollapsedChange] is provided.
 * @param glass When true, applies frosted-glass background and right border.
 */
@Composable
fun DarwinSidebar(
    items: List<DarwinSidebarItem>,
    activeItem: String,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    collapsed: Boolean = false,
    onCollapsedChange: ((Boolean) -> Unit)? = null,
    collapsible: Boolean = onCollapsedChange != null,
    glass: Boolean = false,
) {
    val colors = DarwinTheme.colors
    val isDark = colors.isDark

    // Separate Settings from top items (like React)
    val topItems = items.filter { it.label != "Settings" }
    val settingsItem = items.find { it.label == "Settings" }

    // Animated width: collapsed=56dp, expanded=200dp
    val animatedWidth by animateDpAsState(
        targetValue = if (collapsed) 56.dp else 200.dp,
        animationSpec = darwinSpring(DarwinSpringPreset.Smooth),
    )

    // Animated padding: collapsed=8dp(p-2), expanded=12dp(p-3)
    val animatedPadding by animateDpAsState(
        targetValue = if (collapsed) 8.dp else 12.dp,
        animationSpec = darwinSpring(DarwinSpringPreset.Smooth),
    )

    // Glass colours
    val glassBackground = if (isDark) Zinc900.copy(alpha = 0.80f) else Color.White.copy(alpha = 0.80f)
    val glassBorderColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.White.copy(alpha = 0.20f)

    // Bottom section border
    val bottomBorderColor = if (isDark) Color.White.copy(alpha = 0.10f) else Zinc200

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
                .then(
                    if (glass) Modifier.background(glassBackground)
                    else Modifier
                )
                .padding(animatedPadding),
        ) {
            // ---- Top items (flex-1, space-y-1) ----
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                topItems.forEach { item ->
                    if (item.icon != null) {
                        // Items with icons: always present, label revealed by width
                        SidebarItemRow(
                            label = item.label,
                            onClick = item.onClick,
                            active = activeItem == item.label,
                            icon = item.icon,
                            isCollapsed = collapsed,
                        )
                    } else {
                        // Items without icons: smooth exit, instant enter
                        AnimatedVisibility(
                            visible = !collapsed,
                            enter = EnterTransition.None,
                            exit = shrinkVertically(darwinSpring(DarwinSpringPreset.Smooth)) + fadeOut(darwinTween(DarwinDuration.Fast)),
                        ) {
                            SidebarItemRow(
                                label = item.label,
                                onClick = item.onClick,
                                active = activeItem == item.label,
                                icon = null,
                                isCollapsed = false,
                            )
                        }
                    }
                }
            }

            // ---- Bottom section: border-t + pt-3, space-y-1 ----
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
                    if (settingsItem.icon != null) {
                        SidebarItemRow(
                            label = settingsItem.label,
                            onClick = settingsItem.onClick,
                            active = activeItem == settingsItem.label,
                            icon = settingsItem.icon,
                            isCollapsed = collapsed,
                        )
                    } else {
                        AnimatedVisibility(
                            visible = !collapsed,
                            enter = EnterTransition.None,
                            exit = shrinkVertically(darwinSpring(DarwinSpringPreset.Smooth)) + fadeOut(darwinTween(DarwinDuration.Fast)),
                        ) {
                            SidebarItemRow(
                                label = settingsItem.label,
                                onClick = settingsItem.onClick,
                                active = activeItem == settingsItem.label,
                                icon = null,
                                isCollapsed = false,
                            )
                        }
                    }
                }

                // Logout (always has icon)
                SidebarItemRow(
                    label = "Logout",
                    onClick = onLogout,
                    active = false,
                    icon = LucideLogOut,
                    isCollapsed = collapsed,
                )
            }
        }

        // Right border (glass mode only)
        if (glass) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(glassBorderColor),
            )
        }
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

    // Animated icon size: 16dp expanded → 20dp collapsed
    val iconSize by animateDpAsState(
        targetValue = if (isCollapsed) 20.dp else 16.dp,
        animationSpec = darwinSpring(DarwinSpringPreset.Smooth),
    )

    // Animated horizontal padding: 12dp expanded → 10dp collapsed
    // At 10dp collapsed: item internal width = 40dp - 20dp = 20dp = icon size → centered
    val hPadding by animateDpAsState(
        targetValue = if (isCollapsed) 10.dp else 12.dp,
        animationSpec = darwinSpring(DarwinSpringPreset.Smooth),
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
            // gap-3 spacer between icon and label
            Spacer(modifier = Modifier.width(12.dp))
        }

        DarwinText(
            text = label,
            style = typography.bodyMedium.merge(
                TextStyle(fontWeight = FontWeight.Medium)
            ),
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Clip,
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

    // Animated icon size and padding (same as SidebarItemRow)
    val iconSize by animateDpAsState(
        targetValue = if (isCollapsed) 20.dp else 16.dp,
        animationSpec = darwinSpring(DarwinSpringPreset.Smooth),
    )
    val hPadding by animateDpAsState(
        targetValue = if (isCollapsed) 10.dp else 12.dp,
        animationSpec = darwinSpring(DarwinSpringPreset.Smooth),
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

        Spacer(modifier = Modifier.width(12.dp))

        DarwinText(
            text = "Collapse",
            style = typography.bodyMedium.merge(
                TextStyle(fontWeight = FontWeight.Medium)
            ),
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Clip,
        )
    }
}
