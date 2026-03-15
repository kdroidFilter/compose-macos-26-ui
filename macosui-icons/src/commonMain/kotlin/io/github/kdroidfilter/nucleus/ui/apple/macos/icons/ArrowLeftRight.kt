package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideArrowLeftRight: ImageVector
    get() {
        if (_LucideArrowLeftRight != null) return _LucideArrowLeftRight!!
        _LucideArrowLeftRight = ImageVector.Builder(
            name = "arrow-left-right", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(8f, 3f); lineToRelative(-4f, 4f); lineToRelative(4f, 4f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(4f, 7f); horizontalLineToRelative(16f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(16f, 21f); lineToRelative(4f, -4f); lineToRelative(-4f, -4f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(20f, 17f); horizontalLineTo(4f)
            }
        }.build()
        return _LucideArrowLeftRight!!
    }
private var _LucideArrowLeftRight: ImageVector? = null
