package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideCircleCheck: ImageVector
    get() {
        if (_LucideCircleCheck != null) return _LucideCircleCheck!!
        _LucideCircleCheck = ImageVector.Builder(
            name = "circle-check", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(22f, 12f); arcTo(10f, 10f, 0f, false, true, 12f, 22f); arcTo(10f, 10f, 0f, false, true, 2f, 12f); arcTo(10f, 10f, 0f, false, true, 22f, 12f); close()
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(9f, 12f); lineToRelative(2f, 2f); lineToRelative(4f, -4f)
            }
        }.build()
        return _LucideCircleCheck!!
    }
private var _LucideCircleCheck: ImageVector? = null
