package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideChevronsLeft: ImageVector
    get() {
        if (_LucideChevronsLeft != null) return _LucideChevronsLeft!!
        _LucideChevronsLeft = ImageVector.Builder(
            name = "chevrons-left", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(11f, 17f); lineTo(6f, 12f); lineTo(11f, 7f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(18f, 17f); lineTo(13f, 12f); lineTo(18f, 7f)
            }
        }.build()
        return _LucideChevronsLeft!!
    }
private var _LucideChevronsLeft: ImageVector? = null
