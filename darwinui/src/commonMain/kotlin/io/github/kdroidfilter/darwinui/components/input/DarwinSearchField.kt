package io.github.kdroidfilter.darwinui.components.input

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import io.github.kdroidfilter.darwinui.icons.DarwinIcon
import io.github.kdroidfilter.darwinui.icons.LucideSearch
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

/**
 * A search input field with a leading search icon.
 *
 * Mirrors the React darwin-ui `Input.Search` component — a convenience wrapper
 * around [DarwinTextField] that pre-sets a [LucideSearch] leading icon.
 *
 * @param value The current text value.
 * @param onValueChange Callback invoked when the text changes.
 * @param modifier Modifier applied to the outer container.
 * @param placeholder Placeholder text shown when [value] is empty.
 * @param label Label text displayed above the input.
 * @param supportingText Supporting text displayed below the input.
 * @param isError When true, applies a red border and triggers a shake animation.
 * @param isSuccess When true, applies a green border.
 * @param enabled When false, the input is not editable and appears at reduced opacity.
 * @param singleLine Whether this is a single-line input. Defaults to true.
 * @param size The size variant of the input. Defaults to [DarwinInputSize.Md].
 * @param glass When true, applies glass-morphism styling.
 * @param trailingIcon Optional composable rendered at the end of the input row.
 * @param keyboardOptions Software keyboard options forwarded to the underlying text field.
 * @param keyboardActions Software keyboard actions forwarded to the underlying text field.
 * @param focusRequester Optional [FocusRequester] to control focus programmatically.
 * @param textStyle The text style for the input content.
 */
@Composable
fun DarwinSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    label: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    size: DarwinInputSize = DarwinInputSize.Lg,
    glass: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester = remember { FocusRequester() },
    textStyle: TextStyle? = null,
) {
    val colors = DarwinTheme.colors

    DarwinTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder,
        label = label,
        supportingText = supportingText,
        isError = isError,
        isSuccess = isSuccess,
        enabled = enabled,
        singleLine = singleLine,
        size = size,
        glass = glass,
        leadingIcon = {
            DarwinIcon(
                imageVector = LucideSearch,
                tint = colors.textTertiary,
            )
        },
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        focusRequester = focusRequester,
        textStyle = textStyle,
    )
}
