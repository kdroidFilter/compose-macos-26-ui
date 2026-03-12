package io.github.kdroidfilter.darwinui.components

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.darwinGlass
import kotlinx.coroutines.delay

// ===========================================================================
// Alert Type
// ===========================================================================

/**
 * The semantic type of an alert, determining its accent color and icon.
 */
enum class AlertType {
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
 * Returns the accent [Color] for the given [AlertType].
 */
@Composable
private fun resolveAlertTypeColor(type: AlertType): Color {
    val colors = DarwinTheme.colorScheme
    return when (type) {
        AlertType.Info -> colors.info
        AlertType.Success -> colors.success
        AlertType.Warning -> colors.warning
        AlertType.Error -> colors.destructive
    }
}

// ===========================================================================
// AlertBanner (Inline)
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
 *
 * Usage:
 * ```
 * AlertBanner(
 *     message = "Your changes have been saved.",
 *     title = "Success",
 *     type = AlertType.Success,
 *     onDismiss = { /* handle dismiss */ },
 * )
 * ```
 *
 * @param message The main text content of the alert.
 * @param title Optional title displayed above the message in bold.
 * @param type The semantic type determining accent color and icon.
 * @param onDismiss Optional callback invoked when the close button is pressed.
 *                  When null, no close button is shown.
 * @param modifier Modifier applied to the banner container.
 */
@Composable
fun AlertBanner(
    message: String,
    title: String? = null,
    type: AlertType = AlertType.Info,
    onDismiss: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colorScheme
    val typography = DarwinTheme.typography
    val shapes = DarwinTheme.shapes

    val accentColor = resolveAlertTypeColor(type)
    val tintedBackground = accentColor.copy(alpha = 0.05f)
    val borderColor = colors.border

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
                    Text(
                        text = title,
                        style = typography.subheadline,
                        color = colors.textPrimary,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
                Text(
                    text = message,
                    style = typography.subheadline,
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
// AlertDialog (Modal)
// ===========================================================================

/**
 * A modal alert dialog matching the macOS native alert panel.
 *
 * Features:
 * - Animated entry/exit (scale + fade)
 * - Large rounded corners (26dp) with frosted-glass appearance
 * - Vertically stacked full-width pill buttons (accent, destructive, secondary)
 * - Centered title (bold) and message text
 *
 * Usage:
 * ```
 * var showDialog by remember { mutableStateOf(false) }
 *
 * AlertDialog(
 *     open = showDialog,
 *     onDismissRequest = { showDialog = false },
 *     title = "Save the document?",
 *     message = "Your changes will be lost if you don't save them.",
 *     confirmText = "Save",
 *     destructiveText = "Don't Save",
 *     cancelText = "Cancel",
 *     onConfirm = { /* save logic */ },
 *     onDestructive = { /* discard logic */ },
 * )
 * ```
 *
 * @param open Whether the dialog is visible.
 * @param onDismissRequest Callback invoked when the dialog should close.
 * @param title The dialog title text (bold, centered).
 * @param message The dialog body message (regular, centered).
 * @param type The semantic type (currently used for theming compatibility).
 * @param confirmText The label for the primary action button (blue accent).
 * @param destructiveText Optional label for a destructive action button (red tint).
 * @param cancelText The label for the cancel button. Pass null to hide it.
 * @param onConfirm Callback invoked when the confirm button is pressed.
 * @param onDestructive Optional callback for the destructive action button.
 * @param onCancel Optional callback invoked when the cancel button is pressed.
 *                 Falls back to [onDismissRequest] when null.
 */
@Composable
fun AlertDialog(
    open: Boolean,
    onDismissRequest: () -> Unit,
    title: String,
    message: String,
    type: AlertType = AlertType.Info,
    confirmText: String = "OK",
    destructiveText: String? = null,
    cancelText: String? = "Cancel",
    onConfirm: () -> Unit = {},
    onDestructive: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null,
) {
    // Keep the popup mounted while the exit animation plays
    var showPopup by remember { mutableStateOf(false) }
    var animateIn by remember { mutableStateOf(false) }

    LaunchedEffect(open) {
        if (open) {
            showPopup = true
            delay(16)
            animateIn = true
        } else {
            animateIn = false
            delay(200 + 50L)
            showPopup = false
        }
    }

    if (showPopup) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = onDismissRequest,
            properties = PopupProperties(focusable = true),
        ) {
            val colors = DarwinTheme.colorScheme

            // Scrim with fade animation
            AnimatedVisibility(
                visible = animateIn,
                enter = fadeIn(animationSpec = tween(durationMillis = 200)),
                exit = fadeOut(animationSpec = tween(durationMillis = 150)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.scrim)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onDismissRequest,
                        ),
                )
            }

            // Dialog card with scale animation
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                AnimatedVisibility(
                    visible = animateIn,
                    enter = scaleIn(
                        initialScale = 0.9f,
                        animationSpec = tween(durationMillis = 200),
                    ) + fadeIn(animationSpec = tween(durationMillis = 200)),
                    exit = scaleOut(
                        targetScale = 0.9f,
                        animationSpec = tween(durationMillis = 150),
                    ) + fadeOut(animationSpec = tween(durationMillis = 150)),
                ) {
                    AlertDialogContent(
                        title = title,
                        message = message,
                        type = type,
                        confirmText = confirmText,
                        cancelText = cancelText,
                        destructiveText = destructiveText,
                        onConfirm = {
                            onConfirm()
                            onDismissRequest()
                        },
                        onCancel = {
                            (onCancel ?: onDismissRequest).invoke()
                        },
                        onDestructive = if (onDestructive != null) {
                            {
                                onDestructive()
                                onDismissRequest()
                            }
                        } else null,
                    )
                }
            }
        }
    }
}

// ===========================================================================
// Dialog Content (Internal)
// ===========================================================================

@Composable
private fun AlertDialogContent(
    title: String,
    message: String,
    type: AlertType,
    confirmText: String,
    cancelText: String?,
    destructiveText: String? = null,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    onDestructive: (() -> Unit)? = null,
) {
    val colors = DarwinTheme.colorScheme
    val isDark = colors.isDark

    // macOS native alert panel appearance — frosted glass vibrancy
    val fallbackBg = if (isDark) Color(0xFF262626).copy(alpha = 0.85f)
    else Color(0xFFF5F5F5).copy(alpha = 0.82f)
    val borderColor = if (isDark) Color(0xFF5E5E5E).copy(alpha = 0.6f)
    else Color(0xFFC1C1C1).copy(alpha = 0.45f)
    val textColor = if (isDark) Color.White else Color.Black.copy(alpha = 0.85f)

    val shape = RoundedCornerShape(26.dp)

    Column(
        modifier = Modifier
            .widthIn(max = 260.dp)
            .shadow(elevation = 25.dp, shape = shape, clip = false, ambientColor = Color.Black.copy(alpha = 0.5f))
            .darwinGlass(shape = shape, fallbackColor = fallbackBg)
            .border(width = 0.5.dp, color = borderColor, shape = shape)
            // Prevent click-through to scrim
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {},
            )
            .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Title — macOS uses ~13sp bold, centered
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Message — macOS uses ~11sp regular, centered
        Text(
            text = message,
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            color = textColor,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Vertically stacked full-width pill buttons (macOS native style)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            // Primary action — accent blue pill
            AlertPillButton(
                text = confirmText,
                onClick = onConfirm,
                style = AlertPillButtonStyle.Accent,
            )

            // Destructive action — red tinted pill (optional)
            if (destructiveText != null && onDestructive != null) {
                AlertPillButton(
                    text = destructiveText,
                    onClick = onDestructive,
                    style = AlertPillButtonStyle.Destructive,
                )
            }

            // Cancel — neutral gray pill
            if (cancelText != null) {
                AlertPillButton(
                    text = cancelText,
                    onClick = onCancel,
                    style = AlertPillButtonStyle.Secondary,
                )
            }
        }
    }
}

// ===========================================================================
// AlertPillButton — macOS-native full-width pill button for alert dialogs
// ===========================================================================

private enum class AlertPillButtonStyle { Accent, Destructive, Secondary }

@Composable
private fun AlertPillButton(
    text: String,
    onClick: () -> Unit,
    style: AlertPillButtonStyle,
    modifier: Modifier = Modifier,
) {
    val isDark = DarwinTheme.colorScheme.isDark
    val interactionSource = remember { MutableInteractionSource() }

    val pillShape = RoundedCornerShape(14.dp)

    val backgroundColor: Color
    val textColor: Color
    val borderColor: Color

    when (style) {
        AlertPillButtonStyle.Accent -> {
            backgroundColor = Color(0xFF0088FF)
            textColor = Color.White
            borderColor = Color.Transparent
        }
        AlertPillButtonStyle.Destructive -> {
            backgroundColor = Color(0xFFFF383C).copy(alpha = 0.23f)
            textColor = Color(0xFFFF383C)
            borderColor = Color.Transparent
        }
        AlertPillButtonStyle.Secondary -> {
            backgroundColor = if (isDark) Color.White.copy(alpha = 0.12f) else Color(0xFFE6E6E6)
            textColor = if (isDark) Color.White else Color.Black.copy(alpha = 0.85f)
            borderColor = Color.Transparent
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(28.dp)
            .clip(pillShape)
            .background(backgroundColor, pillShape)
            .then(
                if (borderColor != Color.Transparent) Modifier.border(0.5.dp, borderColor, pillShape)
                else Modifier,
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,
            textAlign = TextAlign.Center,
        )
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
private fun DrawScope.drawAlertTypeIcon(type: AlertType, color: Color) {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val radius = size.minDimension / 2f - 1f
    val strokeWidth = size.minDimension * 0.09f

    when (type) {
        AlertType.Info -> {
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

        AlertType.Success -> {
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

        AlertType.Warning -> {
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

        AlertType.Error -> {
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

// ===========================================================================
// AlertDialog — M3-compatible overload with composable slots
// ===========================================================================

/**
 * A modal alert dialog — mirrors Material3's AlertDialog with composable slots.
 *
 * @param onDismissRequest Callback when the dialog should close.
 * @param confirmButton The confirm action button composable.
 * @param modifier Modifier for the dialog container.
 * @param dismissButton Optional dismiss/cancel button composable.
 * @param icon Optional icon displayed above the title.
 * @param title Optional title composable.
 * @param text Optional body text composable.
 * @param shape Dialog container shape.
 * @param containerColor Background color of the dialog.
 * @param iconContentColor Tint color for the icon.
 * @param titleContentColor Color for the title content.
 * @param textContentColor Color for the text content.
 * @param tonalElevation Elevation for tonal color.
 */
@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: (@Composable () -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    text: (@Composable () -> Unit)? = null,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(26.dp),
    containerColor: Color = Color.Unspecified,
    iconContentColor: Color = Color.Unspecified,
    titleContentColor: Color = Color.Unspecified,
    textContentColor: Color = Color.Unspecified,
    tonalElevation: androidx.compose.ui.unit.Dp = 0.dp,
) {
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true),
    ) {
        val colors = DarwinTheme.colorScheme
        val isDark = colors.isDark

        // macOS native alert panel colors
        val fallbackBg = if (containerColor != Color.Unspecified) containerColor
        else if (isDark) Color(0xFF262626).copy(alpha = 0.85f) else Color(0xFFF5F5F5).copy(alpha = 0.82f)
        val borderColor = if (isDark) Color(0xFF5E5E5E).copy(alpha = 0.6f) else Color(0xFFC1C1C1).copy(alpha = 0.45f)
        val nativeTextColor = if (isDark) Color.White else Color.Black.copy(alpha = 0.85f)
        val resolvedIconColor = if (iconContentColor != Color.Unspecified) iconContentColor else nativeTextColor
        val resolvedTitleColor = if (titleContentColor != Color.Unspecified) titleContentColor else nativeTextColor
        val resolvedTextColor = if (textContentColor != Color.Unspecified) textContentColor else nativeTextColor

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.scrim)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onDismissRequest,
                ),
        )

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = modifier
                    .widthIn(max = 260.dp)
                    .shadow(elevation = 25.dp, shape = shape, clip = false, ambientColor = Color.Black.copy(alpha = 0.5f))
                    .darwinGlass(shape = shape, fallbackColor = fallbackBg)
                    .border(width = 0.5.dp, color = borderColor, shape = shape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {},
                    )
                    .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (icon != null) {
                    androidx.compose.runtime.CompositionLocalProvider(
                        io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor provides resolvedIconColor,
                    ) {
                        Box(modifier = Modifier.padding(bottom = 10.dp)) { icon() }
                    }
                }
                if (title != null) {
                    androidx.compose.runtime.CompositionLocalProvider(
                        io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle provides DarwinTheme.typography.caption1.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = resolvedTitleColor,
                        ),
                        io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor provides resolvedTitleColor,
                    ) {
                        Box(modifier = Modifier.padding(bottom = 4.dp)) { title() }
                    }
                }
                if (text != null) {
                    androidx.compose.runtime.CompositionLocalProvider(
                        io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle provides DarwinTheme.typography.caption1.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp,
                            color = resolvedTextColor,
                        ),
                        io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor provides resolvedTextColor,
                    ) {
                        Box(modifier = Modifier.padding(bottom = 16.dp)) { text() }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    confirmButton()
                    dismissButton?.invoke()
                }
            }
        }
    }
}

@Preview
@Composable
private fun AlertPreview() {
    DarwinTheme {
        AlertBanner(
            message = "Your changes have been saved.",
            title = "Success",
            type = AlertType.Success,
        )
    }
}
