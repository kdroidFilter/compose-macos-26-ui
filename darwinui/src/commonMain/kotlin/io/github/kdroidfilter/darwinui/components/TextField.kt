package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.darwinTween

// ===========================================================================
// InputSize — Darwin extension
// ===========================================================================

enum class InputSize(val height: Dp) {
    Sm(26.dp), Md(32.dp), Lg(40.dp),
}

// ===========================================================================
// TextFieldColors — mirrors M3's TextFieldColors
// ===========================================================================

class TextFieldColors(
    val focusedTextColor: Color,
    val unfocusedTextColor: Color,
    val disabledTextColor: Color,
    val errorTextColor: Color,
    val focusedContainerColor: Color,
    val unfocusedContainerColor: Color,
    val disabledContainerColor: Color,
    val errorContainerColor: Color,
    val cursorColor: Color,
    val errorCursorColor: Color,
    val focusedIndicatorColor: Color,
    val unfocusedIndicatorColor: Color,
    val disabledIndicatorColor: Color,
    val errorIndicatorColor: Color,
    val focusedLeadingIconColor: Color,
    val unfocusedLeadingIconColor: Color,
    val disabledLeadingIconColor: Color,
    val errorLeadingIconColor: Color,
    val focusedTrailingIconColor: Color,
    val unfocusedTrailingIconColor: Color,
    val disabledTrailingIconColor: Color,
    val errorTrailingIconColor: Color,
    val focusedLabelColor: Color,
    val unfocusedLabelColor: Color,
    val disabledLabelColor: Color,
    val errorLabelColor: Color,
    val focusedPlaceholderColor: Color,
    val unfocusedPlaceholderColor: Color,
    val disabledPlaceholderColor: Color,
    val errorPlaceholderColor: Color,
    val focusedSupportingTextColor: Color,
    val unfocusedSupportingTextColor: Color,
    val disabledSupportingTextColor: Color,
    val errorSupportingTextColor: Color,
)

// ===========================================================================
// TextFieldDefaults — mirrors M3's TextFieldDefaults
// ===========================================================================

object TextFieldDefaults {
    @Composable
    fun colors(
        focusedTextColor: Color = DarwinTheme.colorScheme.textPrimary,
        unfocusedTextColor: Color = DarwinTheme.colorScheme.textPrimary,
        disabledTextColor: Color = DarwinTheme.colorScheme.textTertiary,
        errorTextColor: Color = DarwinTheme.colorScheme.textPrimary,
        focusedContainerColor: Color = DarwinTheme.colorScheme.inputFocusBackground,
        unfocusedContainerColor: Color = DarwinTheme.colorScheme.inputBackground,
        disabledContainerColor: Color = DarwinTheme.colorScheme.inputBackground,
        errorContainerColor: Color = DarwinTheme.colorScheme.inputBackground,
        cursorColor: Color = DarwinTheme.colorScheme.inputFocusBorder,
        errorCursorColor: Color = DarwinTheme.colorScheme.destructive,
        focusedIndicatorColor: Color = DarwinTheme.colorScheme.inputFocusBorder,
        unfocusedIndicatorColor: Color = DarwinTheme.colorScheme.inputBorder,
        disabledIndicatorColor: Color = DarwinTheme.colorScheme.inputBorder.copy(alpha = 0.5f),
        errorIndicatorColor: Color = DarwinTheme.colorScheme.destructive,
        focusedLeadingIconColor: Color = DarwinTheme.colorScheme.textTertiary,
        unfocusedLeadingIconColor: Color = DarwinTheme.colorScheme.textTertiary,
        disabledLeadingIconColor: Color = DarwinTheme.colorScheme.textTertiary.copy(alpha = 0.5f),
        errorLeadingIconColor: Color = DarwinTheme.colorScheme.destructive,
        focusedTrailingIconColor: Color = DarwinTheme.colorScheme.textTertiary,
        unfocusedTrailingIconColor: Color = DarwinTheme.colorScheme.textTertiary,
        disabledTrailingIconColor: Color = DarwinTheme.colorScheme.textTertiary.copy(alpha = 0.5f),
        errorTrailingIconColor: Color = DarwinTheme.colorScheme.destructive,
        focusedLabelColor: Color = DarwinTheme.colorScheme.textPrimary,
        unfocusedLabelColor: Color = DarwinTheme.colorScheme.textPrimary,
        disabledLabelColor: Color = DarwinTheme.colorScheme.textTertiary,
        errorLabelColor: Color = DarwinTheme.colorScheme.destructive,
        focusedPlaceholderColor: Color = DarwinTheme.colorScheme.textTertiary,
        unfocusedPlaceholderColor: Color = DarwinTheme.colorScheme.textTertiary,
        disabledPlaceholderColor: Color = DarwinTheme.colorScheme.textTertiary.copy(alpha = 0.5f),
        errorPlaceholderColor: Color = DarwinTheme.colorScheme.textTertiary,
        focusedSupportingTextColor: Color = DarwinTheme.colorScheme.textTertiary,
        unfocusedSupportingTextColor: Color = DarwinTheme.colorScheme.textTertiary,
        disabledSupportingTextColor: Color = DarwinTheme.colorScheme.textTertiary.copy(alpha = 0.5f),
        errorSupportingTextColor: Color = DarwinTheme.colorScheme.destructive,
    ) = TextFieldColors(
        focusedTextColor, unfocusedTextColor, disabledTextColor, errorTextColor,
        focusedContainerColor, unfocusedContainerColor, disabledContainerColor, errorContainerColor,
        cursorColor, errorCursorColor,
        focusedIndicatorColor, unfocusedIndicatorColor, disabledIndicatorColor, errorIndicatorColor,
        focusedLeadingIconColor, unfocusedLeadingIconColor, disabledLeadingIconColor, errorLeadingIconColor,
        focusedTrailingIconColor, unfocusedTrailingIconColor, disabledTrailingIconColor, errorTrailingIconColor,
        focusedLabelColor, unfocusedLabelColor, disabledLabelColor, errorLabelColor,
        focusedPlaceholderColor, unfocusedPlaceholderColor, disabledPlaceholderColor, errorPlaceholderColor,
        focusedSupportingTextColor, unfocusedSupportingTextColor, disabledSupportingTextColor, errorSupportingTextColor,
    )

