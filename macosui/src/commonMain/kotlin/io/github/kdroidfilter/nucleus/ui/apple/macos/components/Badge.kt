package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalTextStyle

enum class BadgeVariant {
    Default,
    Secondary,
    Outline,
    Destructive,
    Success,
    Warning,
    Info,
    Published,
    Draft,
    Archived,
    NewBadge,
    Read,
    Responded,
}

private data class BadgeColors(
    val background: Color,
    val text: Color,
    val borderColor: Color?,
)

@Composable
private fun resolveColors(variant: BadgeVariant): BadgeColors {
    val cs = MacosTheme.colorScheme

    val subtleBg = if (cs.isDark) Color.White.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.05f)
    val neutralText = cs.textSecondary

    return when (variant) {
        BadgeVariant.Default -> BadgeColors(
            background = subtleBg,
            text = neutralText,
            borderColor = if (cs.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f),
        )

        BadgeVariant.Secondary -> BadgeColors(
            background = subtleBg,
            text = neutralText,
            borderColor = Color.Transparent,
        )

        BadgeVariant.Outline -> BadgeColors(
            background = Color.Transparent,
            text = neutralText,
            borderColor = if (cs.isDark) Color.White.copy(alpha = 0.20f) else Color.Black.copy(alpha = 0.20f),
        )

        BadgeVariant.Destructive -> BadgeColors(
            background = cs.destructive.copy(alpha = 0.15f),
            text = cs.destructive,
            borderColor = Color.Transparent,
        )

        BadgeVariant.Success, BadgeVariant.Published, BadgeVariant.Responded -> BadgeColors(
            background = cs.success.copy(alpha = 0.15f),
            text = cs.success,
            borderColor = Color.Transparent,
        )

        BadgeVariant.Warning, BadgeVariant.Draft, BadgeVariant.Read -> BadgeColors(
            background = cs.warning.copy(alpha = 0.15f),
            text = if (cs.isDark) cs.warning else Color(0xFFCC9900),
            borderColor = Color.Transparent,
        )

        BadgeVariant.Info, BadgeVariant.NewBadge -> BadgeColors(
            background = cs.info.copy(alpha = 0.15f),
            text = cs.info,
            borderColor = Color.Transparent,
        )

        BadgeVariant.Archived -> BadgeColors(
            background = subtleBg,
            text = neutralText,
            borderColor = Color.Transparent,
        )
    }
}

@Composable
fun Badge(
    variant: BadgeVariant = BadgeVariant.Default,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val badgeColors = resolveColors(variant)
    val shape = MacosTheme.shapes.full

    val textStyle = MacosTheme.typography.caption2.merge(
        TextStyle(color = badgeColors.text)
    )

    val badgeModifier = modifier
        .clip(shape)
        .background(badgeColors.background, shape)
        .then(
            if (badgeColors.borderColor != null && badgeColors.borderColor != Color.Transparent) {
                Modifier.border(width = 1.dp, color = badgeColors.borderColor, shape = shape)
            } else {
                Modifier.border(width = 1.dp, color = Color.Transparent, shape = shape)
            }
        )
        .padding(horizontal = 10.dp, vertical = 2.dp)

    CompositionLocalProvider(LocalTextStyle provides textStyle) {
        Box(modifier = badgeModifier) {
            content()
        }
    }
}

// ===========================================================================
// M3-style Badge overload
// ===========================================================================

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    containerColor: Color = MacosTheme.colorScheme.destructive,
    contentColor: Color = MacosTheme.colorScheme.onDestructive,
    content: (@Composable () -> Unit)? = null,
) {
    val shape = MacosTheme.shapes.full
    if (content == null) {
        Box(
            modifier = modifier
                .clip(shape)
                .background(containerColor, shape)
                .size(8.dp),
        )
    } else {
        val textStyle = MacosTheme.typography.caption2.merge(TextStyle(color = contentColor))
        Box(
            modifier = modifier
                .clip(shape)
                .background(containerColor, shape)
                .padding(horizontal = 6.dp, vertical = 2.dp),
        ) {
            CompositionLocalProvider(LocalTextStyle provides textStyle) {
                content()
            }
        }
    }
}

@Preview
@Composable
private fun BadgePreview() {
    MacosTheme {
        Badge { Text("Badge") }
    }
}
