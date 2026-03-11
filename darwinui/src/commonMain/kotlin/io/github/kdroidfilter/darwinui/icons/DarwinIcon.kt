package io.github.kdroidfilter.darwinui.icons

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor

/**
 * Renders an [ImageVector] icon — mirrors Material3's Icon.
 *
 * @param imageVector The icon vector to render.
 * @param contentDescription Accessibility description (null for decorative icons).
 * @param modifier Modifier applied to the icon. Use [Modifier.size] to set icon size.
 * @param tint Tint color. Defaults to [LocalDarwinContentColor].
 */
@Composable
fun Icon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    tint: Color = LocalDarwinContentColor.current,
) {
    val resolvedTint = if (tint == Color.Unspecified) Color.Black else tint
    Image(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        colorFilter = ColorFilter.tint(resolvedTint),
    )
}

/**
 * Renders a [Painter] icon — mirrors Material3's Icon.
 */
@Composable
fun Icon(
    painter: Painter,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    tint: Color = LocalDarwinContentColor.current,
) {
    val resolvedTint = if (tint == Color.Unspecified) Color.Black else tint
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        colorFilter = ColorFilter.tint(resolvedTint),
    )
}

/**
 * Renders an [ImageBitmap] icon — mirrors Material3's Icon.
 */
@Composable
fun Icon(
    bitmap: ImageBitmap,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    tint: Color = LocalDarwinContentColor.current,
) {
    val resolvedTint = if (tint == Color.Unspecified) Color.Black else tint
    Image(
        bitmap = bitmap,
        contentDescription = contentDescription,
        modifier = modifier,
        colorFilter = ColorFilter.tint(resolvedTint),
    )
}
