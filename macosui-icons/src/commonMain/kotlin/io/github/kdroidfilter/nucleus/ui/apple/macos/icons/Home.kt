package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideHome: ImageVector
    get() {
        if (_LucideHome != null) return _LucideHome!!
        _LucideHome = ImageVector.Builder(
            name = "home", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(15f, 21f); verticalLineToRelative(-8f); arcToRelative(1f, 1f, 0f, false, false, -1f, -1f); horizontalLineToRelative(-4f); arcToRelative(1f, 1f, 0f, false, false, -1f, 1f); verticalLineToRelative(8f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(3f, 10f); arcToRelative(2f, 2f, 0f, false, true, 0.709f, -1.528f); lineToRelative(7f, -5.999f); arcToRelative(2f, 2f, 0f, false, true, 2.582f, 0f); lineToRelative(7f, 5.999f); arcTo(2f, 2f, 0f, false, true, 21f, 10f); verticalLineToRelative(9f); arcToRelative(2f, 2f, 0f, false, true, -2f, 2f); horizontalLineTo(5f); arcToRelative(2f, 2f, 0f, false, true, -2f, -2f); close()
            }
        }.build()
        return _LucideHome!!
    }
private var _LucideHome: ImageVector? = null
