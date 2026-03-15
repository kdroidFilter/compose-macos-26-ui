package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideInfo: ImageVector
    get() {
        if (_LucideInfo != null) return _LucideInfo!!
        _LucideInfo = ImageVector.Builder(
            name = "info", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(22f, 12f); arcTo(10f, 10f, 0f, false, true, 12f, 22f); arcTo(10f, 10f, 0f, false, true, 2f, 12f); arcTo(10f, 10f, 0f, false, true, 22f, 12f); close()
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(12f, 16f); verticalLineToRelative(-4f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(12f, 8f); horizontalLineToRelative(0.01f)
            }
        }.build()
        return _LucideInfo!!
    }
private var _LucideInfo: ImageVector? = null
