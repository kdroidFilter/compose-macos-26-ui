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

@Composable
fun DarwinCard(
    modifier: Modifier = Modifier,
    shape: Shape = DarwinTheme.shapes.extraLarge, // rounded-2xl = 16dp
    content: @Composable ColumnScope.() -> Unit,
) {
    val backgroundColor = DarwinTheme.colors.card
    val borderColor = DarwinTheme.colors.border

    Column(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor, shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .fillMaxWidth(),
        content = content,
    )
}

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

@Composable
fun DarwinCardFooter(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp), // p-6 pt-0
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically, // items-center
        content = content,
    )
}

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
