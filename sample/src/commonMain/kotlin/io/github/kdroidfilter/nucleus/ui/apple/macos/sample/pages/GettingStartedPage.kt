package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lightbulb
import com.composables.icons.lucide.Lucide
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownBlockQuote
import com.mikepenz.markdown.compose.elements.MarkdownCodeFence
import com.mikepenz.markdown.model.rememberMarkdownState
import composemacosui.sample.generated.resources.Res
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.GroupBox
import io.github.kdroidfilter.nucleus.ui.apple.macos.markdown.Markdown
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.CodeBlock
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi

private val galleryComponents = markdownComponents(
    codeFence = {
        MarkdownCodeFence(it.content, it.node, it.typography.code) { code, _, _ ->
            CodeBlock(code)
        }
    },
    blockQuote = {
        val accent = MacosTheme.colorScheme.accent
        val shape = MacosTheme.shapes.medium
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                .background(accent.copy(alpha = 0.08f))
                .drawBehind {
                    drawLine(
                        color = accent,
                        strokeWidth = 3.dp.toPx(),
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                    )
                }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Icon(
                Lucide.Lightbulb,
                modifier = Modifier.size(16.dp).padding(top = 2.dp),
                tint = accent,
            )
            MarkdownBlockQuote(it.content, it.node)
        }
    },
)

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun GettingStartedPage() {
    val markdownState = rememberMarkdownState {
        Res.readBytes("files/getting_started.md").decodeToString()
    }

    GalleryPage("Getting Started") {
        GroupBox(modifier = Modifier.fillMaxWidth()) {
            Markdown(
                markdownState = markdownState,
                modifier = Modifier.fillMaxWidth(),
                components = galleryComponents,
            )
        }
    }
}
