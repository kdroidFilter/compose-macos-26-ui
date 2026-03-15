package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideChevronsUpDown: ImageVector
    get() {
        if (_LucideChevronsUpDown != null) return _LucideChevronsUpDown!!
        _LucideChevronsUpDown = ImageVector.Builder(
            name = "chevrons-up-down", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(7f, 15f); lineTo(12f, 20f); lineTo(17f, 15f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(7f, 9f); lineTo(12f, 4f); lineTo(17f, 9f)
            }
        }.build()
        return _LucideChevronsUpDown!!
    }
private var _LucideChevronsUpDown: ImageVector? = null
