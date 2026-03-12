package io.github.kdroidfilter.darwinui.components

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
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.theme.Amber400
import io.github.kdroidfilter.darwinui.theme.Amber500
import io.github.kdroidfilter.darwinui.theme.Amber600
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.Emerald400
import io.github.kdroidfilter.darwinui.theme.Emerald500
import io.github.kdroidfilter.darwinui.theme.Emerald600
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.Red400
import io.github.kdroidfilter.darwinui.theme.Red500
import io.github.kdroidfilter.darwinui.theme.Red600
import io.github.kdroidfilter.darwinui.theme.Sky400
import io.github.kdroidfilter.darwinui.theme.Sky500
import io.github.kdroidfilter.darwinui.theme.Sky600
import io.github.kdroidfilter.darwinui.theme.Zinc300
import io.github.kdroidfilter.darwinui.theme.Zinc700

enum class BadgeVariant {
    /** Subtle bg (black/5 or white/5), subtle border (black/10 or white/10), zinc text. */
    Default,

    /** Subtle bg (black/5 or white/5), transparent border, zinc text. */
    Secondary,

    /** Transparent bg, visible border (black/20 or white/20), zinc text. */
    Outline,

    /** Red-500/15 bg, transparent border, red-600 or red-400 text. */
    Destructive,

    /** Emerald-500/15 bg, transparent border, emerald-600 or emerald-400 text. */
    Success,

    /** Amber-500/15 bg, transparent border, amber-600 or amber-400 text. */
    Warning,

    /** Sky-500/15 bg, transparent border, sky-600 or sky-400 text. */
    Info,

    /** Same as Success: emerald-500/15 bg, emerald-600 or emerald-400 text. */
    Published,

    /** Same as Warning: amber-500/15 bg, amber-600 or amber-400 text. */
    Draft,

    /** Same as Secondary: subtle bg, transparent border, zinc text. */
    Archived,

    /** Same as Info: sky-500/15 bg, sky-600 or sky-400 text. */
    NewBadge,

    /** Same as Warning/Draft: amber-500/15 bg, amber-600 or amber-400 text. */
    Read,

    /** Same as Success/Published: emerald-500/15 bg, emerald-600 or emerald-400 text. */
    Responded,
}

/**
 * Resolved colors for a badge variant.
 */
private data class BadgeColors(
    val background: Color,
    val text: Color,
    val borderColor: Color?,
)

