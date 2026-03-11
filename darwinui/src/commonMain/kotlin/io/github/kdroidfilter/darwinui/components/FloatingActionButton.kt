package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.icons.LucideCheck
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor

object FloatingActionButtonDefaults {
    val containerColor: Color @Composable get() = DarwinTheme.colorScheme.primaryContainer
    val contentColor: Color @Composable get() = DarwinTheme.colorScheme.onPrimaryContainer
    val largeContainerColor: Color @Composable get() = DarwinTheme.colorScheme.primaryContainer
    val smallContainerColor: Color @Composable get() = DarwinTheme.colorScheme.primaryContainer
}

@Composable
fun FloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = DarwinTheme.shapes.extraLarge,
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    contentColor: Color = FloatingActionButtonDefaults.contentColor,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val hoverOverlay = if (isHovered) Color.White.copy(alpha = 0.08f) else Color.Transparent

    CompositionLocalProvider(LocalDarwinContentColor provides contentColor) {
        Box(
            modifier = modifier
                .shadow(elevation = 6.dp, shape = shape, clip = false)
                .clip(shape)
                .background(containerColor, shape)
                .background(hoverOverlay, shape)
                .hoverable(interactionSource)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = true,
                    onClickLabel = null,
                    role = Role.Button,
                    onClick = onClick,
                )
                .size(56.dp),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}

@Composable
fun SmallFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = DarwinTheme.shapes.extraLarge,
    containerColor: Color = FloatingActionButtonDefaults.smallContainerColor,
    contentColor: Color = FloatingActionButtonDefaults.contentColor,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val hoverOverlay = if (isHovered) Color.White.copy(alpha = 0.08f) else Color.Transparent

    CompositionLocalProvider(LocalDarwinContentColor provides contentColor) {
        Box(
            modifier = modifier
                .shadow(elevation = 4.dp, shape = shape, clip = false)
                .clip(shape)
                .background(containerColor, shape)
                .background(hoverOverlay, shape)
                .hoverable(interactionSource)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = true,
                    onClickLabel = null,
                    role = Role.Button,
                    onClick = onClick,
                )
                .size(40.dp),
            contentAlignment = Alignment.Center,
        ) { content() }
    }
}

@Composable
fun LargeFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = DarwinTheme.shapes.extraLarge,
    containerColor: Color = FloatingActionButtonDefaults.largeContainerColor,
    contentColor: Color = FloatingActionButtonDefaults.contentColor,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val hoverOverlay = if (isHovered) Color.White.copy(alpha = 0.08f) else Color.Transparent

    CompositionLocalProvider(LocalDarwinContentColor provides contentColor) {
        Box(
            modifier = modifier
                .shadow(elevation = 6.dp, shape = shape, clip = false)
                .clip(shape)
                .background(containerColor, shape)
                .background(hoverOverlay, shape)
                .hoverable(interactionSource)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = true,
                    onClickLabel = null,
                    role = Role.Button,
                    onClick = onClick,
                )
                .size(96.dp),
            contentAlignment = Alignment.Center,
        ) { content() }
    }
}

@Composable
fun ExtendedFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = DarwinTheme.shapes.full,
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    contentColor: Color = FloatingActionButtonDefaults.contentColor,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    expanded: Boolean = true,
    icon: (@Composable () -> Unit)? = null,
    text: @Composable () -> Unit,
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val hoverOverlay = if (isHovered) Color.White.copy(alpha = 0.08f) else Color.Transparent

    CompositionLocalProvider(LocalDarwinContentColor provides contentColor) {
        Row(
            modifier = modifier
                .shadow(elevation = 6.dp, shape = shape, clip = false)
                .clip(shape)
                .background(containerColor, shape)
                .background(hoverOverlay, shape)
                .hoverable(interactionSource)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = true,
                    onClickLabel = null,
                    role = Role.Button,
                    onClick = onClick,
                )
                .defaultMinSize(minHeight = 56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (icon != null) { icon() }
            AnimatedVisibility(visible = expanded, enter = expandHorizontally(), exit = shrinkHorizontally()) {
                text()
            }
        }
    }
}

@Preview
@Composable
private fun FabPreview() {
    DarwinTheme {
        FloatingActionButton(onClick = {}) {
            io.github.kdroidfilter.darwinui.icons.Icon(LucideCheck, tint = FloatingActionButtonDefaults.contentColor)
        }
    }
}
