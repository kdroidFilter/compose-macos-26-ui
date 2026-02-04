package io.github.kdroidfilter.darwinui.components.dialog

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.components.button.DarwinButton
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.icons.DarwinIcon
import io.github.kdroidfilter.darwinui.icons.LucideX
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import kotlinx.coroutines.delay

// ===========================================================================
// Dialog Size
// ===========================================================================

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

@Composable
fun DarwinDialog(
    open: Boolean,
    onOpenChange: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    val contextValue = remember(onOpenChange) {
        DialogContextValue(onDismiss = { onOpenChange(false) })
    }

    // Keep the popup mounted while the exit animation plays
    var showPopup by remember { mutableStateOf(false) }
    var animateIn by remember { mutableStateOf(false) }

    LaunchedEffect(open) {
        if (open) {
            showPopup = true
            // Small delay so the Popup is mounted before triggering the enter animation
            delay(16)
            animateIn = true
        } else {
            animateIn = false
            // Wait for exit animation to finish before removing the Popup
            delay(DarwinDuration.Slow.millis.toLong() + 50)
            showPopup = false
        }
    }

    if (showPopup) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = { onOpenChange(false) },
            properties = PopupProperties(focusable = true),
        ) {
            CompositionLocalProvider(LocalDialogContext provides contextValue) {
                val colors = DarwinTheme.colors

                val scrimColor = if (colors.isDark) Color.Black.copy(alpha = 0.50f) else Color.Black.copy(alpha = 0.30f)

                // Scrim with fade animation
                AnimatedVisibility(
                    visible = animateIn,
                    enter = fadeIn(tween(DarwinDuration.Fast.millis)),
                    exit = fadeOut(tween(DarwinDuration.Slow.millis)),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(scrimColor)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = { onOpenChange(false) },
                            ),
                    )
                }

                // Content with scale + fade + slide animation
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    AnimatedVisibility(
                        visible = animateIn,
                        enter = fadeIn(tween(DarwinDuration.Slow.millis)) +
                                scaleIn(
                                    initialScale = 0.95f,
                                    transformOrigin = TransformOrigin.Center,
                                    animationSpec = tween(DarwinDuration.Slow.millis),
                                ) +
                                slideInVertically(
                                    initialOffsetY = { 10 },
                                    animationSpec = tween(DarwinDuration.Slow.millis),
                                ),
                        exit = fadeOut(tween(DarwinDuration.Slow.millis)) +
                                scaleOut(
                                    targetScale = 0.95f,
                                    transformOrigin = TransformOrigin.Center,
                                    animationSpec = tween(DarwinDuration.Slow.millis),
                                ) +
                                slideOutVertically(
                                    targetOffsetY = { 10 },
                                    animationSpec = tween(DarwinDuration.Slow.millis),
                                ),
                    ) {
                        content()
                    }
                }
            }
        }
    }
}

// ===========================================================================
// DarwinDialogContent
// ===========================================================================

@Composable
fun DarwinDialogContent(
    modifier: Modifier = Modifier,
    size: DarwinDialogSize = DarwinDialogSize.Md,
    showCloseButton: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colors
    val shape = RoundedCornerShape(16.dp) // rounded-2xl

    val bgColor = if (colors.isDark) Color(0xFF18181B).copy(alpha = 0.95f)
        else Color.White.copy(alpha = 0.95f)

    val borderColor = if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

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
        Column(modifier = Modifier.fillMaxWidth()) {
            content()
        }

        if (showCloseButton) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
            ) {
                DarwinDialogClose()
            }
        }
    }
}

// ===========================================================================
// DarwinDialogHeader
// ===========================================================================

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

@Composable
fun DarwinDialogTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    DarwinText(
        text = text,
        modifier = modifier,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = DarwinTheme.colors.textPrimary,
    )
}

// ===========================================================================
// DarwinDialogDescription
// ===========================================================================

@Composable
fun DarwinDialogDescription(
    text: String,
    modifier: Modifier = Modifier,
) {
    DarwinText(
        text = text,
        modifier = modifier.padding(top = 4.dp),
        style = DarwinTheme.typography.bodySmall,
        color = DarwinTheme.colors.textTertiary,
    )
}

// ===========================================================================
// DarwinDialogBody
// ===========================================================================

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

@Composable
fun DarwinDialogClose(
    modifier: Modifier = Modifier,
    content: (@Composable () -> Unit)? = null,
) {
    val context = LocalDialogContext.current ?: return
    val colors = DarwinTheme.colors

    if (content != null) {
        Box(
            modifier = modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = context.onDismiss,
            ),
        ) {
            content()
        }
    } else {
        val interactionSource = remember { MutableInteractionSource() }
        val isHovered by interactionSource.collectIsHoveredAsState()
        val closeShape = RoundedCornerShape(8.dp)

        Box(
            modifier = modifier
                .size(28.dp)
                .clip(closeShape)
                .hoverable(interactionSource)
                .background(
                    if (isHovered) {
                        if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.05f)
                    } else Color.Transparent,
                    closeShape,
                )
                .clickable(
                    indication = null,
                    interactionSource = interactionSource,
                    onClick = context.onDismiss,
                ),
            contentAlignment = Alignment.Center,
        ) {
            DarwinIcon(
                imageVector = LucideX,
                tint = if (isHovered) {
                    if (colors.isDark) Color(0xFFE4E4E7) else Color(0xFF3F3F46)
                } else {
                    if (colors.isDark) Color(0xFFA1A1AA) else Color(0xFF71717A)
                },
                size = 16.dp,
            )
        }
    }
}

@Preview
@Composable
private fun DarwinDialogPreview() {
    DarwinTheme {
        DarwinDialog(open = true, onOpenChange = {}) {
            DarwinDialogContent {
                DarwinDialogHeader { DarwinDialogTitle("Dialog Title") }
                DarwinDialogBody { DarwinText("Dialog body content.") }
                DarwinDialogFooter {
                    DarwinButton(onClick = {}) { DarwinText("OK") }
                }
            }
        }
    }
}
