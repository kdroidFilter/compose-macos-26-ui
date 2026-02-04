package io.github.kdroidfilter.darwinui.components.skeleton

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.Zinc200
import io.github.kdroidfilter.darwinui.theme.Zinc800
import io.github.kdroidfilter.darwinui.theme.glassEffect

// ===========================================================================
// DarwinSkeleton
// ===========================================================================

/**
 * A loading placeholder shape with a pulsing alpha animation.
 *
 * The skeleton acts as a visual placeholder while content is being loaded.
 * The consumer controls the overall dimensions via [modifier] (width, height,
 * fillMaxWidth, etc.).
 *
 * Usage:
 * ```
 * DarwinSkeleton(
 *     modifier = Modifier.fillMaxWidth().height(16.dp),
 * )
 * ```
 *
 * @param modifier Modifier applied to the placeholder. Use this to set the
 *                 desired width and height for the skeleton.
 * @param shape    Shape of the placeholder. Defaults to
 *                 [DarwinTheme.shapes.medium] (10dp rounded corners).
 * @param glass    When true, applies the glass-morphism effect instead of
 *                 the solid skeleton background.
 */
@Composable
fun DarwinSkeleton(
    modifier: Modifier = Modifier,
    shape: Shape = DarwinTheme.shapes.medium,
    glass: Boolean = false,
) {
    val colors = DarwinTheme.colors
    val backgroundColor = if (colors.isDark) Zinc800 else Zinc200

    // Pulsing alpha animation
    val infiniteTransition = rememberInfiniteTransition(label = "darwin_skeleton_pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "darwin_skeleton_alpha",
    )

    val skeletonModifier = modifier
        .clip(shape)
        .alpha(alpha)
        .then(
            if (glass) {
                Modifier.glassEffect(enabled = true, shape = shape)
            } else {
                Modifier.background(backgroundColor, shape)
            }
        )

    Box(modifier = skeletonModifier)
}

// ===========================================================================
// Convenience composables
// ===========================================================================

/**
 * Line widths used by [DarwinSkeletonText] to create a natural-looking
 * multi-line text placeholder.
 *
 * The pattern cycles through 100%, 80%, and 60% of the available width.
 */
private val SkeletonLineWidthFractions = listOf(1f, 0.8f, 0.6f)

/**
 * Shows multiple skeleton lines of varying widths to simulate loading text.
 *
 * Each line is 14dp tall with 8dp vertical spacing, creating a realistic
 * paragraph placeholder.
 *
 * Usage:
 * ```
 * DarwinSkeletonText(lines = 4)
 * ```
 *
 * @param lines    Number of skeleton lines to display. Defaults to 3.
 * @param modifier Modifier applied to the outer column container.
 */
@Composable
fun DarwinSkeletonText(
    lines: Int = 3,
    modifier: Modifier = Modifier,
) {
    val shape = DarwinTheme.shapes.medium

    Column(modifier = modifier) {
        for (i in 0 until lines) {
            val widthFraction = SkeletonLineWidthFractions[i % SkeletonLineWidthFractions.size]

            DarwinSkeleton(
                modifier = Modifier
                    .fillMaxWidth(widthFraction)
                    .height(14.dp),
                shape = shape,
            )

            if (i < lines - 1) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

/**
 * A circular skeleton placeholder, typically used as a loading state for
 * avatars or profile pictures.
 *
 * Usage:
 * ```
 * DarwinSkeletonCircle(size = 48.dp)
 * ```
 *
 * @param size     Diameter of the circle. Defaults to 40dp.
 * @param modifier Modifier applied to the skeleton.
 */
@Composable
fun DarwinSkeletonCircle(
    size: Dp = 40.dp,
    modifier: Modifier = Modifier,
) {
    DarwinSkeleton(
        modifier = modifier.size(size),
        shape = CircleShape,
    )
}
