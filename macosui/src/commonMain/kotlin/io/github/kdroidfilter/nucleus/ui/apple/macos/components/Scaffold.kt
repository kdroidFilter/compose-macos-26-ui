package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.liquid
import io.github.fletchmckee.liquid.rememberLiquidState
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosDuration
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SpringPreset
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalLiquidState
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalSidebarResize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalSidebarWidth
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalTitleBarHeight
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SidebarResizeCallbacks
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalToolbarGlassState
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosSpring
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosTween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.IntSize

/**
 * macOS-style scaffold layout with optional sidebar, content list, inspector,
 * title bar, and bottom bar.
 *
 * Supports 2-column (sidebar + content), 3-column (sidebar + contentList + content),
 * and inspector layouts. Columns are separated by draggable dividers when their
 * width is [ColumnWidth.Flexible].
 *
 * The title bar overlays the full width with frosted glass blur — content scrolls
 * behind it, matching the macOS vibrancy effect.
 *
 * @param modifier Modifier applied to the root container.
 * @param containerColor Background color for the scaffold.
 * @param columnVisibility Controls which columns are visible.
 * @param onColumnVisibilityChange Called when column visibility changes (e.g. sidebar toggle).
 *   When non-null, the scaffold renders a sidebar toggle button in the title bar.
 * @param sidebar Optional sidebar composable (e.g. [Sidebar]).
 * @param sidebarWidth Width configuration for the sidebar column.
 * @param contentList Optional middle column (e.g. message list in a mail app).
 * @param contentListWidth Width configuration for the content list column.
 * @param inspector Optional right-side inspector panel.
 * @param inspectorVisible Whether the inspector is currently visible.
 * @param onInspectorVisibleChange Called when the inspector visibility changes.
 * @param inspectorWidth Width configuration for the inspector column.
 * @param titleBar Optional title bar composable (e.g. [TitleBar]).
 * @param titleBarStyle Visual style of the title bar (determines default height).
 * @param titleBarHeight Height of the title bar for content inset.
 * @param bottomBar Optional bottom bar composable overlaying the content area.
 * @param bottomBarHeight Height of the bottom bar.
 * @param content Main content area. Receives [PaddingValues] for title bar and bottom bar insets.
 */
