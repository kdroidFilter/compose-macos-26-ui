package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun GalleryPage(
    title: String,
    description: String = "",
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        content()
    }
}
