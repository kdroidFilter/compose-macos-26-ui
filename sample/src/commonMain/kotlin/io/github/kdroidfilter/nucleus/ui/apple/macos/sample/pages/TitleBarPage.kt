package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.FileText
import com.composables.icons.lucide.Lucide
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.AddressBar
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.DropdownMenuItem
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.LocalTitleBarStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.NavigationButtons
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SearchSuggestionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SearchSuggestionItem
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SearchSuggestionSeparator
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SidebarButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TitleBar
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TitleBarButtonColors
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TitleBarButtonDefaults
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TitleBarButtonGroup
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TitleBarGroupButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TitleBarStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ToolbarSearchField
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ToolbarSeparator
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.WindowTitle
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import androidx.compose.ui.graphics.Color
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideChevronDown
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideCopy
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideDownload
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideEllipsis
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideLayoutGrid
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideList
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucidePlus
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideSearch
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideSettings
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideShare2
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideTag
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideUpload
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources

// =====================================================================
// Window Title variants (Sketch: Toolbar/_/Window Titles/Standard)
// =====================================================================

@GalleryExample("TitleBar", "Window Title Variants")
@Composable
fun TitleBarWindowTitleExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        WindowTitle(title = "Window Title")
        WindowTitle(
            title = "Window Title",
            proxyIcon = { Icon(Lucide.FileText, modifier = Modifier.size(15.dp)) },
        )
        WindowTitle(
            title = "Window Title",
            subtitle = "Subtitle — Edited",
        )
    }
}

// =====================================================================
// Toolbar Buttons (Sketch: Toolbar/XL/Buttons and Toolbar/Medium/Buttons)
// =====================================================================

@GalleryExample("TitleBar", "Toolbar Buttons XL")
@Composable
fun TitleBarButtonsXLExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TitleBarButtonGroup {
            TitleBarGroupButton(onClick = {}) {
                Icon(LucideSettings, modifier = Modifier.size(14.dp))
            }
        }
        TitleBarButtonGroup {
            TitleBarGroupButton(onClick = {}) {
                Icon(LucideDownload, modifier = Modifier.size(14.dp))
            }
            TitleBarGroupButton(onClick = {}) {
                Icon(LucideUpload, modifier = Modifier.size(14.dp))
            }
        }
        TitleBarButtonGroup {
            TitleBarGroupButton(onClick = {}) {
                Icon(LucideDownload, modifier = Modifier.size(14.dp))
            }
            TitleBarGroupButton(onClick = {}) {
                Icon(LucideUpload, modifier = Modifier.size(14.dp))
            }
            TitleBarGroupButton(onClick = {}) {
                Icon(LucideCopy, modifier = Modifier.size(14.dp))
            }
            TitleBarGroupButton(onClick = {}) {
                Icon(LucideShare2, modifier = Modifier.size(14.dp))
            }
        }
        TitleBarButtonGroup {
            TitleBarGroupButton(onClick = {}, enabled = false) {
                Icon(LucideSettings, modifier = Modifier.size(14.dp))
            }
        }
    }
}

@GalleryExample("TitleBar", "Toolbar Buttons Medium")
@Composable
fun TitleBarButtonsMediumExample() {
    CompositionLocalProvider(LocalTitleBarStyle provides TitleBarStyle.UnifiedCompact) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TitleBarButtonGroup {
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideSettings, modifier = Modifier.size(10.dp))
                }
            }
            TitleBarButtonGroup {
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideDownload, modifier = Modifier.size(10.dp))
                }
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideUpload, modifier = Modifier.size(10.dp))
                }
            }
            TitleBarButtonGroup {
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideDownload, modifier = Modifier.size(10.dp))
                }
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideUpload, modifier = Modifier.size(10.dp))
                }
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideCopy, modifier = Modifier.size(10.dp))
                }
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideShare2, modifier = Modifier.size(10.dp))
                }
            }
            TitleBarButtonGroup {
                TitleBarGroupButton(onClick = {}, enabled = false) {
                    Icon(LucideSettings, modifier = Modifier.size(10.dp))
                }
            }
        }
    }
}

