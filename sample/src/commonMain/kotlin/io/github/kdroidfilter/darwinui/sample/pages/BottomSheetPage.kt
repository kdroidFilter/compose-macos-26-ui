package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.ModalBottomSheet as DarwinModalBottomSheet
import io.github.kdroidfilter.darwinui.components.PrimaryButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import androidx.compose.material3.Text as M3Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomSheetPage() {
    GalleryPage("Bottom Sheet", "Darwin ModalBottomSheet vs Material 3 ModalBottomSheet.") {
        SectionHeader("Modal")
        ComparisonSection(
            darwinContent = {
                var showSheet by remember { mutableStateOf(false) }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    PrimaryButton(text = "Open Darwin Sheet", onClick = { showSheet = true })
                }

                if (showSheet) {
                    DarwinModalBottomSheet(onDismissRequest = { showSheet = false }) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Text("Darwin Bottom Sheet")
                            Text("iOS-style sheet with Darwin theme.")
                            PrimaryButton(
                                text = "Dismiss",
                                onClick = { showSheet = false },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }
            },
            materialContent = {
                var showSheet by remember { mutableStateOf(false) }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    PrimaryButton(text = "Open M3 Sheet", onClick = { showSheet = true })
                }

                if (showSheet) {
                    ModalBottomSheet(onDismissRequest = { showSheet = false }) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            M3Text("Material 3 Bottom Sheet")
                            M3Text("Standard M3 sheet with Material theme.")
                            PrimaryButton(
                                text = "Dismiss",
                                onClick = { showSheet = false },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }
            },
        )
    }
}
