package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalTextStyle

// ===========================================================================
// CardColors — mirrors M3's CardColors
// ===========================================================================

@Immutable
class CardColors(
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
    ) = CardColors(containerColor, contentColor, disabledContainerColor, disabledContentColor)
}

// ===========================================================================
// CardDefaults — mirrors M3's CardDefaults
// ===========================================================================

object CardDefaults {
    @Composable
    fun cardColors(
        containerColor: Color = MacosTheme.colorScheme.card,
        contentColor: Color = MacosTheme.colorScheme.cardForeground,
        disabledContainerColor: Color = containerColor.copy(alpha = 0.5f),
        disabledContentColor: Color = contentColor.copy(alpha = 0.5f),
    ) = CardColors(containerColor, contentColor, disabledContainerColor, disabledContentColor)

    @Composable
    fun elevatedCardColors(
        containerColor: Color = MacosTheme.colorScheme.surfaceContainerLow,
        contentColor: Color = MacosTheme.colorScheme.onSurface,
        disabledContainerColor: Color = containerColor.copy(alpha = 0.5f),
        disabledContentColor: Color = contentColor.copy(alpha = 0.5f),
    ) = CardColors(containerColor, contentColor, disabledContainerColor, disabledContentColor)

    @Composable
    fun outlinedCardColors(
        containerColor: Color = MacosTheme.colorScheme.surface,
        contentColor: Color = MacosTheme.colorScheme.onSurface,
        disabledContainerColor: Color = containerColor.copy(alpha = 0.5f),
        disabledContentColor: Color = contentColor.copy(alpha = 0.5f),
    ) = CardColors(containerColor, contentColor, disabledContainerColor, disabledContentColor)

    @Composable
    fun outlinedCardBorder(enabled: Boolean = true): BorderStroke = BorderStroke(
        width = 1.dp,
        color = if (enabled) MacosTheme.colorScheme.outline else MacosTheme.colorScheme.outline.copy(alpha = 0.5f),
    )
}

// ===========================================================================
// Card — M3-compatible
// ===========================================================================

@Composable
fun Card(
    modifier: Modifier = Modifier,
    shape: Shape = MacosTheme.shapes.extraLarge,
    colors: CardColors = CardDefaults.cardColors(),
    border: BorderStroke? = BorderStroke(1.dp, MacosTheme.colorScheme.outline),
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .clip(shape)
            .background(colors.containerColor, shape)
            .then(if (border != null) Modifier.border(border.width, border.brush, shape) else Modifier)
            .fillMaxWidth(),
    ) {
        content()
    }
}

// ===========================================================================
// ElevatedCard — M3-compatible
// ===========================================================================

@Composable
fun ElevatedCard(
    modifier: Modifier = Modifier,
    shape: Shape = MacosTheme.shapes.extraLarge,
    colors: CardColors = CardDefaults.elevatedCardColors(),
    border: BorderStroke? = null,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .shadow(elevation = 2.dp, shape = shape, clip = false)
            .clip(shape)
            .background(colors.containerColor, shape)
            .then(if (border != null) Modifier.border(border.width, border.brush, shape) else Modifier)
            .fillMaxWidth(),
    ) {
        content()
    }
}

// ===========================================================================
// OutlinedCard — M3-compatible
// ===========================================================================

@Composable
fun OutlinedCard(
    modifier: Modifier = Modifier,
    shape: Shape = MacosTheme.shapes.extraLarge,
    colors: CardColors = CardDefaults.outlinedCardColors(),
    border: BorderStroke? = CardDefaults.outlinedCardBorder(),
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .clip(shape)
            .background(colors.containerColor, shape)
            .then(if (border != null) Modifier.border(border.width, border.brush, shape) else Modifier)
            .fillMaxWidth(),
    ) {
        content()
    }
}

// ===========================================================================
// macOS card sub-components
// ===========================================================================

@Composable
fun CardHeader(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        content = content,
    )
}

@Composable
fun CardTitle(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val style = TextStyle(
        color = MacosTheme.colorScheme.cardForeground,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = (-0.025).em,
    )
    CompositionLocalProvider(LocalTextStyle provides style) {
        Box(modifier = modifier) { content() }
    }
}

@Composable
fun CardDescription(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val style = MacosTheme.typography.subheadline.merge(
        TextStyle(color = MacosTheme.colorScheme.mutedForeground)
    )
    CompositionLocalProvider(LocalTextStyle provides style) {
        Box(modifier = modifier) { content() }
    }
}

@Composable
fun CardContent(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
        content = content,
    )
}

@Composable
fun CardFooter(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Composable
fun CardAction(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Preview
@Composable
private fun CardPreview() {
    MacosTheme {
        Card {
            CardHeader { CardTitle { Text("Card Title") } }
            CardContent { Text("Card content goes here.") }
        }
    }
}