// =====================================================================
// Toolbar Separator (Sketch: _Separator)
// =====================================================================

@GalleryExample("TitleBar", "Toolbar Separator")
@Composable
fun TitleBarSeparatorExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NavigationButtons(onBack = {}, onForward = {}, backEnabled = true, forwardEnabled = true)
        NavigationButtons(onBack = {}, onForward = {}, backEnabled = true, forwardEnabled = false)
        NavigationButtons(onBack = {}, onForward = {}, backEnabled = false, forwardEnabled = false)
    }
}

// =====================================================================
// Full TitleBar compositions
// =====================================================================

@GalleryExample("TitleBar", "Browser")
@Composable
fun TitleBarBrowserExample() {
    var urlText by remember { mutableStateOf("apple.com") }
    TitleBar(
        navigationActions = {
            SidebarButton(
                onClick = {},
                menuContent = {
                    DropdownMenuItem(onClick = {}) { Text("Bookmarks") }
                    DropdownMenuItem(onClick = {}) { Text("Reading List") }
                    DropdownMenuItem(onClick = {}) { Text("Shared with You") }
                },
            )
            Spacer(modifier = Modifier.width(8.dp))
            NavigationButtons(
                onBack = {},
                onForward = {},
                backEnabled = true,
                forwardEnabled = false,
            )
        },
        title = {
            AddressBar(
                value = urlText,
                onValueChange = { urlText = it },
                onGo = {},
                leadingIcon = { Icon(LucideSearch, modifier = Modifier.size(13.dp)) },
                modifier = Modifier.fillMaxWidth(),
            )
        },
        actions = {
            TitleBarButtonGroup {
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideDownload, modifier = Modifier.size(14.dp))
                }
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideUpload, modifier = Modifier.size(14.dp))
                }
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucidePlus, modifier = Modifier.size(14.dp))
                }
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideCopy, modifier = Modifier.size(14.dp))
                }
            }
        },
    )
}

@GalleryExample("TitleBar", "Finder")
@Composable
fun TitleBarFinderExample() {
    var searchQuery by remember { mutableStateOf("") }
    var searchExpanded by remember { mutableStateOf(false) }

    TitleBar(
        navigationActions = {
            NavigationButtons(
                onBack = {},
                onForward = {},
                backEnabled = true,
                forwardEnabled = false,
            )
        },
        title = {
            Text("Documents")
        },
        actions = {
            TitleBarButtonGroup {
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideLayoutGrid, modifier = Modifier.size(14.dp))
                }
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideList, modifier = Modifier.size(14.dp))
                }
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideChevronDown, modifier = Modifier.size(10.dp))
                }
            }

            TitleBarButtonGroup {
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideShare2, modifier = Modifier.size(14.dp))
                }
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideTag, modifier = Modifier.size(14.dp))
                }
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideEllipsis, modifier = Modifier.size(14.dp))
                }
            }

            ToolbarSeparator()

            ToolbarSearchField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                expanded = searchExpanded,
                onExpandedChange = { searchExpanded = it },
                expandedWidth = 220.dp,
                suggestions = {
                    SearchSuggestionHeader("File names")
                    SearchSuggestionItem(onClick = {}) {
                        Text("Name contains \"$searchQuery\"")
                    }
                    SearchSuggestionSeparator()
                    SearchSuggestionHeader("Content")
                    SearchSuggestionItem(onClick = {}) {
                        Text("Contains \"$searchQuery\"")
                    }
                    SearchSuggestionSeparator()
                    SearchSuggestionHeader("Types")
                    SearchSuggestionItem(onClick = {}) { Text("SVG Document") }
                    SearchSuggestionItem(onClick = {}) { Text("Source Code") }
                    SearchSuggestionItem(onClick = {}) { Text("PDF Document") }
                },
            )
        },
    )
}

