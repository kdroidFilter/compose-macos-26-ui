package io.github.kdroidfilter.darwinui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.glassBorderOrDefault
import io.github.kdroidfilter.darwinui.theme.glassOrDefault

/**
 * A card container that follows the Darwin UI design system.
 *
 * Pixel-perfect match with the React darwin-ui Card component:
 * - `rounded-2xl border text-card-foreground transition-colors duration-150`
 * - Default: `bg-card border-border hover:border-border/80`
 * - Glass: `bg-white/80 dark:bg-zinc-900/80 backdrop-blur-md border-white/20 dark:border-white/10`
 *
 * @param glass When true, applies a glass-morphism effect.
 * @param shape The shape of the card. Defaults to [DarwinTheme.shapes.extraLarge] (16dp = rounded-2xl).
 * @param modifier Modifier to be applied to the card container.
 * @param content The card content, typically composed of [DarwinCardHeader],
 *                [DarwinCardContent], and [DarwinCardFooter].
 */
@Composable
fun DarwinCard(
    modifier: Modifier = Modifier,
    glass: Boolean = false,
    shape: Shape = DarwinTheme.shapes.extraLarge, // rounded-2xl = 16dp
    content: @Composable ColumnScope.() -> Unit,
) {
    val backgroundColor = glassOrDefault(glass, DarwinTheme.colors.card)
    val borderColor = glassBorderOrDefault(glass, DarwinTheme.colors.border)

    Column(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor, shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .fillMaxWidth(),
        content = content,
    )
}

/**
 * Header section of a [DarwinCard].
 *
 * Pixel-perfect match with React CardHeader:
 * - `flex flex-col space-y-1.5 p-6`
 *
 * @param modifier Modifier to be applied to the header.
 * @param content The header content, usually [DarwinCardTitle] and [DarwinCardDescription].
 */
@Composable
fun DarwinCardHeader(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp), // p-6 = 24dp
        verticalArrangement = Arrangement.spacedBy(6.dp), // space-y-1.5 = 6dp
        content = content,
    )
}

/**
 * Title text for a [DarwinCardHeader].
 *
 * Pixel-perfect match with React CardTitle:
 * - `font-semibold leading-none tracking-tight text-card-foreground`
 * - h3 with Tailwind Preflight inherits body font-size (1rem = 16sp)
 *
 * @param modifier Modifier to be applied to the title.
 * @param content The composable content for the title, typically a [Text] composable.
 */
@Composable
fun DarwinCardTitle(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val style = TextStyle(
        color = DarwinTheme.colors.cardForeground,
        fontWeight = FontWeight.SemiBold,  // font-semibold
        fontSize = 16.sp,                  // h3 with Preflight = text-base = 16sp
        lineHeight = 16.sp,               // leading-none = lineHeight == fontSize
        letterSpacing = (-0.025).em,       // tracking-tight
    )
    CompositionLocalProvider(
        LocalDarwinTextStyle provides style,
    ) {
        Box(modifier = modifier) {
            content()
        }
    }
}

/**
 * Description text for a [DarwinCardHeader].
 *
 * Pixel-perfect match with React CardDescription:
 * - `text-sm text-muted-foreground`
 *
 * @param modifier Modifier to be applied to the description.
 * @param content The composable content for the description, typically a [Text] composable.
 */
@Composable
fun DarwinCardDescription(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    // text-sm = 14sp, text-muted-foreground
    val style = DarwinTheme.typography.bodyMedium.merge(
        TextStyle(color = DarwinTheme.colors.mutedForeground)
    )
    CompositionLocalProvider(
        LocalDarwinTextStyle provides style,
    ) {
        Box(modifier = modifier) {
            content()
        }
    }
}

/**
 * Content section of a [DarwinCard].
 *
 * Pixel-perfect match with React CardContent:
 * - `p-6 pt-0`
 *
 * @param modifier Modifier to be applied to the content area.
 * @param content The composable content to display in the card body.
 */
@Composable
fun DarwinCardContent(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp), // p-6 pt-0
        content = content,
    )
}

/**
 * Footer section of a [DarwinCard].
 *
 * Pixel-perfect match with React CardFooter:
 * - `flex items-center p-6 pt-0`
 *
 * @param modifier Modifier to be applied to the footer.
 * @param content The footer content, usually action buttons.
 */
@Composable
fun DarwinCardFooter(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp), // p-6 pt-0
        horizontalArrangement = Arrangement.spacedBy(8.dp), // gap-2 (commonly added via className in React)
        verticalAlignment = Alignment.CenterVertically, // items-center
        content = content,
    )
}

/**
 * A slot for action buttons within a [DarwinCard].
 *
 * Pixel-perfect match with React CardAction:
 * - `flex items-center gap-2 p-6 pt-0`
 *
 * @param modifier Modifier to be applied to the action row.
 * @param content The action content, typically button composables.
 */
@Composable
fun DarwinCardAction(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp), // p-6 pt-0
        horizontalArrangement = Arrangement.spacedBy(8.dp), // gap-2 = 8dp
        verticalAlignment = Alignment.CenterVertically,     // items-center
        content = content,
    )
}
