package io.github.kdroidfilter.darwinui.components.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.glassOrDefault
import io.github.kdroidfilter.darwinui.theme.glassBorderOrDefault

// ===========================================================================
// Dialog Size
// ===========================================================================

/**
 * Size presets for [DarwinDialogContent], matching the React darwin-ui sizes.
 */
enum class DarwinDialogSize(val maxWidth: Dp) {
    Sm(384.dp),
    Md(448.dp),
    Lg(512.dp),
    Xl(576.dp),
    Full(720.dp),
}

// ===========================================================================
// Dialog Context (CompositionLocal)
// ===========================================================================

internal data class DialogContextValue(
    val onDismiss: () -> Unit,
)

internal val LocalDialogContext = compositionLocalOf<DialogContextValue?> { null }

// ===========================================================================
// DarwinDialog (root)
// ===========================================================================

/**
 * Darwin UI Dialog component — a modal overlay with scrim, scale/fade animation,
 * and compound sub-components mirroring the React darwin-ui Dialog.
 *
 * Usage:
 * ```
 * DarwinDialog(open = showDialog, onOpenChange = { showDialog = it }) {
 *     DarwinDialogContent {
 *         DarwinDialogHeader {
 *             DarwinDialogTitle("Edit Profile")
 *             DarwinDialogDescription("Make changes to your profile here.")
 *         }
 *         DarwinDialogBody {
 *             DarwinText("Dialog body content")
 *         }
 *         DarwinDialogFooter {
 *             DarwinDialogClose { DarwinText("Cancel") }
 *             DarwinButton(onClick = { /* save */ }) { DarwinText("Save") }
 *         }
 *     }
 * }
 * ```
 *
 * @param open        Whether the dialog is visible.
 * @param onOpenChange Callback invoked when the dialog should open or close.
 * @param content     Slot for the dialog tree (typically [DarwinDialogContent]).
 */
@Composable
fun DarwinDialog(
    open: Boolean,
    onOpenChange: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    val contextValue = remember(onOpenChange) {
        DialogContextValue(onDismiss = { onOpenChange(false) })
    }

    CompositionLocalProvider(LocalDialogContext provides contextValue) {
        AnimatedVisibility(
            visible = open,
            enter = fadeIn(animationSpec = tween(DarwinDuration.Fast.millis)),
            exit = fadeOut(animationSpec = tween(DarwinDuration.Fast.millis)),
        ) {
            // Scrim
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarwinTheme.colors.scrim)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { onOpenChange(false) },
                    ),
                contentAlignment = Alignment.Center,
            ) {
                content()
            }
        }
    }
}

// ===========================================================================
// DarwinDialogContent
// ===========================================================================

/**
 * The content panel of the dialog, centered within the scrim.
 *
 * @param size  Size preset controlling the max width.
 * @param glass Enable frosted glass effect on the panel.
 */
@Composable
fun DarwinDialogContent(
    modifier: Modifier = Modifier,
    size: DarwinDialogSize = DarwinDialogSize.Md,
    glass: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colors
    val shape = RoundedCornerShape(16.dp)

    val bgColor = if (glass) {
        glassOrDefault(true, colors.surface)
    } else {
        if (colors.isDark) colors.surface.copy(alpha = 0.95f)
        else Color.White.copy(alpha = 0.95f)
    }

    val borderColor = glassBorderOrDefault(glass, colors.borderSubtle)

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(tween(DarwinDuration.Slow.millis)) +
                scaleIn(
                    initialScale = 0.95f,
                    animationSpec = tween(DarwinDuration.Slow.millis),
                ),
        exit = fadeOut(tween(DarwinDuration.Slow.millis)) +
                scaleOut(
                    targetScale = 0.95f,
                    animationSpec = tween(DarwinDuration.Slow.millis),
                ),
    ) {
        Box(
            modifier = modifier
                .widthIn(max = size.maxWidth)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .shadow(elevation = 24.dp, shape = shape)
                .clip(shape)
                .background(bgColor, shape)
                .border(1.dp, borderColor, shape)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { /* consume click to prevent scrim dismiss */ },
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
            ) {
                content()
            }
        }
    }
}

// ===========================================================================
// DarwinDialogHeader
// ===========================================================================

/**
 * Header area of the dialog (padding: horizontal 24dp, top 24dp).
 */
@Composable
fun DarwinDialogHeader(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 24.dp),
    ) {
        content()
    }
}

// ===========================================================================
// DarwinDialogTitle
// ===========================================================================

/**
 * Title text for the dialog header.
 */
@Composable
fun DarwinDialogTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colors
    DarwinText(
        text = text,
        modifier = modifier,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = colors.textPrimary,
    )
}

// ===========================================================================
// DarwinDialogDescription
// ===========================================================================

/**
 * Description text beneath the dialog title.
 */
@Composable
fun DarwinDialogDescription(
    text: String,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colors
    DarwinText(
        text = text,
        modifier = modifier.padding(top = 4.dp),
        style = DarwinTheme.typography.bodySmall,
        color = colors.textTertiary,
    )
}

// ===========================================================================
// DarwinDialogBody
// ===========================================================================

/**
 * Body / main content area of the dialog.
 */
@Composable
fun DarwinDialogBody(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        content()
    }
}

// ===========================================================================
// DarwinDialogFooter
// ===========================================================================

/**
 * Footer area with action buttons, aligned to the end (right).
 */
@Composable
fun DarwinDialogFooter(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}

// ===========================================================================
// DarwinDialogClose
// ===========================================================================

/**
 * A close trigger that dismisses the dialog when clicked.
 * Renders its [content] as a clickable element that calls `onOpenChange(false)`.
 *
 * If no content is provided, renders a default "X" close button positioned
 * at the top-right corner.
 */
@Composable
fun DarwinDialogClose(
    modifier: Modifier = Modifier,
    content: (@Composable () -> Unit)? = null,
) {
    val context = LocalDialogContext.current ?: return
    val colors = DarwinTheme.colors

    if (content != null) {
        Box(
            modifier = modifier.clickable(onClick = context.onDismiss),
        ) {
            content()
        }
    } else {
        // Default X button
        DarwinText(
            text = "\u2715", // ✕
            modifier = modifier
                .clickable(onClick = context.onDismiss)
                .padding(4.dp),
            fontSize = 16.sp,
            color = colors.textTertiary,
        )
    }
}
