package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icons
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.GlassMaterialSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosGlassMaterial
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// ===========================================================================
// Toast Data
// ===========================================================================

/**
 * Immutable data class representing a single toast notification.
 *
 * @param id Unique identifier for the toast. Auto-generated via an incrementing counter.
 * @param title The notification title (bold).
 * @param message The notification body text.
 * @param timestamp Optional timestamp text (e.g. "now", "2m ago").
 * @param icon Optional leading icon composable (e.g. an app icon).
 * @param trailingContent Optional trailing composable (e.g. an image thumbnail).
 * @param showCloseButton Whether to display a close button on the trailing edge.
 * @param duration How long (in milliseconds) the toast remains visible before auto-dismissing.
 *                 Pass `null` to make the toast persistent (only dismissible via close button or tap).
 */
data class ToastData(
    val id: Long = nextToastId(),
    val title: String,
    val message: String,
    val timestamp: String? = null,
    val icon: (@Composable () -> Unit)? = null,
    val trailingContent: (@Composable () -> Unit)? = null,
    val showCloseButton: Boolean = false,
    val duration: Long? = 3000L,
    val onClick: (() -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null,
)

/** Simple incrementing counter for generating unique toast IDs. */
private var toastIdCounter = 0L

private fun nextToastId(): Long = ++toastIdCounter

// ===========================================================================
// Toast State
// ===========================================================================

/**
 * State holder for managing a list of active toast notifications.
 *
 * Create an instance with [rememberToastState] and pass it to [ToastHost].
 *
 * Usage:
 * ```
 * val toastState = rememberToastState()
 *
 * ToastHost(state = toastState)
 *
 * // Show a toast
 * toastState.show(title = "Calendar", message = "Meeting in 15 minutes")
 * ```
 */
@Stable
class ToastState {

    private val _toasts = mutableStateListOf<ToastData>()

    /** The current list of active toasts (observable). */
    val toasts: List<ToastData> get() = _toasts

    /**
     * Shows a new toast notification.
     *
     * @param title The notification title (bold).
     * @param message The notification body text.
     * @param timestamp Optional timestamp text (e.g. "now").
     * @param icon Optional leading icon composable.
     * @param trailingContent Optional trailing composable.
     * @param showCloseButton Whether to display a close button.
     * @param duration How long (in milliseconds) the toast remains visible.
     *                 Pass `null` for a persistent toast.
     */
    fun show(
        title: String,
        message: String,
        timestamp: String? = null,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        showCloseButton: Boolean = false,
        duration: Long? = 3000L,
        onClick: (() -> Unit)? = null,
        onDismiss: (() -> Unit)? = null,
    ) {
        _toasts.add(
            ToastData(
                title = title,
                message = message,
                timestamp = timestamp,
                icon = icon,
                trailingContent = trailingContent,
                showCloseButton = showCloseButton,
                duration = duration,
                onClick = onClick,
                onDismiss = onDismiss,
            )
        )
    }

    /**
     * Dismisses a toast by its [id].
     */
    fun dismiss(id: Long) {
        _toasts.removeAll { it.id == id }
    }
}

/**
 * Creates and remembers a [ToastState] instance.
 */
@Composable
fun rememberToastState(): ToastState {
    return remember { ToastState() }
}

// ===========================================================================
// Toast Host
// ===========================================================================

/**
 * A container that displays toast notifications from the given [state].
 *
 * Displays toast notifications from the given [state] using a [Popup] to ensure
 * they render above all other content (including title bars).
 *
 * Toasts appear stacked in the top-end corner, matching macOS notification behavior.
 *
 * @param state The [ToastState] that holds the active toasts.
 * @param modifier Modifier applied to the outer container.
 */
@Composable
fun ToastHost(
    state: ToastState,
    modifier: Modifier = Modifier,
) {
    Popup(
        alignment = Alignment.TopEnd,
        properties = PopupProperties(focusable = false),
    ) {
        Column(
            modifier = modifier.padding(top = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.End,
        ) {
            state.toasts.forEach { toast ->
                key(toast.id) {
                    ToastItem(
                        toast = toast,
                        onDismiss = { state.dismiss(toast.id) },
                    )
                }
            }
        }
    }
}

// ===========================================================================
// Individual Toast Item
// ===========================================================================

/**
 * A single toast notification rendered as a macOS notification banner.
 */
@Composable
private fun ToastItem(
    toast: ToastData,
    onDismiss: () -> Unit,
) {
    val colors = MacosTheme.colorScheme
    val typography = MacosTheme.typography
    val isDark = colors.isDark

    val textColor = if (isDark) Color.White else Color.Black.copy(alpha = 0.85f)
    val secondaryTextColor = if (isDark) Color.White.copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.45f)

    val shape = RoundedCornerShape(14.dp)

    var visible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Animated dismiss with onDismiss callback (close button, auto-expiration)
    fun animatedDismiss() {
        scope.launch {
            toast.onDismiss?.invoke()
            visible = false
            delay(300)
            onDismiss()
        }
    }

    // Silent dismiss without onDismiss callback (tap with onClick)
    fun silentDismiss() {
        scope.launch {
            visible = false
            delay(300)
            onDismiss()
        }
    }

    // Trigger enter animation, then auto-dismiss if duration is set
    LaunchedEffect(toast.id) {
        visible = true
        if (toast.duration != null) {
            delay(toast.duration)
            toast.onDismiss?.invoke()
            visible = false
            delay(300)
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(
            initialOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(durationMillis = 350),
        ) + fadeIn(animationSpec = tween(durationMillis = 250)),
        exit = slideOutHorizontally(
            targetOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(durationMillis = 250),
        ) + shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(durationMillis = 250, delayMillis = 200),
        ) + fadeOut(animationSpec = tween(durationMillis = 200)),
    ) {
        // Outer box with padding to allow the close badge to overflow
        Box(
            modifier = Modifier.padding(
                top = if (toast.showCloseButton) 5.dp else 0.dp,
                end = if (toast.showCloseButton) 5.dp else 0.dp,
                bottom = 8.dp,
            ),
        ) {
            // Toast card
            Box(
                modifier = Modifier
                    .widthIn(max = 360.dp)
                    .macosGlassMaterial(shape = shape, materialSize = GlassMaterialSize.Medium)
                    .border(width = 0.5.dp, color = colors.borderSubtle, shape = shape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            toast.onClick?.invoke()
                            if (toast.onClick != null) silentDismiss() else animatedDismiss()
                        },
                    ),
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Leading icon
                    if (toast.icon != null) {
                        Box(modifier = Modifier.size(36.dp)) {
                            toast.icon.invoke()
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }

                    // Title + Message
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = toast.title,
                                style = typography.caption1,
                                color = textColor,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false),
                            )
                            if (toast.timestamp != null) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = toast.timestamp,
                                    style = typography.caption2,
                                    color = secondaryTextColor,
                                    maxLines = 1,
                                )
                            }
                        }
                        Text(
                            text = toast.message,
                            style = typography.caption2,
                            color = secondaryTextColor,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    // Trailing content
                    if (toast.trailingContent != null) {
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(modifier = Modifier.size(36.dp)) {
                            toast.trailingContent.invoke()
                        }
                    }
                }
            }

            // Close badge — floating circle at top-end, overlapping the card
            if (toast.showCloseButton) {
                val closeBg = if (isDark) Color(0xFF3A3A3C) else Color(0xFFB0B0B0)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 5.dp, y = (-5).dp)
                        .size(16.dp)
                        .background(closeBg, CircleShape)
                        .border(0.5.dp, colors.borderSubtle, CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { animatedDismiss() },
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        icon = Icons.X,
                        modifier = Modifier.size(8.dp),
                        tint = if (isDark) Color.White.copy(alpha = 0.9f) else Color.White,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ToastPreview() {
    MacosTheme {
        val state = rememberToastState()
        ToastHost(state = state)
    }
}
