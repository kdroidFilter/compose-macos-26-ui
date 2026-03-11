package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.Blue500
import io.github.kdroidfilter.darwinui.theme.Blue600
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.Purple500
import io.github.kdroidfilter.darwinui.theme.Red500
import io.github.kdroidfilter.darwinui.theme.Zinc100
import io.github.kdroidfilter.darwinui.theme.Zinc200
import io.github.kdroidfilter.darwinui.theme.Zinc300
import io.github.kdroidfilter.darwinui.theme.Zinc400
import io.github.kdroidfilter.darwinui.theme.Zinc500
import io.github.kdroidfilter.darwinui.theme.Zinc800
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import io.github.kdroidfilter.darwinui.theme.darwinTween

// ===========================================================================
// ButtonColors — mirrors M3's ButtonColors
// ===========================================================================

@Immutable
class ButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
) {
    fun copy(
        containerColor: Color = this.containerColor,
        contentColor: Color = this.contentColor,
        disabledContainerColor: Color = this.disabledContainerColor,
        disabledContentColor: Color = this.disabledContentColor,
    ) = ButtonColors(containerColor, contentColor, disabledContainerColor, disabledContentColor)

    internal fun resolvedContainerColor(enabled: Boolean) =
        if (enabled) containerColor else disabledContainerColor

    internal fun resolvedContentColor(enabled: Boolean) =
        if (enabled) contentColor else disabledContentColor
}

// ===========================================================================
// ButtonElevation — mirrors M3's ButtonElevation (stub, no shadows in Darwin)
// ===========================================================================

@Stable
class ButtonElevation(
    val defaultElevation: Float = 0f,
    val pressedElevation: Float = 0f,
    val focusedElevation: Float = 0f,
    val hoveredElevation: Float = 0f,
    val disabledElevation: Float = 0f,
)

// ===========================================================================
// ButtonDefaults — mirrors M3's ButtonDefaults
// ===========================================================================

object ButtonDefaults {
    val ContentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
    val TextButtonContentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
    val MinHeight = 36.dp
    val MinWidth = 58.dp

    @Composable
    fun buttonColors(
        containerColor: Color = DarwinTheme.colors.primary,
        contentColor: Color = DarwinTheme.colors.onPrimary,
        disabledContainerColor: Color = containerColor.copy(alpha = 0.5f),
        disabledContentColor: Color = contentColor.copy(alpha = 0.5f),
    ) = ButtonColors(containerColor, contentColor, disabledContainerColor, disabledContentColor)

    @Composable
    fun outlinedButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = DarwinTheme.colors.primary,
        disabledContainerColor: Color = Color.Transparent,
        disabledContentColor: Color = contentColor.copy(alpha = 0.5f),
    ) = ButtonColors(containerColor, contentColor, disabledContainerColor, disabledContentColor)

    @Composable
    fun textButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = DarwinTheme.colors.primary,
        disabledContainerColor: Color = Color.Transparent,
        disabledContentColor: Color = contentColor.copy(alpha = 0.5f),
    ) = ButtonColors(containerColor, contentColor, disabledContainerColor, disabledContentColor)

    @Composable
    fun elevatedButtonColors(
        containerColor: Color = DarwinTheme.colors.surface,
        contentColor: Color = DarwinTheme.colors.primary,
        disabledContainerColor: Color = containerColor.copy(alpha = 0.5f),
        disabledContentColor: Color = contentColor.copy(alpha = 0.5f),
    ) = ButtonColors(containerColor, contentColor, disabledContainerColor, disabledContentColor)

    @Composable
    fun filledTonalButtonColors(
        containerColor: Color = DarwinTheme.colors.secondary,
        contentColor: Color = DarwinTheme.colors.onSecondary,
        disabledContainerColor: Color = containerColor.copy(alpha = 0.5f),
        disabledContentColor: Color = contentColor.copy(alpha = 0.5f),
    ) = ButtonColors(containerColor, contentColor, disabledContainerColor, disabledContentColor)

    @Composable
    fun outlinedButtonBorder(enabled: Boolean = true): BorderStroke = BorderStroke(
        width = 1.dp,
        color = if (enabled) DarwinTheme.colors.border else DarwinTheme.colors.border.copy(alpha = 0.5f),
    )

    fun buttonElevation(
        defaultElevation: Float = 0f,
        pressedElevation: Float = 0f,
        focusedElevation: Float = 0f,
        hoveredElevation: Float = 0f,
        disabledElevation: Float = 0f,
    ) = ButtonElevation(defaultElevation, pressedElevation, focusedElevation, hoveredElevation, disabledElevation)
}

