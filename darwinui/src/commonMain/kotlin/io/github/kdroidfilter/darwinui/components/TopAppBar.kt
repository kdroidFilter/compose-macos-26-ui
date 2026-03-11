package io.github.kdroidfilter.darwinui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle

// ===========================================================================
// TopAppBarColors — mirrors M3's TopAppBarColors
// ===========================================================================

@Immutable
class TopAppBarColors(
    val containerColor: Color,
    val scrolledContainerColor: Color,
    val navigationIconContentColor: Color,
    val titleContentColor: Color,
    val actionIconContentColor: Color,
) {
    fun copy(
        containerColor: Color = this.containerColor,
        scrolledContainerColor: Color = this.scrolledContainerColor,
        navigationIconContentColor: Color = this.navigationIconContentColor,
        titleContentColor: Color = this.titleContentColor,
        actionIconContentColor: Color = this.actionIconContentColor,
    ) = TopAppBarColors(containerColor, scrolledContainerColor, navigationIconContentColor, titleContentColor, actionIconContentColor)
}

// ===========================================================================
// TopAppBarDefaults — mirrors M3's TopAppBarDefaults
// ===========================================================================

object TopAppBarDefaults {
    @Composable
    fun topAppBarColors(
        containerColor: Color = DarwinTheme.colorScheme.surface,
        scrolledContainerColor: Color = DarwinTheme.colorScheme.surfaceContainer,
        navigationIconContentColor: Color = DarwinTheme.colorScheme.onSurface,
        titleContentColor: Color = DarwinTheme.colorScheme.onSurface,
        actionIconContentColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
    ) = TopAppBarColors(containerColor, scrolledContainerColor, navigationIconContentColor, titleContentColor, actionIconContentColor)

    @Composable
    fun centerAlignedTopAppBarColors(
        containerColor: Color = DarwinTheme.colorScheme.surface,
        scrolledContainerColor: Color = DarwinTheme.colorScheme.surfaceContainer,
        navigationIconContentColor: Color = DarwinTheme.colorScheme.onSurface,
        titleContentColor: Color = DarwinTheme.colorScheme.onSurface,
        actionIconContentColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
    ) = TopAppBarColors(containerColor, scrolledContainerColor, navigationIconContentColor, titleContentColor, actionIconContentColor)

    @Composable
    fun mediumTopAppBarColors(
        containerColor: Color = DarwinTheme.colorScheme.surface,
        scrolledContainerColor: Color = DarwinTheme.colorScheme.surfaceContainer,
        navigationIconContentColor: Color = DarwinTheme.colorScheme.onSurface,
        titleContentColor: Color = DarwinTheme.colorScheme.onSurface,
        actionIconContentColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
    ) = TopAppBarColors(containerColor, scrolledContainerColor, navigationIconContentColor, titleContentColor, actionIconContentColor)

    @Composable
    fun largeTopAppBarColors(
        containerColor: Color = DarwinTheme.colorScheme.surface,
        scrolledContainerColor: Color = DarwinTheme.colorScheme.surfaceContainer,
        navigationIconContentColor: Color = DarwinTheme.colorScheme.onSurface,
        titleContentColor: Color = DarwinTheme.colorScheme.onSurface,
        actionIconContentColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
    ) = TopAppBarColors(containerColor, scrolledContainerColor, navigationIconContentColor, titleContentColor, actionIconContentColor)
}

// ===========================================================================
// TopAppBar — mirrors M3's TopAppBar
// ===========================================================================

@Composable
fun TopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(colors.containerColor),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CompositionLocalProvider(LocalDarwinContentColor provides colors.navigationIconContentColor) {
                navigationIcon()
            }
            Box(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                CompositionLocalProvider(
                    LocalDarwinContentColor provides colors.titleContentColor,
                    LocalDarwinTextStyle provides DarwinTheme.typography.titleLarge.copy(color = colors.titleContentColor),
                ) { title() }
            }
            CompositionLocalProvider(LocalDarwinContentColor provides colors.actionIconContentColor) {
                Row(content = actions)
            }
        }
    }
}

// ===========================================================================
// CenterAlignedTopAppBar — mirrors M3's CenterAlignedTopAppBar
// ===========================================================================

@Composable
fun CenterAlignedTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(colors.containerColor),
    ) {
        // Center title
        Box(modifier = Modifier.align(Alignment.Center)) {
            CompositionLocalProvider(
                LocalDarwinContentColor provides colors.titleContentColor,
                LocalDarwinTextStyle provides DarwinTheme.typography.titleLarge.copy(color = colors.titleContentColor),
            ) { title() }
        }
        // Left nav
        Box(modifier = Modifier.align(Alignment.CenterStart).padding(start = 4.dp)) {
            CompositionLocalProvider(LocalDarwinContentColor provides colors.navigationIconContentColor) {
                navigationIcon()
            }
        }
        // Right actions
        Row(modifier = Modifier.align(Alignment.CenterEnd).padding(end = 4.dp)) {
            CompositionLocalProvider(LocalDarwinContentColor provides colors.actionIconContentColor) {
                actions()
            }
        }
    }
}

// ===========================================================================
// MediumTopAppBar — mirrors M3's MediumTopAppBar
// ===========================================================================

@Composable
fun MediumTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.mediumTopAppBarColors(),
) {
    androidx.compose.foundation.layout.Column(
        modifier = modifier.fillMaxWidth().background(colors.containerColor),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CompositionLocalProvider(LocalDarwinContentColor provides colors.navigationIconContentColor) { navigationIcon() }
            Box(modifier = Modifier.weight(1f))
            CompositionLocalProvider(LocalDarwinContentColor provides colors.actionIconContentColor) { Row(content = actions) }
        }
        Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 20.dp)) {
            CompositionLocalProvider(
                LocalDarwinContentColor provides colors.titleContentColor,
                LocalDarwinTextStyle provides DarwinTheme.typography.headlineSmall.copy(color = colors.titleContentColor),
            ) { title() }
        }
    }
}

// ===========================================================================
// LargeTopAppBar — mirrors M3's LargeTopAppBar
// ===========================================================================

@Composable
fun LargeTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.largeTopAppBarColors(),
) {
    androidx.compose.foundation.layout.Column(
        modifier = modifier.fillMaxWidth().background(colors.containerColor),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CompositionLocalProvider(LocalDarwinContentColor provides colors.navigationIconContentColor) { navigationIcon() }
            Box(modifier = Modifier.weight(1f))
            CompositionLocalProvider(LocalDarwinContentColor provides colors.actionIconContentColor) { Row(content = actions) }
        }
        Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 28.dp)) {
            CompositionLocalProvider(
                LocalDarwinContentColor provides colors.titleContentColor,
                LocalDarwinTextStyle provides DarwinTheme.typography.displaySmall.copy(color = colors.titleContentColor),
            ) { title() }
        }
    }
}

@Preview
@Composable
private fun TopAppBarPreview() {
    DarwinTheme {
        TopAppBar(title = { Text("Title") })
    }
}
