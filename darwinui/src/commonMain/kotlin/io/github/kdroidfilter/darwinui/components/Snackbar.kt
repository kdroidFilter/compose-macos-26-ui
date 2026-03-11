package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import kotlinx.coroutines.delay

// ===========================================================================
// SnackbarData — mirrors M3's SnackbarData
// ===========================================================================

interface SnackbarData {
    val visuals: SnackbarVisuals
    fun dismiss()
    fun performAction()
}

// ===========================================================================
// SnackbarVisuals — mirrors M3's SnackbarVisuals
// ===========================================================================

interface SnackbarVisuals {
    val message: String
    val actionLabel: String?
    val withDismissAction: Boolean
    val duration: SnackbarDuration
}

// ===========================================================================
// SnackbarDuration — mirrors M3's SnackbarDuration
// ===========================================================================

enum class SnackbarDuration {
    Short, Long, Indefinite
}

// ===========================================================================
// SnackbarResult — mirrors M3's SnackbarResult
// ===========================================================================

enum class SnackbarResult {
    Dismissed, ActionPerformed
}

// ===========================================================================
// SnackbarDefaults — mirrors M3's SnackbarDefaults
// ===========================================================================

object SnackbarDefaults {
    val containerColor: Color @Composable get() = DarwinTheme.colorScheme.inverseSurface
    val contentColor: Color @Composable get() = DarwinTheme.colorScheme.inverseOnSurface
    val actionContentColor: Color @Composable get() = DarwinTheme.colorScheme.inversePrimary
    val dismissActionContentColor: Color @Composable get() = DarwinTheme.colorScheme.inverseOnSurface
}

// ===========================================================================
// Snackbar — mirrors M3's Snackbar
// ===========================================================================

@Composable
fun Snackbar(
    modifier: Modifier = Modifier,
    action: (@Composable () -> Unit)? = null,
    dismissAction: (@Composable () -> Unit)? = null,
    actionOnNewLine: Boolean = false,
    shape: Shape = DarwinTheme.shapes.extraSmall,
    containerColor: Color = SnackbarDefaults.containerColor,
    contentColor: Color = SnackbarDefaults.contentColor,
    actionContentColor: Color = SnackbarDefaults.actionContentColor,
    dismissActionContentColor: Color = SnackbarDefaults.dismissActionContentColor,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .shadow(elevation = 6.dp, shape = shape, clip = false)
            .clip(shape)
            .background(containerColor, shape)
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        if (actionOnNewLine && action != null) {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                CompositionLocalProvider(
                    LocalDarwinContentColor provides contentColor,
                    LocalDarwinTextStyle provides DarwinTheme.typography.bodyMedium.copy(color = contentColor),
                ) { content() }
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    CompositionLocalProvider(LocalDarwinContentColor provides actionContentColor) { action() }
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    CompositionLocalProvider(
                        LocalDarwinContentColor provides contentColor,
                        LocalDarwinTextStyle provides DarwinTheme.typography.bodyMedium.copy(color = contentColor),
                    ) { content() }
                }
                if (action != null) {
                    CompositionLocalProvider(LocalDarwinContentColor provides actionContentColor) { action() }
                }
                if (dismissAction != null) {
                    CompositionLocalProvider(LocalDarwinContentColor provides dismissActionContentColor) { dismissAction() }
                }
            }
        }
    }
}

// ===========================================================================
// SnackbarHostState — mirrors M3's SnackbarHostState
// ===========================================================================

@Stable
class SnackbarHostState {
    private var _currentData by mutableStateOf<SnackbarData?>(null)
    val currentSnackbarData: SnackbarData? get() = _currentData

    suspend fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration = if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
    ): SnackbarResult {
        var result = SnackbarResult.Dismissed
        val data = object : SnackbarData {
            override val visuals = object : SnackbarVisuals {
                override val message = message
                override val actionLabel = actionLabel
                override val withDismissAction = withDismissAction
                override val duration = duration
            }
            override fun dismiss() { _currentData = null; result = SnackbarResult.Dismissed }
            override fun performAction() { _currentData = null; result = SnackbarResult.ActionPerformed }
        }
        _currentData = data
        val delayMs = when (duration) {
            SnackbarDuration.Short -> 4000L
            SnackbarDuration.Long -> 10000L
            SnackbarDuration.Indefinite -> Long.MAX_VALUE
        }
        delay(delayMs)
        _currentData = null
        return result
    }
}

@Composable
fun rememberSnackbarHostState() = remember { SnackbarHostState() }

// ===========================================================================
// SnackbarHost — mirrors M3's SnackbarHost
// ===========================================================================

@Composable
fun SnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    snackbar: @Composable (SnackbarData) -> Unit = { data ->
        Snackbar {
            Text(data.visuals.message)
        }
    },
) {
    val currentData = hostState.currentSnackbarData
    AnimatedVisibility(
        visible = currentData != null,
        enter = fadeIn(tween(200)) + slideInVertically(tween(200)) { it },
        exit = fadeOut(tween(150)) + slideOutVertically(tween(150)) { it },
        modifier = modifier.padding(16.dp),
    ) {
        if (currentData != null) {
            snackbar(currentData)
        }
    }
}

@Preview
@Composable
private fun SnackbarPreview() {
    DarwinTheme {
        Snackbar {
            Text("This is a snackbar message")
        }
    }
}