@Composable
fun Scaffold(
    modifier: Modifier = Modifier,
    containerColor: Color = MacosTheme.colorScheme.background,
    columnVisibility: ColumnVisibility = ColumnVisibility.Automatic,
    onColumnVisibilityChange: ((ColumnVisibility) -> Unit)? = null,
    sidebar: (@Composable () -> Unit)? = null,
    sidebarWidth: ColumnWidth = ColumnWidth.Fixed(240.dp),
    contentList: (@Composable () -> Unit)? = null,
    contentListWidth: ColumnWidth = ColumnWidth.Fixed(250.dp),
    inspector: (@Composable () -> Unit)? = null,
    inspectorVisible: Boolean = false,
    onInspectorVisibleChange: ((Boolean) -> Unit)? = null,
    inspectorWidth: ColumnWidth = ColumnWidth.Fixed(260.dp),
    titleBar: (@Composable () -> Unit)? = null,
    titleBarStyle: TitleBarStyle = TitleBarStyle.Unified,
    titleBarHeight: Int = titleBarStyle.height,
    titleBarGlassTint: Color = Color.Transparent,
    showDividers: Boolean = false,
    bottomBar: (@Composable () -> Unit)? = null,
    bottomBarHeight: Int = 38,
    content: @Composable (PaddingValues) -> Unit,
) {
    // --- Resolve effective column visibility ---
    val effectiveVisibility = if (columnVisibility == ColumnVisibility.Automatic) {
        ColumnVisibility.All
    } else {
        columnVisibility
    }

    val showSidebar = sidebar != null && effectiveVisibility == ColumnVisibility.All
    val showContentList = contentList != null && effectiveVisibility != ColumnVisibility.DetailOnly

    // Toggle callback: switches between All ↔ DoubleColumn
    val managedToggle = sidebar != null && onColumnVisibilityChange != null
    val toggleSidebar: () -> Unit = {
        val newVisibility = if (effectiveVisibility == ColumnVisibility.All) {
            ColumnVisibility.DoubleColumn
        } else {
            ColumnVisibility.All
        }
        onColumnVisibilityChange?.invoke(newVisibility)
    }

    // --- Column width states ---
    var currentSidebarWidth by remember { mutableStateOf(sidebarWidth.idealOrFixed()) }
    var currentContentListWidth by remember { mutableStateOf(contentListWidth.idealOrFixed()) }
    var currentInspectorWidth by remember { mutableStateOf(inspectorWidth.idealOrFixed()) }

    // --- Content padding ---
    val topPadding = if (titleBar != null) titleBarHeight.dp else 0.dp
    val bottomPadding = if (bottomBar != null) bottomBarHeight.dp else 0.dp
    val contentPadding = PaddingValues(top = topPadding, bottom = bottomPadding)

    // --- Glass blur ---
    val titleBarGlassState = rememberLiquidState()
    val bottomBarGlassState = rememberLiquidState()
    val rootLiquidState = LocalLiquidState.current

    val isDark = MacosTheme.colorScheme.isDark
    val glassTint = titleBarGlassTint
    val borderColor = if (showDividers) {
        if (isDark) Color.Black.copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.1f)
    } else {
        Color.Transparent
    }

    Row(
        modifier = modifier
            .fillMaxSize()
            .background(containerColor),
    ) {
        // ---- Sidebar (full height, side-by-side with the title bar) ----
        if (sidebar != null) {
            val sidebarTween = tween<IntSize>(durationMillis = 200, easing = FastOutSlowInEasing)
            AnimatedVisibility(
                visible = showSidebar,
                enter = expandHorizontally(
                    animationSpec = sidebarTween,
                    expandFrom = Alignment.Start,
                ),
                exit = shrinkHorizontally(
                    animationSpec = sidebarTween,
                    shrinkTowards = Alignment.Start,
                ),
            ) {
                val sidebarResizeCallbacks = if (sidebarWidth is ColumnWidth.Flexible) {
                    val flex = sidebarWidth
                    SidebarResizeCallbacks(
                        onDrag = { delta ->
                            currentSidebarWidth = (currentSidebarWidth + delta)
                                .coerceIn(flex.min, flex.max)
                        },
                        onReset = { currentSidebarWidth = flex.ideal },
                    )
                } else {
                    null
                }

                CompositionLocalProvider(
                    LocalSidebarWidth provides currentSidebarWidth,
                    LocalSidebarResize provides sidebarResizeCallbacks,
                ) {
                    sidebar()
                }
            }
        }

        // ---- Content area (title bar + columns) ----
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        ) {
            // Content layer captured for title bar glass
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .liquefiable(titleBarGlassState)
                    .background(containerColor),
            ) {
                Row(modifier = Modifier.fillMaxSize()) {
                    // ---- Content List (middle column) ----
                    if (contentList != null) {
                        AnimatedVisibility(
                            visible = showContentList,
                            enter = expandHorizontally(
                                animationSpec = macosSpring(SpringPreset.Snappy),
                                expandFrom = Alignment.Start,
                            ),
                            exit = shrinkHorizontally(
                                animationSpec = macosSpring(SpringPreset.Snappy),
                                shrinkTowards = Alignment.Start,
                            ),
                        ) {
                            Row {
                                Box(
                                    modifier = Modifier
                                        .width(currentContentListWidth)
                                        .fillMaxHeight()
                                        .padding(top = topPadding)
                                        .background(MacosTheme.colorScheme.surface)
                                        .drawBehind {
                                            // Right border
                                            drawLine(
                                                color = borderColor,
                                                start = Offset(size.width, 0f),
                                                end = Offset(size.width, size.height),
                                                strokeWidth = 1.dp.toPx(),
                                            )
                                        },
                                ) {
                                    contentList()
                                }

                                // Content list divider
                                if (contentListWidth is ColumnWidth.Flexible) {
                                    val flex = contentListWidth
                                    ColumnDivider(
                                        onDrag = { delta ->
                                            currentContentListWidth = (currentContentListWidth + delta)
                                                .coerceIn(flex.min, flex.max)
                                        },
                                        onReset = { currentContentListWidth = flex.ideal },
                                    )
                                }
                            }
                        }
                    }

                    // ---- Main content area ----
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    ) {
                        // Content captured for bottom bar glass
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .liquefiable(bottomBarGlassState)
                                .background(containerColor),
                        ) {
                            content(contentPadding)
                        }

                        // Bottom bar overlay with glass
                        if (bottomBar != null) {
                            val bottomBarGlassModifier = Modifier.liquid(bottomBarGlassState) {
                                frost = 16.dp
                                shape = RectangleShape
                                tint = glassTint
                                saturation = 1.05f
                            }

                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth()
                                    .height(bottomBarHeight.dp)
                                    .then(bottomBarGlassModifier)
                                    .drawBehind {
                                        // Top border
                                        drawLine(
                                            color = borderColor,
                                            start = Offset(0f, 0f),
                                            end = Offset(size.width, 0f),
                                            strokeWidth = 0.5.dp.toPx(),
                                        )
                                    },
                            ) {
                                bottomBar()
                            }
                        }
                    }

                    // ---- Inspector (right panel) ----
                    if (inspector != null) {
                        AnimatedVisibility(
                            visible = inspectorVisible,
                            enter = expandHorizontally(
                                animationSpec = macosSpring(SpringPreset.Snappy),
                                expandFrom = Alignment.End,
                            ),
                            exit = shrinkHorizontally(
                                animationSpec = macosSpring(SpringPreset.Snappy),
                                shrinkTowards = Alignment.End,
                            ),
                        ) {
                            Row {
                                // Inspector divider
                                if (inspectorWidth is ColumnWidth.Flexible) {
                                    val flex = inspectorWidth
                                    ColumnDivider(
                                        onDrag = { delta ->
                                            currentInspectorWidth = (currentInspectorWidth - delta)
                                                .coerceIn(flex.min, flex.max)
                                        },
                                        onReset = { currentInspectorWidth = flex.ideal },
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .width(currentInspectorWidth)
                                        .fillMaxHeight()
                                        .padding(top = topPadding)
                                        .background(MacosTheme.colorScheme.surface)
                                        .drawBehind {
                                            // Left border
                                            drawLine(
                                                color = borderColor,
                                                start = Offset(0f, 0f),
                                                end = Offset(0f, size.height),
                                                strokeWidth = 1.dp.toPx(),
                                            )
                                        },
                                ) {
                                    inspector()
                                }
                            }
                        }
                    }
                }
            }

            // ---- Title bar overlay (over content area only) ----
            if (titleBar != null || managedToggle) {
                val titleBarGlassModifier = Modifier.liquid(titleBarGlassState) {
                    frost = 16.dp
                    shape = RectangleShape
                    tint = glassTint
                    saturation = 1.05f
                }

                CompositionLocalProvider(LocalToolbarGlassState provides rootLiquidState) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopStart)
                            .then(titleBarGlassModifier),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (managedToggle) {
                            val toggleTween = tween<IntSize>(durationMillis = 200, easing = FastOutSlowInEasing)
                            AnimatedVisibility(
                                visible = !showSidebar,
                                enter = expandHorizontally(
                                    animationSpec = toggleTween,
                                    expandFrom = Alignment.Start,
                                ) + fadeIn(tween(durationMillis = 200, easing = FastOutSlowInEasing)),
                                exit = shrinkHorizontally(
                                    animationSpec = toggleTween,
                                    shrinkTowards = Alignment.Start,
                                ) + fadeOut(tween(durationMillis = 100, easing = FastOutSlowInEasing)),
                            ) {
                                val windowControlInset = LocalWindowControlInset.current
                                val startPadding = if (windowControlInset != Dp.Unspecified) windowControlInset else 12.dp
                                Box(modifier = Modifier.padding(start = startPadding)) {
                                    SidebarButton(onClick = toggleSidebar)
                                }
                            }
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            titleBar?.invoke()
                        }
                    }
                }
            }
        }
    }
}
