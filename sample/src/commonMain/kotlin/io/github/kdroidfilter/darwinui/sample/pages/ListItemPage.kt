package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.ChevronRight
import com.composables.icons.lucide.Mail
import io.github.kdroidfilter.darwinui.components.HorizontalDivider
import io.github.kdroidfilter.darwinui.components.ListItem
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("List Item", "One Line")
@Composable
fun ListItemOneLineExample() {
    Column {
        ListItem(headlineContent = { Text("First item") })
        HorizontalDivider()
        ListItem(headlineContent = { Text("Second item") })
        HorizontalDivider()
        ListItem(headlineContent = { Text("Third item") })
    }
}

@GalleryExample("List Item", "Two Line with Lead and Trail")
@Composable
fun ListItemTwoLineExample() {
    Column {
        ListItem(
            headlineContent = { Text("Inbox") },
            supportingContent = { Text("3 unread messages") },
            leadingContent = { Icon(Lucide.Mail) },
            trailingContent = { Icon(Lucide.ChevronRight) },
        )
        HorizontalDivider()
        ListItem(
            headlineContent = { Text("Sent") },
            supportingContent = { Text("Last sent 2 hours ago") },
            leadingContent = { Icon(Lucide.Mail) },
            trailingContent = { Icon(Lucide.ChevronRight) },
        )
    }
}

@Composable
internal fun ListItemPage() {
    GalleryPage("List Item", "A flexible component for displaying rows of information in lists.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "One Line",
            description = "Simple single-line list items",
            sourceCode = GallerySources.ListItemOneLineExample,
        ) { ListItemOneLineExample() }
        ExampleCard(
            title = "Two Line with Lead and Trail",
            description = "List items with leading icon, supporting text, and trailing icon",
            sourceCode = GallerySources.ListItemTwoLineExample,
        ) { ListItemTwoLineExample() }
    }
}
