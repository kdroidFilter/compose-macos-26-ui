package io.github.kdroidfilter.darwinui.components.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.Blue500
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.Green500
import io.github.kdroidfilter.darwinui.theme.Purple500
import io.github.kdroidfilter.darwinui.theme.Yellow500
import io.github.kdroidfilter.darwinui.theme.Zinc400
import io.github.kdroidfilter.darwinui.theme.Zinc500

/**
 * Variants available for [DarwinBadge].
 *
 * Each variant maps to a specific color combination (background and text)
 * following the Darwin UI design system.
 */
enum class DarwinBadgeVariant {
    /** Primary background with onPrimary text. */
    Default,

    /** Secondary background with onSecondary text. */
    Secondary,

    /** Transparent background with a visible border and primary text. */
    Outline,

    /** Destructive tint (10% opacity background, full-color text). */
    Destructive,

    /** Success tint (10% opacity background, full-color text). */
    Success,

    /** Warning tint (10% opacity background, full-color text). */
    Warning,

    /** Info tint (10% opacity background, full-color text). */
    Info,

    /** Green tint indicating a published state. */
    Published,

    /** Yellow tint indicating a draft state. */
    Draft,

    /** Zinc-500 tint indicating an archived state. */
    Archived,

    /** Blue tint indicating a new item. */
    NewBadge,

    /** Zinc-400 tint indicating a read item. */
    Read,

    /** Purple tint indicating a responded item. */
    Responded,

    /** Glass-morphism effect with semi-transparent background. */
    Glass,
}

/**
 * Resolved colors for a badge variant.
 */
private data class BadgeColors(
    val background: Color,
    val text: Color,
    val borderColor: Color?,
)

/**
 * Resolves the colors for the given [DarwinBadgeVariant] from the current theme.
 */
@Composable
private fun resolveColors(variant: DarwinBadgeVariant): BadgeColors {
    val colors = DarwinTheme.colors

    return when (variant) {
        DarwinBadgeVariant.Default -> BadgeColors(
            background = colors.primary,
            text = colors.onPrimary,
            borderColor = null,
        )

        DarwinBadgeVariant.Secondary -> BadgeColors(
            background = colors.secondary,
            text = colors.onSecondary,
            borderColor = null,
        )

        DarwinBadgeVariant.Outline -> BadgeColors(
            background = Color.Transparent,
            text = colors.textPrimary,
            borderColor = colors.border,
        )

        DarwinBadgeVariant.Destructive -> BadgeColors(
            background = colors.destructive.copy(alpha = 0.1f),
            text = colors.destructive,
            borderColor = null,
        )

        DarwinBadgeVariant.Success -> BadgeColors(
            background = colors.success.copy(alpha = 0.1f),
            text = colors.success,
            borderColor = null,
        )

        DarwinBadgeVariant.Warning -> BadgeColors(
            background = colors.warning.copy(alpha = 0.1f),
            text = colors.warning,
            borderColor = null,
        )

        DarwinBadgeVariant.Info -> BadgeColors(
            background = colors.info.copy(alpha = 0.1f),
            text = colors.info,
            borderColor = null,
        )

        DarwinBadgeVariant.Published -> BadgeColors(
            background = Green500.copy(alpha = 0.1f),
            text = Green500,
            borderColor = null,
        )

        DarwinBadgeVariant.Draft -> BadgeColors(
            background = Yellow500.copy(alpha = 0.1f),
            text = Yellow500,
            borderColor = null,
        )

        DarwinBadgeVariant.Archived -> BadgeColors(
            background = Zinc500.copy(alpha = 0.1f),
            text = Zinc500,
            borderColor = null,
        )

        DarwinBadgeVariant.NewBadge -> BadgeColors(
            background = Blue500.copy(alpha = 0.1f),
            text = Blue500,
            borderColor = null,
        )

        DarwinBadgeVariant.Read -> BadgeColors(
            background = Zinc400.copy(alpha = 0.1f),
            text = Zinc400,
            borderColor = null,
        )

        DarwinBadgeVariant.Responded -> BadgeColors(
            background = Purple500.copy(alpha = 0.1f),
            text = Purple500,
            borderColor = null,
        )

        DarwinBadgeVariant.Glass -> BadgeColors(
            background = colors.glassBackground,
            text = colors.textPrimary,
            borderColor = colors.glassBorder,
        )
    }
}

/**
 * A small label component following the Darwin UI design system.
 *
 * Badges are used to highlight status, categories, or counts. They come in 14 variants
 * covering common UI states such as success, warning, destructive, draft, published, etc.
 *
 * Usage:
 * ```
 * DarwinBadge(variant = DarwinBadgeVariant.Success) {
 *     Text("Active")
 * }
 * ```
 *
 * @param variant The visual variant determining background and text colors.
 *                Defaults to [DarwinBadgeVariant.Default].
 * @param modifier Modifier to be applied to the badge container.
 * @param content The composable content displayed inside the badge, typically a [Text].
 */
@Composable
fun DarwinBadge(
    variant: DarwinBadgeVariant = DarwinBadgeVariant.Default,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val badgeColors = resolveColors(variant)
    val shape = DarwinTheme.shapes.full

    val textStyle = DarwinTheme.typography.labelSmall.merge(
        TextStyle(color = badgeColors.text)
    )

    val badgeModifier = modifier
        .clip(shape)
        .background(badgeColors.background, shape)
        .then(
            if (badgeColors.borderColor != null) {
                Modifier.border(width = 1.dp, color = badgeColors.borderColor, shape = shape)
            } else {
                Modifier
            }
        )
        .padding(horizontal = 10.dp, vertical = 2.dp)

    CompositionLocalProvider(LocalDarwinTextStyle provides textStyle) {
        Box(modifier = badgeModifier) {
            content()
        }
    }
}
