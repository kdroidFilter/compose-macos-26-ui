package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icons
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosDuration
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Surface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalContentColor
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalSurface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosTween

/**
 * macOS Safari-style address bar.
 *
 * Uses a pill background that adapts to [Surface.ContentArea] (white with
 * border) and [Surface.OverGlass] (translucent with drop shadow).
 * The URL/query text is centered. An optional leading icon (e.g. lock, reader
 * mode) and a trailing "Go" button (shown when [onGo] is provided) complete
 * the layout.
 *
 * @param value Current text value.
 * @param onValueChange Called when the text changes.
 * @param modifier Modifier applied to the outer pill container.
 * @param placeholder Placeholder shown when [value] is empty.
 * @param enabled Whether the field is interactive.
 * @param onGo Called when the user taps the trailing Go button or presses Enter.
 *   Pass `null` to hide the Go button.
 * @param leadingIcon Optional icon composable shown on the left (e.g. lock icon).
 * @param focusRequester Optional [FocusRequester] to control focus programmatically.
 */
@Composable
fun AddressBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search or enter website name",
    enabled: Boolean = true,
    onGo: (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    focusRequester: FocusRequester = remember { FocusRequester() },
) {
    val tfColors = MacosTheme.componentStyling.textField.colors
    val colors = MacosTheme.colorScheme
    val typography = MacosTheme.typography
    val accent = colors.accent
    val isDark = colors.isDark
    val surface = LocalSurface.current
    val isOverGlass = surface == Surface.OverGlass

    val titleBarStyle = LocalTitleBarStyle.current

    var isFocused by remember { mutableStateOf(false) }

    val shape = MacosTheme.shapes.full
    val fieldHeight = titleBarStyle.buttonHeight
    val pillCornerRadius = fieldHeight / 2

    // Icon/button sizes adapt to toolbar style
    val iconSlotSize = fieldHeight - (titleBarStyle.buttonPadding * 2)
    val goIconSize = titleBarStyle.iconSize

    // Background — same logic as SearchField
    val bgColor by animateColorAsState(
        targetValue = when {
            !enabled -> if (isOverGlass) tfColors.overGlassDisabledBackground else tfColors.backgroundDisabled
            isFocused && isOverGlass -> tfColors.overGlassFocusedBackground
            isOverGlass -> tfColors.overGlassBackground
            else -> tfColors.background
        },
        animationSpec = macosTween(MacosDuration.Fast),
        label = "address_bg",
    )

    // Border — Content Area only; Over-glass has no border
    val borderColor by animateColorAsState(
        targetValue = when {
            !enabled -> if (isOverGlass) Color.Transparent else tfColors.borderDisabled
            isOverGlass -> Color.Transparent
            else -> tfColors.border
        },
        animationSpec = macosTween(MacosDuration.Fast),
        label = "address_border",
    )

    val textColor = colors.textPrimary
    val placeholderColor = colors.textSecondary
    val iconColor = colors.textSecondary

    Row(
        modifier = modifier
            .onFocusChanged { isFocused = it.hasFocus }
            .height(fieldHeight)
            // Focus ring
            .then(
                if (isFocused && enabled) {
                    Modifier.addressBarFocusRing(
                        cornerRadius = pillCornerRadius,
                        outerColor = accent.copy(alpha = 0.25f),
                        innerColor = accent.copy(alpha = 0.15f),
                    )
                } else {
                    Modifier
                },
            )
            // Over-glass shadow
            .then(
                if (isOverGlass) {
                    Modifier.addressBarShadow(
                        cornerRadius = pillCornerRadius,
                        shadowAlpha = if (enabled) 0.03f else 0.015f,
                    )
                } else {
                    Modifier
                },
            )
            .clip(shape)
            .background(bgColor, shape)
            .then(
                if (borderColor != Color.Transparent) {
                    Modifier.border(1.dp, borderColor, shape)
                } else {
                    Modifier
                },
            )
            .padding(horizontal = titleBarStyle.buttonPadding + 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Leading icon — fixed width to keep text centered
        Box(
            modifier = Modifier.size(iconSlotSize),
            contentAlignment = Alignment.Center,
        ) {
            if (leadingIcon != null) {
                CompositionLocalProvider(LocalContentColor provides iconColor) {
                    leadingIcon()
                }
            }
        }

        // Center: text field + placeholder overlay
        CenterTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            textColor = textColor,
            placeholderColor = placeholderColor,
            placeholder = placeholder,
            onGo = onGo,
            focusRequester = focusRequester,
        )

        // Trailing Go button — mirrors leading size to keep text centered
        Box(
            modifier = Modifier.size(iconSlotSize),
            contentAlignment = Alignment.Center,
        ) {
            if (onGo != null) {
                GoButton(onClick = onGo, enabled = enabled, isDark = isDark, iconSize = goIconSize)
            }
        }
    }
}