    @Composable
    fun outlinedTextFieldColors(
        focusedTextColor: Color = DarwinTheme.colorScheme.textPrimary,
        unfocusedTextColor: Color = DarwinTheme.colorScheme.textPrimary,
        disabledTextColor: Color = DarwinTheme.colorScheme.textTertiary,
        errorTextColor: Color = DarwinTheme.colorScheme.textPrimary,
        focusedContainerColor: Color = Color.Transparent,
        unfocusedContainerColor: Color = Color.Transparent,
        disabledContainerColor: Color = Color.Transparent,
        errorContainerColor: Color = Color.Transparent,
        cursorColor: Color = DarwinTheme.colorScheme.inputFocusBorder,
        errorCursorColor: Color = DarwinTheme.colorScheme.destructive,
        focusedIndicatorColor: Color = DarwinTheme.colorScheme.inputFocusBorder,
        unfocusedIndicatorColor: Color = DarwinTheme.colorScheme.inputBorder,
        disabledIndicatorColor: Color = DarwinTheme.colorScheme.inputBorder.copy(alpha = 0.5f),
        errorIndicatorColor: Color = DarwinTheme.colorScheme.destructive,
        focusedLeadingIconColor: Color = DarwinTheme.colorScheme.textTertiary,
        unfocusedLeadingIconColor: Color = DarwinTheme.colorScheme.textTertiary,
        disabledLeadingIconColor: Color = DarwinTheme.colorScheme.textTertiary.copy(alpha = 0.5f),
        errorLeadingIconColor: Color = DarwinTheme.colorScheme.destructive,
        focusedTrailingIconColor: Color = DarwinTheme.colorScheme.textTertiary,
        unfocusedTrailingIconColor: Color = DarwinTheme.colorScheme.textTertiary,
        disabledTrailingIconColor: Color = DarwinTheme.colorScheme.textTertiary.copy(alpha = 0.5f),
        errorTrailingIconColor: Color = DarwinTheme.colorScheme.destructive,
        focusedLabelColor: Color = DarwinTheme.colorScheme.textPrimary,
        unfocusedLabelColor: Color = DarwinTheme.colorScheme.textSecondary,
        disabledLabelColor: Color = DarwinTheme.colorScheme.textTertiary,
        errorLabelColor: Color = DarwinTheme.colorScheme.destructive,
        focusedPlaceholderColor: Color = DarwinTheme.colorScheme.textTertiary,
        unfocusedPlaceholderColor: Color = DarwinTheme.colorScheme.textTertiary,
        disabledPlaceholderColor: Color = DarwinTheme.colorScheme.textTertiary.copy(alpha = 0.5f),
        errorPlaceholderColor: Color = DarwinTheme.colorScheme.textTertiary,
        focusedSupportingTextColor: Color = DarwinTheme.colorScheme.textTertiary,
        unfocusedSupportingTextColor: Color = DarwinTheme.colorScheme.textTertiary,
        disabledSupportingTextColor: Color = DarwinTheme.colorScheme.textTertiary.copy(alpha = 0.5f),
        errorSupportingTextColor: Color = DarwinTheme.colorScheme.destructive,
    ) = TextFieldColors(
        focusedTextColor, unfocusedTextColor, disabledTextColor, errorTextColor,
        focusedContainerColor, unfocusedContainerColor, disabledContainerColor, errorContainerColor,
        cursorColor, errorCursorColor,
        focusedIndicatorColor, unfocusedIndicatorColor, disabledIndicatorColor, errorIndicatorColor,
        focusedLeadingIconColor, unfocusedLeadingIconColor, disabledLeadingIconColor, errorLeadingIconColor,
        focusedTrailingIconColor, unfocusedTrailingIconColor, disabledTrailingIconColor, errorTrailingIconColor,
        focusedLabelColor, unfocusedLabelColor, disabledLabelColor, errorLabelColor,
        focusedPlaceholderColor, unfocusedPlaceholderColor, disabledPlaceholderColor, errorPlaceholderColor,
        focusedSupportingTextColor, unfocusedSupportingTextColor, disabledSupportingTextColor, errorSupportingTextColor,
    )
}

