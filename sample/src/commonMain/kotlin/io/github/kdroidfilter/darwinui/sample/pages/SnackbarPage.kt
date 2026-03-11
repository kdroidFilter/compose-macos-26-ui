package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.PrimaryButton
import io.github.kdroidfilter.darwinui.components.Snackbar
import io.github.kdroidfilter.darwinui.components.SnackbarHost
import io.github.kdroidfilter.darwinui.components.SnackbarHostState
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.TextButton
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import kotlinx.coroutines.launch

@GalleryExample("Snackbar", "Static Preview")
@Composable
fun SnackbarStaticExample() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Snackbar {
            Text("This is a snackbar message")
        }
        Snackbar(
            action = {
                TextButton(onClick = {}) { Text("Undo") }
            },
        ) {
            Text("Item deleted")
        }
    }
}

@GalleryExample("Snackbar", "With SnackbarHost")
@Composable
fun SnackbarWithHostExample() {
    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PrimaryButton(
                text = "Show snackbar",
                onClick = {
                    scope.launch {
                        hostState.showSnackbar("Hello from snackbar!")
                    }
                },
            )
        }
        SnackbarHost(
            hostState = hostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
internal fun SnackbarPage() {
    GalleryPage("Snackbar", "Brief messages about app processes shown at the bottom of the screen.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Static Preview",
            description = "Snackbar with and without action",
            sourceCode = GallerySources.SnackbarStaticExample,
        ) { SnackbarStaticExample() }
        ExampleCard(
            title = "With SnackbarHost",
            description = "Trigger a snackbar via SnackbarHostState",
            sourceCode = GallerySources.SnackbarWithHostExample,
        ) { SnackbarWithHostExample() }
    }
}
