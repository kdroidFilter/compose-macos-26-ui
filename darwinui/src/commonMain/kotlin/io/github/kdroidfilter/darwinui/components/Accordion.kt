package io.github.kdroidfilter.darwinui.components

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.darwinTween

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
enum class AccordionType {
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
class AccordionState(
    val type: AccordionType,
)

internal val LocalAccordionState = staticCompositionLocalOf<AccordionState> {
    error("Accordion components must be used within a Accordion composable.")
}

// =============================================================================
// Accordion -- root container
// =============================================================================

/**
 * Root container for the Darwin accordion system.
 *
 * Provides a [AccordionState] to child composables via [CompositionLocalProvider],
 * enabling [AccordionItem], [AccordionTrigger], and [AccordionContent]
 * to access shared configuration.
 *
 * **State management:** The accordion itself does not manage which items are
 * expanded. The consumer is responsible for tracking expanded state and
 * implementing single-expand logic when [type] is [AccordionType.Single].
 *
 * @param type Whether only a single item or multiple items can be expanded.
 * @param modifier Modifier applied to the outer column.
 * @param content The composable children -- typically several [AccordionItem]s.
 */
@Composable
fun Accordion(
    type: AccordionType = AccordionType.Single,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val state = remember(type) {
        AccordionState(type = type)
    }

    CompositionLocalProvider(LocalAccordionState provides state) {
        Column(modifier = modifier) {
            content()
        }
    }
}

// =============================================================================
// AccordionItem -- one collapsible section
// =============================================================================

/**
 * Container for a single accordion section consisting of a trigger header
 * and collapsible content.
 *
 * Draws a 1 dp bottom border in the theme's border color to visually separate
 * items.
 *
 * @param value Unique identifier for this item, used as test tag.
 * @param expanded Whether this item is currently expanded.
 * @param onToggle Callback invoked when the trigger is clicked.
 * @param modifier Modifier applied to the item container.
 * @param trigger The header composable, typically a [AccordionTrigger].
 * @param content The collapsible body, typically a [AccordionContent].
 */
@Composable
fun AccordionItem(
    value: String,
    expanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    trigger: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colorScheme
    val borderColor = colors.border

    val backgroundColor = Color.Transparent

    Column(
        modifier = modifier
            .testTag(value)
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
        AccordionTrigger(
            onClick = onToggle,
            expanded = expanded,
        ) {
            trigger()
        }

        // Collapsible content
        AccordionContent(expanded = expanded) {
            content()
        }
    }
}

// =============================================================================
// AccordionTrigger -- clickable header row
// =============================================================================

/**
 * Clickable header row for a [AccordionItem].
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
fun AccordionTrigger(
    onClick: () -> Unit,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val colors = DarwinTheme.colorScheme
    val typography = DarwinTheme.typography

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    // Chevron rotation animation
    val chevronRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = darwinTween(DarwinDuration.Normal),
        label = "darwin_accordion_chevron_rotation",
    )

    // Hover: textTertiary (zinc-500) gives clear contrast from textPrimary in both themes
    val textColor = if (isHovered) colors.textTertiary else colors.textPrimary

    val textStyle = typography.subheadline.merge(
        TextStyle(
            color = textColor,
            fontWeight = FontWeight.Medium,
        )
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .hoverable(interactionSource = interactionSource)
            .clickable(
                indication = null,
                interactionSource = interactionSource,
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
                LocalDarwinContentColor provides textColor,
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
// AccordionContent -- animated expand/collapse body
// =============================================================================

/**
 * Animated content body for a [AccordionItem].
 *
 * Uses [AnimatedVisibility] with vertical expand/shrink and fade transitions
 * for smooth expand and collapse animations.
 *
 * @param expanded Whether the content is currently visible.
 * @param modifier Modifier applied to the content wrapper.
 * @param content The composable content displayed when expanded.
 */
@Composable
fun AccordionContent(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colorScheme
    val typography = DarwinTheme.typography

    val textStyle = typography.caption1.merge(
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

@Preview
@Composable
private fun AccordionPreview() {
    DarwinTheme {
        var expanded by remember { mutableStateOf(true) }
        Accordion {
            AccordionItem(
                value = "item-1",
                expanded = expanded,
                onToggle = { expanded = !expanded },
                trigger = { Text("Is it accessible?") },
                content = { Text("Yes. It follows WAI-ARIA design patterns.") },
            )
        }
    }
}