// ===========================================================================
// Internal shared text field implementation
// ===========================================================================

@Composable
private fun TextFieldImpl(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    readOnly: Boolean,
    textStyle: TextStyle,
    label: (@Composable () -> Unit)?,
    placeholder: (@Composable () -> Unit)?,
    leadingIcon: (@Composable () -> Unit)?,
    trailingIcon: (@Composable () -> Unit)?,
    prefix: (@Composable () -> Unit)?,
    suffix: (@Composable () -> Unit)?,
    supportingText: (@Composable () -> Unit)?,
    isError: Boolean,
    visualTransformation: VisualTransformation,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    singleLine: Boolean,
    maxLines: Int,
    minLines: Int,
    interactionSource: MutableInteractionSource,
    shape: Shape,
    colors: TextFieldColors,
    size: InputSize,
    focusRequester: FocusRequester,
) {
    val typography = DarwinTheme.typography

    var isFocused by remember { mutableStateOf(false) }

    val borderColor by animateColorAsState(
        targetValue = when {
            isError -> colors.errorIndicatorColor
            isFocused -> colors.focusedIndicatorColor
            !enabled -> colors.disabledIndicatorColor
            else -> colors.unfocusedIndicatorColor
        },
        animationSpec = darwinTween(DarwinDuration.Slow),
        label = "textFieldBorder",
    )

    val containerColor by animateColorAsState(
        targetValue = when {
            isError -> colors.errorContainerColor
            !enabled -> colors.disabledContainerColor
            isFocused -> colors.focusedContainerColor
            else -> colors.unfocusedContainerColor
        },
        animationSpec = darwinTween(DarwinDuration.Slow),
        label = "textFieldContainer",
    )

    val textColor = when {
        !enabled -> colors.disabledTextColor
        isError -> colors.errorTextColor
        isFocused -> colors.focusedTextColor
        else -> colors.unfocusedTextColor
    }

    val resolvedTextStyle = textStyle.copy(color = textColor)

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

    val cursorBrush = SolidColor(if (isError) colors.errorCursorColor else colors.cursorColor)

    Column(modifier = modifier) {
        if (label != null) {
            val labelColor = when {
                isError -> colors.errorLabelColor
                !enabled -> colors.disabledLabelColor
                isFocused -> colors.focusedLabelColor
                else -> colors.unfocusedLabelColor
            }
            Box(modifier = Modifier.padding(bottom = 6.dp, start = 2.dp)) {
                androidx.compose.runtime.CompositionLocalProvider(
                    io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor provides labelColor,
                    io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle provides typography.labelMedium.copy(color = labelColor),
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
            textStyle = resolvedTextStyle,
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
                        .height(size.height)
                        .clip(shape)
                        .background(containerColor, shape)
                        .border(width = if (isFocused) 2.dp else 1.dp, color = borderColor, shape = shape)
                        .then(if (!enabled) Modifier.graphicsLayer { alpha = 0.5f } else Modifier),
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (leadingIcon != null) {
                            val iconColor = when {
                                !enabled -> colors.disabledLeadingIconColor
                                isError -> colors.errorLeadingIconColor
                                isFocused -> colors.focusedLeadingIconColor
                                else -> colors.unfocusedLeadingIconColor
                            }
                            Box(modifier = Modifier.padding(end = 8.dp), contentAlignment = Alignment.Center) {
                                androidx.compose.runtime.CompositionLocalProvider(
                                    io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor provides iconColor,
                                ) { leadingIcon() }
                            }
                        }
                        if (prefix != null) {
                            Box(modifier = Modifier.padding(end = 4.dp)) { prefix() }
                        }
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                            if (value.isEmpty() && placeholder != null) {
                                val placeholderColor = when {
                                    !enabled -> colors.disabledPlaceholderColor
                                    isError -> colors.errorPlaceholderColor
                                    isFocused -> colors.focusedPlaceholderColor
                                    else -> colors.unfocusedPlaceholderColor
                                }
                                androidx.compose.runtime.CompositionLocalProvider(
                                    io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor provides placeholderColor,
                                    io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle provides typography.bodyMedium.copy(color = placeholderColor),
                                ) { placeholder() }
                            }
                            innerTextField()
                        }
                        if (suffix != null) {
                            Box(modifier = Modifier.padding(start = 4.dp)) { suffix() }
                        }
                        val resolvedTrailingIcon: (@Composable () -> Unit)? = when {
                            isError -> ({
                                io.github.kdroidfilter.darwinui.icons.Icon(
                                    io.github.kdroidfilter.darwinui.icons.LucideX,
                                    tint = colors.errorTrailingIconColor,
                                )
                            })
                            else -> trailingIcon
                        }
                        if (resolvedTrailingIcon != null) {
                            val iconColor = when {
                                !enabled -> colors.disabledTrailingIconColor
                                isError -> colors.errorTrailingIconColor
                                isFocused -> colors.focusedTrailingIconColor
                                else -> colors.unfocusedTrailingIconColor
                            }
                            Box(modifier = Modifier.padding(start = 8.dp), contentAlignment = Alignment.Center) {
                                androidx.compose.runtime.CompositionLocalProvider(
                                    io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor provides iconColor,
                                ) { resolvedTrailingIcon() }
                            }
                        }
                    }
                }
            },
        )

        if (supportingText != null) {
            val supportingColor = when {
                isError -> colors.errorSupportingTextColor
                !enabled -> colors.disabledSupportingTextColor
                isFocused -> colors.focusedSupportingTextColor
                else -> colors.unfocusedSupportingTextColor
            }
            Box(modifier = Modifier.padding(top = 4.dp, start = 2.dp)) {
                androidx.compose.runtime.CompositionLocalProvider(
                    io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor provides supportingColor,
                    io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle provides typography.bodySmall.copy(color = supportingColor),
                ) { supportingText() }
            }
        }
    }
}

