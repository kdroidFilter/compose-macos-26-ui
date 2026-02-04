package io.github.kdroidfilter.darwinui.components.sidebar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.glassOrDefault

/**
 * macOS-style navigation sidebar.
 *
 * A fixed-width vertical panel intended for the left side of a layout,
 * providing navigation via [DarwinSidebarSection] and [DarwinSidebarItem]
 * composables. Supports optional header and footer sections, and a
 * scrollable content area in the middle.
 *
 * @param width The fixed width of the sidebar. Defaults to 240dp.
 * @param glass When true, applies a glass-morphism background effect.
 * @param modifier Modifier to be applied to the sidebar container.
 * @param header Optional composable rendered at the top of the sidebar
 *   with 16dp padding.
 * @param footer Optional composable rendered at the bottom of the sidebar
 *   with 16dp padding and a top border.
 * @param content The main sidebar content (typically [DarwinSidebarSection]
 *   and [DarwinSidebarItem] composables), rendered inside a scrollable column.
 */
@Composable
fun DarwinSidebar(
    width: Dp = 240.dp,
    glass: Boolean = false,
    modifier: Modifier = Modifier,
    header: (@Composable () -> Unit)? = null,
    footer: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = DarwinTheme.colors
    val backgroundColor = glassOrDefault(glass, colors.backgroundElevated)

    Column(
        modifier = modifier
            .width(width)
            .fillMaxHeight()
            .background(backgroundColor),
    ) {
        // Right border is drawn via a Box overlay approach
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // ---- Header ----
                if (header != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        header()
                    }
                }

                // ---- Scrollable content ----
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    content = content,
                )

                // ---- Footer ----
                if (footer != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colors.borderSubtle)
                            .height(1.dp),
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        footer()
                    }
                }
            }

            // Right border line
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(colors.border),
            )
        }
    }
}

// ===========================================================================
// Sidebar Item
// ===========================================================================

/**
 * A single navigation item within a [DarwinSidebar].
 *
 * Displays a row containing an optional icon, a label, and an optional
 * badge. Supports selected and hover states with appropriate visual
 * feedback matching macOS sidebar aesthetics.
 *
 * @param label The text label for this navigation item.
 * @param selected Whether this item is currently selected. When true,
 *   the background tints with the accent color and text uses the accent color.
 * @param onClick Callback invoked when this item is clicked.
 * @param icon Optional composable rendered before the label.
 * @param badge Optional composable rendered at the end of the row
 *   (e.g., a count badge).
 * @param modifier Modifier to be applied to this item.
 */
@Composable
fun DarwinSidebarItem(
    label: String,
    selected: Boolean = false,
    onClick: () -> Unit,
    icon: (@Composable () -> Unit)? = null,
    badge: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes
    val typography = DarwinTheme.typography

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val backgroundColor = when {
        selected -> colors.accent.copy(alpha = 0.10f)
        isHovered -> colors.surfaceVariant
        else -> Color.Transparent
    }

    val textColor = if (selected) colors.accent else colors.textPrimary

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .height(36.dp)
            .clip(shapes.medium)
            .background(backgroundColor, shapes.medium)
            .hoverable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Tab,
                onClick = onClick,
            )
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            icon()
            Spacer(modifier = Modifier.width(8.dp))
        }

        DarwinText(
            text = label,
            style = typography.bodyMedium,
            color = textColor,
            modifier = Modifier.weight(1f),
        )

        if (badge != null) {
            badge()
        }
    }
}

// ===========================================================================
// Sidebar Section
// ===========================================================================

/**
 * A grouping section within a [DarwinSidebar].
 *
 * Provides an optional title label above a column of [DarwinSidebarItem]
 * composables, helping organize navigation items into logical groups.
 *
 * @param title Optional section title displayed as a small label above the items.
 * @param modifier Modifier to be applied to this section.
 * @param content The section content, typically [DarwinSidebarItem] composables.
 */
@Composable
fun DarwinSidebarSection(
    title: String? = null,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography

    Column(modifier = modifier.fillMaxWidth()) {
        if (title != null) {
            DarwinText(
                text = title,
                style = typography.labelSmall,
                color = colors.textTertiary,
                modifier = Modifier.padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 16.dp,
                    bottom = 4.dp,
                ),
            )
        }

        content()
    }
}

