package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icons
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosDuration
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Surface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalContentColor
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalSurface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalTextStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosTween

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester = remember { FocusRequester() },
) {
    val controlSize = LocalControlSize.current
    val tfColors = MacosTheme.componentStyling.textField.colors
    val tfMetrics = MacosTheme.componentStyling.textField.metrics
    val colors = MacosTheme.colorScheme
    val typography = MacosTheme.typography
    val accent = colors.accent
    val surface = LocalSurface.current
    val isOverGlass = surface == Surface.OverGlass

    var isFocused by remember { mutableStateOf(false) }

    val shape = MacosTheme.shapes.full
    val fieldHeight = tfMetrics.heightFor(controlSize)
    val startPad = tfMetrics.startPaddingFor(controlSize)
    val endPad = tfMetrics.endPaddingFor(controlSize)

    // Sketch: XL/Lg/Md = 13sp (footnote), Sm = 11sp (caption2), Mn = 10sp
    val resolvedTextStyle = when (controlSize) {
        ControlSize.ExtraLarge, ControlSize.Large, ControlSize.Regular -> typography.footnote
        ControlSize.Small -> typography.caption2
        ControlSize.Mini -> typography.caption2.copy(fontSize = 10.sp)
    }

    // Sketch icon sizes: Mn glyph ~13dp, Sm-XL glyph ~15dp
    val searchIconSize = when (controlSize) {
        ControlSize.Mini -> 13.dp
        else -> 15.dp
    }

    // Sketch clear button: Mn = 13dp, Sm-XL = 16dp
    val clearButtonSize = when (controlSize) {
        ControlSize.Mini -> 13.dp
        else -> 16.dp
    }

    // Background — reuses TextField component styling
    val backgroundColor by animateColorAsState(
        targetValue = when {
            !enabled -> if (isOverGlass) tfColors.overGlassDisabledBackground else tfColors.backgroundDisabled
            isFocused && isOverGlass -> tfColors.overGlassFocusedBackground
            isOverGlass -> tfColors.overGlassBackground
            else -> tfColors.background
        },
        animationSpec = macosTween(MacosDuration.Fast),
        label = "search_bg",
    )

    // Border — Sketch: 1dp #00000014 idle, #0000000a disabled
    val borderColor by animateColorAsState(
        targetValue = when {
            !enabled -> if (isOverGlass) Color.Transparent else tfColors.borderDisabled
            isOverGlass -> Color.Transparent
            else -> tfColors.border
        },
        animationSpec = macosTween(MacosDuration.Fast),
        label = "search_border",
    )

    // Sketch: magnifying glass = Primary, placeholder = Secondary, clear = Secondary
    val textColor = colors.textPrimary
    val placeholderColor = colors.textSecondary
    val searchIconColor = colors.textPrimary
    val clearButtonColor = colors.textSecondary

    // Focus ring — Sketch: 3.5dp accent@25% + 1dp accent@15%
    val pillCornerRadius = fieldHeight / 2

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { isFocused = it.isFocused },
        enabled = enabled,
        singleLine = singleLine,
        textStyle = resolvedTextStyle.copy(color = textColor),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(accent),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(fieldHeight)
                    .then(
                        if (isFocused && enabled) {
                            Modifier.pillFocusRing(
                                cornerRadius = pillCornerRadius,
                                outerColor = accent.copy(alpha = 0.25f),
                                innerColor = accent.copy(alpha = 0.15f),
                            )
                        } else {
                            Modifier
                        },
                    )
                    // Over-glass shadow — Sketch: 0,0,6 #00000008 idle / #00000004 disabled
                    .then(
                        if (isOverGlass) {
                            Modifier.overGlassPillShadow(
                                cornerRadius = pillCornerRadius,
                                shadowAlpha = if (enabled) 0.03f else 0.015f,
                            )
                        } else {
                            Modifier
                        },
                    )
                    .clip(shape)
                    .background(backgroundColor, shape)
                    .then(
                        if (borderColor != Color.Transparent) {
                            Modifier.border(1.dp, borderColor, shape)
                        } else {
                            Modifier
                        },
                    )
                    .padding(start = startPad, end = endPad),
                contentAlignment = Alignment.CenterStart,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    Icon(
                        icon = Icons.Search,
                        tint = searchIconColor,
                        modifier = Modifier.size(searchIconSize),
                    )
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        if (value.isEmpty()) {
                            CompositionLocalProvider(
                                LocalContentColor provides placeholderColor,
                                LocalTextStyle provides resolvedTextStyle.copy(color = placeholderColor),
                            ) {
                                Text(placeholder)
                            }
                        }
                        CompositionLocalProvider(
                            LocalTextStyle provides resolvedTextStyle.copy(color = textColor),
                        ) {
                            innerTextField()
                        }
                    }
                    // Clear button — visible when value is not empty and enabled
                    if (value.isNotEmpty() && enabled) {
                        SearchClearButton(
                            onClick = { onValueChange("") },
                            tint = clearButtonColor,
                            size = clearButtonSize,
                        )
                    }
                }
            }
        },
    )
}

// Sketch: xmark.circle.fill — filled circle with X cutout, rendered in secondary color
@Composable
private fun SearchClearButton(
    onClick: () -> Unit,
    tint: Color,
    size: Dp,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val bgColor by animateColorAsState(
        targetValue = if (isHovered) tint else tint.copy(alpha = tint.alpha * 0.7f),
        animationSpec = macosTween(MacosDuration.Fast),
        label = "search_clear_bg",
    )

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(bgColor, CircleShape)
            .hoverable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            icon = Icons.X,
            tint = Color.White,
            modifier = Modifier.size(size * 0.6f),
        )
    }
}

// Over-glass subtle drop shadow — Sketch: 0 0 6px black@3% (idle) / black@1.5% (disabled)
private fun Modifier.overGlassPillShadow(
    cornerRadius: Dp,
    shadowAlpha: Float,
): Modifier = drawBehind {
    val cornerPx = cornerRadius.toPx()
    val blurPx = 6.dp.toPx()
    val halfBlur = blurPx / 2f

    drawRoundRect(
        color = Color.Black.copy(alpha = shadowAlpha),
        topLeft = Offset(-halfBlur, -halfBlur),
        size = Size(size.width + blurPx, size.height + blurPx),
        cornerRadius = CornerRadius(cornerPx + halfBlur),
    )
}

// Draws macOS-style dual focus ring for pill-shaped fields
private fun Modifier.pillFocusRing(
    cornerRadius: Dp,
    outerColor: Color,
    innerColor: Color,
): Modifier = drawBehind {
    val outerStrokePx = 3.5.dp.toPx()
    val innerStrokePx = 1.dp.toPx()
    val cornerPx = cornerRadius.toPx()

    // Outer ring — expands beyond component bounds
    val outerHalf = outerStrokePx / 2f
    drawRoundRect(
        color = outerColor,
        topLeft = Offset(-outerHalf, -outerHalf),
        size = Size(size.width + outerStrokePx, size.height + outerStrokePx),
        cornerRadius = CornerRadius(cornerPx + outerHalf),
        style = Stroke(width = outerStrokePx),
    )

    // Inner ring — on the component edge
    val innerHalf = innerStrokePx / 2f
    drawRoundRect(
        color = innerColor,
        topLeft = Offset(-innerHalf, -innerHalf),
        size = Size(size.width + innerStrokePx, size.height + innerStrokePx),
        cornerRadius = CornerRadius(cornerPx + innerHalf),
        style = Stroke(width = innerStrokePx),
    )
}
