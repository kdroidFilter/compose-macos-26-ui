package io.github.kdroidfilter.darwinui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideSearch
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    label: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    size: InputSize = InputSize.Lg,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester = remember { FocusRequester() },
) {
    val colors = DarwinTheme.colors

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = { Text(placeholder) },
        label = label?.let { l -> { Text(l) } },
        supportingText = supportingText?.let { s -> { Text(s) } },
        isError = isError,
        enabled = enabled,
        singleLine = singleLine,
        size = size,
        leadingIcon = {
            Icon(
                imageVector = LucideSearch,
                tint = colors.textTertiary,
                modifier = Modifier.size(16.dp),
            )
        },
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        focusRequester = focusRequester,
    )
}

@Preview
@Composable
private fun SearchFieldPreview() {
    DarwinTheme {
        var query by remember { mutableStateOf("") }
        SearchField(value = query, onValueChange = { query = it })
    }
}
