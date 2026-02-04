package io.github.kdroidfilter.darwinui.sample.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@Composable
fun GalleryPage(
    title: String,
    description: String = "",
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        // Header: breadcrumb + title + description
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                DarwinText(
                    text = "Components",
                    style = DarwinTheme.typography.bodySmall,
                    color = DarwinTheme.colors.textTertiary,
                )
                DarwinText(
                    text = ">",
                    style = DarwinTheme.typography.bodySmall,
                    color = DarwinTheme.colors.textTertiary,
                )
                DarwinText(
                    text = title,
                    style = DarwinTheme.typography.bodySmall,
                    color = DarwinTheme.colors.textSecondary,
                )
            }
            DarwinText(
                text = title,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarwinTheme.colors.textPrimary,
            )
            if (description.isNotEmpty()) {
                DarwinText(
                    text = description,
                    style = DarwinTheme.typography.bodyLarge,
                    color = DarwinTheme.colors.textSecondary,
                )
            }
        }

        // Content sections
        content()
    }
}
