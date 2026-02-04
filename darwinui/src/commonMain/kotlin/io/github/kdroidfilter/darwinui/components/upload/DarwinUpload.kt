package io.github.kdroidfilter.darwinui.components.upload

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

/**
 * Upload variant matching the React darwin-ui Upload component.
 */
enum class DarwinUploadVariant {
    /** Large dropzone with icon and text */
    Default,
    /** Smaller dropzone */
    Compact,
    /** Inline button-style upload trigger */
    Inline,
}

/**
 * Represents a file being uploaded or already uploaded.
 */
data class DarwinUploadFile(
    val name: String,
    val url: String? = null,
    val progress: Float = 0f, // 0..1
    val isUploading: Boolean = false,
    val error: String? = null,
)

/**
 * Darwin UI Upload component.
 * File upload area with multiple variants and progress tracking.
 *
 * Since Compose Multiplatform doesn't have native file picker APIs in common code,
 * this component provides the UI and delegates the actual file picking to the consumer
 * via the onPickFiles callback.
 */
@Composable
fun DarwinUpload(
    files: List<DarwinUploadFile> = emptyList(),
    onPickFiles: () -> Unit,
    onRemoveFile: ((Int) -> Unit)? = null,
    maxFiles: Int = 6,
    variant: DarwinUploadVariant = DarwinUploadVariant.Default,
    enabled: Boolean = true,
    glass: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes
    val typography = DarwinTheme.typography

    val canAddMore = files.size < maxFiles

    Column(
        modifier = modifier.animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        when (variant) {
            DarwinUploadVariant.Default -> {
                DefaultUploadZone(
                    onClick = if (canAddMore && enabled) onPickFiles else ({}),
                    enabled = canAddMore && enabled,
                    glass = glass,
                    colors = colors,
                    shapes = shapes,
                )
            }

            DarwinUploadVariant.Compact -> {
                CompactUploadZone(
                    onClick = if (canAddMore && enabled) onPickFiles else ({}),
                    enabled = canAddMore && enabled,
                    glass = glass,
                    colors = colors,
                    shapes = shapes,
                )
            }

            DarwinUploadVariant.Inline -> {
                InlineUploadTrigger(
                    onClick = if (canAddMore && enabled) onPickFiles else ({}),
                    enabled = canAddMore && enabled,
                    colors = colors,
                    shapes = shapes,
                    fileCount = files.size,
                    maxFiles = maxFiles,
                )
            }
        }

        // File list
        if (files.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                files.forEachIndexed { index, file ->
                    UploadFileItem(
                        file = file,
                        onRemove = if (onRemoveFile != null) {
                            { onRemoveFile(index) }
                        } else null,
                        colors = colors,
                        shapes = shapes,
                    )
                }
            }
        }
    }
}

@Composable
private fun DefaultUploadZone(
    onClick: () -> Unit,
    enabled: Boolean,
    glass: Boolean,
    colors: io.github.kdroidfilter.darwinui.theme.DarwinColors,
    shapes: io.github.kdroidfilter.darwinui.theme.DarwinShapes,
) {
    val borderColor = if (enabled) colors.border else colors.border.copy(alpha = 0.5f)
    val bgColor = if (glass) colors.glassBackground else colors.surface

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(shapes.large)
            .background(bgColor)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = shapes.large,
            )
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // Upload icon (arrow up)
            DarwinText(
                text = "↑",
                fontSize = 32.sp,
                color = if (enabled) colors.textSecondary else colors.textQuaternary,
            )
            DarwinText(
                text = "Click to upload",
                style = DarwinTheme.typography.bodyMedium,
                color = if (enabled) colors.textPrimary else colors.textTertiary,
                fontWeight = FontWeight.Medium,
            )
            DarwinText(
                text = "or drag and drop",
                style = DarwinTheme.typography.bodySmall,
                color = colors.textTertiary,
            )
        }
    }
}

@Composable
private fun CompactUploadZone(
    onClick: () -> Unit,
    enabled: Boolean,
    glass: Boolean,
    colors: io.github.kdroidfilter.darwinui.theme.DarwinColors,
    shapes: io.github.kdroidfilter.darwinui.theme.DarwinShapes,
) {
    val borderColor = if (enabled) colors.border else colors.border.copy(alpha = 0.5f)
    val bgColor = if (glass) colors.glassBackground else colors.surface

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(shapes.large)
            .background(bgColor)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = shapes.large,
            )
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DarwinText(
                text = "↑",
                fontSize = 20.sp,
                color = if (enabled) colors.textSecondary else colors.textQuaternary,
            )
            DarwinText(
                text = "Upload files",
                style = DarwinTheme.typography.bodyMedium,
                color = if (enabled) colors.textPrimary else colors.textTertiary,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun InlineUploadTrigger(
    onClick: () -> Unit,
    enabled: Boolean,
    colors: io.github.kdroidfilter.darwinui.theme.DarwinColors,
    shapes: io.github.kdroidfilter.darwinui.theme.DarwinShapes,
    fileCount: Int,
    maxFiles: Int,
) {
    Row(
        modifier = Modifier
            .clip(shapes.medium)
            .background(colors.secondary)
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DarwinText(
            text = "↑",
            fontSize = 14.sp,
            color = if (enabled) colors.textPrimary else colors.textTertiary,
        )
        DarwinText(
            text = "Upload",
            style = DarwinTheme.typography.labelMedium,
            color = if (enabled) colors.textPrimary else colors.textTertiary,
        )
        if (fileCount > 0) {
            DarwinText(
                text = "($fileCount/$maxFiles)",
                style = DarwinTheme.typography.labelSmall,
                color = colors.textTertiary,
            )
        }
    }
}

@Composable
private fun UploadFileItem(
    file: DarwinUploadFile,
    onRemove: (() -> Unit)?,
    colors: io.github.kdroidfilter.darwinui.theme.DarwinColors,
    shapes: io.github.kdroidfilter.darwinui.theme.DarwinShapes,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = file.progress,
        animationSpec = tween(300),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapes.medium)
            .background(colors.surface)
            .border(1.dp, colors.borderSubtle, shapes.medium)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // File icon
        DarwinText(
            text = "📄",
            fontSize = 16.sp,
        )

        // File name + progress
        Column(
            modifier = Modifier.weight(1f),
        ) {
            DarwinText(
                text = file.name,
                style = DarwinTheme.typography.bodySmall,
                color = colors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            if (file.isUploading) {
                Spacer(modifier = Modifier.height(4.dp))
                // Progress bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(colors.border),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction = animatedProgress)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(2.dp))
                            .background(colors.accent),
                    )
                }
            }

            if (file.error != null) {
                DarwinText(
                    text = file.error,
                    style = DarwinTheme.typography.caption,
                    color = colors.destructive,
                )
            }
        }

        // Status / Remove
        if (file.isUploading) {
            DarwinText(
                text = "${(animatedProgress * 100).toInt()}%",
                style = DarwinTheme.typography.labelSmall,
                color = colors.textTertiary,
            )
        } else if (file.url != null) {
            DarwinText(
                text = "✓",
                fontSize = 16.sp,
                color = colors.success,
            )
        }

        if (onRemove != null && !file.isUploading) {
            DarwinText(
                text = "✕",
                fontSize = 14.sp,
                color = colors.textTertiary,
                modifier = Modifier
                    .clickable(onClick = onRemove)
                    .padding(4.dp),
            )
        }
    }
}
