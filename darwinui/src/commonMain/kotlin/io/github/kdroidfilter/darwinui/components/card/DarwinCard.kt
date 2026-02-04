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
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.glassBorderOrDefault
import io.github.kdroidfilter.darwinui.theme.glassOrDefault

/**
 * A card container that follows the Darwin UI design system.
 *
 * Cards are surfaces that display content and actions about a single topic.
 * They use a compound-component pattern with [DarwinCardHeader], [DarwinCardContent],
 * and [DarwinCardFooter] as children.
 *
 * @param glass When true, applies a glass-morphism effect (semi-transparent background with
 *              a subtle border). When false, uses the standard card colors.
 * @param shape The shape of the card. Defaults to [DarwinTheme.shapes.large] (12dp rounded).
 * @param modifier Modifier to be applied to the card container.
 * @param content The card content, typically composed of [DarwinCardHeader],
 *                [DarwinCardContent], and [DarwinCardFooter].
 */
@Composable
fun DarwinCard(
    modifier: Modifier = Modifier,
    glass: Boolean = false,
    shape: Shape = DarwinTheme.shapes.large,
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
 * Typically contains a [DarwinCardTitle] and optionally a [DarwinCardDescription].
 * Rendered as a column with top and horizontal padding.
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
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        content = content,
    )
}

/**
 * Title text for a [DarwinCardHeader].
 *
 * Displays text using the headlineSmall typography style (semibold, 18sp).
 *
 * @param modifier Modifier to be applied to the title.
 * @param content The composable content for the title, typically a [Text] composable.
 */
@Composable
fun DarwinCardTitle(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val style = DarwinTheme.typography.headlineSmall.merge(
        TextStyle(
            color = DarwinTheme.colors.cardForeground,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
        )
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
 * Displays secondary text using the bodySmall typography style with the
 * textSecondary color.
 *
 * @param modifier Modifier to be applied to the description.
 * @param content The composable content for the description, typically a [Text] composable.
 */
@Composable
fun DarwinCardDescription(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val style = DarwinTheme.typography.bodySmall.merge(
        TextStyle(color = DarwinTheme.colors.textSecondary)
    )
    CompositionLocalProvider(
        LocalDarwinTextStyle provides style,
    ) {
        Box(modifier = modifier.padding(top = 4.dp)) {
            content()
        }
    }
}

/**
 * Content section of a [DarwinCard].
 *
 * The main body area of the card with horizontal and vertical padding.
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
            .padding(horizontal = 16.dp, vertical = 8.dp),
        content = content,
    )
}

/**
 * Footer section of a [DarwinCard].
 *
 * Typically used to hold action buttons, aligned to the end of the row.
 *
 * @param modifier Modifier to be applied to the footer.
 * @param content The footer content, usually [DarwinCardAction] composables.
 */
@Composable
fun DarwinCardFooter(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

/**
 * A slot for action buttons within [DarwinCardFooter].
 *
 * Provides a simple wrapper for placing interactive elements in the card footer.
 *
 * @param modifier Modifier to be applied to the action slot.
 * @param content The action content, typically a button composable.
 */
@Composable
fun DarwinCardAction(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier) {
        content()
    }
}
