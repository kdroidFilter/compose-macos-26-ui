package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucidePanelLeft: ImageVector
    get() {
        if (_LucidePanelLeft != null) return _LucidePanelLeft!!
        _LucidePanelLeft = ImageVector.Builder(
            name = "panel-left", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(3f, 5f); arcTo(2f, 2f, 0f, false, true, 5f, 3f); horizontalLineTo(19f); arcTo(2f, 2f, 0f, false, true, 21f, 5f); verticalLineTo(19f); arcTo(2f, 2f, 0f, false, true, 19f, 21f); horizontalLineTo(5f); arcTo(2f, 2f, 0f, false, true, 3f, 19f); close()
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(9f, 3f); verticalLineTo(21f)
            }
        }.build()
        return _LucidePanelLeft!!
    }
private var _LucidePanelLeft: ImageVector? = null
