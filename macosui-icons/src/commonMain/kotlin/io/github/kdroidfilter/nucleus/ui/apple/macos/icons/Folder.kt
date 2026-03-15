package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideFolder: ImageVector
    get() {
        if (_LucideFolder != null) return _LucideFolder!!
        _LucideFolder = ImageVector.Builder(
            name = "folder", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(20f, 20f); arcToRelative(2f, 2f, 0f, false, false, 2f, -2f); verticalLineTo(8f); arcToRelative(2f, 2f, 0f, false, false, -2f, -2f); horizontalLineToRelative(-7.9f); arcToRelative(2f, 2f, 0f, false, true, -1.69f, -0.9f); lineTo(9.6f, 3.9f); arcTo(2f, 2f, 0f, false, false, 7.93f, 3f); horizontalLineTo(4f); arcToRelative(2f, 2f, 0f, false, false, -2f, 2f); verticalLineToRelative(13f); arcToRelative(2f, 2f, 0f, false, false, 2f, 2f); close()
            }
        }.build()
        return _LucideFolder!!
    }
private var _LucideFolder: ImageVector? = null
