package io.github.kdroidfilter.nucleus.ui.apple.macos.theme

import androidx.compose.animation.core.*
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

enum class MacosDuration(val millis: Int) {
    Instant(0),
    Fast(100),
    Normal(150),
    Slow(200),
    Reveal(400),
}

enum class SpringPreset(val stiffness: Float, val dampingRatio: Float) {
    Snappy(400f, 0.65f),
    Smooth(200f, 0.75f),
    Gentle(120f, 0.8f),
}

@Immutable
data class Animations(
    val instant: Int = MacosDuration.Instant.millis,
    val fast: Int = MacosDuration.Fast.millis,
    val normal: Int = MacosDuration.Normal.millis,
    val slow: Int = MacosDuration.Slow.millis,
    val reveal: Int = MacosDuration.Reveal.millis,
)

/**
 * Get a tween animation spec for a given duration key.
 */
fun <T> macosTween(
    duration: MacosDuration = MacosDuration.Normal,
    easing: Easing = FastOutSlowInEasing,
): TweenSpec<T> = tween(
    durationMillis = duration.millis,
    easing = easing,
)

/**
 * Get a spring animation spec for a given spring preset.
 */
fun <T> macosSpring(
    preset: SpringPreset = SpringPreset.Smooth,
    visibilityThreshold: T? = null,
): SpringSpec<T> = spring(
    dampingRatio = preset.dampingRatio,
    stiffness = preset.stiffness,
    visibilityThreshold = visibilityThreshold,
)

val LocalAnimations = staticCompositionLocalOf { Animations() }
