package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideCircleX: ImageVector
    get() {
        if (_LucideCircleX != null) return _LucideCircleX!!
        _LucideCircleX = ImageVector.Builder(
            name = "circle-x", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(22f, 12f); arcTo(10f, 10f, 0f, false, true, 12f, 22f); arcTo(10f, 10f, 0f, false, true, 2f, 12f); arcTo(10f, 10f, 0f, false, true, 22f, 12f); close()
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(15f, 9f); lineToRelative(-6f, 6f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(9f, 9f); lineToRelative(6f, 6f)
            }
        }.build()
        return _LucideCircleX!!
    }
private var _LucideCircleX: ImageVector? = null
