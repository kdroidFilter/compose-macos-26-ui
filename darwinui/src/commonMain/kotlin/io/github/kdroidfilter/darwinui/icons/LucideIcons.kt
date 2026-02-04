/*
 * Lucide Icons for Compose Multiplatform
 * ISC License — Copyright (c) Lucide Contributors 2025
 * MIT License — Copyright (c) Cole Bemis 2013-2023 (Feather portions)
 *
 * Downloaded from https://composables.com/icons/icon-libraries/lucide
 */
package io.github.kdroidfilter.darwinui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

// =============================================================================
// Plus
// =============================================================================

val LucidePlus: ImageVector
    get() {
        if (_LucidePlus != null) return _LucidePlus!!
        _LucidePlus = ImageVector.Builder(
            name = "plus", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(5f, 12f); horizontalLineToRelative(14f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(12f, 5f); verticalLineToRelative(14f)
            }
        }.build()
        return _LucidePlus!!
    }
private var _LucidePlus: ImageVector? = null

// =============================================================================
// Settings
// =============================================================================

val LucideSettings: ImageVector
    get() {
        if (_LucideSettings != null) return _LucideSettings!!
        _LucideSettings = ImageVector.Builder(
            name = "settings", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(9.671f, 4.136f)
                arcToRelative(2.34f, 2.34f, 0f, false, true, 4.659f, 0f)
                arcToRelative(2.34f, 2.34f, 0f, false, false, 3.319f, 1.915f)
                arcToRelative(2.34f, 2.34f, 0f, false, true, 2.33f, 4.033f)
                arcToRelative(2.34f, 2.34f, 0f, false, false, 0f, 3.831f)
                arcToRelative(2.34f, 2.34f, 0f, false, true, -2.33f, 4.033f)
                arcToRelative(2.34f, 2.34f, 0f, false, false, -3.319f, 1.915f)
                arcToRelative(2.34f, 2.34f, 0f, false, true, -4.659f, 0f)
                arcToRelative(2.34f, 2.34f, 0f, false, false, -3.32f, -1.915f)
                arcToRelative(2.34f, 2.34f, 0f, false, true, -2.33f, -4.033f)
                arcToRelative(2.34f, 2.34f, 0f, false, false, 0f, -3.831f)
                arcTo(2.34f, 2.34f, 0f, false, true, 6.35f, 6.051f)
                arcToRelative(2.34f, 2.34f, 0f, false, false, 3.319f, -1.915f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(15f, 12f)
                arcTo(3f, 3f, 0f, false, true, 12f, 15f)
                arcTo(3f, 3f, 0f, false, true, 9f, 12f)
                arcTo(3f, 3f, 0f, false, true, 15f, 12f)
                close()
            }
        }.build()
        return _LucideSettings!!
    }
private var _LucideSettings: ImageVector? = null

// =============================================================================
// Heart
// =============================================================================

val LucideHeart: ImageVector
    get() {
        if (_LucideHeart != null) return _LucideHeart!!
        _LucideHeart = ImageVector.Builder(
            name = "heart", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(2f, 9.5f)
                arcToRelative(5.5f, 5.5f, 0f, false, true, 9.591f, -3.676f)
                arcToRelative(0.56f, 0.56f, 0f, false, false, 0.818f, 0f)
                arcTo(5.49f, 5.49f, 0f, false, true, 22f, 9.5f)
                curveToRelative(0f, 2.29f, -1.5f, 4f, -3f, 5.5f)
                lineToRelative(-5.492f, 5.313f)
                arcToRelative(2f, 2f, 0f, false, true, -3f, 0.019f)
                lineTo(5f, 15f)
                curveToRelative(-1.5f, -1.5f, -3f, -3.2f, -3f, -5.5f)
            }
        }.build()
        return _LucideHeart!!
    }
private var _LucideHeart: ImageVector? = null

// =============================================================================
// X
// =============================================================================

val LucideX: ImageVector
    get() {
        if (_LucideX != null) return _LucideX!!
        _LucideX = ImageVector.Builder(
            name = "x", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(18f, 6f); lineTo(6f, 18f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(6f, 6f); lineToRelative(12f, 12f)
            }
        }.build()
        return _LucideX!!
    }
private var _LucideX: ImageVector? = null

// =============================================================================
// Check
// =============================================================================

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

// =============================================================================
// Trash2
// =============================================================================

