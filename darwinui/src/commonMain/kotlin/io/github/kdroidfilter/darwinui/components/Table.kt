package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.darwinTween

// ==================== TableLayout ====================

/**
 * Controls how a [Table] sizes itself and handles overflow.
 *
 * [Fluid] — columns use weight-based proportional widths; the table fills its
 * container. Use when the data fits comfortably without overflowing.
 *
 * [Scroll] — columns use fixed [Dp] widths; both horizontal and vertical
 * overflow are handled with scrollbars (Excel-style). Use when the table has
 * many columns or long content.
 */
enum class TableLayout { Fluid, Scroll }

// ==================== Table ====================

@Composable
fun Table(
    modifier: Modifier = Modifier,
    layout: TableLayout = TableLayout.Fluid,
    content: @Composable () -> Unit,
) {
    val shape = DarwinTheme.shapes.large
    val backgroundColor = DarwinTheme.colorScheme.card
    val borderColor = DarwinTheme.colorScheme.borderSubtle

    val containerModifier = when (layout) {
        TableLayout.Fluid -> modifier
            .fillMaxWidth()
            .clip(shape)
            .background(backgroundColor, shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
        TableLayout.Scroll -> modifier
            .clip(shape)
            .background(backgroundColor, shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .horizontalScroll(rememberScrollState())
    }

    Column(modifier = containerModifier) {
        content()
    }
}

// ==================== TableHead ====================

@Composable
fun TableHead(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colorScheme
    val headerBackground = if (colors.isDark) {
        Color.White.copy(alpha = 0.05f)
    } else {
        Color.Black.copy(alpha = 0.05f)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(headerBackground)
            .drawBehind {
                // Bottom border line
                drawRect(
                    color = colors.borderSubtle,
                    topLeft = androidx.compose.ui.geometry.Offset(
                        0f,
                        size.height - 1.dp.toPx(),
                    ),
                    size = androidx.compose.ui.geometry.Size(size.width, 1.dp.toPx()),
                )
            },
    ) {
        content()
    }
}

// ==================== TableBody ====================

/**
 * Body section of a [Table].
 *
 * Renders table body rows in a vertically scrollable column.
 *
 * @param scrollable When true, the body is vertically scrollable. Defaults to true.
 * @param modifier Modifier applied to the body container.
 * @param content The body content, typically multiple [TableRow] composables.
 */
@Composable
fun TableBody(
    scrollable: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val scrollModifier = if (scrollable) {
        Modifier.verticalScroll(rememberScrollState())
    } else {
        Modifier
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(scrollModifier),
    ) {
        content()
    }
}

// ==================== TableRow ====================

/**
 * A table row within [TableHead] or [TableBody].
 *
 * Renders cells horizontally in a [Row] with a minimum height of 48dp and a
 * subtle bottom border. Supports hover highlighting and a selected state.
 *
 * @param onClick Optional click handler. When provided, the row becomes clickable
 *                with a pointer cursor.
 * @param selected When true, applies a subtle accent background tint.
 * @param modifier Modifier applied to the row.
 * @param content The row content, typically [TableCell] or [TableHeaderCell]
 *                composables that use [RowScope] for weight-based layout.
 */
@Composable
fun TableRow(
    onClick: (() -> Unit)? = null,
    selected: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val colors = DarwinTheme.colorScheme

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    // Hover overlay
    val hoverBackground: Color = when {
        selected -> colors.accent.copy(alpha = 0.05f)
        isHovered && onClick != null -> colors.surfaceVariant
        else -> Color.Transparent
    }

    val hoverAlpha by animateFloatAsState(
        targetValue = if (isHovered && onClick != null || selected) 1f else 0f,
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "darwin_table_row_hover",
    )

    val resolvedBackground = if (hoverAlpha > 0f) hoverBackground else Color.Transparent

    val rowModifier = modifier
        .fillMaxWidth()
        .heightIn(min = 48.dp)
        .background(resolvedBackground)
        .drawBehind {
            // Bottom border
            drawRect(
                color = colors.borderSubtle,
                topLeft = androidx.compose.ui.geometry.Offset(
                    0f,
                    size.height - 1.dp.toPx(),
                ),
                size = androidx.compose.ui.geometry.Size(size.width, 1.dp.toPx()),
            )
        }
        .then(
            if (onClick != null) {
                Modifier
                    .hoverable(interactionSource = interactionSource)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onClick,
                    )
            } else {
                Modifier
            }
        )

    Row(
        modifier = rowModifier,
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

// ==================== TableCell ====================

/**
 * A standard data cell within a [TableRow].
 *
 * Uses [Modifier.weight] for proportional horizontal sizing within the row.
 * Text content inherits bodyMedium style with the textPrimary color.
 *
 * @param modifier Modifier applied to the cell.
 * @param weight The proportional weight of this cell within the row.
 * @param alignment Vertical alignment of the cell content.
 * @param content The cell content.
 */
@Composable
fun RowScope.TableCell(
    modifier: Modifier = Modifier,
    weight: Float = 1f,
    width: Dp? = null,
    alignment: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable () -> Unit,
) {
    val style = DarwinTheme.typography.subheadline.merge(
        TextStyle(color = DarwinTheme.colorScheme.textPrimary)
    )

    val sizeModifier = if (width != null) modifier.width(width) else modifier.weight(weight)

    CompositionLocalProvider(
        LocalDarwinTextStyle provides style,
    ) {
        Box(
            modifier = sizeModifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = when (alignment) {
                Alignment.Top -> Alignment.TopStart
                Alignment.Bottom -> Alignment.BottomStart
                else -> Alignment.CenterStart
            },
        ) {
            content()
        }
    }
}

// ==================== TableHeaderCell ====================

/**
 * A header cell within a [TableRow] inside [TableHead].
 *
 * Similar to [TableCell] but uses labelMedium typography with
 * a medium font weight and the textSecondary color.
 *
 * @param modifier Modifier applied to the cell.
 * @param weight The proportional weight of this cell within the row.
 * @param content The header cell content.
 */
@Composable
fun RowScope.TableHeaderCell(
    modifier: Modifier = Modifier,
    weight: Float = 1f,
    width: Dp? = null,
    content: @Composable () -> Unit,
) {
    val style = DarwinTheme.typography.caption1.merge(
        TextStyle(
            color = DarwinTheme.colorScheme.textSecondary,
            fontWeight = FontWeight.Medium,
        )
    )

    val sizeModifier = if (width != null) modifier.width(width) else modifier.weight(weight)

    CompositionLocalProvider(
        LocalDarwinTextStyle provides style,
    ) {
        Box(
            modifier = sizeModifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            content()
        }
    }
}

// ==================== TableEmptyRow ====================

/**
 * An empty-state row displayed when the table has no data.
 *
 * Renders a full-width row with centered message text in an italic,
 * tertiary color style.
 *
 * @param message The message to display. Defaults to "No data available".
 * @param modifier Modifier applied to the row.
 */
@Composable
fun TableEmptyRow(
    message: String = "No data available",
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val style = DarwinTheme.typography.subheadline.merge(
            TextStyle(
                color = DarwinTheme.colorScheme.textTertiary,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
            )
        )

        CompositionLocalProvider(
            LocalDarwinTextStyle provides style,
        ) {
            Text(
                text = message,
                style = style,
            )
        }
    }
}

// ==================== TableLoadingRows ====================

/**
 * Skeleton loading rows displayed while table data is being fetched.
 *
 * Renders a specified number of rows, each containing skeleton bars
 * with varying widths and a pulsing opacity animation.
 *
 * @param rowCount The number of skeleton rows to display. Defaults to 3.
 * @param columnCount The number of skeleton columns per row. Defaults to 4.
 * @param modifier Modifier applied to the loading rows container.
 */
@Composable
fun TableLoadingRows(
    rowCount: Int = 3,
    columnCount: Int = 4,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colorScheme

    // Pulsing animation for skeleton effect
    val infiniteTransition = rememberInfiniteTransition(label = "skeleton_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "skeleton_alpha",
    )

    val skeletonColor = if (colors.isDark) {
        Color.White.copy(alpha = 0.10f)
    } else {
        Color.Black.copy(alpha = 0.10f)
    }

    // Vary skeleton bar widths to look more realistic
    val widthFractions = listOf(0.75f, 0.60f, 0.85f, 0.50f, 0.70f, 0.90f, 0.55f, 0.65f)

    Column(modifier = modifier.fillMaxWidth()) {
        for (rowIndex in 0 until rowCount) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp)
                    .drawBehind {
                        // Bottom border
                        drawRect(
                            color = colors.borderSubtle,
                            topLeft = androidx.compose.ui.geometry.Offset(
                                0f,
                                size.height - 1.dp.toPx(),
                            ),
                            size = androidx.compose.ui.geometry.Size(
                                size.width,
                                1.dp.toPx(),
                            ),
                        )
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                for (colIndex in 0 until columnCount) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                    ) {
                        val fraction = widthFractions[
                            (rowIndex * columnCount + colIndex) % widthFractions.size
                        ]
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .height(14.dp)
                                .alpha(pulseAlpha)
                                .clip(RoundedCornerShape(4.dp))
                                .background(skeletonColor),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TablePreview() {
    DarwinTheme {
        Table {
            TableHead {
                TableRow {
                    TableHeaderCell { Text("Name") }
                    TableHeaderCell { Text("Email") }
                }
            }
            TableBody {
                TableRow {
                    TableCell { Text("Alice") }
                    TableCell { Text("alice@example.com") }
                }
            }
        }
    }
}
