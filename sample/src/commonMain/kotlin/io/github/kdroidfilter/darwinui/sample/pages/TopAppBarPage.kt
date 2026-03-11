package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Lucide
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.TopAppBar as DarwinTopAppBar
import io.github.kdroidfilter.darwinui.components.CenterAlignedTopAppBar as DarwinCenterAlignedTopAppBar
import io.github.kdroidfilter.darwinui.components.IconButton as DarwinIconButton
import androidx.compose.material3.Text as M3Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopAppBarPage() {
    GalleryPage("Top App Bar", "Darwin TopAppBar vs Material 3 TopAppBar components.") {
        SectionHeader("Standard")
        ComparisonSection(
            darwinContent = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    DarwinTopAppBar(
                        title = { Text("Page Title") },
                        navigationIcon = {
                            DarwinIconButton(onClick = {}) { Icon(Lucide.ArrowLeft) }
                        },
                        actions = {
                            DarwinIconButton(onClick = {}) { Icon(Lucide.EllipsisVertical) }
                        },
                    )
                }
            },
            materialContent = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    TopAppBar(
                        title = { M3Text("Page Title") },
                        navigationIcon = {
                            IconButton(onClick = {}) { Icon(Lucide.ArrowLeft) }
                        },
                        actions = {
                            IconButton(onClick = {}) { Icon(Lucide.EllipsisVertical) }
                        },
                    )
                }
            },
        )

        SectionHeader("Center Aligned")
        ComparisonSection(
            darwinContent = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    DarwinCenterAlignedTopAppBar(
                        title = { Text("Centered Title") },
                        navigationIcon = {
                            DarwinIconButton(onClick = {}) { Icon(Lucide.ArrowLeft) }
                        },
                        actions = {
                            DarwinIconButton(onClick = {}) { Icon(Lucide.EllipsisVertical) }
                        },
                    )
                }
            },
            materialContent = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    CenterAlignedTopAppBar(
                        title = { M3Text("Centered Title") },
                        navigationIcon = {
                            IconButton(onClick = {}) { Icon(Lucide.ArrowLeft) }
                        },
                        actions = {
                            IconButton(onClick = {}) { Icon(Lucide.EllipsisVertical) }
                        },
                    )
                }
            },
        )
    }
}