val LucideTrash2: ImageVector
    get() {
        if (_LucideTrash2 != null) return _LucideTrash2!!
        _LucideTrash2 = ImageVector.Builder(
            name = "trash-2", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(10f, 11f); verticalLineToRelative(6f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(14f, 11f); verticalLineToRelative(6f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(19f, 6f); verticalLineToRelative(14f); arcToRelative(2f, 2f, 0f, false, true, -2f, 2f); horizontalLineTo(7f); arcToRelative(2f, 2f, 0f, false, true, -2f, -2f); verticalLineTo(6f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(3f, 6f); horizontalLineToRelative(18f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(8f, 6f); verticalLineTo(4f); arcToRelative(2f, 2f, 0f, false, true, 2f, -2f); horizontalLineToRelative(4f); arcToRelative(2f, 2f, 0f, false, true, 2f, 2f); verticalLineToRelative(2f)
            }
        }.build()
        return _LucideTrash2!!
    }
private var _LucideTrash2: ImageVector? = null

// =============================================================================
// Download
// =============================================================================

val LucideDownload: ImageVector
    get() {
        if (_LucideDownload != null) return _LucideDownload!!
        _LucideDownload = ImageVector.Builder(
            name = "download", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(12f, 15f); verticalLineTo(3f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(21f, 15f); verticalLineToRelative(4f); arcToRelative(2f, 2f, 0f, false, true, -2f, 2f); horizontalLineTo(5f); arcToRelative(2f, 2f, 0f, false, true, -2f, -2f); verticalLineToRelative(-4f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(7f, 10f); lineToRelative(5f, 5f); lineToRelative(5f, -5f)
            }
        }.build()
        return _LucideDownload!!
    }
private var _LucideDownload: ImageVector? = null

// =============================================================================
// Share2
// =============================================================================

val LucideShare2: ImageVector
    get() {
        if (_LucideShare2 != null) return _LucideShare2!!
        _LucideShare2 = ImageVector.Builder(
            name = "share-2", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(21f, 5f); arcTo(3f, 3f, 0f, false, true, 18f, 8f); arcTo(3f, 3f, 0f, false, true, 15f, 5f); arcTo(3f, 3f, 0f, false, true, 21f, 5f); close()
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(9f, 12f); arcTo(3f, 3f, 0f, false, true, 6f, 15f); arcTo(3f, 3f, 0f, false, true, 3f, 12f); arcTo(3f, 3f, 0f, false, true, 9f, 12f); close()
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(21f, 19f); arcTo(3f, 3f, 0f, false, true, 18f, 22f); arcTo(3f, 3f, 0f, false, true, 15f, 19f); arcTo(3f, 3f, 0f, false, true, 21f, 19f); close()
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(8.59f, 13.51f); lineTo(15.42f, 17.49f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(15.41f, 6.51f); lineTo(8.59f, 10.49f)
            }
        }.build()
        return _LucideShare2!!
    }
private var _LucideShare2: ImageVector? = null

// =============================================================================
// Sun
// =============================================================================

val LucideSun: ImageVector
    get() {
        if (_LucideSun != null) return _LucideSun!!
        _LucideSun = ImageVector.Builder(
            name = "sun", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(16f, 12f); arcTo(4f, 4f, 0f, false, true, 12f, 16f); arcTo(4f, 4f, 0f, false, true, 8f, 12f); arcTo(4f, 4f, 0f, false, true, 16f, 12f); close()
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) { moveTo(12f, 2f); verticalLineToRelative(2f) }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) { moveTo(12f, 20f); verticalLineToRelative(2f) }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) { moveTo(4.93f, 4.93f); lineToRelative(1.41f, 1.41f) }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) { moveTo(17.66f, 17.66f); lineToRelative(1.41f, 1.41f) }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) { moveTo(2f, 12f); horizontalLineToRelative(2f) }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) { moveTo(20f, 12f); horizontalLineToRelative(2f) }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) { moveTo(6.34f, 17.66f); lineToRelative(-1.41f, 1.41f) }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) { moveTo(19.07f, 4.93f); lineToRelative(-1.41f, 1.41f) }
        }.build()
        return _LucideSun!!
    }
private var _LucideSun: ImageVector? = null

// =============================================================================
// Moon
// =============================================================================

