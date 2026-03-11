package io.github.kdroidfilter.darwinui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.icons.LucideX
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor

// ===========================================================================
// IconButtonColors — mirrors M3's IconButtonColors
// ===========================================================================

@Immutable
class IconButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
) {
    fun copy(
        containerColor: Color = this.containerColor,
        contentColor: Color = this.contentColor,
        disabledContainerColor: Color = this.disabledContainerColor,
        disabledContentColor: Color = this.disabledContentColor,
    ) = IconButtonColors(containerColor, contentColor, disabledContainerColor, disabledContentColor)
}

// ===========================================================================
// IconButtonDefaults — mirrors M3's IconButtonDefaults
// ===========================================================================

object IconButtonDefaults {
    @Composable
    fun iconButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = DarwinTheme.colorScheme.onSurface,
        disabledContainerColor: Color = Color.Transparent,
        disabledContentColor: Color = contentColor.copy(alpha = 0.38f),
    ) = IconButtonColors(containerColor, contentColor, disabledContainerColor, disabledContentColor)

    @Composable
    fun filledIconButtonColors(
        containerColor: Color = DarwinTheme.colorScheme.primary,
        contentColor: Color = DarwinTheme.colorScheme.onPrimary,
        disabledContainerColor: Color = containerColor.copy(alpha = 0.12f),
        disabledContentColor: Color = contentColor.copy(alpha = 0.38f),
    ) = IconButtonColors(containerColor, contentColor, disabledContainerColor, disabledContentColor)

    @Composable
    fun outlinedIconButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        disabledContainerColor: Color = Color.Transparent,
        disabledContentColor: Color = contentColor.copy(alpha = 0.38f),
    ) = IconButtonColors(containerColor, contentColor, disabledContainerColor, disabledContentColor)
}

// ===========================================================================
// IconButton — mirrors M3's IconButton
// ===========================================================================

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val containerColor = if (enabled) colors.containerColor else colors.disabledContainerColor
    val contentColor = if (enabled) colors.contentColor else colors.disabledContentColor
    val hoverOverlay = if (isHovered && enabled) {
        if (DarwinTheme.colorScheme.isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.06f)
    } else Color.Transparent

    val shape = DarwinTheme.shapes.extraLarge

    CompositionLocalProvider(LocalDarwinContentColor provides contentColor) {
        Box(
            modifier = modifier
                .size(40.dp)
                .alpha(if (enabled) 1f else 0.38f)
                .clip(shape)
                .background(containerColor, shape)
                .background(hoverOverlay, shape)
                .hoverable(interactionSource = interactionSource, enabled = enabled)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick,
                ),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}

// ===========================================================================
// FilledIconButton — mirrors M3's FilledIconButton
// ===========================================================================

@Composable
fun FilledIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = DarwinTheme.shapes.extraLarge,
    colors: IconButtonColors = IconButtonDefaults.filledIconButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val containerColor = if (enabled) colors.containerColor else colors.disabledContainerColor
    val contentColor = if (enabled) colors.contentColor else colors.disabledContentColor
    val hoverOverlay = if (isHovered && enabled) Color.White.copy(alpha = 0.08f) else Color.Transparent

    CompositionLocalProvider(LocalDarwinContentColor provides contentColor) {
        Box(
            modifier = modifier
                .size(40.dp)
                .alpha(if (enabled) 1f else 0.38f)
                .clip(shape)
                .background(containerColor, shape)
                .background(hoverOverlay, shape)
                .hoverable(interactionSource, enabled)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    onClickLabel = null,
                    role = Role.Button,
                    onClick = onClick,
                ),
            contentAlignment = Alignment.Center,
        ) { content() }
    }
}

// ===========================================================================
// OutlinedIconButton — mirrors M3's OutlinedIconButton
// ===========================================================================

@Composable
fun OutlinedIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = DarwinTheme.shapes.extraLarge,
    colors: IconButtonColors = IconButtonDefaults.outlinedIconButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val containerColor = if (enabled) colors.containerColor else colors.disabledContainerColor
    val contentColor = if (enabled) colors.contentColor else colors.disabledContentColor
    val hoverOverlay = if (isHovered && enabled) {
        if (DarwinTheme.colorScheme.isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.06f)
    } else Color.Transparent
    val borderColor = if (enabled) DarwinTheme.colorScheme.outline else DarwinTheme.colorScheme.outline.copy(alpha = 0.5f)

    CompositionLocalProvider(LocalDarwinContentColor provides contentColor) {
        Box(
            modifier = modifier
                .size(40.dp)
                .alpha(if (enabled) 1f else 0.38f)
                .clip(shape)
                .background(containerColor, shape)
                .border(1.dp, borderColor, shape)
                .background(hoverOverlay, shape)
                .hoverable(interactionSource, enabled)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    onClickLabel = null,
                    role = Role.Button,
                    onClick = onClick,
                ),
            contentAlignment = Alignment.Center,
        ) { content() }
    }
}

@Preview
@Composable
private fun IconButtonPreview() {
    DarwinTheme {
        IconButton(onClick = {}) {
            io.github.kdroidfilter.darwinui.icons.Icon(LucideX)
        }
    }
}
