package io.github.kdroidfilter.darwinui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Glass-morphism effect modifier.
 * In the React library, glass={true} applies:
 *   bg-white/80 dark:bg-zinc-900/80 backdrop-blur-xl border-white/20 dark:border-white/10
 *
 * Since backdrop-blur is not natively available in Compose Multiplatform,
 * we simulate the glass effect with semi-transparent backgrounds and borders.
 */
@Composable
fun Modifier.glassEffect(
    enabled: Boolean = true,
    shape: Shape = DarwinTheme.shapes.large,
    borderWidth: Dp = 1.dp,
): Modifier {
    if (!enabled) return this

    val colors = DarwinTheme.colors
    return this
        .clip(shape)
        .background(colors.glassBackground, shape)
        .border(borderWidth, colors.glassBorder, shape)
}

/**
 * Returns the glass background color if glass is enabled,
 * otherwise returns the provided fallback color.
 */
@Composable
fun glassOrDefault(glass: Boolean, fallback: Color): Color {
    return if (glass) DarwinTheme.colors.glassBackground else fallback
}

/**
 * Returns the glass border color if glass is enabled,
 * otherwise returns the provided fallback color.
 */
@Composable
fun glassBorderOrDefault(glass: Boolean, fallback: Color): Color {
    return if (glass) DarwinTheme.colors.glassBorder else fallback
}
