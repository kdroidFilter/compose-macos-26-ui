package io.github.kdroidfilter.darwinui.components.reveal

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.github.kdroidfilter.darwinui.theme.DarwinDuration

/**
 * Direction from which the content reveals.
 */
enum class DarwinRevealDirection {
    Up, Down, Left, Right,
}

/**
 * Darwin UI Reveal component.
 * Animates content entry with fade + slide from a specified direction.
 * Mirrors the React darwin-ui Reveal component.
 *
 * Usage:
 * ```
 * DarwinReveal(visible = showContent, direction = DarwinRevealDirection.Up) {
 *     Text("This slides up and fades in")
 * }
 * ```
 */
@Composable
fun DarwinReveal(
    visible: Boolean,
    direction: DarwinRevealDirection = DarwinRevealDirection.Up,
    durationMillis: Int = DarwinDuration.Reveal.millis,
    delayMillis: Int = 0,
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    val enterTransition = when (direction) {
        DarwinRevealDirection.Up -> fadeIn(
            animationSpec = tween(durationMillis, delayMillis)
        ) + slideInVertically(
            animationSpec = tween(durationMillis, delayMillis),
            initialOffsetY = { it / 4 },
        )

        DarwinRevealDirection.Down -> fadeIn(
            animationSpec = tween(durationMillis, delayMillis)
        ) + slideInVertically(
            animationSpec = tween(durationMillis, delayMillis),
            initialOffsetY = { -it / 4 },
        )

        DarwinRevealDirection.Left -> fadeIn(
            animationSpec = tween(durationMillis, delayMillis)
        ) + slideInHorizontally(
            animationSpec = tween(durationMillis, delayMillis),
            initialOffsetX = { it / 4 },
        )

        DarwinRevealDirection.Right -> fadeIn(
            animationSpec = tween(durationMillis, delayMillis)
        ) + slideInHorizontally(
            animationSpec = tween(durationMillis, delayMillis),
            initialOffsetX = { -it / 4 },
        )
    }

    val exitTransition = when (direction) {
        DarwinRevealDirection.Up -> fadeOut(
            animationSpec = tween(durationMillis)
        ) + slideOutVertically(
            animationSpec = tween(durationMillis),
            targetOffsetY = { it / 4 },
        )

        DarwinRevealDirection.Down -> fadeOut(
            animationSpec = tween(durationMillis)
        ) + slideOutVertically(
            animationSpec = tween(durationMillis),
            targetOffsetY = { -it / 4 },
        )

        DarwinRevealDirection.Left -> fadeOut(
            animationSpec = tween(durationMillis)
        ) + slideOutHorizontally(
            animationSpec = tween(durationMillis),
            targetOffsetX = { it / 4 },
        )

        DarwinRevealDirection.Right -> fadeOut(
            animationSpec = tween(durationMillis)
        ) + slideOutHorizontally(
            animationSpec = tween(durationMillis),
            targetOffsetX = { -it / 4 },
        )
    }

    AnimatedVisibility(
        visible = visible,
        enter = enterTransition,
        exit = exitTransition,
        modifier = modifier,
        content = content,
    )
}

/**
 * Reveal content on first composition (auto-reveals once).
 */
@Composable
fun DarwinRevealOnce(
    direction: DarwinRevealDirection = DarwinRevealDirection.Up,
    durationMillis: Int = DarwinDuration.Reveal.millis,
    delayMillis: Int = 0,
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    DarwinReveal(
        visible = visible,
        direction = direction,
        durationMillis = durationMillis,
        delayMillis = delayMillis,
        modifier = modifier,
        content = content,
    )
}
