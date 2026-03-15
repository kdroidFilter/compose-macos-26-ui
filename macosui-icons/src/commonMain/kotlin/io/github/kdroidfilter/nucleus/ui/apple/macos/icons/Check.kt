package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideCheck: ImageVector
    get() {
        if (_LucideCheck != null) return _LucideCheck!!
        _LucideCheck = ImageVector.Builder(
            name = "check", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(20f, 6f); lineTo(9f, 17f); lineToRelative(-5f, -5f)
            }
        }.build()
        return _LucideCheck!!
    }
private var _LucideCheck: ImageVector? = null
