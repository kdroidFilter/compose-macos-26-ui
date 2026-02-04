package io.github.kdroidfilter.darwinui.components.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.darwinTween
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.glassOrDefault

/**
 * A macOS-inspired multi-line text area mirroring the React darwin-ui TextArea component.
 *
 * Uses the same ring-based border styling as [DarwinTextField] but operates in
 * multi-line mode with configurable [minLines] and [maxLines]. The text area
 * auto-expands as the user types, up to [maxLines].
 *
 * @param value The current text value.
 * @param onValueChange Callback invoked when the text changes.
 * @param modifier Modifier applied to the outer container (label + area + supportingText).
 * @param placeholder Placeholder text shown when [value] is empty.
 * @param label Label text displayed above the text area.
 * @param supportingText Supporting text displayed below the text area (e.g. error messages).
 * @param isError When true, applies a red border and triggers a shake animation.
 * @param isSuccess When true, applies a green border.
 * @param enabled When false, the text area is not editable and appears at reduced opacity.
 * @param minLines Minimum number of visible lines. Defaults to 3.
 * @param maxLines Maximum number of visible lines before scrolling. Defaults to 6.
 * @param glass When true, applies glass-morphism styling instead of the default background.
 * @param keyboardOptions Software keyboard options forwarded to [BasicTextField].
 * @param keyboardActions Software keyboard actions forwarded to [BasicTextField].
 * @param focusRequester Optional [FocusRequester] to control focus programmatically.
 * @param textStyle The text style for the text area content. Defaults to the theme's bodyMedium.
 */
@Composable
fun DarwinTextArea(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    enabled: Boolean = true,
    minLines: Int = 3,
    maxLines: Int = 6,
    glass: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester = remember { FocusRequester() },
    textStyle: TextStyle? = null,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography
    val shapes = DarwinTheme.shapes

    val resolvedTextStyle = textStyle ?: typography.bodyMedium.copy(color = colors.textPrimary)
    val mergedTextStyle = resolvedTextStyle.copy(
        color = if (enabled) resolvedTextStyle.color else colors.textTertiary,
    )

    // ---- Focus state ----
    var isFocused by remember { mutableStateOf(false) }

    // ---- Border color animation ----
    val borderColor by animateColorAsState(
        targetValue = when {
            isError -> colors.destructive
            isSuccess -> colors.success
            isFocused -> colors.inputFocusBorder
            else -> if (glass) colors.glassBorder else colors.inputBorder
        },
        animationSpec = darwinTween(DarwinDuration.Normal),
        label = "DarwinTextAreaBorderColor",
    )

    val resolvedBorderWidth: Dp = when {
        isError || isSuccess || isFocused -> 2.dp
        else -> 1.dp
    }

    // ---- Shake animation for error state ----
    val shakeOffset = remember { Animatable(0f) }

    LaunchedEffect(isError) {
        if (isError) {
            val shakeValues = listOf(6f, -6f, 4f, -4f, 2f, -2f, 0f)
            for (target in shakeValues) {
                shakeOffset.animateTo(
                    targetValue = target,
                    animationSpec = tween(durationMillis = 50),
                )
            }
        } else {
            shakeOffset.snapTo(0f)
        }
    }

    // ---- Background color ----
    val backgroundColor: Color = glassOrDefault(glass, fallback = colors.inputBackground)

    // ---- Layout ----
    Column(modifier = modifier) {
        // Label
        if (label != null) {
            androidx.compose.foundation.text.BasicText(
                text = label,
                style = typography.labelMedium.copy(
                    color = when {
                        isError -> colors.destructive
                        else -> colors.textSecondary
                    },
                ),
                modifier = Modifier.padding(bottom = 6.dp, start = 2.dp),
            )
        }

        // Text area container
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged { isFocused = it.isFocused }
                .graphicsLayer { translationX = shakeOffset.value },
            enabled = enabled,
            textStyle = mergedTextStyle,
            singleLine = false,
            minLines = minLines,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            cursorBrush = SolidColor(colors.inputFocusBorder),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 0.dp)
                        .clip(shapes.medium)
                        .background(backgroundColor)
                        .border(
                            width = resolvedBorderWidth,
                            color = borderColor,
                            shape = shapes.medium,
                        )
                        .then(
                            if (!enabled) Modifier.graphicsLayer { alpha = 0.5f }
                            else Modifier
                        )
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                ) {
                    // Placeholder
                    if (value.isEmpty() && placeholder.isNotEmpty()) {
                        androidx.compose.foundation.text.BasicText(
                            text = placeholder,
                            style = typography.bodyMedium.copy(
                                color = colors.textTertiary,
                            ),
                        )
                    }
                    innerTextField()
                }
            },
        )

        // Supporting text
        if (supportingText != null) {
            androidx.compose.foundation.text.BasicText(
                text = supportingText,
                style = typography.bodySmall.copy(
                    color = when {
                        isError -> colors.destructive
                        isSuccess -> colors.success
                        else -> colors.textTertiary
                    },
                ),
                modifier = Modifier.padding(top = 4.dp, start = 2.dp),
            )
        }
    }
}
