package io.github.kdroidfilter.darwinui.components.alert

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.glassBorderOrDefault
import io.github.kdroidfilter.darwinui.theme.glassOrDefault

// ===========================================================================
// Alert Type
// ===========================================================================

/**
 * The semantic type of an alert, determining its accent color and icon.
 */
enum class DarwinAlertType {
    /** Informational alert with blue accent. */
    Info,

    /** Success alert with green accent. */
    Success,

    /** Warning alert with amber accent. */
    Warning,

    /** Error alert with red accent. */
    Error,
}

// ===========================================================================
// Type Color Resolution
// ===========================================================================

/**
 * Returns the accent [Color] for the given [DarwinAlertType].
 */
@Composable
private fun resolveAlertTypeColor(type: DarwinAlertType): Color {
    val colors = DarwinTheme.colors
    return when (type) {
        DarwinAlertType.Info -> colors.info
        DarwinAlertType.Success -> colors.success
        DarwinAlertType.Warning -> colors.warning
        DarwinAlertType.Error -> colors.destructive
    }
}

// ===========================================================================
// DarwinAlertBanner (Inline)
// ===========================================================================

/**
 * An inline alert banner that displays a message with a type-specific accent.
 *
 * The banner spans the full available width and features:
 * - A 4dp left accent border colored by [type]
 * - A tinted background (type color at 5% opacity)
 * - A type-specific icon
 * - Optional [title] displayed in bold above the [message]
 * - Optional dismiss "X" button when [onDismiss] is provided
 * - Glass-morphism support via [glass]
 *
 * Usage:
 * ```
 * DarwinAlertBanner(
 *     message = "Your changes have been saved.",
 *     title = "Success",
 *     type = DarwinAlertType.Success,
 *     onDismiss = { /* handle dismiss */ },
 * )
 * ```
 *
 * @param message The main text content of the alert.
 * @param title Optional title displayed above the message in bold.
 * @param type The semantic type determining accent color and icon.
 * @param onDismiss Optional callback invoked when the close button is pressed.
 *                  When null, no close button is shown.
 * @param glass When true, applies a glass-morphism effect to the background.
 * @param modifier Modifier applied to the banner container.
 */
@Composable
fun DarwinAlertBanner(
    message: String,
    title: String? = null,
    type: DarwinAlertType = DarwinAlertType.Info,
    onDismiss: (() -> Unit)? = null,
    glass: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography
    val shapes = DarwinTheme.shapes

    val accentColor = resolveAlertTypeColor(type)
    val tintedBackground = if (glass) {
        glassOrDefault(true, colors.card)
    } else {
        accentColor.copy(alpha = 0.05f)
    }
    val borderColor = if (glass) {
        glassBorderOrDefault(true, colors.border)
    } else {
        colors.border
    }

    val shape = shapes.large

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(tintedBackground, shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .drawBehind {
                // Left accent border (4dp wide)
                drawRect(
                    color = accentColor,
                    topLeft = Offset.Zero,
                    size = Size(4.dp.toPx(), size.height),
                )
            }
            .padding(start = 4.dp), // offset content past the accent border
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
        ) {
            // Type icon
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .drawBehind { drawAlertTypeIcon(type, accentColor) },
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Title + Message
            Column(
                modifier = Modifier.weight(1f),
            ) {
                if (title != null) {
                    DarwinText(
                        text = title,
                        style = typography.labelLarge,
                        color = colors.textPrimary,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
                DarwinText(
                    text = message,
                    style = typography.bodyMedium,
                    color = colors.textSecondary,
                )
            }

            // Dismiss button
            if (onDismiss != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onDismiss,
                        )
                        .drawBehind { drawAlertCloseIcon(colors.textTertiary) },
                    contentAlignment = Alignment.Center,
                ) {}
            }
        }
    }
}

// ===========================================================================
// DarwinAlertDialog (Modal)
// ===========================================================================

/**
 * A modal alert dialog that overlays the screen with a scrim and a centered card.
 *
 * Features:
 * - Animated entry/exit (scale + fade)
 * - Type-specific icon displayed prominently at 48dp
 * - Title in headlineSmall typography
 * - Message in bodyMedium with secondary text color
 * - Footer with optional Cancel (outline) and Confirm (filled) buttons
 * - Error type uses destructive red for the confirm button
 * - Glass-morphism support via [glass]
 *
 * Usage:
 * ```
 * var showDialog by remember { mutableStateOf(false) }
 *
 * DarwinAlertDialog(
 *     open = showDialog,
 *     onDismissRequest = { showDialog = false },
 *     title = "Delete Item?",
 *     message = "This action cannot be undone.",
 *     type = DarwinAlertType.Error,
 *     confirmText = "Delete",
 *     onConfirm = { /* delete logic */ },
 * )
 * ```
 *
 * @param open Whether the dialog is visible.
 * @param onDismissRequest Callback invoked when the dialog should close
 *                         (scrim tap or cancel).
 * @param title The dialog title text.
 * @param message The dialog body message.
 * @param type The semantic type determining icon and confirm button color.
 * @param confirmText The label for the confirm button.
 * @param cancelText The label for the cancel button. Pass null to hide it.
 * @param onConfirm Callback invoked when the confirm button is pressed.
 * @param onCancel Optional callback invoked when the cancel button is pressed.
 *                 Falls back to [onDismissRequest] when null.
 * @param glass When true, applies a glass-morphism effect to the dialog card.
 */
@Composable
fun DarwinAlertDialog(
    open: Boolean,
    onDismissRequest: () -> Unit,
    title: String,
    message: String,
    type: DarwinAlertType = DarwinAlertType.Info,
    confirmText: String = "OK",
    cancelText: String? = "Cancel",
    onConfirm: () -> Unit = {},
    onCancel: (() -> Unit)? = null,
    glass: Boolean = false,
) {
    AnimatedVisibility(
        visible = open,
        enter = fadeIn(animationSpec = tween(durationMillis = 200)),
        exit = fadeOut(animationSpec = tween(durationMillis = 150)),
    ) {
        val colors = DarwinTheme.colors

        // Scrim
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.scrim)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onDismissRequest,
                ),
            contentAlignment = Alignment.Center,
        ) {
            // Dialog card with its own animated visibility for scale
            AnimatedVisibility(
                visible = open,
                enter = scaleIn(
                    initialScale = 0.9f,
                    animationSpec = tween(durationMillis = 200),
                ) + fadeIn(animationSpec = tween(durationMillis = 200)),
                exit = scaleOut(
                    targetScale = 0.9f,
                    animationSpec = tween(durationMillis = 150),
                ) + fadeOut(animationSpec = tween(durationMillis = 150)),
            ) {
                DarwinAlertDialogContent(
                    title = title,
                    message = message,
                    type = type,
                    confirmText = confirmText,
                    cancelText = cancelText,
                    onConfirm = {
                        onConfirm()
                        onDismissRequest()
                    },
                    onCancel = {
                        (onCancel ?: onDismissRequest).invoke()
                    },
                    onDismissRequest = onDismissRequest,
                    glass = glass,
                )
            }
        }
    }
}

// ===========================================================================
// Dialog Content (Internal)
// ===========================================================================

@Composable
private fun DarwinAlertDialogContent(
    title: String,
    message: String,
    type: DarwinAlertType,
    confirmText: String,
    cancelText: String?,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    onDismissRequest: () -> Unit,
    glass: Boolean,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography
    val shapes = DarwinTheme.shapes

    val accentColor = resolveAlertTypeColor(type)
    val backgroundColor = glassOrDefault(glass, colors.card)
    val borderColor = glassBorderOrDefault(glass, colors.border)

    val confirmBackgroundColor = when (type) {
        DarwinAlertType.Error -> colors.destructive
        DarwinAlertType.Success -> colors.success
        DarwinAlertType.Warning -> colors.warning
        DarwinAlertType.Info -> colors.accent
    }
    val confirmContentColor = when (type) {
        DarwinAlertType.Error -> colors.onDestructive
        DarwinAlertType.Success -> colors.onSuccess
        DarwinAlertType.Warning -> colors.onWarning
        DarwinAlertType.Info -> colors.onAccent
    }

    val shape = shapes.extraLarge

    Column(
        modifier = Modifier
            .width(400.dp)
            .shadow(elevation = 16.dp, shape = shape, clip = false)
            .clip(shape)
            .background(backgroundColor, shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
            // Prevent click-through to scrim
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {},
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Type icon (48dp)
        Box(
            modifier = Modifier
                .size(48.dp)
                .drawBehind { drawAlertTypeIcon(type, accentColor) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        DarwinText(
            text = title,
            style = typography.headlineSmall,
            color = colors.textPrimary,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Message
        DarwinText(
            text = message,
            style = typography.bodyMedium,
            color = colors.textSecondary,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Footer buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
        ) {
            // Cancel button (outline style)
            if (cancelText != null) {
                val cancelShape = shapes.medium
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .clip(cancelShape)
                        .background(Color.Transparent, cancelShape)
                        .border(1.dp, colors.border, cancelShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onCancel,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    DarwinText(
                        text = cancelText,
                        style = typography.labelLarge,
                        color = colors.textPrimary,
                    )
                }
            }

            // Confirm button (filled, colored by type)
            val confirmShape = shapes.medium
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .clip(confirmShape)
                    .background(confirmBackgroundColor, confirmShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onConfirm,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                DarwinText(
                    text = confirmText,
                    style = typography.labelLarge,
                    color = confirmContentColor,
                )
            }
        }
    }
}

// ===========================================================================
// Canvas Icon Drawing
// ===========================================================================

/**
 * Draws the type-specific icon within a [DrawScope].
 *
 * - Info: Circle with "i" (vertical line with dot)
 * - Success: Circle with checkmark
 * - Warning: Triangle with "!" exclamation
 * - Error: Circle with "X"
 */
private fun DrawScope.drawAlertTypeIcon(type: DarwinAlertType, color: Color) {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val radius = size.minDimension / 2f - 1f
    val strokeWidth = size.minDimension * 0.09f

    when (type) {
        DarwinAlertType.Info -> {
            // Circle outline
            drawCircle(
                color = color,
                radius = radius,
                center = Offset(cx, cy),
                style = Stroke(width = strokeWidth),
            )
            // Dot at top
            drawCircle(
                color = color,
                radius = strokeWidth * 0.8f,
                center = Offset(cx, cy - radius * 0.35f),
                style = Fill,
            )
            // Vertical line (info "i" body)
            drawLine(
                color = color,
                start = Offset(cx, cy - radius * 0.05f),
                end = Offset(cx, cy + radius * 0.45f),
                strokeWidth = strokeWidth,
            )
        }

        DarwinAlertType.Success -> {
            // Circle outline
            drawCircle(
                color = color,
                radius = radius,
                center = Offset(cx, cy),
                style = Stroke(width = strokeWidth),
            )
            // Checkmark
            val path = Path().apply {
                moveTo(cx - radius * 0.35f, cy + radius * 0.0f)
                lineTo(cx - radius * 0.05f, cy + radius * 0.3f)
                lineTo(cx + radius * 0.35f, cy - radius * 0.25f)
            }
            drawPath(
                path = path,
                color = color,
                style = Stroke(width = strokeWidth),
            )
        }

        DarwinAlertType.Warning -> {
            // Triangle
            val trianglePath = Path().apply {
                moveTo(cx, cy - radius * 0.85f)
                lineTo(cx + radius * 0.95f, cy + radius * 0.75f)
                lineTo(cx - radius * 0.95f, cy + radius * 0.75f)
                close()
            }
            drawPath(
                path = trianglePath,
                color = color,
                style = Stroke(width = strokeWidth),
            )
            // Exclamation line
            drawLine(
                color = color,
                start = Offset(cx, cy - radius * 0.3f),
                end = Offset(cx, cy + radius * 0.2f),
                strokeWidth = strokeWidth,
            )
            // Exclamation dot
            drawCircle(
                color = color,
                radius = strokeWidth * 0.8f,
                center = Offset(cx, cy + radius * 0.5f),
                style = Fill,
            )
        }

        DarwinAlertType.Error -> {
            // Circle outline
            drawCircle(
                color = color,
                radius = radius,
                center = Offset(cx, cy),
                style = Stroke(width = strokeWidth),
            )
            // X mark
            val offset = radius * 0.3f
            drawLine(
                color = color,
                start = Offset(cx - offset, cy - offset),
                end = Offset(cx + offset, cy + offset),
                strokeWidth = strokeWidth,
            )
            drawLine(
                color = color,
                start = Offset(cx + offset, cy - offset),
                end = Offset(cx - offset, cy + offset),
                strokeWidth = strokeWidth,
            )
        }
    }
}

/**
 * Draws a small close "X" icon centered in the [DrawScope].
 */
private fun DrawScope.drawAlertCloseIcon(color: Color) {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val half = size.minDimension * 0.25f
    val strokeWidth = 1.5f

    drawLine(
        color = color,
        start = Offset(cx - half, cy - half),
        end = Offset(cx + half, cy + half),
        strokeWidth = strokeWidth,
    )
    drawLine(
        color = color,
        start = Offset(cx + half, cy - half),
        end = Offset(cx - half, cy + half),
        strokeWidth = strokeWidth,
    )
}