// ===========================================================================
// TextField — M3-compatible
// ===========================================================================

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = DarwinTheme.typography.bodyMedium,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    prefix: (@Composable () -> Unit)? = null,
    suffix: (@Composable () -> Unit)? = null,
    supportingText: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = DarwinTheme.shapes.large,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    // Darwin extensions
    size: InputSize = InputSize.Md,
    focusRequester: FocusRequester = remember { FocusRequester() },
) {
    TextFieldImpl(
        value, onValueChange, modifier, enabled, readOnly, textStyle,
        label, placeholder, leadingIcon, trailingIcon, prefix, suffix, supportingText,
        isError, visualTransformation, keyboardOptions, keyboardActions,
        singleLine, maxLines, minLines, interactionSource, shape, colors, size, focusRequester,
    )
}

// ===========================================================================
// OutlinedTextField — M3-compatible
// ===========================================================================

@Composable
fun OutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = DarwinTheme.typography.bodyMedium,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    prefix: (@Composable () -> Unit)? = null,
    suffix: (@Composable () -> Unit)? = null,
    supportingText: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = DarwinTheme.shapes.large,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    // Darwin extensions
    size: InputSize = InputSize.Md,
    focusRequester: FocusRequester = remember { FocusRequester() },
) {
    TextFieldImpl(
        value, onValueChange, modifier, enabled, readOnly, textStyle,
        label, placeholder, leadingIcon, trailingIcon, prefix, suffix, supportingText,
        isError, visualTransformation, keyboardOptions, keyboardActions,
        singleLine, maxLines, minLines, interactionSource, shape, colors, size, focusRequester,
    )
}

@Preview
@Composable
private fun TextFieldPreview() {
    DarwinTheme {
        var text by remember { mutableStateOf("") }
        TextField(
            value = text, onValueChange = { text = it },
            placeholder = { Text("Type here…") },
            label = { Text("Name") },
        )
    }
}
