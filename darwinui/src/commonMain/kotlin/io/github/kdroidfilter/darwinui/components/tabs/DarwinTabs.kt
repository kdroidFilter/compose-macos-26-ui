package io.github.kdroidfilter.darwinui.components.tabs

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.Zinc100
import io.github.kdroidfilter.darwinui.theme.Zinc200
import io.github.kdroidfilter.darwinui.theme.Zinc800
import io.github.kdroidfilter.darwinui.theme.Zinc900
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import io.github.kdroidfilter.darwinui.theme.darwinTween
import kotlinx.coroutines.launch

// =============================================================================
// State
// =============================================================================

/**
 * State holder for the Darwin tabs system.
 *
 * Tracks selected tab and measured trigger positions for the animated
 * indicator. Fields are backed by [mutableStateOf] so the state object
 * can be created once and updated on recomposition without losing
 * measured layout data.
 */
class DarwinTabsState(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
) {
    var selectedTab by mutableStateOf(selectedTab)
    var onTabSelected by mutableStateOf(onTabSelected)

    internal val tabOffsets = mutableStateMapOf<String, Dp>()
    internal val tabWidths = mutableStateMapOf<String, Dp>()
}

internal val LocalDarwinTabsState = staticCompositionLocalOf<DarwinTabsState> {
    error("DarwinTabs components must be used within a DarwinTabs composable.")
}

// =============================================================================
// DarwinTabs — root container
// =============================================================================

/**
 * Root container for the Darwin tab navigation system.
 *
 * Provides a [DarwinTabsState] to child composables via [CompositionLocalProvider].
 * Children — typically a [DarwinTabsList] followed by one or more [DarwinTabsContent]
 * blocks — read the shared state to determine selection and animate the indicator.
 *
 * @param selectedTab The value of the currently active tab.
 * @param onTabSelected Callback invoked when a tab trigger is clicked.
 * @param modifier Modifier applied to the outer column.
 * @param content Composable children.
 */
@Composable
fun DarwinTabs(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val state = remember { DarwinTabsState(selectedTab, onTabSelected) }
    state.selectedTab = selectedTab
    state.onTabSelected = onTabSelected

    CompositionLocalProvider(LocalDarwinTabsState provides state) {
        Column(modifier = modifier) {
            content()
        }
    }
}

// =============================================================================
// DarwinTabsList — pill-shaped container for tab triggers
// =============================================================================

@Composable
fun DarwinTabsList(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val state = LocalDarwinTabsState.current
    val shapes = DarwinTheme.shapes
    val density = LocalDensity.current
    val isDark = DarwinTheme.colors.isDark

    // Background & border colours
    val backgroundColor = if (isDark) Color.White.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.05f)

    val borderColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

    // Indicator colour: bg-black/10 dark:bg-white/10
    val indicatorColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

    // ---- Animated indicator ----
    val targetOffset = state.tabOffsets[state.selectedTab] ?: 0.dp
    val targetWidth = state.tabWidths[state.selectedTab] ?: 0.dp

    val indicatorOffset = remember { Animatable(0f) }
    val indicatorWidth = remember { Animatable(0f) }
    var hasInitialized by remember { mutableStateOf(false) }

    val springSpec = darwinSpring<Float>(DarwinSpringPreset.Snappy)

    LaunchedEffect(targetOffset, targetWidth) {
        if (targetWidth.value > 0f) {
            if (!hasInitialized) {
                // First measurement — snap to position without animation
                indicatorOffset.snapTo(targetOffset.value)
                indicatorWidth.snapTo(targetWidth.value)
                hasInitialized = true
            } else {
                launch { indicatorOffset.animateTo(targetOffset.value, springSpec) }
                launch { indicatorWidth.animateTo(targetWidth.value, springSpec) }
            }
        }
    }

    // Pill container: inline-flex h-10 rounded-xl p-1
    Box(
        modifier = modifier
            .height(40.dp)
            .background(backgroundColor, shapes.large)
            .clip(shapes.large)
            .border(1.dp, borderColor, shapes.large)
            .padding(4.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        // Animated background indicator behind the selected trigger
        // fillMaxHeight makes it fill the inner pill area (32dp)
        if (indicatorWidth.value > 0f) {
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = with(density) { indicatorOffset.value.dp.roundToPx() },
                            y = 0,
                        )
                    }
                    .size(width = indicatorWidth.value.dp, height = 32.dp)
                    .background(indicatorColor, shapes.small),
            )
        }

        // Tab triggers row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }
}

// =============================================================================
// DarwinTabsTrigger — individual tab button
// =============================================================================

@Composable
fun DarwinTabsTrigger(
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val state = LocalDarwinTabsState.current
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography
    val density = LocalDensity.current
    val isDark = colors.isDark

    val isSelected = state.selectedTab == value
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val textColor = when {
        isSelected -> if (isDark) Zinc100 else Zinc900
        isHovered -> if (isDark) Zinc100 else Zinc900
        else -> if (isDark) Zinc200 else Zinc800
    }

    val textStyle = typography.bodyMedium.merge(
        TextStyle(
            color = textColor,
            fontWeight = FontWeight.Medium,
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
            .then(if (enabled) Modifier else Modifier.alpha(0.5f))
            .hoverable(interactionSource = interactionSource, enabled = enabled)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Tab,
                enabled = enabled,
                onClick = { state.onTabSelected(value) },
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(
            LocalDarwinTextStyle provides textStyle,
            LocalDarwinContentColor provides textColor,
        ) {
            if (icon != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Box(modifier = Modifier.size(16.dp)) { icon() }
                    content()
                }
            } else {
                content()
            }
        }
    }
}

// =============================================================================
// DarwinTabsContent — content panel for a tab
// =============================================================================

@Composable
fun DarwinTabsContent(
    value: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val state = LocalDarwinTabsState.current

    if (value != state.selectedTab) return

    val density = LocalDensity.current
    val slideOffsetPx = with(density) { 10.dp.toPx() }

    // Trigger a one-shot enter animation each time this content becomes visible.
    // The key changes every time `value` becomes the selected tab.
    var animationProgress by remember(state.selectedTab) { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = darwinTween(DarwinDuration.Slow),
        label = "tab_content_enter",
    )
    animationProgress = animatedProgress

    Box(
        modifier = modifier
            .padding(top = 8.dp)
            .graphicsLayer {
                alpha = animationProgress
                translationY = slideOffsetPx * (1f - animationProgress)
            },
    ) {
        content()
    }
}

@Preview
@Composable
private fun DarwinTabsPreview() {
    DarwinTheme {
        var selectedTab by remember { mutableStateOf("account") }
        DarwinTabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it }) {
            DarwinTabsList {
                DarwinTabsTrigger("account") { DarwinText("Account") }
                DarwinTabsTrigger("password") { DarwinText("Password") }
            }
        }
    }
}
