package io.github.kdroidfilter.darwinui.components.dropdown

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.icons.DarwinIcon
import io.github.kdroidfilter.darwinui.icons.LucideCheck
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.Red500
import io.github.kdroidfilter.darwinui.theme.Zinc100
import io.github.kdroidfilter.darwinui.theme.Zinc300
import io.github.kdroidfilter.darwinui.theme.Zinc400
import io.github.kdroidfilter.darwinui.theme.Zinc500
import io.github.kdroidfilter.darwinui.theme.Zinc700
import io.github.kdroidfilter.darwinui.theme.Zinc900

// =============================================================================
// DarwinDropdownMenu — main component
// =============================================================================

/**
 * A dropdown menu component for Darwin UI.
 *
 * Displays a list of actions or options anchored to a trigger element. The menu
 * appears when [expanded] is true with a scale and fade animation, and dismisses
 * when the user clicks outside of it.
 *
 * @param expanded Whether the dropdown menu is currently visible.
 * @param onDismissRequest Callback invoked when the user clicks outside the menu.
 * @param modifier Modifier applied to the root container.
 * @param trigger The composable element that acts as the menu anchor.
 * @param content The menu content.
 */
@Composable
fun DarwinDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    trigger: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = DarwinTheme.colors
    val isDark = colors.isDark
    val shapes = DarwinTheme.shapes

    val backgroundColor = if (isDark) Zinc900.copy(alpha = 0.95f) else Color.White.copy(alpha = 0.95f)
    val borderColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

    val density = LocalDensity.current
    var triggerHeightPx by remember { mutableStateOf(0) }

    Box(modifier = modifier) {
        Box(modifier = Modifier.onGloballyPositioned { triggerHeightPx = it.size.height }) {
            trigger()
        }

        if (expanded) {
            val gapPx = with(density) { 4.dp.roundToPx() } // sideOffset = 4
            Popup(
                offset = IntOffset(0, triggerHeightPx + gapPx),
                onDismissRequest = onDismissRequest,
                properties = PopupProperties(focusable = true),
            ) {
                AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn(tween(150)) +
                            scaleIn(
                                initialScale = 0.95f,
                                transformOrigin = TransformOrigin(0.5f, 0f),
                                animationSpec = tween(150),
                            ) +
                            slideInVertically(tween(150)) { -20 },
                    exit = fadeOut(tween(100)) +
                            scaleOut(
                                targetScale = 0.95f,
                                transformOrigin = TransformOrigin(0.5f, 0f),
                                animationSpec = tween(100),
                            ) +
                            slideOutVertically(tween(100)) { -20 },
                ) {
                    val scrollState = rememberScrollState()

                    Column(
                        modifier = Modifier
                            .width(IntrinsicSize.Max)
                            .widthIn(min = 180.dp)
                            .shadow(elevation = 8.dp, shape = shapes.large)
                            .clip(shapes.large)
                            .background(backgroundColor, shapes.large)
                            .border(1.dp, borderColor, shapes.large)
                            .heightIn(max = 360.dp)
                            .verticalScroll(scrollState)
                            .padding(vertical = 4.dp), // p-1 top/bottom
                        content = content,
                    )
                }
            }
        }
    }
}

// =============================================================================
// DarwinDropdownMenuItem
// =============================================================================

/**
 * A clickable menu item within a [DarwinDropdownMenu].
 *
 * @param onClick Callback invoked when the item is clicked.
 * @param enabled Whether the item is interactive.
 * @param destructive When true, renders the item in red for dangerous actions.
 * @param modifier Modifier applied to the item row.
 * @param leadingIcon Optional composable displayed before the label.
 * @param trailingContent Optional composable displayed after the label.
 * @param content The item label content.
 */
