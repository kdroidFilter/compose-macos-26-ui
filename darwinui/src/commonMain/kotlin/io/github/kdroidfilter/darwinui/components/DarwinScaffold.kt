package io.github.kdroidfilter.darwinui.components

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.liquid
import io.github.fletchmckee.liquid.rememberLiquidState
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucidePanelLeft
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinLiquidState
import io.github.kdroidfilter.darwinui.theme.LocalToolbarGlassState
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import io.github.kdroidfilter.darwinui.theme.darwinTween

/**
 * macOS-style scaffold layout with optional sidebar and title bar.
 *
 * The sidebar occupies the full height on the left edge. The title bar
 * overlays the content from the top with a frosted glass blur — content
 * scrolls behind it, matching the macOS vibrancy effect.
 *
 * The [content] lambda receives [PaddingValues] with a top inset equal
 * to the title bar height so initial content appears below the bar.
 * Apply it to the scroll container (not the outer Box) so items scroll
 * behind the title bar.
 *
 * When [onSidebarVisibleChange] is provided, the scaffold renders a
 * toggle button that slides into the title bar when the sidebar is hidden.
 * The caller should place a corresponding hide button inside the sidebar
 * (e.g. in its header slot).
 *
 * @param modifier Modifier applied to the root container.
 * @param containerColor Background color for the scaffold.
 * @param sidebarVisible Whether the sidebar is currently visible.
 * @param onSidebarVisibleChange Called when the user toggles the sidebar.
 *   When non-null, the scaffold renders the "show" toggle in the title bar.
 * @param sidebar Optional sidebar composable (e.g. [Sidebar]).
 * @param titleBar Optional title bar composable (e.g. [TitleBar]).
 *   When used with glass, pass `glass = true` to [TitleBar] so it renders
 *   with a transparent background (the scaffold provides the frosted blur).
 * @param titleBarHeight Height of the title bar for content inset (default 52dp).
 * @param content Main content area. Receives [PaddingValues] for the title bar inset.
 */
@Composable
fun DarwinScaffold(
    modifier: Modifier = Modifier,
    containerColor: Color = DarwinTheme.colorScheme.background,
    sidebarVisible: Boolean = true,
    onSidebarVisibleChange: ((Boolean) -> Unit)? = null,
    sidebar: (@Composable () -> Unit)? = null,
    titleBar: (@Composable () -> Unit)? = null,
    titleBarHeight: Int = 52,
    content: @Composable (PaddingValues) -> Unit,
) {
    val managedToggle = sidebar != null && onSidebarVisibleChange != null
    val contentPadding = if (titleBar != null) PaddingValues(top = titleBarHeight.dp) else PaddingValues()

    // Content captured → title bar blurs it
    val titleBarGlassState = rememberLiquidState()
    // Reuse the root liquid state from DarwinTheme for button-level glass
    val rootLiquidState = LocalDarwinLiquidState.current

    val isDark = DarwinTheme.colorScheme.isDark
    val glassTint = if (isDark) Color.Black.copy(alpha = 0.15f)
                    else Color.White.copy(alpha = 0.15f)

    Row(
        modifier = modifier
            .fillMaxSize()
            .background(containerColor),
    ) {
        // Sidebar — full height, push animation
        if (sidebar != null) {
            AnimatedVisibility(
                visible = sidebarVisible,
                enter = expandHorizontally(
                    animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
                    expandFrom = Alignment.Start,
                ),
                exit = shrinkHorizontally(
                    animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
                    shrinkTowards = Alignment.Start,
                ),
            ) {
                sidebar()
            }
        }

        // Main area: content with title bar overlay
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        ) {
            // Content captured by liquefiable — title bar blurs this
            // Background MUST be inside liquefiable so it's part of the captured layer,
            // otherwise text on transparent background produces invisible blur.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .liquefiable(titleBarGlassState)
                    .background(containerColor),
            ) {
                content(contentPadding)
            }

            // Title bar overlaid at top with frosted glass blur
            val titleBarGlassModifier = Modifier.liquid(titleBarGlassState) {
                frost = 16.dp
                shape = RectangleShape
                tint = glassTint
                saturation = 1.05f
            }

            // Provide root liquid state for button-level glass inside title bar
            CompositionLocalProvider(LocalToolbarGlassState provides rootLiquidState) {
                if (managedToggle) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopStart)
                            .then(titleBarGlassModifier),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AnimatedVisibility(
                            visible = !sidebarVisible,
                            enter = expandHorizontally(
                                animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
                                expandFrom = Alignment.Start,
                            ) + fadeIn(darwinTween(DarwinDuration.Normal)),
                            exit = shrinkHorizontally(
                                animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
                                shrinkTowards = Alignment.Start,
                            ) + fadeOut(darwinTween(DarwinDuration.Fast)),
                        ) {
                            Box(modifier = Modifier.padding(start = 12.dp)) {
                                IconButton(onClick = { onSidebarVisibleChange(true) }) {
                                    Icon(LucidePanelLeft, modifier = Modifier.size(20.dp))
                                }
                            }
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            titleBar?.invoke()
                        }
                    }
                } else if (titleBar != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopStart)
                            .then(titleBarGlassModifier),
                    ) {
                        titleBar()
                    }
                }
            }
        }
    }
}
