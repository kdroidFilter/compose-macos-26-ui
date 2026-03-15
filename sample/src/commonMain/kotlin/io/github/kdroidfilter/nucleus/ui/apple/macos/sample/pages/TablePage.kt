package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Badge
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.BadgeVariant
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Table
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TableBody
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TableCell
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TableHead
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TableHeaderCell
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TableLayout
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TableRow
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources

@GalleryExample("Table", "Default")
@Composable
fun TableDefaultExample() {
    Table {
        TableHead {
            TableRow {
                TableHeaderCell { Text("Name") }
                TableHeaderCell { Text("Email") }
                TableHeaderCell { Text("Role") }
                TableHeaderCell(weight = 0.5f) { Text("Status") }
            }
        }
        TableBody(scrollable = false) {
            TableRow {
                TableCell { Text("Alice Smith") }
                TableCell { Text("alice@example.com") }
                TableCell { Text("Admin") }
                TableCell(weight = 0.5f) { Badge(variant = BadgeVariant.Success) { Text("Active") } }
            }
            TableRow {
                TableCell { Text("Bob Jones") }
                TableCell { Text("bob@example.com") }
                TableCell { Text("Editor") }
                TableCell(weight = 0.5f) { Badge(variant = BadgeVariant.Warning) { Text("Pending") } }
            }
            TableRow {
                TableCell { Text("Carol White") }
                TableCell { Text("carol@example.com") }
                TableCell { Text("Viewer") }
                TableCell(weight = 0.5f) { Badge(variant = BadgeVariant.Archived) { Text("Inactive") } }
            }
        }
    }
}

@GalleryExample("Table", "Scroll")
@Composable
fun TableScrollExample() {
    Table(layout = TableLayout.Scroll, modifier = Modifier.height(280.dp)) {
        TableHead {
            TableRow {
                TableHeaderCell(width = 160.dp) { Text("Name") }
                TableHeaderCell(width = 200.dp) { Text("Email") }
                TableHeaderCell(width = 120.dp) { Text("Department") }
                TableHeaderCell(width = 100.dp) { Text("Role") }
                TableHeaderCell(width = 120.dp) { Text("Location") }
                TableHeaderCell(width = 100.dp) { Text("Joined") }
                TableHeaderCell(width = 120.dp) { Text("Status") }
            }
        }
        TableBody(scrollable = true) {
            val rows = listOf(
                listOf("Alice Smith", "alice@example.com", "Engineering", "Admin", "Paris", "2021-03"),
                listOf("Bob Jones", "bob@example.com", "Design", "Editor", "London", "2022-07"),
                listOf("Carol White", "carol@example.com", "Marketing", "Viewer", "Berlin", "2020-11"),
                listOf("David Brown", "david@example.com", "Engineering", "Editor", "NYC", "2023-01"),
                listOf("Eva Green", "eva@example.com", "Product", "Admin", "Tokyo", "2019-05"),
                listOf("Frank Lee", "frank@example.com", "Sales", "Viewer", "Sydney", "2022-09"),
            )
            val statuses = listOf(
                BadgeVariant.Success, BadgeVariant.Warning, BadgeVariant.Archived,
                BadgeVariant.Success, BadgeVariant.Success, BadgeVariant.Warning,
            )
            val statusLabels = listOf("Active", "Pending", "Inactive", "Active", "Active", "Pending")

            rows.forEachIndexed { i, row ->
                TableRow {
                    row.forEach { cell ->
                        TableCell(width = when (row.indexOf(cell)) {
                            0 -> 160.dp; 1 -> 200.dp; 2 -> 120.dp
                            3 -> 100.dp; 4 -> 120.dp; else -> 100.dp
                        }) { Text(cell) }
                    }
                    TableCell(width = 120.dp) {
                        Badge(variant = statuses[i]) { Text(statusLabels[i]) }
                    }
                }
            }
        }
    }
}

@Composable
internal fun TablePage() {
    GalleryPage("Table", "A responsive table component for displaying tabular data.") {
        SectionHeader("Fluid (adapts to container)")
        ExampleCard(title = "Default", sourceCode = GallerySources.TableDefaultExample) { TableDefaultExample() }
        SectionHeader("Scroll (Excel-style, fixed column widths)")
        ExampleCard(title = "Scroll", sourceCode = GallerySources.TableScrollExample) { TableScrollExample() }
    }
}
