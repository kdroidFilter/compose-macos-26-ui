package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideChevronRight: ImageVector
    get() {
        if (_LucideChevronRight != null) return _LucideChevronRight!!
        _LucideChevronRight = ImageVector.Builder(
            name = "chevron-right", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(9f, 18f); lineToRelative(6f, -6f); lineToRelative(-6f, -6f)
            }
        }.build()
        return _LucideChevronRight!!
    }
private var _LucideChevronRight: ImageVector? = null
