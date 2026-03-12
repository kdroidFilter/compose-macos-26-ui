package io.github.kdroidfilter.darwinui.components

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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.darwinGlass
import kotlinx.coroutines.delay

// ===========================================================================
// Dialog Size
// ===========================================================================

enum class DialogSize(val maxWidth: Dp) {
    Standard(448.dp),
    Small(384.dp),
    Large(512.dp),
    ExtraLarge(576.dp),
    Full(720.dp),
}

// ===========================================================================
// SmallDialog — macOS-native sheet-style dialog with horizontal button footer
// ===========================================================================

/**
 * A macOS-native small dialog with a title, content area, and a horizontal button footer.
 *
 * Mirrors the macOS NSPanel/NSSavePanel layout:
 * - Optional destructive button anchored to the left of the footer.
 * - Cancel and confirm buttons anchored to the right of the footer.
 * - Frosted-glass background consistent with native macOS panels.
 *
 * @param visible Whether the dialog is shown.
 * @param onDismissRequest Called when the user clicks outside or presses cancel.
 * @param title The dialog title displayed at the top.
 * @param confirmText Label for the primary confirm button.
 * @param onConfirm Called when the confirm button is clicked.
 * @param modifier Modifier for the dialog panel.
 * @param cancelText Optional label for the cancel button. Pass null to hide it.
 * @param onCancel Called when the cancel button is clicked. Defaults to [onDismissRequest].
 * @param destructiveText Optional label for a destructive action button on the left.
 * @param onDestructive Called when the destructive button is clicked.
 * @param size The maximum width constraint of the dialog.
 * @param content The dialog body.
 */
@Composable
fun SmallDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    title: String,
    confirmText: String,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    cancelText: String? = "Cancel",
    onCancel: (() -> Unit)? = null,
    destructiveText: String? = null,
    onDestructive: (() -> Unit)? = null,
    size: DialogSize = DialogSize.Small,
    content: @Composable ColumnScope.() -> Unit,
) {
    var showPopup by remember { mutableStateOf(false) }
    var animateIn by remember { mutableStateOf(false) }

    LaunchedEffect(visible) {
        if (visible) {
            showPopup = true
            delay(16)
            animateIn = true
        } else {
            animateIn = false
            delay(DarwinDuration.Slow.millis.toLong() + 50)
            showPopup = false
        }
    }

    if (showPopup) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = { (onCancel ?: onDismissRequest)() },
            properties = PopupProperties(focusable = true),
        ) {
            val colors = DarwinTheme.colorScheme
            val isDark = colors.isDark
            val scrimColor = if (isDark) Color.Black.copy(alpha = 0.50f) else Color.Black.copy(alpha = 0.30f)

            // Scrim
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
                            onClick = onDismissRequest,
                        ),
                )
            }

            // Dialog panel
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
                    val shape = RoundedCornerShape(16.dp)
                    val fallbackBg = if (isDark) Color(0xFF262626).copy(alpha = 0.92f)
                    else Color.White.copy(alpha = 0.95f)
                    val borderColor = if (isDark) Color(0xFF5E5E5E).copy(alpha = 0.6f)
                    else Color.Black.copy(alpha = 0.10f)
                    val titleColor = if (isDark) Color.White else Color.Black.copy(alpha = 0.85f)
                    val separatorColor = if (isDark) Color.White.copy(alpha = 0.08f)
                    else Color.Black.copy(alpha = 0.06f)

                    Column(
                        modifier = modifier
                            .widthIn(max = size.maxWidth)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .shadow(elevation = 24.dp, shape = shape)
                            .darwinGlass(shape = shape, fallbackColor = fallbackBg)
                            .border(1.dp, borderColor, shape)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = { /* consume clicks */ },
                            ),
                    ) {
                        // Title
                        Text(
                            text = title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = titleColor,
                            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 16.dp),
                        )

                        // Separator
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(separatorColor),
                        )

                        // Body content
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 16.dp),
                            content = content,
                        )

                        // Footer separator
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(separatorColor),
                        )

                        // Footer buttons: [destructive] ... [cancel] [confirm]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            if (destructiveText != null && onDestructive != null) {
                                MacNativeDestructiveButton(
                                    text = destructiveText,
                                    onClick = onDestructive,
                                    fillWidth = false,
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            if (cancelText != null) {
                                MacNativeSecondaryButton(
                                    text = cancelText,
                                    onClick = onCancel ?: onDismissRequest,
                                    fillWidth = false,
                                )
                                Spacer(modifier = Modifier.padding(start = 8.dp))
                            }

                            MacNativeAccentButton(
                                text = confirmText,
                                onClick = onConfirm,
                                fillWidth = false,
                            )
                        }
                    }
                }
            }
        }
    }
}

