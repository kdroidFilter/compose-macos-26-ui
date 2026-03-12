package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideSearch
import io.github.kdroidfilter.darwinui.icons.LucideX
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.Outline
import io.github.kdroidfilter.darwinui.theme.darwinTween
import io.github.kdroidfilter.darwinui.theme.outline as validationOutline

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester = remember { FocusRequester() },
    // Backward-compatible params (ignored but API-stable)
    label: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    size: InputSize = InputSize.Md,
    trailingIcon: @Composable (() -> Unit)? = null,
    outline: Outline = Outline.None,
) {
    val colors = DarwinTheme.colorScheme
    val typography = DarwinTheme.typography
    val isDark = colors.isDark
    val accentColor = colors.accent
    val outlines = DarwinTheme.globalColors.outlines

    var isFocused by remember { mutableStateOf(false) }

    val shape = DarwinTheme.shapes.full

    // Animated background: white when focused (macOS native behavior)
    val bgColor by animateColorAsState(
        targetValue = when {
            isFocused -> if (isDark) Color.White.copy(alpha = 0.12f) else Color.Black.copy(alpha = 0.03f)
            else -> if (isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.06f)
        },
        animationSpec = darwinTween(DarwinDuration.Normal),
        label = "search_bg",
    )

    // Animated blue focus ring
    val ringColor by animateColorAsState(
        targetValue = if (isFocused) accentColor.copy(alpha = 0.4f) else Color.Transparent,
        animationSpec = darwinTween(DarwinDuration.Normal),
        label = "search_ring",
    )

    val textColor = colors.textPrimary
    val placeholderColor = colors.textTertiary
    val iconColor = colors.textTertiary

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { isFocused = it.isFocused },
        enabled = enabled,
        singleLine = singleLine,
        textStyle = typography.subheadline.copy(color = textColor),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(accentColor),
        decorationBox = { innerTextField ->
            // Outer unclipped box carries the validation ring (Phase 1.4).
            // SearchField handles its own animated focus ring via border(), so only
            // the validation outline (Warning/Error) is drawn here.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
                    .shadow(
                        elevation = if (isFocused) 4.dp else 0.dp,
                        shape = shape,
                        clip = false,
                        ambientColor = accentColor.copy(alpha = 0.25f),
                        spotColor = accentColor.copy(alpha = 0.25f),
                    )
                    .validationOutline(outline, shape, outlines),
            ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape)
                    .background(bgColor, shape)
                    .border(1.5.dp, ringColor, shape)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Icon(
                    imageVector = LucideSearch,
                    tint = iconColor,
                    modifier = Modifier.size(14.dp),
                )
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (value.isEmpty()) {
                        CompositionLocalProvider(
                            LocalDarwinContentColor provides placeholderColor,
                            LocalDarwinTextStyle provides typography.caption1.copy(color = placeholderColor),
                        ) {
                            Text(placeholder)
                        }
                    }
                    CompositionLocalProvider(
                        LocalDarwinTextStyle provides typography.caption1.copy(color = textColor),
                    ) {
                        innerTextField()
                    }
                }
                if (isFocused && value.isNotEmpty()) {
                    SearchClearButton(
                        onClick = { onValueChange("") },
                        isDark = isDark,
                    )
                } else if (trailingIcon != null) {
                    trailingIcon()
                }
            } // Row
            } // validation outline Box
        },
    )
}

// macOS-style clear button — filled circle with × icon
@Composable
private fun SearchClearButton(
    onClick: () -> Unit,
    isDark: Boolean,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val bgColor by animateColorAsState(
        targetValue = when {
            isHovered -> if (isDark) Color.White.copy(alpha = 0.35f) else Color.Black.copy(alpha = 0.30f)
            else -> if (isDark) Color.White.copy(alpha = 0.25f) else Color.Black.copy(alpha = 0.20f)
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "search_clear_bg",
    )
    val iconColor by animateColorAsState(
        targetValue = when {
            isHovered -> if (isDark) Color.Black.copy(alpha = 0.8f) else Color.White.copy(alpha = 0.9f)
            else -> if (isDark) Color.Black.copy(alpha = 0.65f) else Color.White.copy(alpha = 0.8f)
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "search_clear_icon",
    )

    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(bgColor, CircleShape)
            .hoverable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = LucideX,
            tint = iconColor,
            modifier = Modifier.size(10.dp),
        )
    }
}

@Preview
@Composable
private fun SearchFieldPreview() {
    DarwinTheme {
        var query by remember { mutableStateOf("") }
        SearchField(value = query, onValueChange = { query = it })
    }
}
