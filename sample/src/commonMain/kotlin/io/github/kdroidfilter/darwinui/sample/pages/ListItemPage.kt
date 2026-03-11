package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import com.composables.icons.lucide.ChevronRight
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Mail
import io.github.kdroidfilter.darwinui.components.HorizontalDivider as DarwinHorizontalDivider
import io.github.kdroidfilter.darwinui.components.ListItem as DarwinListItem
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import androidx.compose.material3.Text as M3Text

@Composable
internal fun ListItemPage() {
    GalleryPage("List Item", "Darwin ListItem vs Material 3 ListItem.") {
        SectionHeader("One Line")
        ComparisonSection(
            darwinContent = {
                Column {
                    DarwinListItem(headlineContent = { Text("First item") })
                    DarwinHorizontalDivider()
                    DarwinListItem(headlineContent = { Text("Second item") })
                    DarwinHorizontalDivider()
                    DarwinListItem(headlineContent = { Text("Third item") })
                }
            },
            materialContent = {
                Column {
                    ListItem(headlineContent = { M3Text("First item") })
                    HorizontalDivider()
                    ListItem(headlineContent = { M3Text("Second item") })
                    HorizontalDivider()
                    ListItem(headlineContent = { M3Text("Third item") })
                }
            },
        )

        SectionHeader("Two Line with Icons")
        ComparisonSection(
            darwinContent = {
                Column {
                    DarwinListItem(
                        headlineContent = { Text("Inbox") },
                        supportingContent = { Text("3 unread messages") },
                        leadingContent = { Icon(Lucide.Mail) },
                        trailingContent = { Icon(Lucide.ChevronRight) },
                    )
                    DarwinHorizontalDivider()
                    DarwinListItem(
                        headlineContent = { Text("Sent") },
                        supportingContent = { Text("Last sent 2 hours ago") },
                        leadingContent = { Icon(Lucide.Mail) },
                        trailingContent = { Icon(Lucide.ChevronRight) },
                    )
                }
            },
            materialContent = {
                Column {
                    ListItem(
                        headlineContent = { M3Text("Inbox") },
                        supportingContent = { M3Text("3 unread messages") },
                        leadingContent = { Icon(Lucide.Mail) },
                        trailingContent = { Icon(Lucide.ChevronRight) },
                    )
                    HorizontalDivider()
                    ListItem(
                        headlineContent = { M3Text("Sent") },
                        supportingContent = { M3Text("Last sent 2 hours ago") },
                        leadingContent = { Icon(Lucide.Mail) },
                        trailingContent = { Icon(Lucide.ChevronRight) },
                    )
                }
            },
        )
    }
}