@Composable
private fun resolveColors(variant: BadgeVariant): BadgeColors {
    val isDark = DarwinTheme.colorScheme.isDark

    val subtleBg = if (isDark) Color.White.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.05f)
    val zincText = if (isDark) Zinc300 else Zinc700

    return when (variant) {
        // default: border-black/10 dark:border-white/10 bg-black/5 dark:bg-white/5 text-zinc-700 dark:text-zinc-300
        BadgeVariant.Default -> BadgeColors(
            background = subtleBg,
            text = zincText,
            borderColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f),
        )

        // secondary: border-transparent bg-black/5 dark:bg-white/5 text-zinc-700 dark:text-zinc-300
        BadgeVariant.Secondary -> BadgeColors(
            background = subtleBg,
            text = zincText,
            borderColor = Color.Transparent,
        )

        // outline: border-black/20 dark:border-white/20 text-zinc-700 dark:text-zinc-300 bg-transparent
        BadgeVariant.Outline -> BadgeColors(
            background = Color.Transparent,
            text = zincText,
            borderColor = if (isDark) Color.White.copy(alpha = 0.20f) else Color.Black.copy(alpha = 0.20f),
        )

        // destructive: border-transparent bg-red-500/15 text-red-600 dark:text-red-400
        BadgeVariant.Destructive -> BadgeColors(
            background = Red500.copy(alpha = 0.15f),
            text = if (isDark) Red400 else Red600,
            borderColor = Color.Transparent,
        )

        // success: border-transparent bg-emerald-500/15 text-emerald-600 dark:text-emerald-400
        BadgeVariant.Success -> BadgeColors(
            background = Emerald500.copy(alpha = 0.15f),
            text = if (isDark) Emerald400 else Emerald600,
            borderColor = Color.Transparent,
        )

        // published: border-transparent bg-emerald-500/15 text-emerald-600 dark:text-emerald-400
        BadgeVariant.Published -> BadgeColors(
            background = Emerald500.copy(alpha = 0.15f),
            text = if (isDark) Emerald400 else Emerald600,
            borderColor = Color.Transparent,
        )

        // responded: border-transparent bg-emerald-500/15 text-emerald-600 dark:text-emerald-400
        BadgeVariant.Responded -> BadgeColors(
            background = Emerald500.copy(alpha = 0.15f),
            text = if (isDark) Emerald400 else Emerald600,
            borderColor = Color.Transparent,
        )

        // warning: border-transparent bg-amber-500/15 text-amber-600 dark:text-amber-400
        BadgeVariant.Warning -> BadgeColors(
            background = Amber500.copy(alpha = 0.15f),
            text = if (isDark) Amber400 else Amber600,
            borderColor = Color.Transparent,
        )

        // draft: border-transparent bg-amber-500/15 text-amber-600 dark:text-amber-400
        BadgeVariant.Draft -> BadgeColors(
            background = Amber500.copy(alpha = 0.15f),
            text = if (isDark) Amber400 else Amber600,
            borderColor = Color.Transparent,
        )

        // read: border-transparent bg-amber-500/15 text-amber-600 dark:text-amber-400
        BadgeVariant.Read -> BadgeColors(
            background = Amber500.copy(alpha = 0.15f),
            text = if (isDark) Amber400 else Amber600,
            borderColor = Color.Transparent,
        )

        // info: border-transparent bg-sky-500/15 text-sky-600 dark:text-sky-400
        BadgeVariant.Info -> BadgeColors(
            background = Sky500.copy(alpha = 0.15f),
            text = if (isDark) Sky400 else Sky600,
            borderColor = Color.Transparent,
        )

        // new: border-transparent bg-sky-500/15 text-sky-600 dark:text-sky-400
        BadgeVariant.NewBadge -> BadgeColors(
            background = Sky500.copy(alpha = 0.15f),
            text = if (isDark) Sky400 else Sky600,
            borderColor = Color.Transparent,
        )

        // archived: border-transparent bg-black/5 dark:bg-white/5 text-zinc-700 dark:text-zinc-300
        BadgeVariant.Archived -> BadgeColors(
            background = subtleBg,
            text = zincText,
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
    val shape = DarwinTheme.shapes.full // rounded-full

    val textStyle = DarwinTheme.typography.caption2.merge(
        TextStyle(color = badgeColors.text)
    )

    // rounded-full border px-2.5 py-0.5
    val badgeModifier = modifier
        .clip(shape)
        .background(badgeColors.background, shape)
        .then(
            if (badgeColors.borderColor != null && badgeColors.borderColor != Color.Transparent) {
                Modifier.border(width = 1.dp, color = badgeColors.borderColor, shape = shape)
            } else {

                // We add a transparent border to keep consistent sizing.
                Modifier.border(width = 1.dp, color = Color.Transparent, shape = shape)
            }
        )
        .padding(horizontal = 10.dp, vertical = 2.dp) // px-2.5 = 10dp, py-0.5 = 2dp

    CompositionLocalProvider(LocalDarwinTextStyle provides textStyle) {
        Box(modifier = badgeModifier) {
            content()
        }
    }
}

// ===========================================================================
// M3-style Badge overload
// ===========================================================================

/**
 * A simple badge — mirrors Material3's Badge API.
 *
 * For the full Darwin Badge with variant support, use [Badge] with [BadgeVariant].
 *
 * @param modifier Modifier for the badge container.
 * @param containerColor Background color of the badge.
 * @param contentColor Text/content color.
 * @param content Optional content (number, label). If null, renders a dot.
 */
@Composable
fun Badge(
    modifier: Modifier = Modifier,
    containerColor: Color = DarwinTheme.colorScheme.destructive,
    contentColor: Color = DarwinTheme.colorScheme.onDestructive,
    content: (@Composable () -> Unit)? = null,
) {
    val shape = DarwinTheme.shapes.full
    if (content == null) {
        Box(
            modifier = modifier
                .clip(shape)
                .background(containerColor, shape)
                .size(8.dp),
        )
    } else {
        val textStyle = DarwinTheme.typography.caption2.merge(TextStyle(color = contentColor))
        Box(
            modifier = modifier
                .clip(shape)
                .background(containerColor, shape)
                .padding(horizontal = 6.dp, vertical = 2.dp),
        ) {
            CompositionLocalProvider(LocalDarwinTextStyle provides textStyle) {
                content()
            }
        }
    }
}

@Preview
@Composable
private fun BadgePreview() {
    DarwinTheme {
        Badge { Text("Badge") }
    }
}
