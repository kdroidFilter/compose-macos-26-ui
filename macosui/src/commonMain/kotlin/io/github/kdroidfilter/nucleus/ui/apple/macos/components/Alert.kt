package io.github.kdroidfilter.nucleus.ui.apple.macos.components

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
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosGlassMaterial
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.GlassMaterialSize
import kotlinx.coroutines.delay

// ===========================================================================
// Alert Type (used by AlertDialog)
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
// AlertBanner — macOS notification banner
// ===========================================================================

/**
 * A macOS-style notification banner.
 *
 * Renders as a floating dark glass pill with:
 * - An optional [icon] on the left (typically a rounded app icon)
 * - [title] and [message] text in the center
 * - An optional [timestamp] in the top-right area
 * - Optional [trailingContent] on the far right (e.g. an image or avatar)
 *
 * @param title The notification title (bold).
 * @param message The notification body text.
 * @param modifier Modifier applied to the banner container.
 * @param timestamp Optional timestamp text displayed top-right (e.g. "now", "2m ago").
 * @param icon Optional leading icon composable (e.g. an app icon).
 * @param trailingContent Optional trailing composable (e.g. an image thumbnail).
 * @param onDismiss Optional callback invoked when the banner is tapped to dismiss.
 */
@Composable
fun AlertBanner(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    timestamp: String? = null,
    icon: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
) {
    val colors = MacosTheme.colorScheme
    val typography = MacosTheme.typography
    val isDark = colors.isDark

    val shape = RoundedCornerShape(14.dp)

    val textColor = if (isDark) Color.White else Color.Black.copy(alpha = 0.85f)
    val secondaryTextColor = if (isDark) Color.White.copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.45f)

    Box(
        modifier = modifier
            .widthIn(max = 360.dp)
            .macosGlassMaterial(shape = shape, materialSize = GlassMaterialSize.Medium)
            .then(
                if (onDismiss != null) {
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onDismiss,
                    )
                } else {
                    Modifier
                }
            ),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Leading icon
            if (icon != null) {
                Box(modifier = Modifier.size(36.dp)) {
                    icon()
                }
                Spacer(modifier = Modifier.width(10.dp))
            }

            // Title + Message
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        style = typography.caption1,
                        color = textColor,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false),
                    )
                    if (timestamp != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = timestamp,
                            style = typography.caption2,
                            color = secondaryTextColor,
                            maxLines = 1,
                        )
                    }
                }
                Text(
                    text = message,
                    style = typography.caption2,
                    color = secondaryTextColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            // Trailing content
            if (trailingContent != null) {
                Spacer(modifier = Modifier.width(10.dp))
                Box(modifier = Modifier.size(36.dp)) {
                    trailingContent()
                }
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
 * @param buttonLayout The arrangement of buttons: [AlertDialogButtonLayout.Stacked] (default)
 *                     for vertical stacking, or [AlertDialogButtonLayout.SideBySide] for horizontal layout.
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
    buttonLayout: AlertDialogButtonLayout = AlertDialogButtonLayout.Stacked,
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
            val colors = MacosTheme.colorScheme

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
                        buttonLayout = buttonLayout,
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
    buttonLayout: AlertDialogButtonLayout = AlertDialogButtonLayout.Stacked,
) {
    val colors = MacosTheme.colorScheme
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
            .macosGlassMaterial(shape = shape, materialSize = GlassMaterialSize.Large)
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

        when (buttonLayout) {
            AlertDialogButtonLayout.Stacked -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    AlertPillButton(
                        text = confirmText,
                        onClick = onConfirm,
                        style = AlertPillButtonStyle.Accent,
                    )
                    if (destructiveText != null && onDestructive != null) {
                        AlertPillButton(
                            text = destructiveText,
                            onClick = onDestructive,
                            style = AlertPillButtonStyle.Destructive,
                        )
                    }
                    if (cancelText != null) {
                        AlertPillButton(
                            text = cancelText,
                            onClick = onCancel,
                            style = AlertPillButtonStyle.Secondary,
                        )
                    }
                }
            }

            AlertDialogButtonLayout.SideBySide -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    if (destructiveText != null && onDestructive != null) {
                        AlertPillButton(
                            text = destructiveText,
                            onClick = onDestructive,
                            style = AlertPillButtonStyle.Destructive,
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        if (cancelText != null) {
                            AlertPillButton(
                                text = cancelText,
                                onClick = onCancel,
                                style = AlertPillButtonStyle.Secondary,
                                modifier = Modifier.weight(1f),
                            )
                        }
                        AlertPillButton(
                            text = confirmText,
                            onClick = onConfirm,
                            style = AlertPillButtonStyle.Accent,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }
    }
}

// ===========================================================================
// AlertPillButton — macOS-native full-width pill button for alert dialogs
// ===========================================================================

/**
 * Controls the button arrangement in an [AlertDialog].
 */
enum class AlertDialogButtonLayout {
    /** Buttons stacked vertically (macOS default for 3+ buttons). */
    Stacked,

    /** Buttons placed side by side (macOS style for 2-button alerts). */
    SideBySide,
}

private enum class AlertPillButtonStyle { Accent, Destructive, Secondary }

@Composable
private fun AlertPillButton(
    text: String,
    onClick: () -> Unit,
    style: AlertPillButtonStyle,
    modifier: Modifier = Modifier,
) {
    val isDark = MacosTheme.colorScheme.isDark
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
        val colors = MacosTheme.colorScheme
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
                    .macosGlassMaterial(shape = shape, materialSize = GlassMaterialSize.Large)
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
                        io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalContentColor provides resolvedIconColor,
                    ) {
                        Box(modifier = Modifier.padding(bottom = 10.dp)) { icon() }
                    }
                }
                if (title != null) {
                    androidx.compose.runtime.CompositionLocalProvider(
                        io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalTextStyle provides MacosTheme.typography.caption1.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = resolvedTitleColor,
                        ),
                        io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalContentColor provides resolvedTitleColor,
                    ) {
                        Box(modifier = Modifier.padding(bottom = 4.dp)) { title() }
                    }
                }
                if (text != null) {
                    androidx.compose.runtime.CompositionLocalProvider(
                        io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalTextStyle provides MacosTheme.typography.caption1.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp,
                            color = resolvedTextColor,
                        ),
                        io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalContentColor provides resolvedTextColor,
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
    MacosTheme {
        AlertBanner(
            title = "Calendar",
            message = "You have a meeting in 15 minutes",
            timestamp = "now",
        )
    }
}
