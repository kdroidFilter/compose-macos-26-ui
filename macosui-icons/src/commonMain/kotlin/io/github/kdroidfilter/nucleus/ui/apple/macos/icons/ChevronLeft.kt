package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideChevronLeft: ImageVector
    get() {
        if (_LucideChevronLeft != null) return _LucideChevronLeft!!
        _LucideChevronLeft = ImageVector.Builder(
            name = "chevron-left", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(15f, 18f); lineToRelative(-6f, -6f); lineToRelative(6f, -6f)
            }
        }.build()
        return _LucideChevronLeft!!
    }
private var _LucideChevronLeft: ImageVector? = null
