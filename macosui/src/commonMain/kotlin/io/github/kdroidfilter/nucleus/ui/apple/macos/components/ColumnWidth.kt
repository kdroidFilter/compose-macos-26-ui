package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.ui.unit.Dp

/**
 * Describes the width behavior of a column in [Scaffold].
 *
 * [Fixed] columns have a constant width with no user resizing.
 * [Flexible] columns show a draggable divider and can be resized
 * between [Flexible.min] and [Flexible.max].
 */
sealed class ColumnWidth {
    /** Column with a constant, non-resizable width. */
    data class Fixed(val width: Dp) : ColumnWidth()

    /**
     * Column that can be resized by dragging its divider.
     *
     * @param min Minimum width during drag.
     * @param ideal Default width (restored on double-click).
     * @param max Maximum width during drag.
     */
    data class Flexible(
        val min: Dp,
        val ideal: Dp,
        val max: Dp,
    ) : ColumnWidth()
}

/** Returns the effective default width: [ColumnWidth.Fixed.width] or [ColumnWidth.Flexible.ideal]. */
internal fun ColumnWidth.idealOrFixed(): Dp = when (this) {
    is ColumnWidth.Fixed -> width
    is ColumnWidth.Flexible -> ideal
}