// ===========================================================================
// ButtonSize — Darwin extension (not in M3)
// ===========================================================================

enum class ButtonSize { Small, Default, Large, Icon }

// ===========================================================================
// Internal layout
// ===========================================================================

private enum class ButtonBehavior { Solid, Ghost, Outline, Link }

@Composable
private fun ButtonImpl(
    onClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    shape: Shape,
    colors: ButtonColors,
    border: BorderStroke?,
    contentPadding: PaddingValues,
    interactionSource: MutableInteractionSource,
    behavior: ButtonBehavior = ButtonBehavior.Solid,
    content: @Composable RowScope.() -> Unit,
) {
    val themeColors = DarwinTheme.colors
    val typography = DarwinTheme.typography

    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.97f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "button_scale",
    )
    val alpha by animateFloatAsState(
        targetValue = if (!enabled) 0.5f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "button_alpha",
    )

    // Animated container and content colors for smooth enabled/disabled transitions
    val containerColor by animateColorAsState(
        targetValue = colors.resolvedContainerColor(enabled),
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "buttonContainerColor",
    )
    val contentColor by animateColorAsState(
        targetValue = colors.resolvedContentColor(enabled),
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "buttonContentColor",
    )

    // Animated hover overlay
    val hoverOverlay: Color by animateColorAsState(
        targetValue = when {
            !isHovered || !enabled -> Color.Transparent
            behavior == ButtonBehavior.Ghost -> if (themeColors.isDark) Color.White.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.05f)
            behavior == ButtonBehavior.Outline -> if (themeColors.isDark) Color.White.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.04f)
            behavior == ButtonBehavior.Link -> Color.Transparent
            else -> if (themeColors.isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.06f)
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "buttonHoverOverlay",
    )

    val linkUnderline = behavior == ButtonBehavior.Link && isHovered && enabled

    val textStyle = if (linkUnderline)
        typography.labelMedium.copy(textDecoration = TextDecoration.Underline)
    else
        typography.labelMedium

    val buttonModifier = modifier
        .scale(scale)
        .alpha(alpha)
        .clip(shape)
        .background(containerColor, shape)
        .then(if (border != null) Modifier.border(border.width, border.brush, shape) else Modifier)
        .background(hoverOverlay, shape)
        .hoverable(interactionSource = interactionSource, enabled = enabled)
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            enabled = enabled,
            role = Role.Button,
            onClick = onClick,
        )
        .defaultMinSize(minHeight = ButtonDefaults.MinHeight)
        .padding(contentPadding)

    CompositionLocalProvider(
        LocalDarwinContentColor provides contentColor,
        LocalDarwinTextStyle provides textStyle,
    ) {
        Box(modifier = buttonModifier, contentAlignment = Alignment.Center) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                content = content,
            )
        }
    }
}

// ===========================================================================
// Button — M3-compatible primary API
// ===========================================================================

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = DarwinTheme.shapes.medium,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonImpl(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}

// ===========================================================================
// OutlinedButton
// ===========================================================================

@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = DarwinTheme.shapes.medium,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = ButtonDefaults.outlinedButtonBorder(enabled),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonImpl(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        behavior = ButtonBehavior.Outline,
        content = content,
    )
}

// ===========================================================================
// TextButton
// ===========================================================================

@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = DarwinTheme.shapes.medium,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonImpl(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        behavior = ButtonBehavior.Ghost,
        content = content,
    )
}

// ===========================================================================
// ElevatedButton
// ===========================================================================

@Composable
fun ElevatedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = DarwinTheme.shapes.medium,
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(defaultElevation = 1f),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonImpl(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}

// ===========================================================================
// FilledTonalButton
// ===========================================================================

@Composable
fun FilledTonalButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = DarwinTheme.shapes.medium,
    colors: ButtonColors = ButtonDefaults.filledTonalButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    ButtonImpl(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}

