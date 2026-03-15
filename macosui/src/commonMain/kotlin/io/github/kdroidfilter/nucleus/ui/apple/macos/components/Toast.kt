package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.GlassMaterialSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosGlassMaterial
import kotlinx.coroutines.delay

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
 * @param duration How long (in milliseconds) the toast remains visible before auto-dismissing.
 */
data class ToastData(
    val id: Long = nextToastId(),
    val title: String,
    val message: String,
    val timestamp: String? = null,
    val icon: (@Composable () -> Unit)? = null,
    val trailingContent: (@Composable () -> Unit)? = null,
    val duration: Long = 3000L,
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
     * @param duration How long (in milliseconds) the toast remains visible.
     */
    fun show(
        title: String,
        message: String,
        timestamp: String? = null,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        duration: Long = 3000L,
    ) {
        _toasts.add(
            ToastData(
                title = title,
                message = message,
                timestamp = timestamp,
                icon = icon,
                trailingContent = trailingContent,
                duration = duration,
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
    if (state.toasts.isEmpty()) return

    Popup(
        alignment = Alignment.TopEnd,
        properties = PopupProperties(focusable = false),
    ) {
        Column(
            modifier = modifier.padding(top = 12.dp, end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.End,
        ) {
            state.toasts.forEach { toast ->
                ToastItem(
                    toast = toast,
                    onDismiss = { state.dismiss(toast.id) },
                )
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

    // Auto-dismiss
    LaunchedEffect(toast.id) {
        delay(toast.duration)
        onDismiss()
    }

    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 300),
        ) + fadeIn(animationSpec = tween(durationMillis = 300)),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 200),
        ) + fadeOut(animationSpec = tween(durationMillis = 200)),
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 360.dp)
                .macosGlassMaterial(shape = shape, materialSize = GlassMaterialSize.Medium)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onDismiss,
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
