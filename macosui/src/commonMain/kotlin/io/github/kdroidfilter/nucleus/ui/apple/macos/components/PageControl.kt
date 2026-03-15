package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SpringPreset
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosSpring

// ===========================================================================
// PageControlColors
// ===========================================================================

@Immutable
class PageControlColors(
    val indicatorColor: Color,
    val selectedAlpha: Float,
    val unselectedAlpha: Float,
    val backgroundColor: Color,
) {
    fun copy(
        indicatorColor: Color = this.indicatorColor,
        selectedAlpha: Float = this.selectedAlpha,
        unselectedAlpha: Float = this.unselectedAlpha,
        backgroundColor: Color = this.backgroundColor,
    ) = PageControlColors(indicatorColor, selectedAlpha, unselectedAlpha, backgroundColor)
}

// ===========================================================================
// PageControlDefaults
// ===========================================================================

object PageControlDefaults {
    @Composable
    fun colors(
        indicatorColor: Color = MacosTheme.colorScheme.textPrimary,
        selectedAlpha: Float = 1f,
        unselectedAlpha: Float = 0.3f,
        backgroundColor: Color = Color.Transparent,
    ) = PageControlColors(indicatorColor, selectedAlpha, unselectedAlpha, backgroundColor)
}

// ===========================================================================
// PageControl
// ===========================================================================

/**
 * An iOS-style page control indicator showing a row of dots.
 *
 * When [pageCount] exceeds [maxVisibleDots], dots at the edges shrink progressively
 * to indicate more content, mimicking the native iOS behavior.
 *
 * @param pageCount Total number of pages.
 * @param currentPage Zero-based index of the currently selected page.
 * @param onPageSelected Called when the user taps a dot.
 * @param modifier Modifier for the root layout.
 * @param maxVisibleDots Maximum number of dots shown before edge-shrinking kicks in.
 * @param colors Color configuration.
 */
@Composable
fun PageControl(
    pageCount: Int,
    currentPage: Int,
    onPageSelected: ((Int) -> Unit)? = null,
    modifier: Modifier = Modifier,
    maxVisibleDots: Int = 7,
    colors: PageControlColors = PageControlDefaults.colors(),
) {
    if (pageCount < 2) return

    val hasBackground = colors.backgroundColor != Color.Transparent

    val content = @Composable {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            for (index in 0 until pageCount) {
                val isSelected = index == currentPage
                val dotScale = computeDotScale(index, currentPage, pageCount, maxVisibleDots)

                val animatedScale by animateFloatAsState(
                    targetValue = dotScale,
                    animationSpec = macosSpring(SpringPreset.Snappy),
                )
                val animatedAlpha by animateFloatAsState(
                    targetValue = if (isSelected) colors.selectedAlpha else colors.unselectedAlpha,
                    animationSpec = macosSpring(SpringPreset.Snappy),
                )

                if (animatedScale > 0f) {
                    Box(
                        modifier = Modifier
                            .size(DotSize)
                            .then(
                                if (onPageSelected != null) {
                                    Modifier.clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                    ) { onPageSelected(index) }
                                } else {
                                    Modifier
                                }
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(DotSize * animatedScale)
                                .graphicsLayer { alpha = animatedAlpha }
                                .clip(CircleShape)
                                .background(colors.indicatorColor),
                        )
                    }
                }
            }
        }
    }

    if (hasBackground) {
        Box(
            modifier = modifier
                .height(ContainerHeight)
                .clip(RoundedCornerShape(12.dp))
                .background(colors.backgroundColor),
            contentAlignment = Alignment.Center,
        ) {
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                content()
            }
        }
    } else {
        Box(
            modifier = modifier.height(ContainerHeight),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}

// ===========================================================================
// Internal helpers
// ===========================================================================

private val DotSize = 8.dp
private val ContainerHeight = 44.dp

/**
 * Computes a 0..1 scale factor for a dot, replicating iOS page control behavior:
 * - Dots near the selected page are full size (1.0)
 * - Dots at the edges shrink progressively (0.75 → 0.5 → 0)
 */
private fun computeDotScale(
    index: Int,
    currentPage: Int,
    pageCount: Int,
    maxVisibleDots: Int,
): Float {
    if (pageCount <= maxVisibleDots) return 1f

    // Determine the visible window centered on currentPage
    val halfWindow = maxVisibleDots / 2
    val windowStart = (currentPage - halfWindow).coerceIn(0, pageCount - maxVisibleDots)
    val windowEnd = windowStart + maxVisibleDots - 1

    return when {
        index < windowStart - 1 -> 0f
        index == windowStart - 1 -> 0.5f // tiny dot just outside window
        index == windowStart && windowStart > 0 -> 0.75f // small dot at edge
        index == windowEnd && windowEnd < pageCount - 1 -> 0.75f // small dot at edge
        index == windowEnd + 1 -> 0.5f // tiny dot just outside window
        index > windowEnd + 1 -> 0f
        else -> 1f
    }
}
