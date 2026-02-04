package io.github.kdroidfilter.darwinui.components.toast

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
import androidx.compose.foundation.shape.RoundedCornerShape
import io.github.kdroidfilter.darwinui.components.text.DarwinText
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
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import kotlinx.coroutines.delay

// ===========================================================================
// Toast Type
// ===========================================================================

/**
 * The semantic type of a toast notification, determining its accent color and icon.
 */
enum class DarwinToastType {
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
data class DarwinToastData(
    val id: Long = nextToastId(),
    val message: String,
    val title: String? = null,
    val type: DarwinToastType = DarwinToastType.Info,
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
 * Create an instance with [rememberDarwinToastState] and pass it to [DarwinToastHost].
 *
 * Usage:
 * ```
 * val toastState = rememberDarwinToastState()
 *
 * DarwinToastHost(state = toastState)
 *
 * // Show a toast
 * toastState.show("File saved successfully", type = DarwinToastType.Success)
 * ```
 */
@Stable
class DarwinToastState {

    private val _toasts = mutableStateListOf<DarwinToastData>()

    /** The current list of active toasts (observable). */
    val toasts: List<DarwinToastData> get() = _toasts

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
        type: DarwinToastType = DarwinToastType.Info,
        duration: Long = 3000L,
    ) {
        _toasts.add(
            DarwinToastData(
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
 * Creates and remembers a [DarwinToastState] instance.
 */
@Composable
fun rememberDarwinToastState(): DarwinToastState {
    return remember { DarwinToastState() }
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
 * @param state The [DarwinToastState] that holds the active toasts.
 * @param modifier Modifier applied to the outer container.
 */
@Composable
fun DarwinToastHost(
    state: DarwinToastState,
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
                DarwinToastItem(
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
 * - 4dp left accent border colored by [DarwinToastData.type]
 * - Type-specific icon drawn via Canvas
 * - Close button in the top-right corner
 * - Auto-dismiss after [DarwinToastData.duration] via [LaunchedEffect]
 * - Animated entry (slide from right + fade in) and exit (slide out + fade out)
 */
@Composable
private fun DarwinToastItem(
    toast: DarwinToastData,
    onDismiss: () -> Unit,
) {
    val colors = DarwinTheme.colors
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
                        DarwinText(
                            text = toast.title,
                            style = typography.labelLarge,
                            color = colors.textPrimary,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    DarwinText(
                        text = toast.message,
                        style = typography.bodySmall,
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
 * Returns the accent [Color] for the given [DarwinToastType].
 */
@Composable
private fun resolveTypeColor(type: DarwinToastType): Color {
    val colors = DarwinTheme.colors
    return when (type) {
        DarwinToastType.Info -> colors.info
        DarwinToastType.Success -> colors.success
        DarwinToastType.Warning -> colors.warning
        DarwinToastType.Error -> colors.destructive
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
private fun DrawScope.drawTypeIcon(type: DarwinToastType, color: Color) {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val radius = size.minDimension / 2f - 1f
    val strokeWidth = 1.8f

    when (type) {
        DarwinToastType.Info -> {
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

        DarwinToastType.Success -> {
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

        DarwinToastType.Warning -> {
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

        DarwinToastType.Error -> {
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
