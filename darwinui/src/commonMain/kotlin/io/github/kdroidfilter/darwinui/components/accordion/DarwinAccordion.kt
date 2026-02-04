package io.github.kdroidfilter.darwinui.components.accordion

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.darwinTween
import io.github.kdroidfilter.darwinui.theme.glassOrDefault

// =============================================================================
// Accordion type
// =============================================================================

/**
 * Controls how many accordion items can be expanded simultaneously.
 *
 * - [Single]: Only one item can be expanded at a time. Expanding a new item
 *   automatically collapses the previously expanded one.
 * - [Multiple]: Any number of items can be expanded independently.
 */
enum class DarwinAccordionType {
    /** Only one item can be expanded at a time. */
    Single,

    /** Multiple items can be expanded simultaneously. */
    Multiple,
}

// =============================================================================
// CompositionLocal for sharing accordion state
// =============================================================================

/**
 * State holder for the Darwin accordion system.
 */
class DarwinAccordionState(
    val type: DarwinAccordionType,
    val glass: Boolean,
)

internal val LocalDarwinAccordionState = staticCompositionLocalOf<DarwinAccordionState> {
    error("DarwinAccordion components must be used within a DarwinAccordion composable.")
}

// =============================================================================
// DarwinAccordion -- root container
// =============================================================================

/**
 * Root container for the Darwin accordion system.
 *
 * Provides a [DarwinAccordionState] to child composables via [CompositionLocalProvider],
 * enabling [DarwinAccordionItem], [DarwinAccordionTrigger], and [DarwinAccordionContent]
 * to access shared configuration.
 *
 * **State management:** The accordion itself does not manage which items are
 * expanded. The consumer is responsible for tracking expanded state and
 * implementing single-expand logic when [type] is [DarwinAccordionType.Single].
 *
 * @param type Whether only a single item or multiple items can be expanded.
 * @param glass When true, accordion items use a glass-morphism background.
 * @param modifier Modifier applied to the outer column.
 * @param content The composable children -- typically several [DarwinAccordionItem]s.
 */
@Composable
fun DarwinAccordion(
    type: DarwinAccordionType = DarwinAccordionType.Single,
    glass: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val state = remember(type, glass) {
        DarwinAccordionState(type = type, glass = glass)
    }

    CompositionLocalProvider(LocalDarwinAccordionState provides state) {
        Column(modifier = modifier) {
            content()
        }
    }
}

// =============================================================================
// DarwinAccordionItem -- one collapsible section
// =============================================================================

/**
 * Container for a single accordion section consisting of a trigger header
 * and collapsible content.
 *
 * Draws a 1 dp bottom border in the theme's border color to visually separate
 * items.
 *
 * @param value Unique identifier for this item.
 * @param expanded Whether this item is currently expanded.
 * @param onToggle Callback invoked when the trigger is clicked.
 * @param glass When true, applies a glass-morphism background to the item.
 * @param modifier Modifier applied to the item container.
 * @param trigger The header composable, typically a [DarwinAccordionTrigger].
 * @param content The collapsible body, typically a [DarwinAccordionContent].
 */
@Composable
fun DarwinAccordionItem(
    value: String,
    expanded: Boolean,
    onToggle: () -> Unit,
    glass: Boolean = false,
    modifier: Modifier = Modifier,
    trigger: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colors
    val borderColor = colors.border

    val backgroundColor = if (glass) {
        glassOrDefault(glass = true, fallback = Color.Transparent)
    } else {
        Color.Transparent
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .drawBehind {
                // Bottom border
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = borderColor,
                    start = Offset(0f, size.height - strokeWidth / 2),
                    end = Offset(size.width, size.height - strokeWidth / 2),
                    strokeWidth = strokeWidth,
                )
            },
    ) {
        // Trigger header -- wrap in a clickable to handle expand/collapse
        DarwinAccordionTrigger(
            onClick = onToggle,
            expanded = expanded,
        ) {
            trigger()
        }

        // Collapsible content
        DarwinAccordionContent(expanded = expanded) {
            content()
        }
    }
}

// =============================================================================
// DarwinAccordionTrigger -- clickable header row
// =============================================================================

/**
 * Clickable header row for a [DarwinAccordionItem].
 *
 * Displays the trigger content on the left and a chevron icon on the right
 * that rotates 180 degrees when the item is expanded.
 *
 * @param onClick Callback invoked when the trigger is clicked.
 * @param expanded Whether the parent accordion item is expanded.
 * @param modifier Modifier applied to the trigger row.
 * @param content Row-scoped content for the trigger label.
 */
@Composable
fun DarwinAccordionTrigger(
    onClick: () -> Unit,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    // Chevron rotation animation
    val chevronRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = darwinTween(DarwinDuration.Normal),
        label = "darwin_accordion_chevron_rotation",
    )

    // Hover background
    val hoverBackground = if (isHovered) {
        if (colors.isDark) Color.White.copy(alpha = 0.03f)
        else Color.Black.copy(alpha = 0.03f)
    } else {
        Color.Transparent
    }

    // Text style for the trigger content
    val textStyle = typography.bodyMedium.merge(
        TextStyle(
            color = if (isHovered) colors.textSecondary else colors.textPrimary,
            fontWeight = FontWeight.Medium,
        )
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(hoverBackground)
            .hoverable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = onClick,
            )
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        // Trigger label
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CompositionLocalProvider(
                LocalDarwinTextStyle provides textStyle,
            ) {
                content()
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Chevron icon (Canvas-drawn to avoid material-icons dependency)
        Box(
            modifier = Modifier
                .size(20.dp)
                .rotate(chevronRotation)
                .drawWithContent {
                    val strokeW = 2.dp.toPx()
                    val pad = 4.dp.toPx()
                    val path = Path().apply {
                        moveTo(pad, size.height * 0.35f)
                        lineTo(size.width / 2f, size.height * 0.65f)
                        lineTo(size.width - pad, size.height * 0.35f)
                    }
                    drawPath(
                        path = path,
                        color = colors.textSecondary,
                        style = Stroke(
                            width = strokeW,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round,
                        ),
                    )
                },
        )
    }
}

// =============================================================================
// DarwinAccordionContent -- animated expand/collapse body
// =============================================================================

/**
 * Animated content body for a [DarwinAccordionItem].
 *
 * Uses [AnimatedVisibility] with vertical expand/shrink and fade transitions
 * for smooth expand and collapse animations.
 *
 * @param expanded Whether the content is currently visible.
 * @param modifier Modifier applied to the content wrapper.
 * @param content The composable content displayed when expanded.
 */
@Composable
fun DarwinAccordionContent(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography

    val textStyle = typography.bodySmall.merge(
        TextStyle(color = colors.textSecondary)
    )

    AnimatedVisibility(
        visible = expanded,
        enter = expandVertically(
            animationSpec = darwinTween(DarwinDuration.Slow),
            expandFrom = Alignment.Top,
        ) + fadeIn(
            animationSpec = darwinTween(DarwinDuration.Slow),
        ),
        exit = shrinkVertically(
            animationSpec = darwinTween(DarwinDuration.Normal),
            shrinkTowards = Alignment.Top,
        ) + fadeOut(
            animationSpec = darwinTween(DarwinDuration.Normal),
        ),
    ) {
        Box(
            modifier = modifier.padding(bottom = 16.dp),
        ) {
            CompositionLocalProvider(
                LocalDarwinTextStyle provides textStyle,
            ) {
                content()
            }
        }
    }
}
