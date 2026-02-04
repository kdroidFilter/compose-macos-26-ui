package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.upload.DarwinUpload
import io.github.kdroidfilter.darwinui.components.upload.DarwinUploadFile
import io.github.kdroidfilter.darwinui.components.upload.DarwinUploadVariant
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.CodeBlock
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("Upload", "Default")
@Composable
fun UploadDefaultExample() {
    val files = remember {
        mutableStateListOf(
            DarwinUploadFile(name = "hero-image.jpg", url = "https://example.com/hero.jpg"),
            DarwinUploadFile(name = "product-shot.png", url = "https://example.com/product.png"),
            DarwinUploadFile(name = "banner.jpg", url = "https://example.com/banner.jpg"),
        )
    }
    DarwinUpload(
        files = files,
        onPickFiles = { files.add(DarwinUploadFile(name = "new-image.jpg", isUploading = true, progress = 0.4f)) },
        onRemoveFile = { index -> files.removeAt(index) },
        onClearAll = { files.clear() },
        onSetCover = { index ->
            if (index > 0 && index < files.size) {
                val item = files.removeAt(index)
                files.add(0, item)
            }
        },
        onSwap = { i, j ->
            if (i in files.indices && j in files.indices) {
                val tmp = files[i]
                files[i] = files[j]
                files[j] = tmp
            }
        },
        maxFiles = 4,
        label = "Upload product images",
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@GalleryExample("Upload", "Compact")
@Composable
fun UploadCompactExample() {
    DarwinUpload(
        files = emptyList(),
        onPickFiles = {},
        variant = DarwinUploadVariant.Compact,
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@GalleryExample("Upload", "Inline")
@Composable
fun UploadInlineExample() {
    DarwinUpload(
        files = emptyList(),
        onPickFiles = {},
        variant = DarwinUploadVariant.Inline,
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@Composable
internal fun UploadPage() {
    GalleryPage("Upload", "A file upload component with drag and drop support.") {
        SectionHeader("Usage")
        CodeBlock("""DarwinUpload(
    files = files,
    onPickFiles = { /* open file picker */ },
    onRemoveFile = { index -> files.removeAt(index) },
    onClearAll = { files.clear() },
    onSetCover = { index -> /* move to front */ },
    onSwap = { i, j -> /* swap positions */ },
    maxFiles = 4,
    label = "Upload product images",
    variant = DarwinUploadVariant.Default,
)""")

        SectionHeader("Examples")
        ExampleCard(
            title = "Default",
            description = "Full grid with image cards, cover selection, and reordering",
            sourceCode = GallerySources.UploadDefaultExample,
        ) { UploadDefaultExample() }
        ExampleCard(
            title = "Compact",
            description = "Smaller dropzone with 2-column grid",
            sourceCode = GallerySources.UploadCompactExample,
        ) { UploadCompactExample() }
        ExampleCard(
            title = "Inline",
            description = "Single-row inline upload bar",
            sourceCode = GallerySources.UploadInlineExample,
        ) { UploadInlineExample() }
    }
}
