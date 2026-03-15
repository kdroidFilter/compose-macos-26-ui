package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

// ===========================================================================
// GroupedList — macOS-style grouped settings/list container
// ===========================================================================

/**
 * A macOS-style grouped list container. Renders children in a rounded card
 * with a subtle border. Use [GroupedListItem] for rows.
 *
 * @param modifier Modifier applied to the container.
 * @param content The list rows, typically [GroupedListItem] composables.
 */
@Composable
fun GroupedList(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val shape = MacosTheme.shapes.large
    val colors = MacosTheme.colorScheme

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(colors.card, shape)
            .border(1.dp, colors.borderSubtle, shape),
    ) {
        content()
    }
}

// ===========================================================================
// GroupedListItem — a single row inside GroupedList
// ===========================================================================

/**
 * A row inside a [GroupedList].
 *
 * Renders leading content, a main label slot (fills available width), and
 * optional trailing content. A subtle bottom divider is drawn automatically;
 * set [showDivider] to false on the last item if needed (though the container
 * clip handles it visually).
 *
 * @param modifier Modifier applied to the row.
 * @param onClick Optional click handler. When provided, the row becomes
 *   clickable with a hover highlight.
 * @param showDivider Whether to draw a bottom separator line. Defaults to true.
 * @param dividerInset Left inset of the divider line. Defaults to 16dp.
 * @param minHeight Minimum row height. Defaults to 48dp.
 * @param leading Optional leading content (e.g. an icon).
 * @param trailing Optional trailing content (e.g. action icons, disclosure).
 * @param content The main label content.
 */
@Composable
fun GroupedListItem(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    showDivider: Boolean = true,
    dividerInset: Dp = 16.dp,
    minHeight: Dp = 48.dp,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val colors = MacosTheme.colorScheme
    val dividerColor = colors.borderSubtle
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val hoverBackground = if (isHovered && onClick != null) {
        colors.surfaceVariant
    } else {
        Color.Transparent
    }

    val rowModifier = modifier
        .fillMaxWidth()
        .heightIn(min = minHeight)
        .background(hoverBackground)
        .then(
            if (showDivider) {
                Modifier.drawBehind {
                    drawLine(
                        color = dividerColor,
                        start = Offset(dividerInset.toPx(), size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx(),
                    )
                }
            } else Modifier
        )
        .then(
            if (onClick != null) {
                Modifier
                    .hoverable(interactionSource)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onClick,
                    )
            } else Modifier
        )
        .padding(horizontal = 16.dp, vertical = 10.dp)

    Row(
        modifier = rowModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        if (leading != null) {
            leading()
        }
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
        if (trailing != null) {
            trailing()
        }
    }
}

@Preview
@Composable
private fun GroupedListPreview() {
    MacosTheme {
        GroupedList {
            GroupedListItem(trailing = { Text("Detail") }) { Text("Item 1") }
            GroupedListItem(trailing = { Text("Detail") }) { Text("Item 2") }
            GroupedListItem(showDivider = false) { Text("Item 3") }
        }
    }
}
