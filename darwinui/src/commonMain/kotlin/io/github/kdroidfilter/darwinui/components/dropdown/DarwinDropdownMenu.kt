package io.github.kdroidfilter.darwinui.components.dropdown

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.glassBorderOrDefault
import io.github.kdroidfilter.darwinui.theme.glassOrDefault

/**
 * A dropdown menu component for Darwin UI.
 *
 * Displays a list of actions or options anchored to a trigger element. The menu
 * appears when [expanded] is true with a scale and fade animation, and dismisses
 * when the user clicks outside of it.
 *
 * Use [DarwinDropdownMenuItem], [DarwinDropdownMenuCheckboxItem],
 * [DarwinDropdownMenuLabel], [DarwinDropdownMenuSeparator], and
 * [DarwinDropdownMenuShortcut] as children within the [content] block.
 *
 * @param expanded Whether the dropdown menu is currently visible.
 * @param onDismissRequest Callback invoked when the user clicks outside the menu
 *   to dismiss it.
 * @param glass When true, applies a glass-morphism background and border instead of
 *   the standard card colors.
 * @param modifier Modifier applied to the root container.
 * @param trigger The composable element that acts as the menu anchor. Typically
 *   a button that toggles [expanded].
 * @param content The menu content, composed of [DarwinDropdownMenuItem],
 *   [DarwinDropdownMenuLabel], [DarwinDropdownMenuSeparator], etc.
 */
@Composable
fun DarwinDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    glass: Boolean = false,
    modifier: Modifier = Modifier,
    trigger: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes

    val backgroundColor = glassOrDefault(glass, colors.card)
    val borderColor = glassBorderOrDefault(glass, colors.border)

    Box(modifier = modifier) {
        trigger()

        if (expanded) {
            Popup(
                alignment = Alignment.TopStart,
                onDismissRequest = onDismissRequest,
                properties = PopupProperties(
                    focusable = true,
                ),
            ) {
                AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn(animationSpec = tween(150)) +
                            scaleIn(
                                initialScale = 0.95f,
                                transformOrigin = TransformOrigin(0.5f, 0f),
                                animationSpec = tween(150),
                            ),
                    exit = fadeOut(animationSpec = tween(100)) +
                            scaleOut(
                                targetScale = 0.95f,
                                transformOrigin = TransformOrigin(0.5f, 0f),
                                animationSpec = tween(100),
                            ),
                ) {
                    val scrollState = rememberScrollState()

                    Column(
                        modifier = Modifier
                            .shadow(
                                elevation = 8.dp,
                                shape = shapes.large,
                            )
                            .clip(shapes.large)
                            .background(backgroundColor, shapes.large)
                            .border(
                                width = 1.dp,
                                color = borderColor,
                                shape = shapes.large,
                            )
                            .widthIn(min = 180.dp, max = 300.dp)
                            .heightIn(max = 360.dp)
                            .verticalScroll(scrollState)
                            .padding(vertical = 4.dp),
                        content = content,
                    )
                }
            }
        }
    }
}

/**
 * A clickable menu item within a [DarwinDropdownMenu].
 *
 * Displays a row with optional leading icon and trailing content. The item
 * highlights on hover or press with a subtle accent-colored background.
 *
 * @param onClick Callback invoked when the item is clicked.
 * @param enabled Whether the item is interactive. Disabled items are rendered
 *   at 50% opacity and do not respond to clicks.
 * @param modifier Modifier applied to the item row.
 * @param leadingIcon Optional composable displayed before the item label.
 * @param trailingContent Optional composable displayed after the item label,
 *   typically a [DarwinDropdownMenuShortcut].
 * @param content The item label content, typically a [BasicText] composable.
 */
@Composable
fun DarwinDropdownMenuItem(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val itemBackground = when {
        !enabled -> Color.Transparent
        isPressed -> colors.accent.copy(alpha = 0.12f)
        isHovered -> colors.accent.copy(alpha = 0.08f)
        else -> Color.Transparent
    }

    val contentStyle = typography.bodyMedium.merge(
        TextStyle(color = colors.textPrimary)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(36.dp)
            .then(
                if (enabled) {
                    Modifier
                        .hoverable(interactionSource)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = onClick,
                        )
                } else {
                    Modifier
                }
            )
            .background(itemBackground)
            .padding(horizontal = 12.dp)
            .alpha(if (enabled) 1f else 0.5f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        if (leadingIcon != null) {
            leadingIcon()
            Spacer(modifier = Modifier.width(8.dp))
        }

        Box(modifier = Modifier.weight(1f)) {
            androidx.compose.runtime.CompositionLocalProvider(
                LocalDarwinTextStyle provides contentStyle,
            ) {
                content()
            }
        }

        if (trailingContent != null) {
            Spacer(modifier = Modifier.width(8.dp))
            trailingContent()
        }
    }
}

/**
 * A menu item with a checkbox indicator within a [DarwinDropdownMenu].
 *
 * Behaves like [DarwinDropdownMenuItem] but includes a checkmark indicator on
 * the left side that reflects the [checked] state.
 *
 * @param checked Whether the checkbox is currently checked.
 * @param onCheckedChange Callback invoked when the user toggles the item.
 * @param enabled Whether the item is interactive. Disabled items are rendered
 *   at 50% opacity.
 * @param modifier Modifier applied to the item row.
 * @param content The item label content.
 */
@Composable
fun DarwinDropdownMenuCheckboxItem(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography

    DarwinDropdownMenuItem(
        onClick = { onCheckedChange(!checked) },
        enabled = enabled,
        modifier = modifier,
        leadingIcon = {
            Box(
                modifier = Modifier.size(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                if (checked) {
                    BasicText(
                        text = "\u2713",
                        style = typography.bodyMedium.merge(
                            TextStyle(color = colors.accent)
                        ),
                    )
                }
            }
        },
        content = content,
    )
}

/**
 * A non-clickable label or section header within a [DarwinDropdownMenu].
 *
 * Used to visually group related menu items under a descriptive heading.
 *
 * @param text The label text to display.
 * @param modifier Modifier applied to the label container.
 */
@Composable
fun DarwinDropdownMenuLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        BasicText(
            text = text,
            style = typography.labelSmall.merge(
                TextStyle(color = colors.textTertiary)
            ),
        )
    }
}

/**
 * A horizontal separator line within a [DarwinDropdownMenu].
 *
 * Used to visually separate groups of menu items.
 *
 * @param modifier Modifier applied to the separator container.
 */
@Composable
fun DarwinDropdownMenuSeparator(modifier: Modifier = Modifier) {
    val colors = DarwinTheme.colors

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colors.borderSubtle),
        )
    }
}

/**
 * A right-aligned keyboard shortcut label within a [DarwinDropdownMenuItem].
 *
 * Typically passed as the [trailingContent] parameter of [DarwinDropdownMenuItem].
 * Displays a shortcut hint (e.g., "Cmd+C") in a small, muted style.
 *
 * @param text The shortcut text to display (e.g., "Cmd+C").
 * @param modifier Modifier applied to the shortcut text.
 */
@Composable
fun DarwinDropdownMenuShortcut(
    text: String,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography

    BasicText(
        text = text,
        style = typography.labelSmall.merge(
            TextStyle(color = colors.textTertiary)
        ),
        modifier = modifier,
    )
}
