package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import io.github.kdroidfilter.darwinui.theme.darwinTween

// ===========================================================================
// ChipColors — mirrors M3's ChipColors
// ===========================================================================

@Immutable
class ChipColors(
    val containerColor: Color,
    val labelColor: Color,
    val leadingIconContentColor: Color,
    val trailingIconContentColor: Color,
    val disabledContainerColor: Color,
    val disabledLabelColor: Color,
    val disabledLeadingIconContentColor: Color,
    val disabledTrailingIconContentColor: Color,
)

// ===========================================================================
// SelectableChipColors — mirrors M3's SelectableChipColors
// ===========================================================================

@Immutable
class SelectableChipColors(
    val containerColor: Color,
    val labelColor: Color,
    val leadingIconColor: Color,
    val trailingIconColor: Color,
    val disabledContainerColor: Color,
    val disabledLabelColor: Color,
    val disabledLeadingIconColor: Color,
    val disabledTrailingIconColor: Color,
    val selectedContainerColor: Color,
    val disabledSelectedContainerColor: Color,
    val selectedLabelColor: Color,
    val selectedLeadingIconColor: Color,
    val selectedTrailingIconColor: Color,
)

// ===========================================================================
// ChipDefaults — mirrors M3's ChipDefaults
// ===========================================================================

object ChipDefaults {
    @Composable
    fun assistChipColors(
        containerColor: Color = Color.Transparent,
        labelColor: Color = DarwinTheme.colorScheme.onSurface,
        leadingIconContentColor: Color = DarwinTheme.colorScheme.primary,
        trailingIconContentColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        disabledContainerColor: Color = Color.Transparent,
        disabledLabelColor: Color = labelColor.copy(alpha = 0.38f),
        disabledLeadingIconContentColor: Color = leadingIconContentColor.copy(alpha = 0.38f),
        disabledTrailingIconContentColor: Color = trailingIconContentColor.copy(alpha = 0.38f),
    ) = ChipColors(containerColor, labelColor, leadingIconContentColor, trailingIconContentColor, disabledContainerColor, disabledLabelColor, disabledLeadingIconContentColor, disabledTrailingIconContentColor)

    @Composable
    fun chipColors(
        containerColor: Color = DarwinTheme.colorScheme.surfaceContainerLow,
        labelColor: Color = DarwinTheme.colorScheme.onSurface,
        leadingIconContentColor: Color = DarwinTheme.colorScheme.primary,
        trailingIconContentColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        disabledContainerColor: Color = containerColor.copy(alpha = 0.12f),
        disabledLabelColor: Color = labelColor.copy(alpha = 0.38f),
        disabledLeadingIconContentColor: Color = leadingIconContentColor.copy(alpha = 0.38f),
        disabledTrailingIconContentColor: Color = trailingIconContentColor.copy(alpha = 0.38f),
    ) = ChipColors(containerColor, labelColor, leadingIconContentColor, trailingIconContentColor, disabledContainerColor, disabledLabelColor, disabledLeadingIconContentColor, disabledTrailingIconContentColor)

    @Composable
    fun filterChipColors(
        containerColor: Color = Color.Transparent,
        labelColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        iconColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        disabledContainerColor: Color = Color.Transparent,
        disabledLabelColor: Color = labelColor.copy(alpha = 0.38f),
        disabledLeadingIconColor: Color = iconColor.copy(alpha = 0.38f),
        disabledTrailingIconColor: Color = iconColor.copy(alpha = 0.38f),
        selectedContainerColor: Color = DarwinTheme.colorScheme.secondaryContainer,
        disabledSelectedContainerColor: Color = selectedContainerColor.copy(alpha = 0.12f),
        selectedLabelColor: Color = DarwinTheme.colorScheme.onSecondaryContainer,
        selectedLeadingIconColor: Color = DarwinTheme.colorScheme.onSecondaryContainer,
        selectedTrailingIconColor: Color = DarwinTheme.colorScheme.onSecondaryContainer,
    ) = SelectableChipColors(containerColor, labelColor, iconColor, iconColor, disabledContainerColor, disabledLabelColor, disabledLeadingIconColor, disabledTrailingIconColor, selectedContainerColor, disabledSelectedContainerColor, selectedLabelColor, selectedLeadingIconColor, selectedTrailingIconColor)

