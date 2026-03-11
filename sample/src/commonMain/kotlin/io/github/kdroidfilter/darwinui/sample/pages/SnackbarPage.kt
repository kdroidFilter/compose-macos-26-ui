package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.AlertBanner
import io.github.kdroidfilter.darwinui.components.AlertType
import io.github.kdroidfilter.darwinui.components.PrimaryButton
import io.github.kdroidfilter.darwinui.components.Snackbar as DarwinSnackbar
import io.github.kdroidfilter.darwinui.components.SnackbarHost as DarwinSnackbarHost
import io.github.kdroidfilter.darwinui.components.SnackbarHostState as DarwinSnackbarHostState
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.TextButton as DarwinTextButton
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import kotlinx.coroutines.launch
import androidx.compose.material3.Text as M3Text

@Composable
internal fun SnackbarPage() {
    GalleryPage("Snackbar", "Darwin Snackbar (iOS-style) vs Material 3 Snackbar.") {
        SectionHeader("Static")
        ComparisonSection(
            darwinContent = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DarwinSnackbar {
                        Text("This is a snackbar message")
                    }
                    DarwinSnackbar(
                        action = {
                            DarwinTextButton(onClick = {}) { Text("Undo") }
                        },
                    ) {
                        Text("Item deleted")
                    }
                }
            },
            materialContent = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Snackbar {
                        M3Text("This is a snackbar message")
                    }
                    Snackbar(
                        action = {
                            TextButton(onClick = {}) { M3Text("Undo") }
                        },
                    ) {
                        M3Text("Item deleted")
                    }
                }
            },
        )

        SectionHeader("With Host")
        ComparisonSection(
            darwinContent = {
                val hostState = remember { DarwinSnackbarHostState() }
                val scope = rememberCoroutineScope()

                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        PrimaryButton(
                            text = "Show Darwin snackbar",
                            onClick = {
                                scope.launch { hostState.showSnackbar("Hello from Darwin!") }
                            },
                        )
                    }
                    DarwinSnackbarHost(
                        hostState = hostState,
                        modifier = Modifier.align(Alignment.BottomCenter),
                    )
                }
            },
            materialContent = {
                val hostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        PrimaryButton(
                            text = "Show M3 snackbar",
                            onClick = {
                                scope.launch { hostState.showSnackbar("Hello from Material 3!") }
                            },
                        )
                    }
                    SnackbarHost(
                        hostState = hostState,
                        modifier = Modifier.align(Alignment.BottomCenter),
                    )
                }
            },
        )
    }
}
