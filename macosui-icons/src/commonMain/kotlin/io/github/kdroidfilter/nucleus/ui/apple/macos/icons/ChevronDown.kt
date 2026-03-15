package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideChevronDown: ImageVector
    get() {
        if (_LucideChevronDown != null) return _LucideChevronDown!!
        _LucideChevronDown = ImageVector.Builder(
            name = "chevron-down", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(6f, 9f); lineToRelative(6f, 6f); lineToRelative(6f, -6f)
            }
        }.build()
        return _LucideChevronDown!!
    }
private var _LucideChevronDown: ImageVector? = null
