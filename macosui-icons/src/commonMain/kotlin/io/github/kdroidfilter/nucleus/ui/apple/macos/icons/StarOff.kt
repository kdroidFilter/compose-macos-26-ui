package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideStarOff: ImageVector
    get() {
        if (_LucideStarOff != null) return _LucideStarOff!!
        _LucideStarOff = ImageVector.Builder(
            name = "star-off", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(8.34f, 8.34f)
                lineToRelative(-1.99f, -1.99f)
                lineToRelative(-3.979f, 3.879f)
                arcToRelative(0.53f, 0.53f, 0f, false, false, 0.294f, 0.904f)
                lineToRelative(5.164f, 0.75f)
                arcToRelative(0.53f, 0.53f, 0f, false, true, 0.399f, 0.291f)
                lineToRelative(2.31f, 4.679f)
                arcToRelative(0.53f, 0.53f, 0f, false, false, 0.95f, 0f)
                lineToRelative(2.31f, -4.68f)
                arcToRelative(0.53f, 0.53f, 0f, false, true, 0.399f, -0.29f)
                lineToRelative(5.163f, -0.75f)
                arcToRelative(0.53f, 0.53f, 0f, false, false, 0.294f, -0.904f)
                lineToRelative(-3.736f, -3.642f)
                arcToRelative(0.53f, 0.53f, 0f, false, true, -0.152f, -0.468f)
                lineToRelative(0.882f, -5.14f)
                arcToRelative(0.53f, 0.53f, 0f, false, false, -0.77f, -0.56f)
                lineToRelative(-4.617f, 2.429f)
                arcToRelative(0.53f, 0.53f, 0f, false, true, -0.493f, 0f)
                lineTo(8.34f, 8.34f)
                close()
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(2f, 2f); lineToRelative(20f, 20f)
            }
        }.build()
        return _LucideStarOff!!
    }
private var _LucideStarOff: ImageVector? = null
