package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.HorizontalScrollbar
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.VerticalScrollbar
import io.github.kdroidfilter.darwinui.components.rememberScrollbarState
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("Scrollbar", "Vertical (ScrollState)")
@Composable
fun ScrollbarVerticalExample() {
    val scrollState = rememberScrollState()
    val scrollbarState = rememberScrollbarState(scrollState)

    Box(modifier = Modifier.height(160.dp).fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            repeat(20) { i ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .background(
                            DarwinTheme.colorScheme.textPrimary.copy(alpha = 0.04f),
                            DarwinTheme.shapes.small,
                        ),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        text = "Item ${i + 1}",
                        style = DarwinTheme.typography.subheadline,
                        color = DarwinTheme.colorScheme.textPrimary,
                        modifier = Modifier.padding(horizontal = 10.dp),
                    )
                }
            }
        }

        VerticalScrollbar(
            state = scrollbarState,
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
        )
    }
}

@GalleryExample("Scrollbar", "Vertical (LazyColumn)")
@Composable
fun ScrollbarLazyVerticalExample() {
    val lazyState = rememberLazyListState()
    val scrollbarState = rememberScrollbarState(lazyState)

    Box(modifier = Modifier.height(160.dp).fillMaxWidth()) {
        LazyColumn(
            state = lazyState,
            modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 8.dp),
        ) {
            items(30) { i ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .background(
                            DarwinTheme.colorScheme.textPrimary.copy(alpha = 0.04f),
                            DarwinTheme.shapes.small,
                        ),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        text = "Row ${i + 1}",
                        style = DarwinTheme.typography.subheadline,
                        color = DarwinTheme.colorScheme.textPrimary,
                        modifier = Modifier.padding(horizontal = 10.dp),
                    )
                }
            }
        }

        VerticalScrollbar(
            state = scrollbarState,
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
        )
    }
}

@GalleryExample("Scrollbar", "Horizontal")
@Composable
fun ScrollbarHorizontalExample() {
    val scrollState = rememberScrollState()
    val scrollbarState = rememberScrollbarState(scrollState)

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxWidth().height(60.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(20) { i ->
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(40.dp)
                            .background(
                                DarwinTheme.colorScheme.textPrimary.copy(alpha = 0.04f),
                                DarwinTheme.shapes.small,
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Col ${i + 1}",
                            style = DarwinTheme.typography.caption1,
                            color = DarwinTheme.colorScheme.textPrimary,
                        )
                    }
                }
            }
        }

        HorizontalScrollbar(
            state = scrollbarState,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
internal fun ScrollbarPage() {
    GalleryPage("Scrollbar", "macOS-style overlay scrollbars — appear on scroll, fade after idle.") {
        SectionHeader("Vertical")
        ExampleCard(
            title = "ScrollState",
            description = "Overlay vertical scrollbar with ScrollState",
            sourceCode = GallerySources.ScrollbarVerticalExample,
        ) { ScrollbarVerticalExample() }
        ExampleCard(
            title = "LazyColumn",
            description = "Overlay vertical scrollbar with LazyListState",
            sourceCode = GallerySources.ScrollbarLazyVerticalExample,
        ) { ScrollbarLazyVerticalExample() }

        SectionHeader("Horizontal")
        ExampleCard(
            title = "Horizontal",
            description = "Overlay horizontal scrollbar with ScrollState",
            sourceCode = GallerySources.ScrollbarHorizontalExample,
        ) { ScrollbarHorizontalExample() }
    }
}
