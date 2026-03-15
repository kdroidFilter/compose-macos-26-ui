package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideCopy: ImageVector
    get() {
        if (_LucideCopy != null) return _LucideCopy!!
        _LucideCopy = ImageVector.Builder(
            name = "copy", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(10f, 8f); horizontalLineToRelative(10f); arcToRelative(2f, 2f, 0f, false, true, 2f, 2f); verticalLineToRelative(10f); arcToRelative(2f, 2f, 0f, false, true, -2f, 2f); horizontalLineTo(10f); arcToRelative(2f, 2f, 0f, false, true, -2f, -2f); verticalLineTo(10f); arcToRelative(2f, 2f, 0f, false, true, 2f, -2f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(4f, 16f); curveToRelative(-1.1f, 0f, -2f, -0.9f, -2f, -2f); verticalLineTo(4f); curveToRelative(0f, -1.1f, 0.9f, -2f, 2f, -2f); horizontalLineToRelative(10f); curveToRelative(1.1f, 0f, 2f, 0.9f, 2f, 2f)
            }
        }.build()
        return _LucideCopy!!
    }
private var _LucideCopy: ImageVector? = null