@Composable
fun DarwinDropdownMenuItem(
    onClick: () -> Unit,
    enabled: Boolean = true,
    destructive: Boolean = false,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val isDark = DarwinTheme.colors.isDark
    val typography = DarwinTheme.typography
    val shapes = DarwinTheme.shapes

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    // Destructive: hover:bg-red-500/10
    val itemBackground = when {
        !enabled -> Color.Transparent
        destructive && isHovered -> Red500.copy(alpha = 0.10f)
        isHovered -> if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.05f)
        else -> Color.Transparent
    }

    // Destructive: text-red-500 (always)
    val textColor = when {
        destructive -> Red500
        isHovered -> if (isDark) Zinc100 else Zinc900
        else -> if (isDark) Zinc300 else Zinc700
    }

    val contentStyle = typography.bodyMedium.merge(TextStyle(color = textColor))

    // Outer padding(horizontal=4.dp) simulates the p-1 container inset
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp) // container p-1 inset
            .clip(shapes.small) // rounded-lg = 8dp
            .background(itemBackground, shapes.small)
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
            .padding(horizontal = 8.dp, vertical = 6.dp) // px-2 py-1.5
            .alpha(if (enabled) 1f else 0.5f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        if (leadingIcon != null) {
            leadingIcon()
            Spacer(modifier = Modifier.width(8.dp))
        }

        Box(modifier = Modifier.weight(1f)) {
            CompositionLocalProvider(
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

// =============================================================================
// DarwinDropdownMenuCheckboxItem
// =============================================================================

/**
 * A menu item with a checkbox indicator within a [DarwinDropdownMenu].
 *
 * @param checked Whether the checkbox is currently checked.
 * @param onCheckedChange Callback invoked when the user toggles the item.
 * @param enabled Whether the item is interactive.
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
                    DarwinIcon(
                        imageVector = LucideCheck,
                        size = 12.dp,
                        tint = if (DarwinTheme.colors.isDark) Zinc300 else Zinc700,
                    )
                }
            }
        },
        content = content,
    )
}

// =============================================================================
// DarwinDropdownMenuLabel
// =============================================================================

/**
 * A non-clickable label or section header within a [DarwinDropdownMenu].
 *
 * @param text The label text to display.
 * @param modifier Modifier applied to the label container.
 */
@Composable
fun DarwinDropdownMenuLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    val isDark = DarwinTheme.colors.isDark
    val typography = DarwinTheme.typography

    // Outer horizontal padding 4dp (container p-1 inset) + inner px-2 (8dp)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp) // container p-1 inset
            .padding(horizontal = 8.dp, vertical = 6.dp), // px-2 py-1.5
    ) {
        BasicText(
            text = text,
            style = typography.labelMedium.merge(
                TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDark) Zinc400 else Zinc500,
                )
            ),
        )
    }
}

// =============================================================================
// DarwinDropdownMenuSeparator
// =============================================================================

/**
 * A horizontal separator line within a [DarwinDropdownMenu].
 *
 * @param modifier Modifier applied to the separator.
 */
@Composable
fun DarwinDropdownMenuSeparator(modifier: Modifier = Modifier) {
    val isDark = DarwinTheme.colors.isDark

    // -mx-1 extends past the container p-1 → edge-to-edge within the border.
    // Since the Column has no horizontal padding, separator fills full width.
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) // my-1
            .height(1.dp)
            .background(
                if (isDark) Color.White.copy(alpha = 0.10f)
                else Color.Black.copy(alpha = 0.10f)
            ),
    )
}

// =============================================================================
// DarwinDropdownMenuShortcut
// =============================================================================

/**
 * A right-aligned keyboard shortcut label within a [DarwinDropdownMenuItem].
 *
 * @param text The shortcut text to display (e.g., "Cmd+C").
 * @param modifier Modifier applied to the shortcut text.
 */
@Composable
fun DarwinDropdownMenuShortcut(
    text: String,
    modifier: Modifier = Modifier,
) {
    val isDark = DarwinTheme.colors.isDark
    val typography = DarwinTheme.typography

    BasicText(
        text = text,
        style = typography.labelMedium.merge(
            TextStyle(
                color = if (isDark) Zinc400 else Zinc500,
                letterSpacing = 1.2.sp, // tracking-widest ≈ 0.1em at 12sp
            )
        ),
        modifier = modifier,
    )
}
