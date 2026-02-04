package io.github.kdroidfilter.darwinui.components.skeleton

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.Zinc900

// ===========================================================================
// DarwinSkeleton
// ===========================================================================

/**
 * A loading placeholder shape with a pulsing animation.
 *
 * Pixel-perfect match with the React darwin-ui Skeleton component:
 * - Default: `bg-black/10 dark:bg-white/10 animate-pulse rounded-lg`
 * - Glass:   `bg-white/40 dark:bg-zinc-900/40 backdrop-blur-sm`
 *
 * The CSS `animate-pulse` keyframe oscillates opacity between 1.0 and 0.5
 * over 2 seconds with `cubic-bezier(0.4, 0, 0.6, 1)`.
 *
 * @param modifier Modifier applied to the placeholder. Use this to set the
 *                 desired width and height for the skeleton.
 * @param shape    Shape of the placeholder. Defaults to
 *                 [DarwinTheme.shapes.small] (8dp rounded corners = `rounded-lg`).
 * @param glass    When true, applies the glass-morphism effect instead of
 *                 the default skeleton background.
 */
@Composable
fun DarwinSkeleton(
    modifier: Modifier = Modifier,
    shape: Shape = DarwinTheme.shapes.small, // rounded-lg = 8dp
    glass: Boolean = false,
) {
    val isDark = DarwinTheme.colors.isDark

    // Background color matching React exactly
    val backgroundColor = if (glass) {
        // glass: bg-white/40 dark:bg-zinc-900/40
        if (isDark) Zinc900.copy(alpha = 0.40f) else Color.White.copy(alpha = 0.40f)
    } else {
        // default: bg-black/10 dark:bg-white/10
        if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)
    }

    // CSS animate-pulse: opacity oscillates 1.0 → 0.5 → 1.0 over 2s
    val infiniteTransition = rememberInfiniteTransition(label = "skeleton_pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = androidx.compose.animation.core.LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "skeleton_alpha",
    )

    Box(
        modifier = modifier
            .clip(shape)
            .alpha(alpha)
            .background(backgroundColor, shape)
    )
}

// ===========================================================================
// Convenience composables
// ===========================================================================

/**
 * Line widths used by [DarwinSkeletonText] to create a natural-looking
 * multi-line text placeholder.
 *
 * The pattern cycles through 100%, 75%, and 50% of the available width,
 * matching the React demo pattern: `w-full`, `w-3/4`, `w-1/2`.
 */
private val SkeletonLineWidthFractions = listOf(1f, 0.75f, 0.5f)

/**
 * Shows multiple skeleton lines of varying widths to simulate loading text.
 *
 * @param lines    Number of skeleton lines to display. Defaults to 3.
 * @param modifier Modifier applied to the outer column container.
 * @param glass    When true, applies the glass-morphism effect.
 */
@Composable
fun DarwinSkeletonText(
    lines: Int = 3,
    modifier: Modifier = Modifier,
    glass: Boolean = false,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp), // space-y-2 = 8dp
    ) {
        for (i in 0 until lines) {
            val widthFraction = SkeletonLineWidthFractions[i % SkeletonLineWidthFractions.size]
            DarwinSkeleton(
                modifier = Modifier
                    .fillMaxWidth(widthFraction)
                    .height(16.dp), // h-4 = 16dp
                glass = glass,
            )
        }
    }
}

/**
 * A circular skeleton placeholder, typically used as a loading state for
 * avatars or profile pictures.
 *
 * @param size     Diameter of the circle. Defaults to 48dp (h-12 w-12).
 * @param modifier Modifier applied to the skeleton.
 * @param glass    When true, applies the glass-morphism effect.
 */
@Composable
fun DarwinSkeletonCircle(
    size: Dp = 48.dp, // h-12 w-12 = 48dp
    modifier: Modifier = Modifier,
    glass: Boolean = false,
) {
    DarwinSkeleton(
        modifier = modifier.size(size),
        shape = CircleShape,
        glass = glass,
    )
}
