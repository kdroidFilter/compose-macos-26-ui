package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icons
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.*

// =============================================================================
// TitleBarButtonColors — customizable colors for titlebar buttons
// =============================================================================

@Immutable
class TitleBarButtonColors(
    val backgroundColor: Color,
    val contentColor: Color,
    val disabledContentColor: Color,
    val pressedOverlay: Color,
    val hoveredOverlay: Color,
) {
    fun copy(
        backgroundColor: Color = this.backgroundColor,
        contentColor: Color = this.contentColor,
        disabledContentColor: Color = this.disabledContentColor,
        pressedOverlay: Color = this.pressedOverlay,
        hoveredOverlay: Color = this.hoveredOverlay,
    ) = TitleBarButtonColors(backgroundColor, contentColor, disabledContentColor, pressedOverlay, hoveredOverlay)
}

object TitleBarButtonDefaults {

    @Composable
    fun colors(
        backgroundColor: Color = with(MacosTheme.colorScheme) {
            if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.06f)
        },
        contentColor: Color = with(MacosTheme.colorScheme) {
            if (isDark) Color.White.copy(alpha = 0.85f) else Color(0xFF1A1A1A)
        },
        disabledContentColor: Color = with(MacosTheme.colorScheme) {
            if (isDark) Color.White.copy(alpha = 0.28f) else Color(0xFFBFBFBF)
        },
        pressedOverlay: Color = with(MacosTheme.colorScheme) {
            if (isDark) Color.White.copy(alpha = 0.18f) else Color.Black.copy(alpha = 0.12f)
        },
        hoveredOverlay: Color = with(MacosTheme.colorScheme) {
            if (isDark) Color.White.copy(alpha = 0.12f) else Color.Black.copy(alpha = 0.08f)
        },
    ) = TitleBarButtonColors(backgroundColor, contentColor, disabledContentColor, pressedOverlay, hoveredOverlay)
}

/**
 * macOS-style Title Bar.
 * Matches the style of Safari, Finder, and other macOS system apps.
 *
 * Window controls (close/minimize/maximize) are intentionally omitted — on Desktop they are
 * provided natively by the OS and should not be redrawn in Compose.
 *
 * @param modifier Modifier applied to the entire TitleBar.
 * @param style Visual style variant (Unified, UnifiedCompact, Expanded).
 * @param showsTitle When false the title slot is invisible but its space is preserved.
 * @param backgroundStyle Controls whether the title bar uses glass, opaque, or transparent background.
 * @param forcedColorScheme When non-null, overrides the color scheme for the title bar and all its children.
 * @param navigationActions Left-side actions (e.g. [NavigationButtons]).
 * @param title The central area, typically containing a title or an [AddressBar].
 * @param actions Right-side action buttons (e.g. [TitleBarButtonGroup]).
 * @param backgroundColor Background color of the title bar (used when not glass).
 * @param showBottomBorder Whether to show a subtle border at the bottom.
 * @param glass When true, uses a translucent glass background so content
 *   can scroll behind the title bar (macOS vibrancy style).
 * @param height Height of the title bar. Defaults to the [style] height.
 */
@Composable
fun TitleBar(
    modifier: Modifier = Modifier,
    style: TitleBarStyle = TitleBarStyle.Unified,
    showsTitle: Boolean = true,
    backgroundStyle: TitleBarBackground = TitleBarBackground.Automatic,
    forcedColorScheme: ColorScheme? = null,
    navigationActions: @Composable RowScope.() -> Unit = {},
    title: @Composable () -> Unit = {},
    titleAlignment: Alignment = Alignment.Center,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MacosTheme.colorScheme.background,
    showBottomBorder: Boolean = false,
    glass: Boolean = false,
    height: Int = style.height,
    navigationActionsMinWidth: Dp = style.navMinWidth,
    pushActionsOnExpand: Boolean = false,
) {
    val content = @Composable {
        TitleBarContent(
            modifier = modifier,
            style = style,
            showsTitle = showsTitle,
            backgroundStyle = backgroundStyle,
            navigationActions = navigationActions,
            title = title,
            titleAlignment = titleAlignment,
            actions = actions,
            backgroundColor = backgroundColor,
            showBottomBorder = showBottomBorder,
            glass = glass,
            height = height,
            navigationActionsMinWidth = navigationActionsMinWidth,
            pushActionsOnExpand = pushActionsOnExpand,
        )
    }

    if (forcedColorScheme != null) {
        CompositionLocalProvider(LocalColorScheme provides forcedColorScheme) {
            content()
        }
    } else {
        content()
    }
}