    @Composable
    fun inputChipColors(
        containerColor: Color = Color.Transparent,
        labelColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        leadingIconColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        trailingIconColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        disabledContainerColor: Color = Color.Transparent,
        disabledLabelColor: Color = labelColor.copy(alpha = 0.38f),
        disabledLeadingIconColor: Color = leadingIconColor.copy(alpha = 0.38f),
        disabledTrailingIconColor: Color = trailingIconColor.copy(alpha = 0.38f),
        selectedContainerColor: Color = DarwinTheme.colorScheme.secondaryContainer,
        disabledSelectedContainerColor: Color = selectedContainerColor.copy(alpha = 0.12f),
        selectedLabelColor: Color = DarwinTheme.colorScheme.onSecondaryContainer,
        selectedLeadingIconColor: Color = DarwinTheme.colorScheme.onSecondaryContainer,
        selectedTrailingIconColor: Color = DarwinTheme.colorScheme.onSecondaryContainer,
    ) = SelectableChipColors(containerColor, labelColor, leadingIconColor, trailingIconColor, disabledContainerColor, disabledLabelColor, disabledLeadingIconColor, disabledTrailingIconColor, selectedContainerColor, disabledSelectedContainerColor, selectedLabelColor, selectedLeadingIconColor, selectedTrailingIconColor)

    @Composable
    fun suggestionChipColors(
        containerColor: Color = Color.Transparent,
        labelColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        iconContentColor: Color = DarwinTheme.colorScheme.primary,
        disabledContainerColor: Color = Color.Transparent,
        disabledLabelColor: Color = labelColor.copy(alpha = 0.38f),
        disabledIconContentColor: Color = iconContentColor.copy(alpha = 0.38f),
    ) = ChipColors(containerColor, labelColor, iconContentColor, iconContentColor, disabledContainerColor, disabledLabelColor, disabledIconContentColor, disabledIconContentColor)

    @Composable
    fun assistChipBorder(enabled: Boolean = true): BorderStroke = BorderStroke(1.dp, DarwinTheme.colorScheme.outline.copy(alpha = if (enabled) 1f else 0.12f))

    @Composable
    fun filterChipBorder(enabled: Boolean = true, selected: Boolean = false): BorderStroke? =
        if (!selected) BorderStroke(1.dp, DarwinTheme.colorScheme.outline.copy(alpha = if (enabled) 1f else 0.12f)) else null

    @Composable
    fun inputChipBorder(enabled: Boolean = true, selected: Boolean = false): BorderStroke? =
        if (!selected) BorderStroke(1.dp, DarwinTheme.colorScheme.outline.copy(alpha = if (enabled) 1f else 0.12f)) else null
}

// ===========================================================================
// Internal chip layout
// ===========================================================================

@Composable
private fun ChipLayout(
    onClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    shape: Shape,
    containerColor: Color,
    labelColor: Color,
    border: BorderStroke?,
    leadingContent: (@Composable () -> Unit)?,
    trailingContent: (@Composable () -> Unit)?,
    interactionSource: MutableInteractionSource,
    label: @Composable () -> Unit,
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    // Animated container color for smooth state transitions
    val animatedContainerColor by animateColorAsState(
        targetValue = containerColor,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "chipContainerColor",
    )

    // Animated label color
    val animatedLabelColor by animateColorAsState(
        targetValue = if (enabled) labelColor else labelColor.copy(0.38f),
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "chipLabelColor",
    )

    // iOS-style press scale
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.95f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "chipPressScale",
    )

    // Animated hover overlay
    val hoverOverlay by animateColorAsState(
        targetValue = if (isHovered && enabled) {
            if (DarwinTheme.colorScheme.isDark) Color.White.copy(0.08f) else Color.Black.copy(0.06f)
        } else Color.Transparent,
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "chipHoverOverlay",
    )

    CompositionLocalProvider(
        LocalDarwinContentColor provides animatedLabelColor,
        LocalDarwinTextStyle provides DarwinTheme.typography.labelLarge.copy(
            color = animatedLabelColor,
        ),
    ) {
        Row(
            modifier = modifier
                .scale(pressScale)
                .alpha(if (enabled) 1f else 0.38f)
                .clip(shape)
                .background(animatedContainerColor, shape)
                .then(if (border != null) Modifier.border(border.width, border.brush, shape) else Modifier)
                .background(hoverOverlay, shape)
                .hoverable(interactionSource, enabled)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    onClickLabel = null,
                    role = Role.Button,
                    onClick = onClick,
                )
                .defaultMinSize(minHeight = 32.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (leadingContent != null) {
                Box(modifier = Modifier.size(18.dp)) { leadingContent() }
            }
            label()
            if (trailingContent != null) {
                Box(modifier = Modifier.size(18.dp)) { trailingContent() }
            }
        }
    }
}

