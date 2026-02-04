package io.github.kdroidfilter.darwinui.components.button

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.spinner.DarwinSpinner
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.theme.Blue500
import io.github.kdroidfilter.darwinui.theme.Blue600
import io.github.kdroidfilter.darwinui.theme.DarwinColors
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.DarwinTypography
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
import io.github.kdroidfilter.darwinui.theme.Zinc900
import io.github.kdroidfilter.darwinui.theme.darwinTween

enum class DarwinButtonVariant {
    Default,
    Primary,
    Secondary,
    Success,
    Warning,
    Info,
    Destructive,
    Outline,
    Ghost,
    Link,
    Accent,
}

enum class DarwinButtonSize {
    /** Small: height 32dp, smaller text, tighter padding */
    Small,

    /** Default: height 40dp */
    Default,

    /** Large: height 48dp, larger text, wider padding */
    Large,

    /** Icon: square 40dp, centered content */
    Icon,
}

// ===========================================================================
// Internal resolved style holder
// ===========================================================================

private data class ButtonColors(
    val background: Color,
    val contentColor: Color,
    val borderColor: Color?,
    val borderWidth: Dp = 1.dp,
)

private data class ButtonDimensions(
    val height: Dp,
    val horizontalPadding: Dp,
    val iconSpacing: Dp,
    val textStyle: TextStyle,
    val indicatorSize: Dp,
    val indicatorStrokeWidth: Dp,
)

// ===========================================================================
// Composable
// ===========================================================================

@Composable
fun DarwinButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: DarwinButtonVariant = DarwinButtonVariant.Default,
    size: DarwinButtonSize = DarwinButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes
    val typography = DarwinTheme.typography

    // ---- Resolve colors per variant ----
    val buttonColors = resolveButtonColors(variant, colors)

    // ---- Resolve dimensions per size ----
    val dimensions = resolveButtonDimensions(size, typography)

    // ---- Shape ----
    val shape: Shape = when (size) {
        DarwinButtonSize.Icon -> shapes.medium
        else -> shapes.medium
    }

    // ---- Interaction ----
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    val isInteractive = enabled && !loading

    // Animated scale for press feedback (macOS-like subtle press-in)
    val scale by animateFloatAsState(
        targetValue = if (isPressed && isInteractive) 0.97f else 1f,
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "darwin_button_scale",
    )

    // Disabled opacity
    val alpha by animateFloatAsState(
        targetValue = if (!enabled) 0.5f else 1f,
        animationSpec = darwinTween(DarwinDuration.Normal),
        label = "darwin_button_alpha",
    )

    // ---- Hover overlay ----
    val hoverOverlay: Color = when {
        !isHovered || !isInteractive -> Color.Transparent
        variant == DarwinButtonVariant.Ghost -> {
            if (colors.isDark) Color.White.copy(alpha = 0.05f)
            else Color.Black.copy(alpha = 0.05f)
        }
        variant == DarwinButtonVariant.Outline -> {
            if (colors.isDark) Color.White.copy(alpha = 0.05f)
            else Color.Black.copy(alpha = 0.04f)
        }
        variant == DarwinButtonVariant.Link -> Color.Transparent
        else -> {
            if (colors.isDark) Color.White.copy(alpha = 0.08f)
            else Color.Black.copy(alpha = 0.06f)
        }
    }

    // ---- Link underline on hover ----
    val linkUnderline = variant == DarwinButtonVariant.Link && isHovered && isInteractive

    // ---- Build modifier chain ----
    val buttonModifier = modifier
        .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier)
        .scale(scale)
        .alpha(alpha)
        .clip(shape)
        .background(buttonColors.background, shape)
        .then(
            if (buttonColors.borderColor != null) {
                Modifier.border(buttonColors.borderWidth, buttonColors.borderColor, shape)
            } else {
                Modifier
            }
        )
        // Hover overlay drawn on top of background
        .background(hoverOverlay, shape)
        .hoverable(interactionSource = interactionSource, enabled = isInteractive)
        .clickable(
            interactionSource = interactionSource,
            indication = null, // We handle our own visual feedback
            enabled = isInteractive,
            role = Role.Button,
            onClick = onClick,
        )
        .then(
            when (size) {
                DarwinButtonSize.Icon -> Modifier.size(36.dp)
                else -> Modifier
                    .defaultMinSize(minHeight = dimensions.height)
                    .padding(
                        PaddingValues(
                            horizontal = dimensions.horizontalPadding,
                            vertical = 0.dp,
                        )
                    )
            }
        )

    Box(
        modifier = buttonModifier,
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (loading) {
                DarwinSpinner(
                    modifier = Modifier.size(dimensions.indicatorSize),
                    color = buttonColors.contentColor,
                    strokeWidth = dimensions.indicatorStrokeWidth,
                )

                if (loadingText != null) {
                    Spacer(modifier = Modifier.width(dimensions.iconSpacing))
                    DarwinText(
                        text = loadingText,
                        style = dimensions.textStyle,
                        color = buttonColors.contentColor,
                    )
                }
            } else {
                val textStyle = if (linkUnderline) {
                    dimensions.textStyle.copy(textDecoration = TextDecoration.Underline)
                } else {
                    dimensions.textStyle
                }

                CompositionLocalProvider(
                    LocalDarwinContentColor provides buttonColors.contentColor,
                    LocalDarwinTextStyle provides textStyle,
                ) {
                    if (leftIcon != null) {
                        leftIcon()
                        Spacer(modifier = Modifier.width(dimensions.iconSpacing))
                    }

                    content()

                    if (rightIcon != null) {
                        Spacer(modifier = Modifier.width(dimensions.iconSpacing))
                        rightIcon()
                    }
                }
            }
        }
    }
}