@GalleryExample("TitleBar", "Document")
@Composable
fun TitleBarDocumentExample() {
    TitleBar(
        title = {
            WindowTitle(
                title = "Document.txt",
                subtitle = "Subtitle — Edited",
                proxyIcon = { Icon(Lucide.FileText, modifier = Modifier.size(15.dp)) },
            )
        },
        actions = {
            TitleBarButtonGroup {
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideShare2, modifier = Modifier.size(14.dp))
                }
            }
        },
    )
}

// =====================================================================
// Custom Button Colors
// =====================================================================

@GalleryExample("TitleBar", "Custom Button Colors")
@Composable
fun TitleBarCustomColorsExample() {
    val accent = MacosTheme.colorScheme.accent
    val accentColors = TitleBarButtonDefaults.colors(
        backgroundColor = accent.copy(alpha = 0.15f),
        contentColor = accent,
    )
    val greenColors = TitleBarButtonDefaults.colors(
        backgroundColor = Color(0xFF34C759).copy(alpha = 0.15f),
        contentColor = Color(0xFF34C759),
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TitleBarButtonGroup(colors = accentColors) {
            TitleBarGroupButton(onClick = {}, colors = accentColors) {
                Icon(LucideDownload, modifier = Modifier.size(14.dp))
            }
            TitleBarGroupButton(onClick = {}, colors = accentColors) {
                Icon(LucideUpload, modifier = Modifier.size(14.dp))
            }
        }
        NavigationButtons(
            onBack = {},
            onForward = {},
            backEnabled = true,
            forwardEnabled = true,
            colors = greenColors,
        )
        SidebarButton(
            onClick = {},
            colors = accentColors,
        )
    }
}

// =====================================================================
// Page
// =====================================================================

@Composable
internal fun TitleBarPage() {
    GalleryPage("TitleBar", "macOS-style title bar with window controls, navigation, and grouped toolbar actions.") {
        SectionHeader("Window Titles")
        ExampleCard(
            title = "Window Title Variants",
            description = "Title only, title + proxy icon, title + subtitle — matching Sketch specs",
            sourceCode = GallerySources.TitleBarWindowTitleExample,
        ) { TitleBarWindowTitleExample() }

        SectionHeader("Toolbar Buttons")
        ExampleCard(
            title = "XL Buttons (36px)",
            description = "Button groups with 1, 2, 4 icons and disabled state — standard toolbar size",
            sourceCode = GallerySources.TitleBarButtonsXLExample,
        ) { TitleBarButtonsXLExample() }
        ExampleCard(
            title = "Medium Buttons (24px)",
            description = "Button groups in compact toolbar size",
            sourceCode = GallerySources.TitleBarButtonsMediumExample,
        ) { TitleBarButtonsMediumExample() }

        SectionHeader("Separator")
        ExampleCard(
            title = "Toolbar Separator",
            description = "Vertical separator between button groups, like SwiftUI Divider() in .toolbar",
            sourceCode = GallerySources.TitleBarSeparatorExample,
        ) { TitleBarSeparatorExample() }

        SectionHeader("Custom Button Colors")
        ExampleCard(
            title = "Custom Button Colors",
            description = "Toolbar buttons with custom content colors (accent, green)",
            sourceCode = GallerySources.TitleBarCustomColorsExample,
        ) { TitleBarCustomColorsExample() }

        SectionHeader("Full Title Bars")
        ExampleCard(
            title = "Browser",
            description = "Safari-style toolbar: sidebar, back/forward, URL field, and action group",
            sourceCode = GallerySources.TitleBarBrowserExample,
        ) { TitleBarBrowserExample() }
        ExampleCard(
            title = "Document",
            description = "Document window with proxy icon, subtitle, and share action",
            sourceCode = GallerySources.TitleBarDocumentExample,
        ) { TitleBarDocumentExample() }
        ExampleCard(
            title = "Finder",
            description = "Finder-style toolbar: navigation, title, view modes, actions, and search",
            sourceCode = GallerySources.TitleBarFinderExample,
        ) { TitleBarFinderExample() }
    }
}
