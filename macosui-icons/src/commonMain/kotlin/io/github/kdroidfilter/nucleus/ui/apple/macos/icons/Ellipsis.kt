package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideEllipsis: ImageVector
    get() {
        if (_LucideEllipsis != null) return _LucideEllipsis!!
        _LucideEllipsis = ImageVector.Builder(
            name = "ellipsis", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(6f, 12f); arcTo(1f, 1f, 0f, false, true, 4f, 12f); arcTo(1f, 1f, 0f, false, true, 6f, 12f); close()
            }
            path(fill = SolidColor(Color.Black), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(13f, 12f); arcTo(1f, 1f, 0f, false, true, 11f, 12f); arcTo(1f, 1f, 0f, false, true, 13f, 12f); close()
            }
            path(fill = SolidColor(Color.Black), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(20f, 12f); arcTo(1f, 1f, 0f, false, true, 18f, 12f); arcTo(1f, 1f, 0f, false, true, 20f, 12f); close()
            }
        }.build()
        return _LucideEllipsis!!
    }
private var _LucideEllipsis: ImageVector? = null
