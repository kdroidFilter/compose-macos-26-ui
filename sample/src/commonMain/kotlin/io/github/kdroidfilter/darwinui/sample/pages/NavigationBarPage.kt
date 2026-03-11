package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.CircleUser
import io.github.kdroidfilter.darwinui.components.NavigationBar
import io.github.kdroidfilter.darwinui.components.NavigationBarItem
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("Navigation Bar", "Four Items")
@Composable
fun NavigationBarFourItemsExample() {
    var selected by remember { mutableStateOf(0) }
    Box(modifier = Modifier.fillMaxWidth()) {
        NavigationBar {
            NavigationBarItem(
                selected = selected == 0,
                onClick = { selected = 0 },
                icon = { Icon(Lucide.House) },
                label = { Text("Home") },
            )
            NavigationBarItem(
                selected = selected == 1,
                onClick = { selected = 1 },
                icon = { Icon(Lucide.Search) },
                label = { Text("Search") },
            )
            NavigationBarItem(
                selected = selected == 2,
                onClick = { selected = 2 },
                icon = { Icon(Lucide.Bell) },
                label = { Text("Alerts") },
            )
            NavigationBarItem(
                selected = selected == 3,
                onClick = { selected = 3 },
                icon = { Icon(Lucide.CircleUser) },
                label = { Text("Profile") },
            )
        }
    }
}

@Composable
internal fun NavigationBarPage() {
    GalleryPage("Navigation Bar", "A bottom navigation bar for switching between top-level destinations.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Four Items",
            description = "Navigation bar with four destinations",
            sourceCode = GallerySources.NavigationBarFourItemsExample,
        ) { NavigationBarFourItemsExample() }
    }
}
