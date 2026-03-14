package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.darwinui.theme.*

// ===========================================================================
// TextField — macOS-style text field matching Sketch specs
// ===========================================================================

/**
 * A macOS-style text field with two visual variants driven by [LocalDarwinSurface]:
 *
 * - **ContentArea** (default): White background with subtle border,
 *   matching "Content Area/Input Fields" from Sketch.
 * - **OverGlass**: Translucent background with drop shadow for use on
 *   glass/blurred surfaces, matching "Over-Glass/Input Fields" from Sketch.
 *
 * Supports all 5 macOS control sizes (Mini through ExtraLarge) with
 * Sketch-accurate heights, corner radii, padding, and font sizes.
 *
 * The surface variant is read from [LocalDarwinSurface]. Wrap the text field
 * in [DarwinSurface] to switch appearance:
 * ```kotlin
 * DarwinSurface(DarwinSurface.OverGlass) {
 *     TextField(value = text, onValueChange = { text = it })
 * }
 * ```
 */
@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeholder: (@Composable () -> Unit)? = null,
    label: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    supportingText: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusRequester: FocusRequester = remember { FocusRequester() },
) {
    val colors = DarwinTheme.componentStyling.textField.colors
    val metrics = DarwinTheme.componentStyling.textField.metrics
    val typography = DarwinTheme.typography
    val accent = DarwinTheme.colorScheme.accent
    val controlSize = LocalControlSize.current
    val surface = LocalDarwinSurface.current
    val isOverGlass = surface == DarwinSurface.OverGlass

    val fieldHeight = metrics.heightFor(controlSize)
    val cornerRadius = metrics.cornerRadiusFor(controlSize)
    val startPad = metrics.startPaddingFor(controlSize)
    val endPad = metrics.endPaddingFor(controlSize)
    val shape = RoundedCornerShape(cornerRadius)

    // Text style per control size — Sketch: XL/Lg/Md=13sp, Sm=11sp, Mn=10sp
    val resolvedTextStyle = when (controlSize) {
        ControlSize.ExtraLarge, ControlSize.Large, ControlSize.Regular -> typography.caption1
        ControlSize.Small -> typography.caption2
        ControlSize.Mini -> typography.caption2.copy(fontSize = 10.sp)
    }

    var isFocused by remember { mutableStateOf(false) }

    // Resolve background per surface variant
    val backgroundColor by animateColorAsState(
        targetValue = when {
            !enabled -> if (isOverGlass) colors.overGlassDisabledBackground else colors.backgroundDisabled
            isFocused && isOverGlass -> colors.overGlassFocusedBackground
            isOverGlass -> colors.overGlassBackground
            else -> colors.background
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "tf_bg",
    )

    // Border (Content Area only; Over-glass has no border when idle)
    val borderColor by animateColorAsState(
        targetValue = when {
            isError -> colors.errorBorder
            !enabled -> if (isOverGlass) Color.Transparent else colors.borderDisabled
            isOverGlass -> Color.Transparent
            else -> colors.border
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "tf_border",
    )

    val textColor = if (enabled) colors.text else colors.textDisabled
    val cursorBrush = SolidColor(if (isError) colors.errorBorder else colors.cursor)

    // Focus ring colors — Sketch: 3.5dp accent@25%, 1dp accent@15%
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
            readOnly = readOnly,
            textStyle = resolvedTextStyle.copy(color = textColor),
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            cursorBrush = cursorBrush,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(fieldHeight)
                        // Focus ring — drawn behind the component
                        .then(
                            if (isFocused && enabled && !isError) {
                                Modifier.macOsFocusRing(
                                    cornerRadius = cornerRadius,
                                    outerColor = focusRingOuterColor,
                                    innerColor = focusRingInnerColor,
                                )
                            } else if (isError) {
                                Modifier.macOsFocusRing(
                                    cornerRadius = cornerRadius,
                                    outerColor = colors.errorBorder.copy(alpha = 0.25f),
                                    innerColor = colors.errorBorder.copy(alpha = 0.15f),
                                )
                            } else {
                                Modifier
                            },
                        )
                        // Over-glass shadow
                        .then(
                            if (isOverGlass) {
                                Modifier.overGlassShadow(
                                    cornerRadius = cornerRadius,
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
                        .then(if (!enabled) Modifier.graphicsLayer { alpha = 0.5f } else Modifier)
                        .padding(start = startPad, end = endPad),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (leadingIcon != null) {
                            Box(modifier = Modifier.padding(end = 4.dp), contentAlignment = Alignment.Center) {
                                CompositionLocalProvider(
                                    LocalDarwinContentColor provides (if (enabled) colors.icon else colors.iconDisabled),
                                ) { leadingIcon() }
                            }
                        }
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                            if (value.isEmpty() && placeholder != null) {
                                CompositionLocalProvider(
                                    LocalDarwinContentColor provides colors.placeholder,
                                    LocalDarwinTextStyle provides resolvedTextStyle.copy(color = colors.placeholder),
                                ) { placeholder() }
                            }
                            innerTextField()
                        }
                        if (trailingIcon != null) {
                            Box(modifier = Modifier.padding(start = 4.dp), contentAlignment = Alignment.Center) {
                                CompositionLocalProvider(
                                    LocalDarwinContentColor provides (if (enabled) colors.icon else colors.iconDisabled),
                                ) { trailingIcon() }
                            }
                        }
                    }
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

// ===========================================================================
// macOS Focus Ring modifier
// ===========================================================================

/**
 * Draws the macOS-style dual focus ring behind the composable.
 * Sketch: 3.5dp outer ring (accent@25%) + 1dp inner ring (accent@15%).
 */
private fun Modifier.macOsFocusRing(
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

// ===========================================================================
// Over-glass shadow modifier
// ===========================================================================

/**
 * Draws a subtle drop shadow for the Over-glass variant.
 * Sketch: shadow 0 0 6px #00000008 (idle) / #00000004 (disabled).
 */
private fun Modifier.overGlassShadow(
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
