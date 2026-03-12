package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucidePanelLeft
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import io.github.kdroidfilter.darwinui.theme.darwinTween

/**
 * macOS-style scaffold layout with optional sidebar and title bar.
 *
 * The sidebar occupies the full height on the left edge. The title bar
 * and content sit to its right in a vertical column.
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
 * @param content Main content area.
 */
@Composable
fun DarwinScaffold(
    modifier: Modifier = Modifier,
    containerColor: Color = DarwinTheme.colors.background,
    sidebarVisible: Boolean = true,
    onSidebarVisibleChange: ((Boolean) -> Unit)? = null,
    sidebar: (@Composable () -> Unit)? = null,
    titleBar: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val managedToggle = sidebar != null && onSidebarVisibleChange != null

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

        // Title bar + content column
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        ) {
            // Title bar with optional toggle sliding in when sidebar hidden
            if (managedToggle) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
                                Icon(LucidePanelLeft, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        titleBar?.invoke()
                    }
                }
            } else {
                titleBar?.invoke()
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                content()
            }
        }
    }
}