@Composable
private fun TitleBarContent(
    modifier: Modifier,
    style: TitleBarStyle,
    showsTitle: Boolean,
    backgroundStyle: TitleBarBackground,
    navigationActions: @Composable RowScope.() -> Unit,
    title: @Composable () -> Unit,
    titleAlignment: Alignment = Alignment.Center,
    actions: @Composable RowScope.() -> Unit,
    backgroundColor: Color,
    showBottomBorder: Boolean,
    glass: Boolean,
    height: Int,
    navigationActionsMinWidth: Dp = style.navMinWidth,
    pushActionsOnExpand: Boolean = false,
) {
    val isDark = MacosTheme.colorScheme.isDark
    val borderColor = if (isDark) Color.Black.copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.1f)

    // Resolve background behavior
    val insideScaffold = LocalToolbarGlassState.current != null
    val isTransparent = when (backgroundStyle) {
        is TitleBarBackground.Automatic -> glass || insideScaffold
        is TitleBarBackground.Visible -> false
        is TitleBarBackground.Hidden -> true
        is TitleBarBackground.Material -> true // glass effect handled by scaffold
    }
    val bgModifier = if (isTransparent) Modifier else Modifier.background(backgroundColor)

    val showBorder = showBottomBorder && backgroundStyle !is TitleBarBackground.Hidden

    val titleTextStyle = when (style) {
        TitleBarStyle.UnifiedCompact -> MacosTheme.typography.caption1
        TitleBarStyle.Expanded -> MacosTheme.typography.headline
        else -> MacosTheme.typography.body
    }

    val onTitleBarDoubleClick = LocalTitleBarDoubleClick.current

    val outerModifier = modifier
        .fillMaxWidth()
        .height(height.dp)
        .then(
            if (onTitleBarDoubleClick != null) {
                Modifier.pointerInput(onTitleBarDoubleClick) {
                    awaitPointerEventScope {
                        var lastPressTime = 0L
                        while (true) {
                            val event = awaitPointerEvent(PointerEventPass.Final)
                            if (event.type == PointerEventType.Press &&
                                event.changes.none { it.isConsumed }
                            ) {
                                val now = event.changes.first().uptimeMillis
                                if (now - lastPressTime < viewConfiguration.doubleTapTimeoutMillis) {
                                    onTitleBarDoubleClick()
                                    lastPressTime = 0L
                                } else {
                                    lastPressTime = now
                                }
                            }
                        }
                    }
                }
            } else {
                Modifier
            },
        )
        .then(bgModifier)
        .then(
            if (showBorder) {
                Modifier.drawBehind {
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 0.5.dp.toPx(),
                    )
                }
            } else {
                Modifier
            },
        )

    CompositionLocalProvider(
        LocalTitleBarStyle provides style,
        LocalTextStyle provides titleTextStyle,
    ) {
        if (pushActionsOnExpand) {
            val density = LocalDensity.current
            var actionsWidthPx by remember { mutableStateOf(0) }
            val actionsMinWidthPx = remember(style, density) { with(density) { style.actionsMinWidth.roundToPx() } }
            val pushOffsetPx = (actionsWidthPx - actionsMinWidthPx).coerceAtLeast(0)

            Box(modifier = outerModifier.clipToBounds().padding(horizontal = style.horizontalPadding)) {
                // Left section (nav + title): fills full width, pushed left as actions expand
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .offset { IntOffset(-pushOffsetPx, 0) },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp).widthIn(min = navigationActionsMinWidth),
                    ) {
                        navigationActions()
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .graphicsLayer { alpha = if (showsTitle) 1f else 0f },
                        contentAlignment = titleAlignment,
                    ) {
                        title()
                    }
                }

                // Right section (actions): anchored at end, unbounded so expanding
                // children (e.g. search field) push the left section instead of compressing
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(style.actionSpacing, Alignment.End),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .wrapContentWidth(unbounded = true, align = Alignment.End)
                        .padding(start = 8.dp)
                        .widthIn(min = style.actionsMinWidth)
                        .onGloballyPositioned { actionsWidthPx = it.size.width },
                ) {
                    actions()
                }
            }
        } else {
            Row(
                modifier = outerModifier.padding(horizontal = style.horizontalPadding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Left section: Nav Actions
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 8.dp).widthIn(min = navigationActionsMinWidth),
                ) {
                    navigationActions()
                }

                // Center section: Title
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .graphicsLayer { alpha = if (showsTitle) 1f else 0f },
                    contentAlignment = titleAlignment,
                ) {
                    title()
                }

                // Right section: Actions
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(style.actionSpacing, Alignment.End),
                    modifier = Modifier.padding(start = 8.dp).widthIn(min = style.actionsMinWidth),
                ) {
                    actions()
                }
            }
        }
    }
}

