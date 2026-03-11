package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle

// ===========================================================================
// NavigationBarItemColors — mirrors M3's NavigationBarItemColors
// ===========================================================================

@Immutable
class NavigationBarItemColors(
    val selectedIconColor: Color,
    val selectedTextColor: Color,
    val indicatorColor: Color,
    val unselectedIconColor: Color,
    val unselectedTextColor: Color,
    val disabledIconColor: Color,
    val disabledTextColor: Color,
)

// ===========================================================================
// NavigationBarItemDefaults — mirrors M3's NavigationBarItemDefaults
// ===========================================================================

object NavigationBarItemDefaults {
    @Composable
    fun colors(
        selectedIconColor: Color = DarwinTheme.colorScheme.onSecondaryContainer,
        selectedTextColor: Color = DarwinTheme.colorScheme.onSurface,
        indicatorColor: Color = DarwinTheme.colorScheme.secondaryContainer,
        unselectedIconColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        unselectedTextColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        disabledIconColor: Color = DarwinTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
        disabledTextColor: Color = DarwinTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
    ) = NavigationBarItemColors(
        selectedIconColor, selectedTextColor, indicatorColor,
        unselectedIconColor, unselectedTextColor,
        disabledIconColor, disabledTextColor,
    )
}

object NavigationBarDefaults {
    val containerColor: Color @Composable get() = DarwinTheme.colorScheme.surfaceContainer
    val contentColor: Color @Composable get() = DarwinTheme.colorScheme.onSurfaceVariant
    val TonalElevation: Dp = 3.dp
}

// ===========================================================================
// NavigationBar — mirrors M3's NavigationBar
// ===========================================================================

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = NavigationBarDefaults.containerColor,
    contentColor: Color = NavigationBarDefaults.contentColor,
    tonalElevation: Dp = NavigationBarDefaults.TonalElevation,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(containerColor)
            .border(1.dp, DarwinTheme.colorScheme.outlineVariant),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

// ===========================================================================
// NavigationBarItem — mirrors M3's NavigationBarItem
// ===========================================================================

@Composable
fun RowScope.NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: (@Composable () -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val iconColor by animateColorAsState(
        targetValue = when {
            !enabled -> colors.disabledIconColor
            selected -> colors.selectedIconColor
            else -> colors.unselectedIconColor
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "navBarIconColor",
    )
    val textColor by animateColorAsState(
        targetValue = when {
            !enabled -> colors.disabledTextColor
            selected -> colors.selectedTextColor
            else -> colors.unselectedTextColor
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "navBarTextColor",
    )

    // Indicator pill animates from narrow (unselected) to full width (selected), M3-style
    val indicatorColor by animateColorAsState(
        targetValue = if (selected) colors.indicatorColor else Color.Transparent,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "navBarIndicatorColor",
    )
    val indicatorWidth by animateDpAsState(
        targetValue = if (selected) 64.dp else 32.dp,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "navBarIndicatorWidth",
    )

    val indicatorShape = DarwinTheme.shapes.extraLarge

    Column(
        modifier = modifier
            .weight(1f)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Tab,
                onClick = onClick,
            )
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Box(
            modifier = Modifier
                .defaultMinSize(minWidth = 64.dp, minHeight = 32.dp),
            contentAlignment = Alignment.Center,
        ) {
            // Animated pill indicator behind the icon
            Box(
                modifier = Modifier
                    .width(indicatorWidth)
                    .height(32.dp)
                    .clip(indicatorShape)
                    .background(indicatorColor, indicatorShape),
            )
            Box(modifier = Modifier.size(24.dp)) {
                CompositionLocalProvider(LocalDarwinContentColor provides iconColor) { icon() }
            }
        }
        if (label != null && (alwaysShowLabel || selected)) {
            CompositionLocalProvider(
                LocalDarwinContentColor provides textColor,
                LocalDarwinTextStyle provides DarwinTheme.typography.labelMedium.copy(color = textColor),
            ) { label() }
        }
    }
}

@Preview
@Composable
private fun NavigationBarPreview() {
    DarwinTheme {
        NavigationBar {
            NavigationBarItem(selected = true, onClick = {}, icon = {}, label = { Text("Home") })
            NavigationBarItem(selected = false, onClick = {}, icon = {}, label = { Text("Search") })
        }
    }
}
