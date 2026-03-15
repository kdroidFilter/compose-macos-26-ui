package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.PageControl
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.PageControlDefaults
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@GalleryExample("Page Control", "Basic")
@Composable
fun PageControlBasicExample() {
    var currentPage by remember { mutableStateOf(0) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PageControl(
            pageCount = 5,
            currentPage = currentPage,
            onPageSelected = { currentPage = it },
        )
        Text(
            text = "Page ${currentPage + 1} of 5",
            style = MacosTheme.typography.caption1,
            color = MacosTheme.colorScheme.textTertiary,
        )
    }
}

@GalleryExample("Page Control", "Many Pages")
@Composable
fun PageControlManyPagesExample() {
    var currentPage by remember { mutableStateOf(0) }
    val pageCount = 15
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PageControl(
            pageCount = pageCount,
            currentPage = currentPage,
            onPageSelected = { currentPage = it },
        )
        Text(
            text = "Page ${currentPage + 1} of $pageCount",
            style = MacosTheme.typography.caption1,
            color = MacosTheme.colorScheme.textTertiary,
        )
    }
}

@GalleryExample("Page Control", "With Background")
@Composable
fun PageControlWithBackgroundExample() {
    var currentPage by remember { mutableStateOf(0) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PageControl(
            pageCount = 5,
            currentPage = currentPage,
            onPageSelected = { currentPage = it },
            colors = PageControlDefaults.colors(
                backgroundColor = MacosTheme.colorScheme.textPrimary.copy(alpha = 0.08f),
            ),
        )
    }
}

@Composable
internal fun PageControlPage() {
    GalleryPage("Page Control", "A control that displays a horizontal series of dots, each of which corresponds to a page.") {
        SectionHeader("Examples")
        ExampleCard(title = "Basic", sourceCode = GallerySources.PageControlBasicExample) { PageControlBasicExample() }
        ExampleCard(title = "Many Pages", sourceCode = GallerySources.PageControlManyPagesExample) { PageControlManyPagesExample() }
        ExampleCard(title = "With Background", sourceCode = GallerySources.PageControlWithBackgroundExample) { PageControlWithBackgroundExample() }
    }
}