// ===========================================================================
// Convenience overload accepting a plain string label
// ===========================================================================

/**
 * Convenience overload of [DarwinButton] that accepts a plain [String] label.
 */
@Composable
fun DarwinButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: DarwinButtonVariant = DarwinButtonVariant.Default,
    size: DarwinButtonSize = DarwinButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    DarwinButton(
        onClick = onClick,
        modifier = modifier,
        variant = variant,
        size = size,
        enabled = enabled,
        loading = loading,
        loadingText = loadingText,
        fullWidth = fullWidth,
        leftIcon = leftIcon,
        rightIcon = rightIcon,
    ) {
        DarwinText(text = text)
    }
}

// ===========================================================================
// Internal helpers
// ===========================================================================

@Composable
private fun resolveButtonColors(
    variant: DarwinButtonVariant,
    colors: DarwinColors,
): ButtonColors {

    return when (variant) {

        DarwinButtonVariant.Default -> ButtonColors(
            background = if (colors.isDark) Color.White.copy(alpha = 0.10f) else Zinc200,
            contentColor = if (colors.isDark) Zinc100 else Zinc900,
            borderColor = if (colors.isDark) Color.White.copy(alpha = 0.10f) else Zinc300,
        )

        DarwinButtonVariant.Primary -> ButtonColors(
            background = Blue500,
            contentColor = Color.White,
            borderColor = null,
        )

        DarwinButtonVariant.Secondary -> ButtonColors(
            background = if (colors.isDark) Color.White.copy(alpha = 0.05f) else Zinc100,
            contentColor = if (colors.isDark) Zinc300 else Zinc800,
            borderColor = if (colors.isDark) Color.White.copy(alpha = 0.10f) else Zinc200,
        )

        DarwinButtonVariant.Success -> ButtonColors(
            background = colors.success,
            contentColor = Color.White,
            borderColor = null,
        )

        DarwinButtonVariant.Warning -> ButtonColors(
            background = colors.warning,
            contentColor = Color.Black,
            borderColor = null,
        )

        DarwinButtonVariant.Info -> ButtonColors(
            background = colors.info,
            contentColor = Color.White,
            borderColor = null,
        )

        DarwinButtonVariant.Destructive -> ButtonColors(
            background = Red500,
            contentColor = Color.White,
            borderColor = null,
        )

        DarwinButtonVariant.Outline -> ButtonColors(
            background = Color.Transparent,
            contentColor = if (colors.isDark) Zinc200 else Zinc800,
            borderColor = if (colors.isDark) Zinc500 else Zinc400,
            borderWidth = 2.dp,
        )

        DarwinButtonVariant.Ghost -> ButtonColors(
            background = Color.Transparent,
            contentColor = if (colors.isDark) Zinc300 else Zinc800,
            borderColor = null,
        )

        DarwinButtonVariant.Link -> ButtonColors(
            background = Color.Transparent,
            contentColor = if (colors.isDark) Color(0xFF60A5FA) else Blue600,
            borderColor = null,
        )

        DarwinButtonVariant.Accent -> ButtonColors(
            background = Purple500,
            contentColor = Color.White,
            borderColor = null,
        )
    }
}

@Composable
private fun resolveButtonDimensions(
    size: DarwinButtonSize,
    typography: DarwinTypography,
): ButtonDimensions {

    return when (size) {
        DarwinButtonSize.Small -> ButtonDimensions(
            height = 32.dp,           // h-8
            horizontalPadding = 12.dp, // px-3
            iconSpacing = 6.dp,
            textStyle = typography.labelSmall,
            indicatorSize = 14.dp,
            indicatorStrokeWidth = 1.5.dp,
        )
        DarwinButtonSize.Default -> ButtonDimensions(
            height = 36.dp,           // h-9
            horizontalPadding = 16.dp, // px-4
            iconSpacing = 8.dp,
            textStyle = typography.labelMedium,
            indicatorSize = 16.dp,
            indicatorStrokeWidth = 2.dp,
        )
        DarwinButtonSize.Large -> ButtonDimensions(
            height = 40.dp,           // h-10
            horizontalPadding = 32.dp, // px-8
            iconSpacing = 8.dp,
            textStyle = typography.labelLarge,
            indicatorSize = 18.dp,
            indicatorStrokeWidth = 2.dp,
        )
        DarwinButtonSize.Icon -> ButtonDimensions(
            height = 36.dp,           // h-9 w-9
            horizontalPadding = 0.dp,
            iconSpacing = 0.dp,
            textStyle = typography.labelMedium,
            indicatorSize = 16.dp,
            indicatorStrokeWidth = 2.dp,
        )
    }
}

@Preview
@Composable
private fun DarwinButtonPreview() {
    DarwinTheme {
        DarwinButton(onClick = {}) { DarwinText("Button") }
    }
}