// =============================================================================
// WindowTitle — centered title + optional subtitle
// =============================================================================

/**
 * A macOS-style centered window title with optional subtitle and proxy icon.
 * Matches the macOS title bar layout from the Sketch specs:
 *
 * - **Title Only**: centered title text
 * - **Title + Symbol**: title with a trailing proxy icon (e.g. document icon)
 * - **Title + Subtitle**: title with subtitle row below (e.g. "Subtitle — Edited")
 *
 * Intended to be used inside the [TitleBar] `title` slot.
 *
 * @param title The main window title.
 * @param subtitle Optional subtitle displayed below the title in a smaller, secondary style.
 * @param proxyIcon Optional trailing icon shown next to the title (e.g. a document proxy icon).
 */
@Composable
fun WindowTitle(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    proxyIcon: (@Composable () -> Unit)? = null,
) {
    val colors = MacosTheme.colorScheme
    val typography = MacosTheme.typography

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = title,
                style = typography.body,
                color = colors.textPrimary,
            )
            if (proxyIcon != null) {
                CompositionLocalProvider(
                    LocalContentColor provides colors.textSecondary,
                ) {
                    proxyIcon()
                }
            }
        }
        if (subtitle != null) {
            Text(
                text = subtitle,
                style = typography.caption2,
                color = colors.textSecondary,
            )
        }
    }
}

// =============================================================================
// TitleBarButtonGroup — shared pill container for grouped toolbar buttons
// =============================================================================

private val TitleBarGroupShape = RoundedCornerShape(percent = 50)

/**
 * Tracks the number of [TitleBarGroupButton] children in a [TitleBarButtonGroup].
 * When > 1, buttons automatically use circular highlight instead of classic.
 */
internal val LocalTitleBarGroupButtonCount = compositionLocalOf { mutableIntStateOf(0) }

/**
 * A pill-shaped container grouping toolbar buttons with a liquid glass background,
 * matching the macOS Toolbar button-group style (e.g. Safari's back/forward or action clusters).
 *
 * Adapts its height to the current [TitleBarStyle] via [LocalTitleBarStyle].
 * Place [TitleBarGroupButton] composables inside.
 *
 * Buttons automatically use circular highlight when the group contains more than one button,
 * and classic (full-width) highlight when the group contains a single button.
 */
@Composable
fun TitleBarButtonGroup(
    modifier: Modifier = Modifier,
    colors: TitleBarButtonColors? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val isDark = MacosTheme.colorScheme.isDark
    val style = LocalTitleBarStyle.current
    val buttonCount = remember { mutableIntStateOf(0) }

    val pillBg = colors?.backgroundColor
        ?: if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.06f)

    CompositionLocalProvider(LocalTitleBarGroupButtonCount provides buttonCount) {
        Row(
            modifier = modifier
                .height(style.buttonHeight)
                .clip(TitleBarGroupShape)
                .background(pillBg, TitleBarGroupShape),
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }
}

/**
 * A single button slot inside a [TitleBarButtonGroup].
 * Provides hover/press feedback without its own pill shape — the group provides it.
 *
 * The highlight style is determined automatically by the group:
 * - Single button in group → classic (full-width) highlight
 * - Multiple buttons in group → circular highlight
 */
