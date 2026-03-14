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
import io.github.kdroidfilter.darwinui.theme.FocusableComponentState
import io.github.kdroidfilter.darwinui.theme.LocalControlSize
import io.github.kdroidfilter.darwinui.theme.Outline
import io.github.kdroidfilter.darwinui.theme.darwinTween
import io.github.kdroidfilter.darwinui.theme.focusOrValidationOutline

// ===========================================================================
// TextFieldState — implements FocusableComponentState (Phase 1.2)
// ===========================================================================

/**
 * State snapshot for a Darwin text field.
 * Implements [FocusableComponentState] to integrate with the Phase 1.2 state hierarchy.
 */
data class TextFieldState(
    override val isActive: Boolean,
    override val isEnabled: Boolean,
    override val isHovered: Boolean,
    override val isPressed: Boolean,
    override val isFocused: Boolean,
) : FocusableComponentState {
    companion object {
        fun of(isEnabled: Boolean, isFocused: Boolean): TextFieldState = TextFieldState(
            isActive = true,
            isEnabled = isEnabled,
            isHovered = false,
            isPressed = false,
            isFocused = isFocused,
        )
    }
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
// Reads defaults from DarwinTheme.componentStyling.textField (Phase 1.1)
// ===========================================================================

object TextFieldDefaults {
    @Composable
    fun colors(
        focusedTextColor: Color = DarwinTheme.componentStyling.textField.colors.content,
        unfocusedTextColor: Color = DarwinTheme.componentStyling.textField.colors.content,
        disabledTextColor: Color = DarwinTheme.componentStyling.textField.colors.contentDisabled,
        errorTextColor: Color = DarwinTheme.componentStyling.textField.colors.content,
        focusedContainerColor: Color = DarwinTheme.componentStyling.textField.colors.backgroundFocused,
        unfocusedContainerColor: Color = DarwinTheme.componentStyling.textField.colors.background,
        disabledContainerColor: Color = DarwinTheme.componentStyling.textField.colors.backgroundDisabled,
        errorContainerColor: Color = DarwinTheme.componentStyling.textField.colors.backgroundError,
        cursorColor: Color = DarwinTheme.componentStyling.textField.colors.cursor,
        errorCursorColor: Color = DarwinTheme.componentStyling.textField.colors.cursorError,
        focusedIndicatorColor: Color = DarwinTheme.componentStyling.textField.colors.borderFocused,
        unfocusedIndicatorColor: Color = DarwinTheme.componentStyling.textField.colors.border,
        disabledIndicatorColor: Color = DarwinTheme.componentStyling.textField.colors.borderDisabled,
        errorIndicatorColor: Color = DarwinTheme.componentStyling.textField.colors.borderError,
        focusedLeadingIconColor: Color = DarwinTheme.componentStyling.textField.colors.icon,
        unfocusedLeadingIconColor: Color = DarwinTheme.componentStyling.textField.colors.icon,
        disabledLeadingIconColor: Color = DarwinTheme.componentStyling.textField.colors.iconDisabled,
        errorLeadingIconColor: Color = DarwinTheme.componentStyling.textField.colors.iconError,
        focusedTrailingIconColor: Color = DarwinTheme.componentStyling.textField.colors.icon,
        unfocusedTrailingIconColor: Color = DarwinTheme.componentStyling.textField.colors.icon,
        disabledTrailingIconColor: Color = DarwinTheme.componentStyling.textField.colors.iconDisabled,
        errorTrailingIconColor: Color = DarwinTheme.componentStyling.textField.colors.iconError,
        focusedLabelColor: Color = DarwinTheme.componentStyling.textField.colors.label,
        unfocusedLabelColor: Color = DarwinTheme.componentStyling.textField.colors.label,
        disabledLabelColor: Color = DarwinTheme.componentStyling.textField.colors.labelDisabled,
        errorLabelColor: Color = DarwinTheme.componentStyling.textField.colors.labelError,
        focusedPlaceholderColor: Color = DarwinTheme.componentStyling.textField.colors.placeholder,
        unfocusedPlaceholderColor: Color = DarwinTheme.componentStyling.textField.colors.placeholder,
        disabledPlaceholderColor: Color = DarwinTheme.componentStyling.textField.colors.placeholderDisabled,
        errorPlaceholderColor: Color = DarwinTheme.componentStyling.textField.colors.placeholder,
        focusedSupportingTextColor: Color = DarwinTheme.componentStyling.textField.colors.placeholder,
        unfocusedSupportingTextColor: Color = DarwinTheme.componentStyling.textField.colors.placeholder,
        disabledSupportingTextColor: Color = DarwinTheme.componentStyling.textField.colors.placeholderDisabled,
        errorSupportingTextColor: Color = DarwinTheme.componentStyling.textField.colors.labelError,
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
        focusedTextColor: Color = DarwinTheme.componentStyling.textField.colors.content,
        unfocusedTextColor: Color = DarwinTheme.componentStyling.textField.colors.content,
        disabledTextColor: Color = DarwinTheme.componentStyling.textField.colors.contentDisabled,
        errorTextColor: Color = DarwinTheme.componentStyling.textField.colors.content,
        // Outlined variant uses transparent background
        focusedContainerColor: Color = Color.Transparent,
        unfocusedContainerColor: Color = Color.Transparent,
        disabledContainerColor: Color = Color.Transparent,
        errorContainerColor: Color = Color.Transparent,
        cursorColor: Color = DarwinTheme.componentStyling.textField.colors.cursor,
        errorCursorColor: Color = DarwinTheme.componentStyling.textField.colors.cursorError,
        focusedIndicatorColor: Color = DarwinTheme.componentStyling.textField.colors.borderFocused,
        unfocusedIndicatorColor: Color = DarwinTheme.componentStyling.textField.colors.border,
        disabledIndicatorColor: Color = DarwinTheme.componentStyling.textField.colors.borderDisabled,
        errorIndicatorColor: Color = DarwinTheme.componentStyling.textField.colors.borderError,
        focusedLeadingIconColor: Color = DarwinTheme.componentStyling.textField.colors.icon,
        unfocusedLeadingIconColor: Color = DarwinTheme.componentStyling.textField.colors.icon,
        disabledLeadingIconColor: Color = DarwinTheme.componentStyling.textField.colors.iconDisabled,
        errorLeadingIconColor: Color = DarwinTheme.componentStyling.textField.colors.iconError,
        focusedTrailingIconColor: Color = DarwinTheme.componentStyling.textField.colors.icon,
        unfocusedTrailingIconColor: Color = DarwinTheme.componentStyling.textField.colors.icon,
        disabledTrailingIconColor: Color = DarwinTheme.componentStyling.textField.colors.iconDisabled,
        errorTrailingIconColor: Color = DarwinTheme.componentStyling.textField.colors.iconError,
        focusedLabelColor: Color = DarwinTheme.componentStyling.textField.colors.label,
        unfocusedLabelColor: Color = DarwinTheme.componentStyling.textField.colors.labelDisabled,
        disabledLabelColor: Color = DarwinTheme.componentStyling.textField.colors.labelDisabled,
        errorLabelColor: Color = DarwinTheme.componentStyling.textField.colors.labelError,
        focusedPlaceholderColor: Color = DarwinTheme.componentStyling.textField.colors.placeholder,
        unfocusedPlaceholderColor: Color = DarwinTheme.componentStyling.textField.colors.placeholder,
        disabledPlaceholderColor: Color = DarwinTheme.componentStyling.textField.colors.placeholderDisabled,
        errorPlaceholderColor: Color = DarwinTheme.componentStyling.textField.colors.placeholder,
        focusedSupportingTextColor: Color = DarwinTheme.componentStyling.textField.colors.placeholder,
        unfocusedSupportingTextColor: Color = DarwinTheme.componentStyling.textField.colors.placeholder,
        disabledSupportingTextColor: Color = DarwinTheme.componentStyling.textField.colors.placeholderDisabled,
        errorSupportingTextColor: Color = DarwinTheme.componentStyling.textField.colors.labelError,
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
    focusRequester: FocusRequester,
    outline: Outline,
) {
    val typography = DarwinTheme.typography
    val outlines = DarwinTheme.globalColors.outlines
    val controlSize = LocalControlSize.current
    val metrics = DarwinTheme.componentStyling.textField.metrics
    val resolvedHeight = metrics.minHeightFor(controlSize)

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
                    io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle provides typography.caption1.copy(color = labelColor),
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
                // Outer unclipped box carries the focus/validation outline ring (Phase 1.4)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(resolvedHeight)
                        .focusOrValidationOutline(isFocused, outline, shape, outlines),
                ) {
                    // Inner clipped box handles background, border, and disabled opacity
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
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
                                        io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle provides typography.subheadline.copy(color = placeholderColor),
                                    ) { placeholder() }
                                }
                                innerTextField()
                            }
                            if (suffix != null) {
                                Box(modifier = Modifier.padding(start = 4.dp)) { suffix() }
                            }
                            if (trailingIcon != null) {
                                val iconColor = when {
                                    !enabled -> colors.disabledTrailingIconColor
                                    isError -> colors.errorTrailingIconColor
                                    isFocused -> colors.focusedTrailingIconColor
                                    else -> colors.unfocusedTrailingIconColor
                                }
                                Box(modifier = Modifier.padding(start = 8.dp), contentAlignment = Alignment.Center) {
                                    androidx.compose.runtime.CompositionLocalProvider(
                                        io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor provides iconColor,
                                    ) { trailingIcon() }
                                }
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
                    io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle provides typography.caption1.copy(color = supportingColor),
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
    textStyle: TextStyle = DarwinTheme.typography.subheadline,
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
    shape: Shape = DarwinTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    focusRequester: FocusRequester = remember { FocusRequester() },
    outline: Outline = Outline.None,
) {
    TextFieldImpl(
        value, onValueChange, modifier, enabled, readOnly, textStyle,
        label, placeholder, leadingIcon, trailingIcon, prefix, suffix, supportingText,
        isError, visualTransformation, keyboardOptions, keyboardActions,
        singleLine, maxLines, minLines, interactionSource, shape, colors, focusRequester,
        outline,
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
    textStyle: TextStyle = DarwinTheme.typography.subheadline,
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
    shape: Shape = DarwinTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    focusRequester: FocusRequester = remember { FocusRequester() },
    outline: Outline = Outline.None,
) {
    TextFieldImpl(
        value, onValueChange, modifier, enabled, readOnly, textStyle,
        label, placeholder, leadingIcon, trailingIcon, prefix, suffix, supportingText,
        isError, visualTransformation, keyboardOptions, keyboardActions,
        singleLine, maxLines, minLines, interactionSource, shape, colors, focusRequester,
        outline,
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
