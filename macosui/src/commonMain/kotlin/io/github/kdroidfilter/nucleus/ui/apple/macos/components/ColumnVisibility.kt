package io.github.kdroidfilter.nucleus.ui.apple.macos.components

/**
 * Controls which columns are visible in a multi-column [Scaffold].
 *
 * Mirrors Apple's `NavigationSplitViewVisibility` enum.
 */
enum class ColumnVisibility {
    /** All columns visible (sidebar + contentList + content). */
    All,

    /** Sidebar hidden; contentList + content visible. */
    DoubleColumn,

    /** Only the detail content column is visible. */
    DetailOnly,

    /** Platform-adaptive: equivalent to [All] on desktop. */
    Automatic,
}
