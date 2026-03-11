package io.github.kdroidfilter.darwinui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

/**
 * Darwin UI Scaffold — mirrors Material3's Scaffold.
 *
 * Provides a layout structure with optional topBar, bottomBar,
 * snackbarHost, and floatingActionButton slots.
 */
@Composable
fun Scaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.BottomEnd,
    containerColor: Color = DarwinTheme.colorScheme.background,
    contentColor: Color = DarwinTheme.colorScheme.onBackground,
    content: @Composable (PaddingValues) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(containerColor),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            topBar()
            Box(modifier = Modifier.weight(1f)) {
                content(PaddingValues(bottom = 0.dp))
            }
            bottomBar()
        }

        // FAB
        val fabAlignment = when (floatingActionButtonPosition) {
            FabPosition.BottomEnd -> Alignment.BottomEnd
            FabPosition.BottomStart -> Alignment.BottomStart
            FabPosition.BottomCenter -> Alignment.BottomCenter
            FabPosition.Center -> Alignment.Center
        }
        Box(
            modifier = Modifier
                .align(fabAlignment)
                .padding(16.dp),
        ) {
            floatingActionButton()
        }

        // Snackbar host
        Box(
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            snackbarHost()
        }
    }
}

/**
 * Position options for the FloatingActionButton in a [Scaffold].
 * Mirrors M3's FabPosition.
 */
enum class FabPosition {
    BottomEnd,
    BottomStart,
    BottomCenter,
    Center,
}
