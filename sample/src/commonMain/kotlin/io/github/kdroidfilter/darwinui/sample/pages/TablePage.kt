package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.runtime.Composable
import io.github.kdroidfilter.darwinui.components.badge.DarwinBadge
import io.github.kdroidfilter.darwinui.components.badge.DarwinBadgeVariant
import io.github.kdroidfilter.darwinui.components.table.DarwinTable
import io.github.kdroidfilter.darwinui.components.table.DarwinTableBody
import io.github.kdroidfilter.darwinui.components.table.DarwinTableCell
import io.github.kdroidfilter.darwinui.components.table.DarwinTableHead
import io.github.kdroidfilter.darwinui.components.table.DarwinTableHeaderCell
import io.github.kdroidfilter.darwinui.components.table.DarwinTableRow
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("Table", "Default")
@Composable
fun TableDefaultExample() {
    DarwinTable {
        DarwinTableHead {
            DarwinTableRow {
                DarwinTableHeaderCell { DarwinText("Name") }
                DarwinTableHeaderCell { DarwinText("Email") }
                DarwinTableHeaderCell { DarwinText("Role") }
                DarwinTableHeaderCell(weight = 0.5f) { DarwinText("Status") }
            }
        }
        DarwinTableBody(scrollable = false) {
            DarwinTableRow {
                DarwinTableCell { DarwinText("Alice Smith") }
                DarwinTableCell { DarwinText("alice@example.com") }
                DarwinTableCell { DarwinText("Admin") }
                DarwinTableCell(weight = 0.5f) {
                    DarwinBadge(variant = DarwinBadgeVariant.Success) {
                        DarwinText("Active")
                    }
                }
            }
            DarwinTableRow {
                DarwinTableCell { DarwinText("Bob Jones") }
                DarwinTableCell { DarwinText("bob@example.com") }
                DarwinTableCell { DarwinText("Editor") }
                DarwinTableCell(weight = 0.5f) {
                    DarwinBadge(variant = DarwinBadgeVariant.Warning) {
                        DarwinText("Pending")
                    }
                }
            }
            DarwinTableRow {
                DarwinTableCell { DarwinText("Carol White") }
                DarwinTableCell { DarwinText("carol@example.com") }
                DarwinTableCell { DarwinText("Viewer") }
                DarwinTableCell(weight = 0.5f) {
                    DarwinBadge(variant = DarwinBadgeVariant.Archived) {
                        DarwinText("Inactive")
                    }
                }
            }
        }
    }
}

@Composable
internal fun TablePage() {
    GalleryPage("Table", "A responsive table component for displaying tabular data.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.TableDefaultExample) { TableDefaultExample() }
    }
}
