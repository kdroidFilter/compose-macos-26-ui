package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.darwinui.theme.*

/**
 * A macOS-style multiline text area using the same visual language as [TextField].
 *
 * Reads [LocalDarwinSurface] for Content Area vs Over-glass appearance,
 * and [LocalControlSize] for font size and corner radius.
 *
 * @param value Current text value.
 * @param onValueChange Callback when the text changes.
 * @param modifier Modifier applied to the outer container.
 * @param placeholder Placeholder composable shown when value is empty.
 * @param label Optional label shown above the field.
 * @param supportingText Optional text shown below the field.
 * @param isError When true, shows error styling.
 * @param enabled Whether the field is interactive.
 * @param minLines Minimum number of visible lines.
 * @param maxLines Maximum number of visible lines.
 * @param keyboardOptions Keyboard configuration.
 * @param keyboardActions Keyboard action handlers.
 * @param focusRequester Focus requester for programmatic focus control.
 */
@Composable
fun TextArea(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: (@Composable () -> Unit)? = null,
    label: (@Composable () -> Unit)? = null,
    supportingText: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    minLines: Int = 3,
    maxLines: Int = 6,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester = remember { FocusRequester() },
) {
    val colors = DarwinTheme.componentStyling.textField.colors
    val metrics = DarwinTheme.componentStyling.textField.metrics
    val typography = DarwinTheme.typography
    val accent = DarwinTheme.colorScheme.accent
    val controlSize = LocalControlSize.current
    val surface = LocalDarwinSurface.current
    val isOverGlass = surface == DarwinSurface.OverGlass

    val cornerRadius = metrics.cornerRadiusFor(controlSize)
    val startPad = metrics.startPaddingFor(controlSize)
    val endPad = metrics.endPaddingFor(controlSize)
    val shape = RoundedCornerShape(cornerRadius)

    // Text style per control size — same as TextField
    val resolvedTextStyle = when (controlSize) {
        ControlSize.ExtraLarge, ControlSize.Large, ControlSize.Regular -> typography.caption1
        ControlSize.Small -> typography.caption2
        ControlSize.Mini -> typography.caption2.copy(fontSize = 10.sp)
    }

    var isFocused by remember { mutableStateOf(false) }

    // Background per surface variant — same logic as TextField
    val backgroundColor by animateColorAsState(
        targetValue = when {
            !enabled -> if (isOverGlass) colors.overGlassDisabledBackground else colors.backgroundDisabled
            isFocused && isOverGlass -> colors.overGlassFocusedBackground
            isOverGlass -> colors.overGlassBackground
            else -> colors.background
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "ta_bg",
    )

    // Border — same logic as TextField
    val borderColor by animateColorAsState(
        targetValue = when {
            isError -> colors.errorBorder
            !enabled -> if (isOverGlass) Color.Transparent else colors.borderDisabled
            isOverGlass -> Color.Transparent
            else -> colors.border
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "ta_border",
    )

    val textColor = if (enabled) colors.text else colors.textDisabled
    val cursorBrush = SolidColor(if (isError) colors.errorBorder else colors.cursor)

    // Focus ring colors — same as TextField
    val focusRingOuterColor = accent.copy(alpha = 0.25f)
    val focusRingInnerColor = accent.copy(alpha = 0.15f)

    // Error shake
    val shakeOffset = remember { Animatable(0f) }
    LaunchedEffect(isError) {
        if (isError) {
            for (target in listOf(6f, -6f, 4f, -4f, 2f, -2f, 0f)) {
                shakeOffset.animateTo(target, tween(50))
            }
        } else {
            shakeOffset.snapTo(0f)
        }
    }

    Column(modifier = modifier) {
        // Label
        if (label != null) {
            Box(modifier = Modifier.padding(bottom = metrics.labelBottomPadding, start = 2.dp)) {
                CompositionLocalProvider(
                    LocalDarwinContentColor provides (if (enabled) colors.label else colors.labelDisabled),
                    LocalDarwinTextStyle provides typography.caption1.copy(
                        color = if (enabled) colors.label else colors.labelDisabled,
                    ),
                ) { label() }
            }
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged { isFocused = it.isFocused }
                .graphicsLayer { translationX = shakeOffset.value },
            enabled = enabled,
            textStyle = resolvedTextStyle.copy(color = textColor),
            singleLine = false,
            minLines = minLines,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            cursorBrush = cursorBrush,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        // Focus ring
                        .then(
                            if (isFocused && enabled && !isError) {
                                Modifier.textFieldFocusRing(cornerRadius, focusRingOuterColor, focusRingInnerColor)
                            } else if (isError) {
                                Modifier.textFieldFocusRing(
                                    cornerRadius,
                                    colors.errorBorder.copy(alpha = 0.25f),
                                    colors.errorBorder.copy(alpha = 0.15f),
                                )
                            } else {
                                Modifier
                            },
                        )
                        // Over-glass shadow
                        .then(
                            if (isOverGlass) {
                                Modifier.textAreaOverGlassShadow(cornerRadius, if (enabled) 0.03f else 0.015f)
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
                        .then(if (!enabled) Modifier.graphicsLayer { alpha = 0.5f } else Modifier)
                        .padding(start = startPad, end = endPad, top = 8.dp, bottom = 8.dp),
                ) {
                    if (value.isEmpty() && placeholder != null) {
                        CompositionLocalProvider(
                            LocalDarwinContentColor provides colors.placeholder,
                            LocalDarwinTextStyle provides resolvedTextStyle.copy(color = colors.placeholder),
                        ) { placeholder() }
                    }
                    innerTextField()
                }
            },
        )

        // Supporting text
        if (supportingText != null) {
            val supportingColor = if (isError) colors.errorBorder else colors.placeholder
            Box(modifier = Modifier.padding(top = metrics.supportingTextTopPadding, start = 2.dp)) {
                CompositionLocalProvider(
                    LocalDarwinContentColor provides supportingColor,
                    LocalDarwinTextStyle provides typography.caption2.copy(color = supportingColor),
                ) { supportingText() }
            }
        }
    }
}

// Focus ring — reuses the same dual-ring pattern as TextField
private fun Modifier.textFieldFocusRing(
    cornerRadius: Dp,
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

private fun Modifier.textAreaOverGlassShadow(
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