val LucideMoon: ImageVector
    get() {
        if (_LucideMoon != null) return _LucideMoon!!
        _LucideMoon = ImageVector.Builder(
            name = "moon", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(20.985f, 12.486f)
                arcToRelative(9f, 9f, 0f, true, true, -9.473f, -9.472f)
                curveToRelative(0.405f, -0.022f, 0.617f, 0.46f, 0.402f, 0.803f)
                arcToRelative(6f, 6f, 0f, false, false, 8.268f, 8.268f)
                curveToRelative(0.344f, -0.215f, 0.825f, -0.004f, 0.803f, 0.401f)
            }
        }.build()
        return _LucideMoon!!
    }
private var _LucideMoon: ImageVector? = null

// =============================================================================
// Search
// =============================================================================

val LucideSearch: ImageVector
    get() {
        if (_LucideSearch != null) return _LucideSearch!!
        _LucideSearch = ImageVector.Builder(
            name = "search", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(21f, 21f); lineToRelative(-4.34f, -4.34f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(19f, 11f); arcTo(8f, 8f, 0f, false, true, 11f, 19f); arcTo(8f, 8f, 0f, false, true, 3f, 11f); arcTo(8f, 8f, 0f, false, true, 19f, 11f); close()
            }
        }.build()
        return _LucideSearch!!
    }
private var _LucideSearch: ImageVector? = null

// =============================================================================
// ChevronDown
// =============================================================================

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

// =============================================================================
// Info
// =============================================================================

val LucideInfo: ImageVector
    get() {
        if (_LucideInfo != null) return _LucideInfo!!
        _LucideInfo = ImageVector.Builder(
            name = "info", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(22f, 12f); arcTo(10f, 10f, 0f, false, true, 12f, 22f); arcTo(10f, 10f, 0f, false, true, 2f, 12f); arcTo(10f, 10f, 0f, false, true, 22f, 12f); close()
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(12f, 16f); verticalLineToRelative(-4f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(12f, 8f); horizontalLineToRelative(0.01f)
            }
        }.build()
        return _LucideInfo!!
    }
private var _LucideInfo: ImageVector? = null

// =============================================================================
// TriangleAlert (AlertTriangle)
// =============================================================================

val LucideTriangleAlert: ImageVector
    get() {
        if (_LucideTriangleAlert != null) return _LucideTriangleAlert!!
        _LucideTriangleAlert = ImageVector.Builder(
            name = "triangle-alert", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(21.73f, 18f); lineToRelative(-8f, -14f); arcToRelative(2f, 2f, 0f, false, false, -3.48f, 0f); lineToRelative(-8f, 14f); arcTo(2f, 2f, 0f, false, false, 4f, 21f); horizontalLineToRelative(16f); arcToRelative(2f, 2f, 0f, false, false, 1.73f, -3f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(12f, 9f); verticalLineToRelative(4f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(12f, 17f); horizontalLineToRelative(0.01f)
            }
        }.build()
        return _LucideTriangleAlert!!
    }
private var _LucideTriangleAlert: ImageVector? = null

// =============================================================================
// CircleCheck (CheckCircle)
// =============================================================================

val LucideCircleCheck: ImageVector
    get() {
        if (_LucideCircleCheck != null) return _LucideCircleCheck!!
        _LucideCircleCheck = ImageVector.Builder(
            name = "circle-check", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(22f, 12f); arcTo(10f, 10f, 0f, false, true, 12f, 22f); arcTo(10f, 10f, 0f, false, true, 2f, 12f); arcTo(10f, 10f, 0f, false, true, 22f, 12f); close()
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(9f, 12f); lineToRelative(2f, 2f); lineToRelative(4f, -4f)
            }
        }.build()
        return _LucideCircleCheck!!
    }
private var _LucideCircleCheck: ImageVector? = null

// =============================================================================
// CircleX (XCircle)
// =============================================================================

val LucideCircleX: ImageVector
    get() {
        if (_LucideCircleX != null) return _LucideCircleX!!
        _LucideCircleX = ImageVector.Builder(
            name = "circle-x", defaultWidth = 24.dp, defaultHeight = 24.dp,
            viewportWidth = 24f, viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(22f, 12f); arcTo(10f, 10f, 0f, false, true, 12f, 22f); arcTo(10f, 10f, 0f, false, true, 2f, 12f); arcTo(10f, 10f, 0f, false, true, 22f, 12f); close()
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(15f, 9f); lineToRelative(-6f, 6f)
            }
            path(fill = SolidColor(Color.Transparent), stroke = SolidColor(Color.Black), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round) {
                moveTo(9f, 9f); lineToRelative(6f, 6f)
            }
        }.build()
        return _LucideCircleX!!
    }
private var _LucideCircleX: ImageVector? = null
