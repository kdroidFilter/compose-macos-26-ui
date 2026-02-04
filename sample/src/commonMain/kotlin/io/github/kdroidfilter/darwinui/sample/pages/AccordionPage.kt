package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.kdroidfilter.darwinui.components.accordion.DarwinAccordion
import io.github.kdroidfilter.darwinui.components.accordion.DarwinAccordionItem
import io.github.kdroidfilter.darwinui.components.accordion.DarwinAccordionType
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("Accordion", "Single Mode")
@Composable
fun AccordionSingleModeExample() {
    var expandedItem by remember { mutableStateOf<String?>("item1") }
    DarwinAccordion(type = DarwinAccordionType.Single) {
        DarwinAccordionItem(value = "item1", expanded = expandedItem == "item1", onToggle = { expandedItem = if (expandedItem == "item1") null else "item1" }, trigger = { DarwinText("What is Darwin UI?") }, content = { DarwinText("Darwin UI is a macOS-inspired design system for Compose Multiplatform.", color = DarwinTheme.colors.textSecondary) })
        DarwinAccordionItem(value = "item2", expanded = expandedItem == "item2", onToggle = { expandedItem = if (expandedItem == "item2") null else "item2" }, trigger = { DarwinText("Which platforms are supported?") }, content = { DarwinText("Android, iOS, Desktop (JVM), Web (JS), and WASM are all supported through Compose Multiplatform.", color = DarwinTheme.colors.textSecondary) })
        DarwinAccordionItem(value = "item3", expanded = expandedItem == "item3", onToggle = { expandedItem = if (expandedItem == "item3") null else "item3" }, trigger = { DarwinText("Is dark mode supported?") }, content = { DarwinText("Yes! Dark mode is the default theme.", color = DarwinTheme.colors.textSecondary) })
    }
}

@Composable
internal fun AccordionPage() {
    GalleryPage("Accordion", "A vertically stacked set of interactive headings that reveal content.") {
        SectionHeader("Examples")
        ExampleCard(title = "Single Mode", sourceCode = GallerySources.AccordionSingleModeExample) { AccordionSingleModeExample() }
    }
}