@Composable
private fun RowScope.CenterTextField(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    textColor: Color,
    placeholderColor: Color,
    placeholder: String,
    onGo: (() -> Unit)?,
    focusRequester: FocusRequester,
) {
    val typography = MacosTheme.typography

    Box(
        modifier = Modifier.weight(1f),
        contentAlignment = Alignment.Center,
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .focusRequester(focusRequester)
                .matchParentSize(),
            enabled = enabled,
            singleLine = true,
            textStyle = typography.caption1.copy(
                color = textColor,
                textAlign = TextAlign.Center,
            ),
            cursorBrush = SolidColor(MacosTheme.colorScheme.accent),
            keyboardActions = KeyboardActions(onGo = { onGo?.invoke() }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
        )
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                style = typography.caption1.copy(
                    color = placeholderColor,
                    textAlign = TextAlign.Center,
                ),
            )
        }
    }
}

@Composable
private fun GoButton(
    onClick: () -> Unit,
    enabled: Boolean,
    isDark: Boolean,
    iconSize: androidx.compose.ui.unit.Dp = 11.dp,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val bgColor by animateColorAsState(
        targetValue = when {
            isPressed && enabled -> if (isDark) Color.White.copy(alpha = 0.18f) else Color.Black.copy(alpha = 0.12f)
            isHovered && enabled -> if (isDark) Color.White.copy(alpha = 0.12f) else Color.Black.copy(alpha = 0.08f)
            else -> Color.Transparent
        },
        animationSpec = macosTween(MacosDuration.Fast),
        label = "go_btn_bg",
    )
    val iconColor by animateColorAsState(
        targetValue = if (!enabled) {
            if (isDark) Color.White.copy(alpha = 0.25f) else Color.Black.copy(alpha = 0.20f)
        } else {
            if (isDark) Color.White.copy(alpha = 0.55f) else Color.Black.copy(alpha = 0.40f)
        },
        animationSpec = macosTween(MacosDuration.Fast),
        label = "go_btn_icon",
    )

    val buttonSize = iconSize + 7.dp
    Box(
        modifier = Modifier
            .size(buttonSize)
            .clip(CircleShape)
            .background(bgColor, CircleShape)
            .hoverable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            icon = Icons.ChevronRight,
            tint = iconColor,
            modifier = Modifier.size(iconSize),
        )
    }
}

// macOS-style dual focus ring for pill shape
private fun Modifier.addressBarFocusRing(
    cornerRadius: androidx.compose.ui.unit.Dp,
    outerColor: Color,
    innerColor: Color,
): Modifier = drawBehind {
    val outerStrokePx = 3.5.dp.toPx()
    val innerStrokePx = 1.dp.toPx()
    val cornerPx = cornerRadius.toPx()

    val outerHalf = outerStrokePx / 2f
    drawRoundRect(
        color = outerColor,
        topLeft = Offset(-outerHalf, -outerHalf),
        size = Size(size.width + outerStrokePx, size.height + outerStrokePx),
        cornerRadius = CornerRadius(cornerPx + outerHalf),
        style = Stroke(width = outerStrokePx),
    )

    val innerHalf = innerStrokePx / 2f
    drawRoundRect(
        color = innerColor,
        topLeft = Offset(-innerHalf, -innerHalf),
        size = Size(size.width + innerStrokePx, size.height + innerStrokePx),
        cornerRadius = CornerRadius(cornerPx + innerHalf),
        style = Stroke(width = innerStrokePx),
    )
}

// Over-glass subtle drop shadow
private fun Modifier.addressBarShadow(
    cornerRadius: androidx.compose.ui.unit.Dp,
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