// ===========================================================================
// Darwin-specific convenience wrappers
// ===========================================================================

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !loading,
        colors = ButtonColors(DarwinTheme.colors.accent, Color.White, DarwinTheme.colors.accent.copy(0.5f), Color.White.copy(0.5f)),
    ) {
        if (loading) {
            Spinner(modifier = Modifier.size(16.dp), color = Color.White)
            if (loadingText != null) { Spacer(Modifier.width(8.dp)); Text(loadingText) }
        } else {
            if (leftIcon != null) { leftIcon(); Spacer(Modifier.width(8.dp)) }
            content()
            if (rightIcon != null) { Spacer(Modifier.width(8.dp)); rightIcon() }
        }
    }
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    PrimaryButton(onClick, modifier, enabled, loading, loadingText, leftIcon, rightIcon) { Text(text) }
}

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val isDark = DarwinTheme.colors.isDark
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !loading,
        colors = ButtonColors(
            containerColor = if (isDark) Color.White.copy(alpha = 0.05f) else Zinc100,
            contentColor = if (isDark) Zinc300 else Zinc800,
            disabledContainerColor = if (isDark) Color.White.copy(alpha = 0.025f) else Zinc100.copy(0.5f),
            disabledContentColor = if (isDark) Zinc300.copy(0.5f) else Zinc800.copy(0.5f),
        ),
        border = BorderStroke(1.dp, if (isDark) Color.White.copy(0.10f) else Zinc200),
    ) {
        if (loading) {
            Spinner(modifier = Modifier.size(16.dp), color = if (isDark) Zinc300 else Zinc800)
            if (loadingText != null) { Spacer(Modifier.width(8.dp)); Text(loadingText) }
        } else {
            if (leftIcon != null) { leftIcon(); Spacer(Modifier.width(8.dp)) }
            content()
            if (rightIcon != null) { Spacer(Modifier.width(8.dp)); rightIcon() }
        }
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    SecondaryButton(onClick, modifier, enabled, loading, loadingText, leftIcon, rightIcon) { Text(text) }
}

@Composable
fun DestructiveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !loading,
        colors = ButtonColors(Red500, Color.White, Red500.copy(0.5f), Color.White.copy(0.5f)),
    ) {
        if (loading) {
            Spinner(modifier = Modifier.size(16.dp), color = Color.White)
            if (loadingText != null) { Spacer(Modifier.width(8.dp)); Text(loadingText) }
        } else {
            if (leftIcon != null) { leftIcon(); Spacer(Modifier.width(8.dp)) }
            content()
            if (rightIcon != null) { Spacer(Modifier.width(8.dp)); rightIcon() }
        }
    }
}

@Composable
fun DestructiveButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    DestructiveButton(onClick, modifier, enabled, loading, loadingText, leftIcon, rightIcon) { Text(text) }
}

@Composable
fun SuccessButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val c = DarwinTheme.colors
    Button(
        onClick = onClick, modifier = modifier, enabled = enabled && !loading,
        colors = ButtonColors(c.success, c.onSuccess, c.success.copy(0.5f), c.onSuccess.copy(0.5f)),
    ) {
        if (loading) {
            Spinner(Modifier.size(16.dp), color = c.onSuccess)
            if (loadingText != null) { Spacer(Modifier.width(8.dp)); Text(loadingText) }
        } else {
            if (leftIcon != null) { leftIcon(); Spacer(Modifier.width(8.dp)) }
            content()
            if (rightIcon != null) { Spacer(Modifier.width(8.dp)); rightIcon() }
        }
    }
}

@Composable
fun SuccessButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    SuccessButton(onClick, modifier, enabled, loading, loadingText, leftIcon, rightIcon) { Text(text) }
}

@Composable
fun WarningButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val c = DarwinTheme.colors
    Button(
        onClick = onClick, modifier = modifier, enabled = enabled && !loading,
        colors = ButtonColors(c.warning, c.onWarning, c.warning.copy(0.5f), c.onWarning.copy(0.5f)),
    ) {
        if (loading) {
            Spinner(Modifier.size(16.dp), color = c.onWarning)
            if (loadingText != null) { Spacer(Modifier.width(8.dp)); Text(loadingText) }
        } else {
            if (leftIcon != null) { leftIcon(); Spacer(Modifier.width(8.dp)) }
            content()
            if (rightIcon != null) { Spacer(Modifier.width(8.dp)); rightIcon() }
        }
    }
}

@Composable
fun WarningButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    WarningButton(onClick, modifier, enabled, loading, loadingText, leftIcon, rightIcon) { Text(text) }
}