// ===========================================================================
// AssistChip — mirrors M3's AssistChip
// ===========================================================================

@Composable
fun AssistChip(
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    shape: Shape = DarwinTheme.shapes.extraSmall,
    colors: ChipColors = ChipDefaults.assistChipColors(),
    border: BorderStroke? = ChipDefaults.assistChipBorder(enabled),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    ChipLayout(onClick, modifier, enabled, shape, if (enabled) colors.containerColor else colors.disabledContainerColor, if (enabled) colors.labelColor else colors.disabledLabelColor, border, leadingIcon, trailingIcon, interactionSource, label)
}

// ===========================================================================
// FilterChip — mirrors M3's FilterChip
// ===========================================================================

@Composable
fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    shape: Shape = DarwinTheme.shapes.extraSmall,
    colors: SelectableChipColors = ChipDefaults.filterChipColors(),
    border: BorderStroke? = ChipDefaults.filterChipBorder(enabled, selected),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val containerColor = when {
        !enabled && selected -> colors.disabledSelectedContainerColor
        !enabled -> colors.disabledContainerColor
        selected -> colors.selectedContainerColor
        else -> colors.containerColor
    }
    val labelColor = when {
        !enabled -> colors.disabledLabelColor
        selected -> colors.selectedLabelColor
        else -> colors.labelColor
    }
    ChipLayout(onClick, modifier, enabled, shape, containerColor, labelColor, border, leadingIcon, trailingIcon, interactionSource, label)
}

// ===========================================================================
// InputChip — mirrors M3's InputChip
// ===========================================================================

@Composable
fun InputChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    avatar: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    shape: Shape = DarwinTheme.shapes.extraSmall,
    colors: SelectableChipColors = ChipDefaults.inputChipColors(),
    border: BorderStroke? = ChipDefaults.inputChipBorder(enabled, selected),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val containerColor = when {
        !enabled && selected -> colors.disabledSelectedContainerColor
        !enabled -> colors.disabledContainerColor
        selected -> colors.selectedContainerColor
        else -> colors.containerColor
    }
    val labelColor = when {
        !enabled -> colors.disabledLabelColor
        selected -> colors.selectedLabelColor
        else -> colors.labelColor
    }
    val leading = avatar ?: leadingIcon
    ChipLayout(onClick, modifier, enabled, shape, containerColor, labelColor, border, leading, trailingIcon, interactionSource, label)
}

// ===========================================================================
// SuggestionChip — mirrors M3's SuggestionChip
// ===========================================================================

@Composable
fun SuggestionChip(
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: (@Composable () -> Unit)? = null,
    shape: Shape = DarwinTheme.shapes.extraSmall,
    colors: ChipColors = ChipDefaults.suggestionChipColors(),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    ChipLayout(onClick, modifier, enabled, shape, if (enabled) colors.containerColor else colors.disabledContainerColor, if (enabled) colors.labelColor else colors.disabledLabelColor, border, icon, null, interactionSource, label)
}

@Preview
@Composable
private fun ChipPreview() {
    DarwinTheme {
        AssistChip(onClick = {}, label = { Text("Chip") })
    }
}
