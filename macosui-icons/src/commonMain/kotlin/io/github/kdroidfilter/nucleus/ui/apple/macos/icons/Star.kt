package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LucideStar: ImageVector
    get() {
        if (_LucideStar != null) return _LucideStar!!
        _LucideStar = ImageVector.Builder(
            name = "star", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(11.525f, 2.295f)
                arcToRelative(0.53f, 0.53f, 0f, false, true, 0.95f, 0f)
                lineToRelative(2.31f, 4.679f)
                arcToRelative(0.53f, 0.53f, 0f, false, false, 0.398f, 0.29f)
                lineToRelative(5.164f, 0.75f)
                arcToRelative(0.53f, 0.53f, 0f, false, true, 0.294f, 0.904f)
                lineToRelative(-3.736f, 3.642f)
                arcToRelative(0.53f, 0.53f, 0f, false, false, -0.152f, 0.469f)
                lineToRelative(0.882f, 5.14f)
                arcToRelative(0.53f, 0.53f, 0f, false, true, -0.769f, 0.559f)
                lineToRelative(-4.618f, -2.428f)
                arcToRelative(0.53f, 0.53f, 0f, false, false, -0.493f, 0f)
                lineToRelative(-4.618f, 2.428f)
                arcToRelative(0.53f, 0.53f, 0f, false, true, -0.769f, -0.559f)
                lineToRelative(0.882f, -5.14f)
                arcToRelative(0.53f, 0.53f, 0f, false, false, -0.152f, -0.469f)
                lineToRelative(-3.736f, -3.642f)
                arcToRelative(0.53f, 0.53f, 0f, false, true, 0.294f, -0.904f)
                lineToRelative(5.164f, -0.75f)
                arcToRelative(0.53f, 0.53f, 0f, false, false, 0.398f, -0.29f)
                close()
            }
        }.build()
        return _LucideStar!!
    }
private var _LucideStar: ImageVector? = null
