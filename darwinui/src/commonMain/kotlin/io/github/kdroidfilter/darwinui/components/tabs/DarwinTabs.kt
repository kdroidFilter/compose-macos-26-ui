package io.github.kdroidfilter.darwinui.components.tabs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.darwinTween
import io.github.kdroidfilter.darwinui.theme.glassOrDefault

// =============================================================================
// CompositionLocal for sharing tabs state
// =============================================================================

/**
 * State holder for the Darwin tabs system.
 *
 * Holds the currently selected tab value and the callback to change it,
 * as well as measured positions and widths for the animated active indicator.
 */
class DarwinTabsState(
    val selectedTab: String,
    val onTabSelected: (String) -> Unit,
    val glass: Boolean,
) {
    /** Measured X-offset (in dp) of each tab trigger, keyed by tab value. */
    internal val tabOffsets = mutableStateMapOf<String, Dp>()

    /** Measured width (in dp) of each tab trigger, keyed by tab value. */
    internal val tabWidths = mutableStateMapOf<String, Dp>()
}

internal val LocalDarwinTabsState = staticCompositionLocalOf<DarwinTabsState> {
    error("DarwinTabs components must be used within a DarwinTabs composable.")
}

// =============================================================================
// DarwinTabs -- root container
// =============================================================================

/**
 * Root container for the Darwin tab navigation system.
 *
 * Provides a [DarwinTabsState] to child composables via [CompositionLocalProvider],
 * enabling [DarwinTabsList], [DarwinTabsTrigger], and [DarwinTabsContent] to
 * share the selected-tab state and animated-indicator measurements.
 *
 * @param selectedTab The value of the currently active tab.
 * @param onTabSelected Callback invoked when a tab trigger is clicked.
 * @param glass When true, the tabs list uses a glass-morphism background.
 * @param modifier Modifier applied to the outer column.
 * @param content The composable children -- typically a [DarwinTabsList] followed
 *   by one or more [DarwinTabsContent] blocks.
 */
@Composable
fun DarwinTabs(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    glass: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val state = remember(selectedTab, onTabSelected, glass) {
        DarwinTabsState(
            selectedTab = selectedTab,
            onTabSelected = onTabSelected,
            glass = glass,
        )
    }

    // Keep state fields in sync when recomposed with new values
    val updatedState = remember { state }.also {
        // DarwinTabsState is recreated via remember key changes, but we also
        // need to preserve measured offsets across recompositions with the same
        // key set. The remember(selectedTab, onTabSelected, glass) handles the
        // recreation when any of these change.
    }

    CompositionLocalProvider(LocalDarwinTabsState provides updatedState) {
        Column(modifier = modifier) {
            content()
        }
    }
}

// =============================================================================
// DarwinTabsList -- horizontal row of tab triggers
// =============================================================================

/**
 * Horizontal row container for [DarwinTabsTrigger] composables.
 *
 * Draws a 1 dp bottom border in the theme's border color and hosts the
 * animated active indicator that slides to the currently selected tab.
 *
 * @param modifier Modifier applied to the outer container.
 * @param content Row-scoped content -- typically several [DarwinTabsTrigger]s.
 */
@Composable
fun DarwinTabsList(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val state = LocalDarwinTabsState.current
    val colors = DarwinTheme.colors

    val backgroundColor = if (state.glass) {
        glassOrDefault(glass = true, fallback = Color.Transparent)
    } else {
        Color.Transparent
    }

    // Animated indicator offset and width
    val targetOffset = state.tabOffsets[state.selectedTab] ?: 0.dp
    val targetWidth = state.tabWidths[state.selectedTab] ?: 0.dp

    val animatedOffset by animateDpAsState(
        targetValue = targetOffset,
        animationSpec = darwinTween(DarwinDuration.Normal),
        label = "darwin_tab_indicator_offset",
    )

    val animatedWidth by animateDpAsState(
        targetValue = targetWidth,
        animationSpec = darwinTween(DarwinDuration.Normal),
        label = "darwin_tab_indicator_width",
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor),
    ) {
        Column {
            // Tab triggers row
            Row(
                modifier = Modifier.fillMaxWidth(),
                content = content,
            )

            // Bottom border line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colors.border),
            )
        }

        // Active indicator -- drawn on top of the bottom border
        if (animatedWidth > 0.dp) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset { IntOffset(x = animatedOffset.roundToPx(), y = 0) }
                    .width(animatedWidth)
                    .height(2.dp)
                    .background(colors.accent),
            )
        }
    }
}

// =============================================================================
// DarwinTabsTrigger -- individual tab button
// =============================================================================

/**
 * Individual tab button within a [DarwinTabsList].
 *
 * Reports its measured position and width back to [DarwinTabsState] so the
 * animated active indicator can slide to the correct location.
 *
 * @param value The unique identifier for this tab.
 * @param selected Whether this tab is the active tab.
 * @param onClick Callback invoked when this trigger is clicked.
 * @param modifier Modifier applied to the trigger container.
 * @param content The label content, typically a [Text] composable.
 */
@Composable
fun DarwinTabsTrigger(
    value: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val state = LocalDarwinTabsState.current
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography
    val density = LocalDensity.current

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    // Determine text color
    val textColor = when {
        selected -> colors.textPrimary
        isHovered -> colors.textPrimary
        else -> colors.textSecondary
    }

    val textStyle = typography.bodyMedium.merge(
        TextStyle(
            color = textColor,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
        )
    )

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                with(density) {
                    state.tabOffsets[value] = coordinates.positionInParent().x.toDp()
                    state.tabWidths[value] = coordinates.size.width.toDp()
                }
            }
            .hoverable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Tab,
                onClick = onClick,
            )
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(
            LocalDarwinTextStyle provides textStyle,
        ) {
            content()
        }
    }
}

// =============================================================================
// DarwinTabsContent -- content panel for a tab
// =============================================================================

/**
 * Content panel that is visible only when [value] matches [selectedTab].
 *
 * Uses [AnimatedVisibility] with a fade transition for smooth appearance.
 *
 * @param value The tab value this content is associated with.
 * @param selectedTab The currently selected tab value.
 * @param modifier Modifier applied to the content wrapper.
 * @param content The composable content displayed when this tab is active.
 */
@Composable
fun DarwinTabsContent(
    value: String,
    selectedTab: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visible = value == selectedTab,
        enter = fadeIn(animationSpec = darwinTween(DarwinDuration.Normal)),
        exit = fadeOut(animationSpec = darwinTween(DarwinDuration.Fast)),
    ) {
        Box(
            modifier = modifier.padding(top = 16.dp),
        ) {
            content()
        }
    }
}
