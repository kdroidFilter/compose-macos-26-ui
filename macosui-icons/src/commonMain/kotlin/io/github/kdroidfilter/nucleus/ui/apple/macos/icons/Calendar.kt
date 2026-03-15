package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideCalendar: ImageVector
    get() {
        if (_LucideCalendar != null) return _LucideCalendar!!
        _LucideCalendar = ImageVector.Builder(
            name = "calendar", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(8f, 2f); verticalLineToRelative(4f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(16f, 2f); verticalLineToRelative(4f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(19f, 4f); horizontalLineTo(5f); arcToRelative(2f, 2f, 0f, false, false, -2f, 2f); verticalLineToRelative(14f); arcToRelative(2f, 2f, 0f, false, false, 2f, 2f); horizontalLineToRelative(14f); arcToRelative(2f, 2f, 0f, false, false, 2f, -2f); verticalLineTo(6f); arcToRelative(2f, 2f, 0f, false, false, -2f, -2f); close()
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(3f, 10f); horizontalLineToRelative(18f)
            }
        }.build()
        return _LucideCalendar!!
    }
private var _LucideCalendar: ImageVector? = null