@Composable
fun TitleBarGroupButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    iconScale: Float = 1.2f,
    colors: TitleBarButtonColors? = null,
    content: @Composable () -> Unit,
) {
    val isDark = MacosTheme.colorScheme.isDark
    val style = LocalTitleBarStyle.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    // Register in the group counter for automatic highlight style detection
    val groupButtonCount = LocalTitleBarGroupButtonCount.current
    DisposableEffect(Unit) {
        groupButtonCount.intValue++
        onDispose { groupButtonCount.intValue-- }
    }
    val circularHighlight = groupButtonCount.intValue > 1

    val defaultPressed = if (isDark) Color.White.copy(alpha = 0.18f) else Color.Black.copy(alpha = 0.12f)
    val defaultHovered = if (isDark) Color.White.copy(alpha = 0.12f) else Color.Black.copy(alpha = 0.08f)
    val bgOverlay by animateColorAsState(
        targetValue = when {
            isPressed && enabled -> colors?.pressedOverlay ?: defaultPressed
            isHovered && enabled -> colors?.hoveredOverlay ?: defaultHovered
            else -> Color.Transparent
        },
        animationSpec = macosTween(MacosDuration.Fast),
        label = "tbar_grp_btn_bg",
    )
    val defaultContent = if (isDark) Color.White.copy(alpha = 0.85f) else Color(0xFF1A1A1A)
    val defaultDisabled = if (isDark) Color.White.copy(alpha = 0.28f) else Color(0xFFBFBFBF)
    val contentColor by animateColorAsState(
        targetValue = if (!enabled) {
            colors?.disabledContentColor ?: defaultDisabled
        } else {
            colors?.contentColor ?: defaultContent
        },
        animationSpec = macosTween(MacosDuration.Fast),
        label = "tbar_grp_btn_content",
    )

    val iconAreaSize = style.buttonHeight - (style.buttonPadding * 2)

    // Scale icons by increasing density so vectors render natively at the larger size
    // (avoids graphicsLayer bitmap scaling which causes blur)
    val currentDensity = LocalDensity.current
    val scaledDensity = remember(currentDensity.density, currentDensity.fontScale, iconScale) {
        if (iconScale != 1f) {
            Density(
                density = currentDensity.density * iconScale,
                fontScale = currentDensity.fontScale,
            )
        } else {
            currentDensity
        }
    }

    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalTextStyle provides MacosTheme.typography.caption1,
    ) {
        Box(
            modifier = modifier
                .size(style.buttonHeight)
                .then(if (!circularHighlight) Modifier.background(bgOverlay) else Modifier)
                .hoverable(interactionSource = interactionSource)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick,
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (circularHighlight) {
                Box(
                    modifier = Modifier
                        .size(iconAreaSize)
                        .background(bgOverlay, CircleShape),
                )
            }
            CompositionLocalProvider(LocalDensity provides scaledDensity) {
                content()
            }
        }
    }
}

/**
 * A vertical separator placed between toolbar items (button groups, search fields, etc.)
 * inside a [TitleBar] actions slot. Equivalent to SwiftUI's `Divider()` in a `.toolbar`.
 *
 * Sketch: `_Separator` — 1px wide, 16px tall.
 */
@Composable
fun ToolbarSeparator(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .width(1.dp)
            .height(16.dp)
            .background(MacosTheme.colorScheme.borderSubtle),
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
    colors: TitleBarButtonColors? = null,
) {
    val style = LocalTitleBarStyle.current
    TitleBarButtonGroup(modifier = modifier, colors = colors) {
        TitleBarGroupButton(
            onClick = onBack,
            enabled = backEnabled,
            iconScale = 1f,
            colors = colors,
        ) {
            Icon(icon = Icons.ChevronLeft, modifier = Modifier.size(style.iconSize + 4.dp))
        }
        ToolbarSeparator()
        TitleBarGroupButton(
            onClick = onForward,
            enabled = forwardEnabled,
            iconScale = 1f,
            colors = colors,
        ) {
            Icon(icon = Icons.ChevronRight, modifier = Modifier.size(style.iconSize + 4.dp))
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
    colors: TitleBarButtonColors? = null,
    icon: @Composable () -> Unit = {
        val style = LocalTitleBarStyle.current
        Icon(icon = Icons.PanelLeft, modifier = Modifier.size(style.iconSize + 4.dp))
    },
    menuContent: (@Composable ColumnScope.() -> Unit)? = null,
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var buttonHeightPx by remember { mutableStateOf(0) }

    Box {
        TitleBarButtonGroup(
            modifier = modifier.onGloballyPositioned { buttonHeightPx = it.size.height },
            colors = colors,
        ) {
            TitleBarGroupButton(
                onClick = onClick,
                enabled = enabled,
                colors = colors,
            ) {
                icon()
            }
            if (menuContent != null) {
                ToolbarSeparator()
                TitleBarGroupButton(
                    onClick = { menuExpanded = true },
                    enabled = enabled,
                    colors = colors,
                ) {
                    Icon(icon = Icons.ChevronDown, modifier = Modifier.size(12.dp))
                }
            }
        }

        if (menuContent != null) {
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                offset = IntOffset(0, buttonHeightPx),
                content = menuContent,
            )
        }
    }
}
