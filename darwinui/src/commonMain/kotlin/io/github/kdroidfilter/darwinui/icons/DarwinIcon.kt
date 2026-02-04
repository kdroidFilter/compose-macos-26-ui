package io.github.kdroidfilter.darwinui.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor

/**
 * Renders a Lucide [ImageVector] icon tinted with the current content color.
 *
 * @param imageVector The icon to render.
 * @param contentDescription Accessibility description (null for decorative icons).
 * @param modifier Modifier applied to the image.
 * @param tint Explicit tint color. Defaults to [LocalDarwinContentColor].
 * @param size Icon size. Defaults to 16 dp (matching Lucide's default in-button size).
 */
@Composable
fun DarwinIcon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    tint: Color = LocalDarwinContentColor.current,
    size: Dp = 16.dp,
) {
    val resolvedTint = if (tint == Color.Unspecified) Color.Black else tint
    Image(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier.size(size),
        colorFilter = ColorFilter.tint(resolvedTint),
    )
}
