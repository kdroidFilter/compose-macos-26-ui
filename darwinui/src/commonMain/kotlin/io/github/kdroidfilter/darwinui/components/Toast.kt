package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import kotlinx.coroutines.delay

// ===========================================================================
// Toast Type
// ===========================================================================

/**
 * The semantic type of a toast notification, determining its accent color and icon.
 */
enum class ToastType {
    /** Informational toast with blue accent. */
    Info,

    /** Success toast with green accent. */
    Success,

    /** Warning toast with amber accent. */
    Warning,

    /** Error toast with red accent. */
    Error,
}

// ===========================================================================
// Toast Data
// ===========================================================================

/**
 * Immutable data class representing a single toast notification.
 *
 * @param id Unique identifier for the toast. Auto-generated via an incrementing counter.
 * @param message The main text content of the toast.
 * @param title Optional title displayed above the message.
 * @param type The semantic type determining accent color and icon.
 * @param duration How long (in milliseconds) the toast remains visible before auto-dismissing.
 */
data class ToastData(
    val id: Long = nextToastId(),
    val message: String,
    val title: String? = null,
    val type: ToastType = ToastType.Info,
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
 * toastState.show("File saved successfully", type = ToastType.Success)
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
     * @param message The main text content of the toast.
     * @param title Optional title displayed above the message.
     * @param type The semantic type determining accent color and icon.
     * @param duration How long (in milliseconds) the toast remains visible.
     */
    fun show(
        message: String,
        title: String? = null,
        type: ToastType = ToastType.Info,
        duration: Long = 3000L,
    ) {
        _toasts.add(
            ToastData(
                message = message,
                title = title,
                type = type,
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
 * Place this composable at the root of your layout (typically inside a [Box] that fills
 * the screen). Toasts appear stacked in the top-right corner with slide and fade animations.
 *
 * @param state The [ToastState] that holds the active toasts.
 * @param modifier Modifier applied to the outer container.
 */
@Composable
fun ToastHost(
    state: ToastState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopEnd,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
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
 * A single toast notification card.
 *
 * Features:
 * - 360dp width
 * - Card background with border
 * - 4dp left accent border colored by [ToastData.type]
 * - Type-specific icon drawn via Canvas
 * - Close button in the top-right corner
 * - Auto-dismiss after [ToastData.duration] via [LaunchedEffect]
 * - Animated entry (slide from right + fade in) and exit (slide out + fade out)
 */
@Composable
private fun ToastItem(
    toast: ToastData,
    onDismiss: () -> Unit,
) {
    val colors = DarwinTheme.colorScheme
    val typography = DarwinTheme.typography
    val shapes = DarwinTheme.shapes

    val accentColor = resolveTypeColor(toast.type)
    val backgroundColor = colors.card
    val borderColor = colors.border

    // Auto-dismiss
    LaunchedEffect(toast.id) {
        delay(toast.duration)
        onDismiss()
    }

    AnimatedVisibility(
        visible = true,
        enter = slideInHorizontally(
            initialOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(durationMillis = 300),
        ) + fadeIn(animationSpec = tween(durationMillis = 300)),
        exit = slideOutHorizontally(
            targetOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(durationMillis = 200),
        ) + fadeOut(animationSpec = tween(durationMillis = 200)),
    ) {
        val shape = shapes.large

        Box(
            modifier = Modifier
                .width(360.dp)
                .shadow(elevation = 8.dp, shape = shape, clip = false)
                .clip(shape)
                .background(backgroundColor, shape)
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
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.Top,
            ) {
                // Type icon
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .drawBehind { drawTypeIcon(toast.type, accentColor) },
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Title + Message
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    if (toast.title != null) {
                        Text(
                            text = toast.title,
                            style = typography.subheadline,
                            color = colors.textPrimary,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    Text(
                        text = toast.message,
                        style = typography.caption1,
                        color = colors.textSecondary,
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Close button
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onDismiss,
                        )
                        .drawBehind { drawCloseIcon(colors.textTertiary) },
                    contentAlignment = Alignment.Center,
                ) {}
            }
        }
    }
}

// ===========================================================================
// Type Color Resolution
// ===========================================================================

/**
 * Returns the accent [Color] for the given [ToastType].
 */
@Composable
private fun resolveTypeColor(type: ToastType): Color {
    val colors = DarwinTheme.colorScheme
    return when (type) {
        ToastType.Info -> colors.info
        ToastType.Success -> colors.success
        ToastType.Warning -> colors.warning
        ToastType.Error -> colors.destructive
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
private fun DrawScope.drawTypeIcon(type: ToastType, color: Color) {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val radius = size.minDimension / 2f - 1f
    val strokeWidth = 1.8f

    when (type) {
        ToastType.Info -> {
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
                radius = 1.5f,
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

        ToastType.Success -> {
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

        ToastType.Warning -> {
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
                radius = 1.5f,
                center = Offset(cx, cy + radius * 0.5f),
                style = Fill,
            )
        }

        ToastType.Error -> {
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
private fun DrawScope.drawCloseIcon(color: Color) {
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

@Preview
@Composable
private fun ToastPreview() {
    DarwinTheme {
        val state = rememberToastState()
        ToastHost(state = state)
    }
}
