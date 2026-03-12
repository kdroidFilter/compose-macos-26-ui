package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.Zinc100
import io.github.kdroidfilter.darwinui.theme.Zinc200
import io.github.kdroidfilter.darwinui.theme.Zinc800
import io.github.kdroidfilter.darwinui.theme.Zinc900
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import kotlinx.coroutines.launch

object TabRowDefaults {
    val containerColor: Color @Composable get() = DarwinTheme.colorScheme.surface
    val contentColor: Color @Composable get() = DarwinTheme.colorScheme.onSurface
    val primaryIndicatorTabContentColor: Color @Composable get() = DarwinTheme.colorScheme.primary
    val secondaryIndicatorTabContentColor: Color @Composable get() = DarwinTheme.colorScheme.onSurfaceVariant
}

/**
 * Tab row with animated indicator.
 * Mirrors Material3's TabRow using index-based selection.
 */
@Composable
fun TabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = TabRowDefaults.containerColor,
    contentColor: Color = TabRowDefaults.contentColor,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = { tabPositions ->
        if (tabPositions.isNotEmpty() && selectedTabIndex < tabPositions.size) {
            DefaultTabIndicator(tabPositions[selectedTabIndex])
        }
    },
    divider: @Composable () -> Unit = {
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(DarwinTheme.colorScheme.outlineVariant))
    },
    tabs: @Composable () -> Unit,
) {
    val tabPositions = remember { mutableStateOf(emptyList<TabPosition>()) }

    Column(modifier = modifier.fillMaxWidth().background(containerColor)) {
        Box {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                tabs()
            }
        }
        divider()
    }
}

/**
 * Position data for a tab, used to position the indicator.
 */
data class TabPosition(val left: Dp, val width: Dp)

@Composable
private fun DefaultTabIndicator(tabPosition: TabPosition) {
    val density = LocalDensity.current
    val indicatorOffset = remember { Animatable(0f) }
    val indicatorWidth = remember { Animatable(0f) }
    var hasInitialized by remember { mutableStateOf(false) }
    val springSpec = darwinSpring<Float>(DarwinSpringPreset.Snappy)

    LaunchedEffect(tabPosition) {
        if (!hasInitialized) {
            indicatorOffset.snapTo(tabPosition.left.value)
            indicatorWidth.snapTo(tabPosition.width.value)
            hasInitialized = true
        } else {
            launch { indicatorOffset.animateTo(tabPosition.left.value, springSpec) }
            launch { indicatorWidth.animateTo(tabPosition.width.value, springSpec) }
        }
    }

    Box(
        modifier = Modifier
            .offset { IntOffset(with(density) { indicatorOffset.value.dp.roundToPx() }, 0) }
            .height(2.dp)
            .then(
                if (indicatorWidth.value > 0f)
                    Modifier.alpha(1f)
                else Modifier.alpha(0f)
            )
            .background(DarwinTheme.colorScheme.primary),
    )
}

/**
 * A single tab in a [TabRow].
 * Mirrors Material3's Tab.
 */
@Composable
fun Tab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: (@Composable () -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    selectedContentColor: Color = TabRowDefaults.primaryIndicatorTabContentColor,
    unselectedContentColor: Color = TabRowDefaults.secondaryIndicatorTabContentColor,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val contentColor = if (selected) selectedContentColor else unselectedContentColor

    Box(
        modifier = modifier
            .then(if (!enabled) Modifier.alpha(0.38f) else Modifier)
            .hoverable(interactionSource, enabled)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Tab,
                onClick = onClick,
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        androidx.compose.runtime.CompositionLocalProvider(
            io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor provides contentColor,
            io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle provides DarwinTheme.typography.caption1.copy(color = contentColor),
        ) {
            if (icon != null && text != null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    icon()
                    text()
                }
            } else {
                icon?.invoke()
                text?.invoke()
            }
        }
    }
}

/**
 * Scrollable version of [TabRow].
 * Mirrors Material3's ScrollableTabRow.
 */
@Composable
fun ScrollableTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = TabRowDefaults.containerColor,
    contentColor: Color = TabRowDefaults.contentColor,
    edgePadding: Dp = 52.dp,
    tabs: @Composable () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier.fillMaxWidth().background(containerColor)) {
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(scrollState).padding(horizontal = edgePadding),
        ) {
            tabs()
        }
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(DarwinTheme.colorScheme.outlineVariant))
    }
}

@Preview
@Composable
private fun TabRowPreview() {
    DarwinTheme {
        var selected by remember { mutableStateOf(0) }
        TabRow(selectedTabIndex = selected) {
            Tab(selected = selected == 0, onClick = { selected = 0 }, text = { Text("Tab 1") })
            Tab(selected = selected == 1, onClick = { selected = 1 }, text = { Text("Tab 2") })
        }
    }
}
