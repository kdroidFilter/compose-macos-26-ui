package io.github.kdroidfilter.darwinui.sample.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@Composable
fun PreviewContainer(
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(DarwinTheme.shapes.extraLarge)
            .border(
                width = 1.dp,
                color = DarwinTheme.colors.border,
                shape = DarwinTheme.shapes.extraLarge,
            )
            .background(DarwinTheme.colors.backgroundSubtle.copy(alpha = 0.5f)),
    ) {
        DarwinText(
            text = "Preview",
            style = DarwinTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = DarwinTheme.colors.textTertiary,
            modifier = Modifier.padding(start = 24.dp, top = 20.dp),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 100.dp)
                .padding(24.dp),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}
