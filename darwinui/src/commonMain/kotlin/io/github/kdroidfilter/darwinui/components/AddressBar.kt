package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideChevronRight
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.darwinTween

/**
 * macOS Safari-style address bar.
 *
 * Uses the same pill background as [TitleBarButtonGroup]. The URL/query text is
 * centered. An optional leading icon (e.g. lock, reader mode) and a trailing
 * "Go" button (shown when [onGo] is provided) complete the layout.
 *
 * @param value Current text value.
 * @param onValueChange Called when the text changes.
 * @param modifier Modifier applied to the outer pill container.
 * @param placeholder Placeholder shown when [value] is empty.
 * @param enabled Whether the field is interactive.
 * @param onGo Called when the user taps the trailing Go button or presses Enter.
 *   Pass `null` to hide the Go button.
 * @param leadingIcon Optional icon composable shown on the left (e.g. lock icon).
 * @param focusRequester Optional [FocusRequester] to control focus programmatically.
 */
@Composable
fun AddressBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search or enter website name",
    enabled: Boolean = true,
    onGo: (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    focusRequester: FocusRequester = remember { FocusRequester() },
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography
    val isDark = colors.isDark
    val accentColor = colors.accent

    var isFocused by remember { mutableStateOf(false) }

    val shape = DarwinTheme.shapes.full

    val bgColor by animateColorAsState(
        targetValue = when {
            isFocused -> if (isDark) Color.White.copy(alpha = 0.12f) else Color.Black.copy(alpha = 0.03f)
            else -> if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.055f)
        },
        animationSpec = darwinTween(DarwinDuration.Normal),
        label = "address_bg",
    )

    val ringColor by animateColorAsState(
        targetValue = if (isFocused) accentColor.copy(alpha = 0.4f) else Color.Transparent,
        animationSpec = darwinTween(DarwinDuration.Normal),
        label = "address_ring",
    )

    val textColor = colors.textPrimary
    val placeholderColor = colors.textTertiary
    val iconColor = colors.textTertiary

    Row(
        modifier = modifier
            .onFocusChanged { isFocused = it.hasFocus }
            .height(28.dp)
            .shadow(
                elevation = if (isFocused) 4.dp else 0.dp,
                shape = shape,
                clip = false,
                ambientColor = accentColor.copy(alpha = 0.25f),
                spotColor = accentColor.copy(alpha = 0.25f),
            )
            .clip(shape)
            .background(bgColor, shape)
            .border(1.5.dp, ringColor, shape)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Leading icon — fixed width to keep text centered
        Box(
            modifier = Modifier.size(18.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (leadingIcon != null) {
                CompositionLocalProvider(LocalDarwinContentColor provides iconColor) {
                    leadingIcon()
                }
            }
        }

        // Center: text field + placeholder overlay
        CenterTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            textColor = textColor,
            placeholderColor = placeholderColor,
            placeholder = placeholder,
            onGo = onGo,
            focusRequester = focusRequester,
        )

        // Trailing Go button — mirrors leading size to keep text centered
        Box(
            modifier = Modifier.size(18.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (onGo != null) {
                GoButton(onClick = onGo, enabled = enabled, isDark = isDark)
            }
        }
    }
}

@Composable
private fun RowScope.CenterTextField(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    textColor: Color,
    placeholderColor: Color,
    placeholder: String,
    onGo: (() -> Unit)?,
    focusRequester: FocusRequester,
) {
    val typography = DarwinTheme.typography

    Box(
        modifier = Modifier.weight(1f),
        contentAlignment = Alignment.Center,
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .focusRequester(focusRequester)
                .matchParentSize(),
            enabled = enabled,
            singleLine = true,
            textStyle = typography.bodySmall.copy(
                color = textColor,
                textAlign = TextAlign.Center,
            ),
            cursorBrush = SolidColor(DarwinTheme.colors.accent),
            keyboardActions = KeyboardActions(onGo = { onGo?.invoke() }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
        )
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                style = typography.bodySmall.copy(
                    color = placeholderColor,
                    textAlign = TextAlign.Center,
                ),
            )
        }
    }
}

@Composable
private fun GoButton(
    onClick: () -> Unit,
    enabled: Boolean,
    isDark: Boolean,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val bgColor by animateColorAsState(
        targetValue = when {
            isPressed && enabled -> if (isDark) Color.White.copy(alpha = 0.18f) else Color.Black.copy(alpha = 0.12f)
            isHovered && enabled -> if (isDark) Color.White.copy(alpha = 0.12f) else Color.Black.copy(alpha = 0.08f)
            else -> Color.Transparent
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "go_btn_bg",
    )
    val iconColor by animateColorAsState(
        targetValue = if (!enabled) {
            if (isDark) Color.White.copy(alpha = 0.25f) else Color.Black.copy(alpha = 0.20f)
        } else {
            if (isDark) Color.White.copy(alpha = 0.55f) else Color.Black.copy(alpha = 0.40f)
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "go_btn_icon",
    )

    Box(
        modifier = Modifier
            .size(18.dp)
            .clip(CircleShape)
            .background(bgColor, CircleShape)
            .hoverable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = LucideChevronRight,
            tint = iconColor,
            modifier = Modifier.size(11.dp),
        )
    }
}
