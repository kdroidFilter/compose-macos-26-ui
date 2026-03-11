package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.CircleUser
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import io.github.kdroidfilter.darwinui.components.NavigationBar as DarwinNavigationBar
import io.github.kdroidfilter.darwinui.components.NavigationBarItem as DarwinNavigationBarItem
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import androidx.compose.material3.Text as M3Text

@Composable
internal fun NavigationBarPage() {
    GalleryPage("Navigation Bar", "Darwin NavigationBar vs Material 3 NavigationBar.") {
        SectionHeader("Four Items")
        ComparisonSection(
            darwinContent = {
                var selected by remember { mutableStateOf(0) }
                Box(modifier = Modifier.fillMaxWidth()) {
                    DarwinNavigationBar {
                        DarwinNavigationBarItem(
                            selected = selected == 0,
                            onClick = { selected = 0 },
                            icon = { Icon(Lucide.House) },
                            label = { Text("Home") },
                        )
                        DarwinNavigationBarItem(
                            selected = selected == 1,
                            onClick = { selected = 1 },
                            icon = { Icon(Lucide.Search) },
                            label = { Text("Search") },
                        )
                        DarwinNavigationBarItem(
                            selected = selected == 2,
                            onClick = { selected = 2 },
                            icon = { Icon(Lucide.Bell) },
                            label = { Text("Alerts") },
                        )
                        DarwinNavigationBarItem(
                            selected = selected == 3,
                            onClick = { selected = 3 },
                            icon = { Icon(Lucide.CircleUser) },
                            label = { Text("Profile") },
                        )
                    }
                }
            },
            materialContent = {
                var selected by remember { mutableStateOf(0) }
                Box(modifier = Modifier.fillMaxWidth()) {
                    NavigationBar {
                        NavigationBarItem(
                            selected = selected == 0,
                            onClick = { selected = 0 },
                            icon = { Icon(Lucide.House) },
                            label = { M3Text("Home") },
                        )
                        NavigationBarItem(
                            selected = selected == 1,
                            onClick = { selected = 1 },
                            icon = { Icon(Lucide.Search) },
                            label = { M3Text("Search") },
                        )
                        NavigationBarItem(
                            selected = selected == 2,
                            onClick = { selected = 2 },
                            icon = { Icon(Lucide.Bell) },
                            label = { M3Text("Alerts") },
                        )
                        NavigationBarItem(
                            selected = selected == 3,
                            onClick = { selected = 3 },
                            icon = { Icon(Lucide.CircleUser) },
                            label = { M3Text("Profile") },
                        )
                    }
                }
            },
        )
    }
}
