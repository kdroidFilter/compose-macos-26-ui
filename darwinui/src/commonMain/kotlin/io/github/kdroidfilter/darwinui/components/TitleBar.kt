package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideChevronDown
import io.github.kdroidfilter.darwinui.icons.LucideChevronLeft
import io.github.kdroidfilter.darwinui.icons.LucideChevronRight
import io.github.kdroidfilter.darwinui.icons.LucidePanelLeft
import io.github.kdroidfilter.darwinui.theme.*

/**
 * macOS-style Title Bar.
 * Matches the style of Safari, Finder, and other macOS system apps.
 *
 * Window controls (close/minimize/maximize) are intentionally omitted — on Desktop they are
 * provided natively by the OS and should not be redrawn in Compose.
 *
 * @param modifier Modifier applied to the entire TitleBar.
 * @param navigationActions Left-side actions (e.g. [NavigationButtons]).
 * @param title The central area, typically containing a title or an [AddressBar].
 *   Use `Modifier.fillMaxWidth()` to fill the available space.
 * @param actions Right-side action buttons (e.g. [TitleBarButtonGroup]).
 * @param backgroundColor Background color of the title bar.
 * @param showBottomBorder Whether to show a subtle border at the bottom.
 * @param height Height of the title bar (default 52dp).
 */
@Composable
fun TitleBar(
    modifier: Modifier = Modifier,
    navigationActions: @Composable RowScope.() -> Unit = {},
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = DarwinTheme.colors.background,
    showBottomBorder: Boolean = true,
    height: Int = 52
) {
    val borderColor = if (DarwinTheme.colors.isDark) Color.Black.copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.1f)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp)
            .background(backgroundColor)
            .then(
                if (showBottomBorder) {
                    Modifier.drawBehind {
                        drawLine(
                            color = borderColor,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 0.5.dp.toPx()
                        )
                    }
                } else Modifier
            )
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left section: Nav Actions
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 8.dp).widthIn(min = 80.dp)
        ) {
            navigationActions()
        }

        // Center section: Title or Search
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            title()
        }

        // Right section: Actions
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
            modifier = Modifier.padding(start = 8.dp).widthIn(min = 80.dp),
        ) {
            actions()
        }
    }
}

// =============================================================================
// TitleBarButtonGroup — shared pill container for grouped toolbar buttons
// =============================================================================

private val TitleBarGroupShape = RoundedCornerShape(percent = 50)

/**
 * A pill-shaped container grouping toolbar buttons, matching the macOS NSToolbar
 * button-group style (e.g. Safari's back/forward or action clusters).
 *
 * Place [TitleBarGroupButton] and [TitleBarGroupDivider] inside.
 */
@Composable
fun TitleBarButtonGroup(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val isDark = DarwinTheme.colors.isDark
    val bgColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.055f)
    Row(
        modifier = modifier
            .height(28.dp)
            .clip(TitleBarGroupShape)
            .background(bgColor, TitleBarGroupShape),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

/**
 * A single button slot inside a [TitleBarButtonGroup].
 * Provides hover/press feedback without its own pill shape — the group provides it.
 */
@Composable
fun TitleBarGroupButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    val isDark = DarwinTheme.colors.isDark
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    val bgOverlay by animateColorAsState(
        targetValue = when {
            isPressed && enabled -> if (isDark) Color.White.copy(alpha = 0.12f) else Color.Black.copy(alpha = 0.09f)
            isHovered && enabled -> if (isDark) Color.White.copy(alpha = 0.07f) else Color.Black.copy(alpha = 0.05f)
            else -> Color.Transparent
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "tbar_grp_btn_bg",
    )
    val contentColor by animateColorAsState(
        targetValue = if (!enabled) {
            if (isDark) Color.White.copy(alpha = 0.28f) else Color.Black.copy(alpha = 0.22f)
        } else {
            if (isDark) Color.White.copy(alpha = 0.85f) else Color.Black.copy(alpha = 0.65f)
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "tbar_grp_btn_content",
    )

    CompositionLocalProvider(
        LocalDarwinContentColor provides contentColor,
        LocalDarwinTextStyle provides DarwinTheme.typography.labelMedium,
    ) {
        Box(
            modifier = modifier
                .fillMaxHeight()
                .background(bgOverlay)
                .hoverable(interactionSource = interactionSource)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick,
                )
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}

/**
 * A vertical separator between buttons inside a [TitleBarButtonGroup].
 */
@Composable
fun TitleBarGroupDivider(modifier: Modifier = Modifier) {
    val isDark = DarwinTheme.colors.isDark
    Spacer(
        modifier = modifier
            .width(0.5.dp)
            .height(14.dp)
            .background(if (isDark) Color.White.copy(alpha = 0.18f) else Color.Black.copy(alpha = 0.15f)),
    )
}

// =============================================================================
// NavigationButtons — back / forward combined pill
// =============================================================================

/**
 * A combined back/forward navigation button pair in a single pill.
 * Matches the style seen in Safari and Finder toolbars.
 *
 * @param onBack Called when the back button is clicked.
 * @param onForward Called when the forward button is clicked.
 * @param backEnabled Whether the back button is interactive.
 * @param forwardEnabled Whether the forward button is interactive.
 */
@Composable
fun NavigationButtons(
    onBack: () -> Unit,
    onForward: () -> Unit,
    modifier: Modifier = Modifier,
    backEnabled: Boolean = true,
    forwardEnabled: Boolean = false,
) {
    TitleBarButtonGroup(modifier = modifier) {
        TitleBarGroupButton(onClick = onBack, enabled = backEnabled) {
            Icon(LucideChevronLeft, modifier = Modifier.size(16.dp))
        }
        TitleBarGroupDivider()
        TitleBarGroupButton(onClick = onForward, enabled = forwardEnabled) {
            Icon(LucideChevronRight, modifier = Modifier.size(16.dp))
        }
    }
}

// =============================================================================
// SidebarButton — sidebar toggle with optional dropdown menu
// =============================================================================

/**
 * A macOS-style sidebar toggle button with an optional dropdown chevron.
 * Matches the pattern seen in Safari and Finder toolbars.
 *
 * The left side shows a sidebar panel icon and triggers [onClick].
 * When [menuContent] is provided, a chevron-down appears on the right side
 * and opens a [DropdownMenu] when clicked.
 *
 * @param onClick Called when the sidebar icon is clicked.
 * @param modifier Modifier applied to the pill container.
 * @param enabled Whether the button is interactive.
 * @param icon The icon composable shown on the left side.
 * @param menuContent Optional dropdown menu content. When non-null, a chevron
 *   button and divider are added to the right of the main icon.
 */
@Composable
fun SidebarButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable () -> Unit = { Icon(LucidePanelLeft, modifier = Modifier.size(16.dp)) },
    menuContent: (@Composable ColumnScope.() -> Unit)? = null,
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Box {
        TitleBarButtonGroup(modifier = modifier) {
            TitleBarGroupButton(onClick = onClick, enabled = enabled) {
                icon()
            }
            if (menuContent != null) {
                TitleBarGroupDivider()
                TitleBarGroupButton(
                    onClick = { menuExpanded = true },
                    enabled = enabled,
                ) {
                    Icon(LucideChevronDown, modifier = Modifier.size(10.dp))
                }
            }
        }

        if (menuContent != null) {
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                content = menuContent,
            )
        }
    }
}
