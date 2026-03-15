package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideImage: ImageVector
    get() {
        if (_LucideImage != null) return _LucideImage!!
        _LucideImage = ImageVector.Builder(
            name = "image", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(5f, 3f); horizontalLineToRelative(14f); arcToRelative(2f, 2f, 0f, false, true, 2f, 2f); verticalLineToRelative(14f); arcToRelative(2f, 2f, 0f, false, true, -2f, 2f); horizontalLineTo(5f); arcToRelative(2f, 2f, 0f, false, true, -2f, -2f); verticalLineTo(5f); arcToRelative(2f, 2f, 0f, false, true, 2f, -2f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(10.5f, 8.5f); arcTo(2.5f, 2.5f, 0f, false, true, 8f, 11f); arcTo(2.5f, 2.5f, 0f, false, true, 5.5f, 8.5f); arcTo(2.5f, 2.5f, 0f, false, true, 10.5f, 8.5f); close()
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(21f, 15f); lineToRelative(-5f, -5f); lineTo(5f, 21f)
            }
        }.build()
        return _LucideImage!!
    }
private var _LucideImage: ImageVector? = null