@Composable
fun InfoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val c = DarwinTheme.colors
    Button(
        onClick = onClick, modifier = modifier, enabled = enabled && !loading,
        colors = ButtonColors(c.info, c.onInfo, c.info.copy(0.5f), c.onInfo.copy(0.5f)),
    ) {
        if (loading) {
            Spinner(Modifier.size(16.dp), color = c.onInfo)
            if (loadingText != null) { Spacer(Modifier.width(8.dp)); Text(loadingText) }
        } else {
            if (leftIcon != null) { leftIcon(); Spacer(Modifier.width(8.dp)) }
            content()
            if (rightIcon != null) { Spacer(Modifier.width(8.dp)); rightIcon() }
        }
    }
}

@Composable
fun InfoButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    InfoButton(onClick, modifier, enabled, loading, loadingText, leftIcon, rightIcon) { Text(text) }
}

@Composable
fun OutlineButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val isDark = DarwinTheme.colors.isDark
    OutlinedButton(
        onClick = onClick, modifier = modifier, enabled = enabled && !loading,
        colors = ButtonColors(
            Color.Transparent, if (isDark) Zinc200 else Zinc800,
            Color.Transparent, if (isDark) Zinc200.copy(0.5f) else Zinc800.copy(0.5f),
        ),
        border = BorderStroke(2.dp, if (isDark) Zinc500 else Zinc400),
    ) {
        if (loading) {
            Spinner(Modifier.size(16.dp), color = if (isDark) Zinc200 else Zinc800)
            if (loadingText != null) { Spacer(Modifier.width(8.dp)); Text(loadingText) }
        } else {
            if (leftIcon != null) { leftIcon(); Spacer(Modifier.width(8.dp)) }
            content()
            if (rightIcon != null) { Spacer(Modifier.width(8.dp)); rightIcon() }
        }
    }
}

@Composable
fun OutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    OutlineButton(onClick, modifier, enabled, loading, loadingText, leftIcon, rightIcon) { Text(text) }
}

@Composable
fun SubtleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val isDark = DarwinTheme.colors.isDark
    TextButton(
        onClick = onClick, modifier = modifier, enabled = enabled && !loading,
        colors = ButtonColors(
            Color.Transparent, if (isDark) Zinc300 else Zinc800,
            Color.Transparent, if (isDark) Zinc300.copy(0.5f) else Zinc800.copy(0.5f),
        ),
    ) {
        if (loading) {
            Spinner(Modifier.size(16.dp), color = if (isDark) Zinc300 else Zinc800)
            if (loadingText != null) { Spacer(Modifier.width(8.dp)); Text(loadingText) }
        } else {
            if (leftIcon != null) { leftIcon(); Spacer(Modifier.width(8.dp)) }
            content()
            if (rightIcon != null) { Spacer(Modifier.width(8.dp)); rightIcon() }
        }
    }
}

@Composable
fun SubtleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    SubtleButton(onClick, modifier, enabled, loading, loadingText, leftIcon, rightIcon) { Text(text) }
}

@Composable
fun HyperlinkButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    val isDark = DarwinTheme.colors.isDark
    val interactionSource = remember { MutableInteractionSource() }
    ButtonImpl(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = DarwinTheme.shapes.medium,
        colors = ButtonColors(
            Color.Transparent, DarwinTheme.colors.accent,
            Color.Transparent, DarwinTheme.colors.accent.copy(0.5f),
        ),
        border = null,
        contentPadding = ButtonDefaults.TextButtonContentPadding,
        interactionSource = interactionSource,
        behavior = ButtonBehavior.Link,
        content = content,
    )
}

@Composable
fun HyperlinkButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    HyperlinkButton(onClick, modifier, enabled) { Text(text) }
}

@Composable
fun AccentButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick, modifier = modifier, enabled = enabled && !loading,
        colors = ButtonColors(Purple500, Color.White, Purple500.copy(0.5f), Color.White.copy(0.5f)),
    ) {
        if (loading) {
            Spinner(Modifier.size(16.dp), color = Color.White)
            if (loadingText != null) { Spacer(Modifier.width(8.dp)); Text(loadingText) }
        } else {
            if (leftIcon != null) { leftIcon(); Spacer(Modifier.width(8.dp)) }
            content()
            if (rightIcon != null) { Spacer(Modifier.width(8.dp)); rightIcon() }
        }
    }
}

@Composable
fun AccentButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    AccentButton(onClick, modifier, enabled, loading, loadingText, leftIcon, rightIcon) { Text(text) }
}

// ===========================================================================
// Preview
// ===========================================================================

@Preview
@Composable
private fun ButtonPreview() {
    DarwinTheme {
        Button(onClick = {}) { Text("Button") }
    }
}
