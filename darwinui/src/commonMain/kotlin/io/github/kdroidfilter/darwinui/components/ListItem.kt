package io.github.kdroidfilter.darwinui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle

// ===========================================================================
// ListItemColors — mirrors M3's ListItemColors
// ===========================================================================

@Immutable
class ListItemColors(
    val containerColor: Color,
    val headlineColor: Color,
    val leadingIconColor: Color,
    val overlineColor: Color,
    val supportingTextColor: Color,
    val trailingIconColor: Color,
    val disabledHeadlineColor: Color,
    val disabledLeadingIconColor: Color,
    val disabledTrailingIconColor: Color,
)

// ===========================================================================
// ListItemDefaults — mirrors M3's ListItemDefaults
// ===========================================================================

object ListItemDefaults {
    val Elevation: Dp = 0.dp

    @Composable
    fun colors(
        containerColor: Color = DarwinTheme.colorScheme.surface,
        headlineColor: Color = DarwinTheme.colorScheme.onSurface,
        leadingIconColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        overlineColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        supportingTextColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        trailingIconColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        disabledHeadlineColor: Color = headlineColor.copy(alpha = 0.38f),
        disabledLeadingIconColor: Color = leadingIconColor.copy(alpha = 0.38f),
        disabledTrailingIconColor: Color = trailingIconColor.copy(alpha = 0.38f),
    ) = ListItemColors(
        containerColor, headlineColor, leadingIconColor,
        overlineColor, supportingTextColor, trailingIconColor,
        disabledHeadlineColor, disabledLeadingIconColor, disabledTrailingIconColor,
    )
}

// ===========================================================================
// ListItem — mirrors M3's ListItem
// ===========================================================================

@Composable
fun ListItem(
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    overlineContent: (@Composable () -> Unit)? = null,
    supportingContent: (@Composable () -> Unit)? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    colors: ListItemColors = ListItemDefaults.colors(),
    tonalElevation: Dp = ListItemDefaults.Elevation,
    shadowElevation: Dp = ListItemDefaults.Elevation,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.containerColor)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leadingContent != null) {
            Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                CompositionLocalProvider(LocalDarwinContentColor provides colors.leadingIconColor) {
                    leadingContent()
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            if (overlineContent != null) {
                CompositionLocalProvider(
                    LocalDarwinContentColor provides colors.overlineColor,
                    LocalDarwinTextStyle provides DarwinTheme.typography.labelSmall.copy(color = colors.overlineColor),
                ) { overlineContent() }
            }
            CompositionLocalProvider(
                LocalDarwinContentColor provides colors.headlineColor,
                LocalDarwinTextStyle provides DarwinTheme.typography.bodyLarge.copy(color = colors.headlineColor),
            ) { headlineContent() }
            if (supportingContent != null) {
                CompositionLocalProvider(
                    LocalDarwinContentColor provides colors.supportingTextColor,
                    LocalDarwinTextStyle provides DarwinTheme.typography.bodyMedium.copy(color = colors.supportingTextColor),
                ) { supportingContent() }
            }
        }

        if (trailingContent != null) {
            Spacer(modifier = Modifier.width(16.dp))
            CompositionLocalProvider(LocalDarwinContentColor provides colors.trailingIconColor) {
                trailingContent()
            }
        }
    }
}

@Preview
@Composable
private fun ListItemPreview() {
    DarwinTheme {
        ListItem(
            headlineContent = { Text("List Item") },
            supportingContent = { Text("Supporting text") },
        )
    }
}
