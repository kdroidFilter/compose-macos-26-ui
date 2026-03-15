package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikepenz.markdown.model.rememberMarkdownState
import composemacosui.sample.generated.resources.Res
import io.github.kdroidfilter.nucleus.ui.apple.macos.markdown.Markdown
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun LicensePage() {
    val markdownState = rememberMarkdownState {
        Res.readBytes("files/license.md").decodeToString()
    }

    GalleryPage("License") {
        Markdown(
            markdownState = markdownState,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
