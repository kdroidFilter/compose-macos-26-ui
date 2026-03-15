package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ChevronRight
import com.composables.icons.lucide.CircleEllipsis
import com.composables.icons.lucide.Lock
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Wifi
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.GroupedList
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.GroupedListItem
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Switch
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@GalleryExample("GroupedList", "Network")
@Composable
fun GroupedListNetworkExample() {
    GroupedList {
        listOf("Charlene", "Charlene_plus", "Geller", "Vardi").forEachIndexed { i, name ->
            GroupedListItem(
                onClick = {},
                showDivider = i < 3,
                trailing = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(Lucide.Lock, modifier = Modifier.size(16.dp), tint = MacosTheme.colorScheme.textSecondary)
                        Icon(Lucide.Wifi, modifier = Modifier.size(16.dp), tint = MacosTheme.colorScheme.textSecondary)
                        Icon(Lucide.CircleEllipsis, modifier = Modifier.size(18.dp), tint = MacosTheme.colorScheme.textSecondary)
                    }
                },
            ) {
                Text(name, style = MacosTheme.typography.subheadline, color = MacosTheme.colorScheme.textPrimary)
            }
        }
    }
}

@GalleryExample("GroupedList", "Settings")
@Composable
fun GroupedListSettingsExample() {
    var wifi by remember { mutableStateOf(true) }
    var bluetooth by remember { mutableStateOf(false) }

    GroupedList {
        GroupedListItem(
            trailing = { Switch(checked = wifi, onCheckedChange = { wifi = it }) },
        ) {
            Text("Wi-Fi", style = MacosTheme.typography.subheadline, color = MacosTheme.colorScheme.textPrimary)
        }
        GroupedListItem(
            trailing = { Switch(checked = bluetooth, onCheckedChange = { bluetooth = it }) },
        ) {
            Text("Bluetooth", style = MacosTheme.typography.subheadline, color = MacosTheme.colorScheme.textPrimary)
        }
        GroupedListItem(
            onClick = {},
            showDivider = false,
            trailing = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("System Preferences", style = MacosTheme.typography.subheadline, color = MacosTheme.colorScheme.textTertiary)
                    Icon(Lucide.ChevronRight, modifier = Modifier.size(16.dp), tint = MacosTheme.colorScheme.textTertiary)
                }
            },
        ) {
            Text("VPN", style = MacosTheme.typography.subheadline, color = MacosTheme.colorScheme.textPrimary)
        }
    }
}

@GalleryExample("GroupedList", "Info")
@Composable
fun GroupedListInfoExample() {
    val info = listOf(
        "Version" to "14.3.1",
        "Model" to "MacBook Pro",
        "Serial Number" to "C02XG1ZZJGH5",
        "Storage" to "512 GB SSD",
    )
    GroupedList {
        info.forEachIndexed { i, (key, value) ->
            GroupedListItem(showDivider = i < info.size - 1) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(key, style = MacosTheme.typography.subheadline, color = MacosTheme.colorScheme.textPrimary, modifier = Modifier.weight(1f))
                    Text(value, style = MacosTheme.typography.subheadline, color = MacosTheme.colorScheme.textTertiary)
                }
            }
        }
    }
}

@Composable
internal fun GroupedListPage() {
    GalleryPage("Grouped List", "macOS-style grouped list for settings and data rows.") {
        SectionHeader("With trailing icons")
        ExampleCard(
            title = "Network list",
            sourceCode = GallerySources.GroupedListNetworkExample,
        ) { GroupedListNetworkExample() }

        SectionHeader("Settings style")
        ExampleCard(
            title = "With switch and disclosure",
            sourceCode = GallerySources.GroupedListSettingsExample,
        ) { GroupedListSettingsExample() }

        SectionHeader("Info rows")
        ExampleCard(
            title = "Key-value pairs",
            sourceCode = GallerySources.GroupedListInfoExample,
        ) { GroupedListInfoExample() }
    }
}
