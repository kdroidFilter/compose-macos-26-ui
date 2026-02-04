package io.github.kdroidfilter.darwinui.components.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.darwinTween
import io.github.kdroidfilter.darwinui.theme.DarwinDuration

/**
 * Size variants for [DarwinTextField].
 *
 * @property height The fixed height of the input field.
 */
enum class DarwinInputSize(val height: Dp) {
    /** Small: 32 dp */
    Sm(32.dp),

    /** Medium (default): 40 dp */
    Md(40.dp),

    /** Large: 48 dp */
    Lg(48.dp),
}

/**
 * A macOS-inspired text input field mirroring the React darwin-ui Input component.
 *
 * The component uses ring-based borders that transition between idle, focused, error,
 * and success states, matching the Tailwind CSS-based styling of the React library:
 * - Idle: ring-1 ring-inset ring-black/10 (light) or ring-white/10 (dark)
 * - Focused: ring-2 ring-blue-500
 * - Error: ring-2 ring-red-500 with optional shake animation
 * - Success: ring-2 ring-green-500
 *
 * @param value The current text value.
 * @param onValueChange Callback invoked when the text changes.
 * @param modifier Modifier applied to the outer container (label + field + supportingText).
 * @param placeholder Placeholder text shown when [value] is empty.
 * @param label Label text displayed above the input.
 * @param supportingText Supporting text displayed below the input (e.g. error messages).
 * @param isError When true, applies a red border and triggers a shake animation.
 * @param isSuccess When true, applies a green border.
 * @param enabled When false, the input is not editable and appears at reduced opacity.
 * @param password When true, the text is visually obscured.
 * @param singleLine Whether this is a single-line input. Defaults to true.
 * @param size The size variant of the input. Defaults to [DarwinInputSize.Md].
 * @param glass When true, applies glass-morphism styling instead of the default background.
 * @param leadingIcon Optional composable rendered at the start of the input row.
 * @param trailingIcon Optional composable rendered at the end of the input row.
 * @param keyboardOptions Software keyboard options forwarded to [BasicTextField].
 * @param keyboardActions Software keyboard actions forwarded to [BasicTextField].
 * @param visualTransformation Visual transformation applied to the text. Overridden when [password] is true.
 * @param focusRequester Optional [FocusRequester] to control focus programmatically.
 * @param textStyle The text style for the input content. Defaults to the theme's bodyMedium.
 */
@Composable
fun DarwinTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    enabled: Boolean = true,
    password: Boolean = false,
    singleLine: Boolean = true,
    size: DarwinInputSize = DarwinInputSize.Md,
    glass: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
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
        animationSpec = darwinTween(DarwinDuration.Slow),
        label = "DarwinTextFieldBorderColor",
    )

    val resolvedBorderWidth: Dp = when {
        isError || isSuccess || isFocused -> 2.dp
        else -> 1.dp
    }

    // ---- Shake animation for error state ----
    val shakeOffset = remember { Animatable(0f) }

    LaunchedEffect(isError) {
        if (isError) {
            // Quick shake: left-right-left-right-center
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

    // ---- Background color (animated on focus, matching React focus:bg-white/90 / dark:focus:bg-zinc-900/90) ----
    // We animate alpha separately to avoid interpolating between two very different colors
    // (e.g. semi-transparent black → near-opaque white) which produces ugly intermediate grays.
    val focusAlpha by animateFloatAsState(
        targetValue = if (!glass && isFocused) 1f else 0f,
        animationSpec = darwinTween(DarwinDuration.Slow),
        label = "DarwinTextFieldFocusBgAlpha",
    )
    val idleBackground: Color = if (glass) colors.glassBackground else colors.inputBackground

    // ---- Visual transformation ----
    val resolvedTransformation = if (password) PasswordVisualTransformation() else visualTransformation

    // ---- Layout ----
    Column(modifier = modifier) {
        // Label
        if (label != null) {
            androidx.compose.foundation.text.BasicText(
                text = label,
                style = typography.labelMedium.copy(
                    color = when {
                        isError -> colors.destructive
                        else -> colors.textPrimary
                    },
                ),
                modifier = Modifier.padding(bottom = 6.dp, start = 2.dp),
            )
        }

        // Input field container
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged { isFocused = it.isFocused }
                .graphicsLayer { translationX = shakeOffset.value },
            enabled = enabled,
            textStyle = mergedTextStyle,
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = resolvedTransformation,
            cursorBrush = SolidColor(colors.inputFocusBorder),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(size.height)
                        .clip(shapes.medium)
                        .background(idleBackground)
                        .background(colors.inputFocusBackground.copy(alpha = colors.inputFocusBackground.alpha * focusAlpha))
                        .border(
                            width = resolvedBorderWidth,
                            color = borderColor,
                            shape = shapes.medium,
                        )
                        .then(
                            if (!enabled) Modifier.graphicsLayer { alpha = 0.5f }
                            else Modifier
                        ),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // Leading icon
                        if (leadingIcon != null) {
                            Box(
                                modifier = Modifier.padding(end = 8.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                leadingIcon()
                            }
                        }

                        // Text field + placeholder
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            if (value.isEmpty() && placeholder.isNotEmpty()) {
                                androidx.compose.foundation.text.BasicText(
                                    text = placeholder,
                                    style = typography.bodyMedium.copy(
                                        color = colors.textTertiary,
                                    ),
                                    maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                                )
                            }
                            innerTextField()
                        }

                        // Trailing icon — auto error/success icons mirror React behaviour
                        val autoTrailingIcon: @Composable (() -> Unit)? = when {
                            isError -> {{ io.github.kdroidfilter.darwinui.icons.DarwinIcon(io.github.kdroidfilter.darwinui.icons.LucideX, tint = colors.destructive) }}
                            isSuccess -> {{ io.github.kdroidfilter.darwinui.icons.DarwinIcon(io.github.kdroidfilter.darwinui.icons.LucideCheck, tint = colors.success) }}
                            else -> trailingIcon
                        }
                        if (autoTrailingIcon != null) {
                            Box(
                                modifier = Modifier.padding(start = 8.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                autoTrailingIcon()
                            }
                        }
                    }
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
