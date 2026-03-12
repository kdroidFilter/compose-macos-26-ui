package io.github.kdroidfilter.darwinui.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.Outline
import io.github.kdroidfilter.darwinui.theme.darwinTween
import io.github.kdroidfilter.darwinui.theme.focusOrValidationOutline

@Composable
fun TextArea(
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
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester = remember { FocusRequester() },
    textStyle: TextStyle? = null,
    outline: Outline = Outline.None,
) {
    val colors = DarwinTheme.colorScheme
    val typography = DarwinTheme.typography
    val shapes = DarwinTheme.shapes
    val outlines = DarwinTheme.globalColors.outlines

    val resolvedTextStyle = textStyle ?: typography.subheadline.copy(color = colors.textPrimary)
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
            else -> colors.inputBorder
        },
        animationSpec = darwinTween(DarwinDuration.Normal),
        label = "DarwinTextAreaBorderColor",
    )

    val resolvedBorderWidth: Dp = when {
        isFocused -> 2.dp
        isError || isSuccess -> 1.dp
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
    val backgroundColor: Color = colors.inputBackground

    // ---- Layout ----
    Column(modifier = modifier) {
        // Label
        if (label != null) {
            androidx.compose.foundation.text.BasicText(
                text = label,
                style = typography.caption1.copy(
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
                // Outer unclipped box carries the focus/validation outline ring (Phase 1.4)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 0.dp)
                        .focusOrValidationOutline(isFocused, outline, shapes.small, outlines),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shapes.small)
                            .background(backgroundColor)
                            .border(
                                width = resolvedBorderWidth,
                                color = borderColor,
                                shape = shapes.small,
                            )
                            .then(
                                if (!enabled) Modifier.graphicsLayer { alpha = 0.5f }
                                else Modifier
                            )
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                    ) {
                        if (value.isEmpty() && placeholder.isNotEmpty()) {
                            androidx.compose.foundation.text.BasicText(
                                text = placeholder,
                                style = typography.subheadline.copy(
                                    color = colors.textTertiary,
                                ),
                            )
                        }
                        innerTextField()
                    }
                }
            },
        )

        // Supporting text
        if (supportingText != null) {
            androidx.compose.foundation.text.BasicText(
                text = supportingText,
                style = typography.caption1.copy(
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

@Preview
@Composable
private fun TextAreaPreview() {
    DarwinTheme {
        var text by remember { mutableStateOf("") }
        TextArea(value = text, onValueChange = { text = it }, placeholder = "Enter description…", label = "Description")
    }
}
