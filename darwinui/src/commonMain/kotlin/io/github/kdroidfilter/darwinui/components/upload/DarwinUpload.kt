package io.github.kdroidfilter.darwinui.components.upload

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.darwinui.icons.DarwinIcon
import io.github.kdroidfilter.darwinui.icons.LucideArrowLeftRight
import io.github.kdroidfilter.darwinui.icons.LucideImage
import io.github.kdroidfilter.darwinui.icons.LucideStar
import io.github.kdroidfilter.darwinui.icons.LucideStarOff
import io.github.kdroidfilter.darwinui.icons.LucideTrash2
import io.github.kdroidfilter.darwinui.icons.LucideUpload
import io.github.kdroidfilter.darwinui.icons.LucideX
import io.github.kdroidfilter.darwinui.theme.Amber500
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.Red500

/**
 * Upload variant matching the React darwin-ui Upload component.
 */
enum class DarwinUploadVariant {
    /** Large dropzone with icon, text, image grid, and upload button */
    Default,
    /** Smaller dropzone with compact grid */
    Compact,
    /** Inline single-row bar */
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
 * File upload area with multiple variants, image grid, cover selection,
 * reordering, progress tracking, and glass morphism support.
 *
 * Pixel-perfect match with the React darwin-ui Upload component.
 */
@Composable
fun DarwinUpload(
    files: List<DarwinUploadFile> = emptyList(),
    onPickFiles: () -> Unit,
    onRemoveFile: ((Int) -> Unit)? = null,
    onClearAll: (() -> Unit)? = null,
    onSetCover: ((Int) -> Unit)? = null,
    onSwap: ((Int, Int) -> Unit)? = null,
    maxFiles: Int = 6,
    label: String? = null,
    variant: DarwinUploadVariant = DarwinUploadVariant.Default,
    enabled: Boolean = true,
    glass: Boolean = false,
    modifier: Modifier = Modifier,
) {
    when (variant) {
        DarwinUploadVariant.Default -> DefaultVariant(
            files = files,
            onPickFiles = onPickFiles,
            onRemoveFile = onRemoveFile,
            onClearAll = onClearAll,
            onSetCover = onSetCover,
            onSwap = onSwap,
            maxFiles = maxFiles,
            label = label,
            enabled = enabled,
            glass = glass,
            modifier = modifier,
        )
        DarwinUploadVariant.Compact -> CompactVariant(
            files = files,
            onPickFiles = onPickFiles,
            onRemoveFile = onRemoveFile,
            onClearAll = onClearAll,
            maxFiles = maxFiles,
            label = label,
            enabled = enabled,
            modifier = modifier,
        )
        DarwinUploadVariant.Inline -> InlineVariant(
            files = files,
            onPickFiles = onPickFiles,
            onClearAll = onClearAll,
            label = label,
            enabled = enabled,
            modifier = modifier,
        )
    }
}

// =============================================================================
// Color helpers
// =============================================================================

/** border-black/20 dark:border-white/20 */
@Composable
private fun dashedBorderColor(): Color {
    return if (DarwinTheme.colors.isDark) Color.White.copy(alpha = 0.20f)
    else Color.Black.copy(alpha = 0.20f)
}

/** bg-black/5 dark:bg-white/5 */
@Composable
private fun subtleBg(): Color {
    return if (DarwinTheme.colors.isDark) Color.White.copy(alpha = 0.05f)
    else Color.Black.copy(alpha = 0.05f)
}

/** border-black/10 dark:border-white/10 */
@Composable
private fun subtleBorder(): Color {
    return if (DarwinTheme.colors.isDark) Color.White.copy(alpha = 0.10f)
    else Color.Black.copy(alpha = 0.10f)
}

/** bg-black/10 dark:bg-white/10 */
@Composable
private fun actionBg(): Color {
    return if (DarwinTheme.colors.isDark) Color.White.copy(alpha = 0.10f)
    else Color.Black.copy(alpha = 0.10f)
}

private val RedBg15 = Red500.copy(alpha = 0.15f)
private val RedBorder20 = Red500.copy(alpha = 0.20f)
private val RedBg10 = Red500.copy(alpha = 0.10f)

// =============================================================================
// DEFAULT VARIANT
// =============================================================================

@Composable
private fun DefaultVariant(
    files: List<DarwinUploadFile>,
    onPickFiles: () -> Unit,
    onRemoveFile: ((Int) -> Unit)?,
    onClearAll: (() -> Unit)?,
    onSetCover: ((Int) -> Unit)?,
    onSwap: ((Int, Int) -> Unit)?,
    maxFiles: Int,
    label: String?,
    enabled: Boolean,
    glass: Boolean,
    modifier: Modifier,
) {
    val colors = DarwinTheme.colors
    val borderColor = if (glass) {
        if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.White.copy(alpha = 0.20f)
    } else {
        dashedBorderColor()
    }
    val bgColor = if (glass) {
        if (colors.isDark) Color(0xFF18181B).copy(alpha = 0.60f) else Color.White.copy(alpha = 0.60f)
    } else {
        Color.Transparent
    }

    Column(
        modifier = modifier.animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // Main container: rounded-xl, dashed border, min-h-52 (208dp), p-4
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 208.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(bgColor)
                .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                .padding(16.dp),
        ) {
            if (files.isNotEmpty()) {
                // === WITH FILES ===
                DefaultWithFiles(
                    files = files,
                    onPickFiles = onPickFiles,
                    onRemoveFile = onRemoveFile,
                    onClearAll = onClearAll,
                    onSetCover = onSetCover,
                    onSwap = onSwap,
                    enabled = enabled,
                )
            } else {
                // === EMPTY STATE ===
                DefaultEmptyState(
                    label = label,
                    maxFiles = maxFiles,
                )
            }

            // Upload button (always at bottom): full-width dashed border, rounded-xl, min-h-12.5 (50dp)
            Spacer(Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, dashedBorderColor(), RoundedCornerShape(12.dp))
                    .clickable(enabled = enabled, onClick = onPickFiles),
                contentAlignment = Alignment.Center,
            ) {
                DarwinText(
                    text = "Click to select images",
                    style = DarwinTheme.typography.bodySmall,
                    color = colors.textSecondary,
                )
            }
        }
    }
}

@Composable
private fun DefaultEmptyState(
    label: String?,
    maxFiles: Int,
) {
    val colors = DarwinTheme.colors

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Circle icon: size-11 (44dp) rounded-full, border-border bg-muted, containing LucideImage 16dp
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(subtleBg())
                .border(1.dp, dashedBorderColor(), CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            DarwinIcon(
                imageVector = LucideImage,
                tint = colors.textSecondary,
                size = 16.dp,
            )
        }

        Spacer(Modifier.height(8.dp))

        // Title: "Drop your images here"
        DarwinText(
            text = "Drop your images here",
            style = DarwinTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = if (colors.isDark) Color(0xFFF4F4F5) else Color(0xFF18181B),
        )

        Spacer(Modifier.height(6.dp))

        // Label text (if provided)
        if (label != null) {
            DarwinText(
                text = label,
                style = DarwinTheme.typography.labelSmall,
                color = colors.textSecondary,
            )
            Spacer(Modifier.height(4.dp))
        }

        // "Max N files"
        DarwinText(
            text = "Max $maxFiles files",
            style = DarwinTheme.typography.labelSmall,
            color = colors.textSecondary,
        )
    }
}

@Composable
private fun DefaultWithFiles(
    files: List<DarwinUploadFile>,
    onPickFiles: () -> Unit,
    onRemoveFile: ((Int) -> Unit)?,
    onClearAll: (() -> Unit)?,
    onSetCover: ((Int) -> Unit)?,
    onSwap: ((Int, Int) -> Unit)?,
    enabled: Boolean,
) {
    val colors = DarwinTheme.colors

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Header row: "Images (N)" + Add button + Remove all button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DarwinText(
                text = "Images (${files.size})",
                style = DarwinTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = colors.textSecondary,
                modifier = Modifier.weight(1f),
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // Add button
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(subtleBg())
                        .border(1.dp, subtleBorder(), RoundedCornerShape(6.dp))
                        .clickable(enabled = enabled, onClick = onPickFiles)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    DarwinIcon(
                        imageVector = LucideUpload,
                        tint = colors.textSecondary,
                        size = 14.dp,
                    )
                    DarwinText(
                        text = "Add",
                        fontSize = 12.sp,
                        color = colors.textSecondary,
                    )
                }

                // Remove all button
                if (onClearAll != null) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(RedBg15)
                            .border(1.dp, RedBorder20, RoundedCornerShape(6.dp))
                            .clickable(enabled = enabled, onClick = onClearAll)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        DarwinIcon(
                            imageVector = LucideTrash2,
                            tint = Red500,
                            size = 14.dp,
                        )
                        DarwinText(
                            text = "Remove all",
                            fontSize = 12.sp,
                            color = Red500,
                        )
                    }
                }
            }
        }

        // Grid: 2-col layout, gap-4
        val chunked = files.chunked(2)
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            for (row in chunked) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    for ((localIndex, file) in row.withIndex()) {
                        val globalIndex = chunked.indexOf(row) * 2 + localIndex
                        Box(modifier = Modifier.weight(1f)) {
                            DefaultImageCard(
                                file = file,
                                index = globalIndex,
                                totalFiles = files.size,
                                onRemove = if (onRemoveFile != null) {
                                    { onRemoveFile(globalIndex) }
                                } else null,
                                onSetCover = if (onSetCover != null) {
                                    { onSetCover(globalIndex) }
                                } else null,
                                onSwap = if (onSwap != null && globalIndex < files.size - 1) {
                                    { onSwap(globalIndex, globalIndex + 1) }
                                } else null,
                            )
                        }
                    }
                    // Fill remaining space if odd number
                    if (row.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun DefaultImageCard(
    file: DarwinUploadFile,
    index: Int,
    totalFiles: Int,
    onRemove: (() -> Unit)?,
    onSetCover: (() -> Unit)?,
    onSwap: (() -> Unit)?,
) {
    val colors = DarwinTheme.colors
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val isCover = index == 0
    val cardLabel = if (isCover) "Cover" else "Image ${index + 1}"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(subtleBg())
            .border(1.dp, subtleBorder(), RoundedCornerShape(6.dp))
            .hoverable(interactionSource),
    ) {
        // Image area: placeholder box max-h-20 (80dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(if (colors.isDark) Color(0xFF27272A) else Color(0xFFE4E4E7)),
            contentAlignment = Alignment.Center,
        ) {
            if (file.isUploading) {
                // Uploading state with progress
                val animatedProgress by animateFloatAsState(
                    targetValue = file.progress,
                    animationSpec = tween(300),
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    DarwinIcon(
                        imageVector = LucideImage,
                        tint = colors.textTertiary,
                        size = 20.dp,
                    )
                    Spacer(Modifier.height(4.dp))
                    // Progress bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (colors.isDark) Color(0xFF3F3F46) else Color(0xFFD4D4D8)),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(fraction = animatedProgress)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(3.dp))
                                .background(colors.accent),
                        )
                    }
                    Spacer(Modifier.height(2.dp))
                    DarwinText(
                        text = "Uploading... ${(animatedProgress * 100).toInt()}%",
                        fontSize = 11.sp,
                        color = colors.textSecondary,
                    )
                }
            } else {
                // Placeholder with image icon
                DarwinIcon(
                    imageVector = LucideImage,
                    tint = colors.textTertiary,
                    size = 24.dp,
                )
            }

            // Remove button: size-6 red circle, top-right
            if (onRemove != null && isHovered && !file.isUploading) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Red500)
                        .clickable(onClick = onRemove),
                    contentAlignment = Alignment.Center,
                ) {
                    DarwinIcon(
                        imageVector = LucideX,
                        tint = Color.White,
                        size = 14.dp,
                    )
                }
            }
        }

        // Footer: border-t, label ("Cover" or "Image N"), star button, swap button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = subtleBorder(), shape = RoundedCornerShape(0.dp))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DarwinText(
                text = cardLabel,
                fontSize = 12.sp,
                color = colors.textSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                // Star button
                if (onSetCover != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(actionBg())
                            .clickable(onClick = onSetCover)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (isCover) {
                            DarwinIcon(
                                imageVector = LucideStar,
                                tint = Amber500,
                                size = 14.dp,
                            )
                        } else {
                            DarwinIcon(
                                imageVector = LucideStarOff,
                                tint = if (colors.isDark) Color(0xFFF4F4F5) else Color(0xFF18181B),
                                size = 14.dp,
                            )
                        }
                    }
                }

                // Swap button
                if (onSwap != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(actionBg())
                            .clickable(onClick = onSwap)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        DarwinIcon(
                            imageVector = LucideArrowLeftRight,
                            tint = if (colors.isDark) Color(0xFFF4F4F5) else Color(0xFF18181B),
                            size = 14.dp,
                        )
                    }
                }
            }
        }
    }
}

// =============================================================================
// COMPACT VARIANT
// =============================================================================

@Composable
private fun CompactVariant(
    files: List<DarwinUploadFile>,
    onPickFiles: () -> Unit,
    onRemoveFile: ((Int) -> Unit)?,
    onClearAll: (() -> Unit)?,
    maxFiles: Int,
    label: String?,
    enabled: Boolean,
    modifier: Modifier,
) {
    val colors = DarwinTheme.colors

    Column(
        modifier = modifier.animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // Container: rounded-xl dashed border, p-3
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, colors.border, RoundedCornerShape(12.dp))
                .padding(12.dp),
        ) {
            if (files.isNotEmpty()) {
                // === WITH FILES ===
                CompactWithFiles(
                    files = files,
                    onPickFiles = onPickFiles,
                    onRemoveFile = onRemoveFile,
                    onClearAll = onClearAll,
                    enabled = enabled,
                )
            } else {
                // === EMPTY STATE ===
                CompactEmptyState(
                    onClick = onPickFiles,
                    enabled = enabled,
                    label = label,
                    maxFiles = maxFiles,
                )
            }
        }
    }
}

@Composable
private fun CompactEmptyState(
    onClick: () -> Unit,
    enabled: Boolean,
    label: String?,
    maxFiles: Int,
) {
    val colors = DarwinTheme.colors

    // Row with circle icon (size-8/32dp) + "Upload images" title + max files label
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, onClick = onClick)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Circle icon
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(colors.muted)
                .border(1.dp, colors.border, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            DarwinIcon(
                imageVector = LucideImage,
                tint = colors.mutedForeground,
                size = 14.dp,
            )
        }

        Column {
            DarwinText(
                text = "Upload images",
                style = DarwinTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = if (colors.isDark) Color(0xFFF4F4F5) else Color(0xFF18181B),
            )
            DarwinText(
                text = label ?: "Max $maxFiles files",
                style = DarwinTheme.typography.labelSmall,
                color = colors.mutedForeground,
            )
        }
    }
}

@Composable
private fun CompactWithFiles(
    files: List<DarwinUploadFile>,
    onPickFiles: () -> Unit,
    onRemoveFile: ((Int) -> Unit)?,
    onClearAll: (() -> Unit)?,
    enabled: Boolean,
) {
    val colors = DarwinTheme.colors

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // Header: count label + "Add" chip + trash chip
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DarwinText(
                text = "${files.size} image${if (files.size != 1) "s" else ""}",
                style = DarwinTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                color = colors.mutedForeground,
            )

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                // Add chip
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(colors.muted)
                        .clickable(enabled = enabled, onClick = onPickFiles)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    DarwinIcon(
                        imageVector = LucideUpload,
                        tint = colors.mutedForeground,
                        size = 12.dp,
                    )
                    DarwinText(
                        text = "Add",
                        fontSize = 12.sp,
                        color = colors.mutedForeground,
                    )
                }

                // Trash chip
                if (onClearAll != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(RedBg10)
                            .clickable(enabled = enabled, onClick = onClearAll)
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        DarwinIcon(
                            imageVector = LucideTrash2,
                            tint = Red500,
                            size = 12.dp,
                        )
                    }
                }
            }
        }

        // Grid: 2-col, thumbnails h-16 (64dp)
        val chunked = files.chunked(2)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            for (row in chunked) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    for ((localIndex, file) in row.withIndex()) {
                        val globalIndex = chunked.indexOf(row) * 2 + localIndex
                        Box(modifier = Modifier.weight(1f)) {
                            CompactImageCard(
                                file = file,
                                index = globalIndex,
                                onRemove = if (onRemoveFile != null) {
                                    { onRemoveFile(globalIndex) }
                                } else null,
                            )
                        }
                    }
                    if (row.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun CompactImageCard(
    file: DarwinUploadFile,
    index: Int,
    onRemove: (() -> Unit)?,
) {
    val colors = DarwinTheme.colors
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(if (colors.isDark) Color(0xFF27272A) else Color(0xFFE4E4E7))
            .hoverable(interactionSource),
    ) {
        // Placeholder
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            if (file.isUploading) {
                val animatedProgress by animateFloatAsState(
                    targetValue = file.progress,
                    animationSpec = tween(300),
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    DarwinIcon(LucideImage, tint = colors.textTertiary, size = 16.dp)
                    Spacer(Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(if (colors.isDark) Color(0xFF3F3F46) else Color(0xFFD4D4D8)),
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
            } else {
                DarwinIcon(LucideImage, tint = colors.textTertiary, size = 20.dp)
            }
        }

        // Remove: red circle, top-right, hover-only
        if (onRemove != null && isHovered && !file.isUploading) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(Red500)
                    .clickable(onClick = onRemove),
                contentAlignment = Alignment.Center,
            ) {
                DarwinIcon(LucideX, tint = Color.White, size = 12.dp)
            }
        }

        // Cover badge: amber-500 bg, "Cover" text, bottom-left of first image
        if (index == 0 && !file.isUploading) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Amber500)
                    .padding(horizontal = 6.dp, vertical = 2.dp),
            ) {
                DarwinText(
                    text = "Cover",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                )
            }
        }
    }
}

// =============================================================================
// INLINE VARIANT
// =============================================================================

@Composable
private fun InlineVariant(
    files: List<DarwinUploadFile>,
    onPickFiles: () -> Unit,
    onClearAll: (() -> Unit)?,
    label: String?,
    enabled: Boolean,
    modifier: Modifier,
) {
    val colors = DarwinTheme.colors
    val hasImages = files.isNotEmpty()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        // Container: h-10 (40dp) px-3 rounded-xl border-border bg-muted, horizontal row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(colors.muted)
                .border(1.dp, colors.border, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (hasImages) {
                // Thumbnails: h-7 w-7 (28dp) squares, gap-1.5, horizontal scroll
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    for (file in files) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (colors.isDark) Color(0xFF27272A) else Color(0xFFE4E4E7)),
                            contentAlignment = Alignment.Center,
                        ) {
                            DarwinIcon(LucideImage, tint = colors.textTertiary, size = 14.dp)
                        }
                    }
                }
            } else {
                // Empty: label text as placeholder
                DarwinText(
                    text = label ?: "No images",
                    style = DarwinTheme.typography.bodySmall,
                    color = colors.mutedForeground,
                    modifier = Modifier.weight(1f),
                )
            }

            // Add button: h-6 w-6 (24dp) rounded bg-background border-border + LucideUpload
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(colors.background)
                    .border(1.dp, colors.border, RoundedCornerShape(4.dp))
                    .clickable(enabled = enabled, onClick = onPickFiles),
                contentAlignment = Alignment.Center,
            ) {
                DarwinIcon(
                    imageVector = LucideUpload,
                    tint = colors.mutedForeground,
                    size = 14.dp,
                )
            }

            // Clear all button (if has images): h-6 w-6 bg-red-500/10 border-red-500/20 + LucideTrash2
            if (hasImages && onClearAll != null) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(RedBg10)
                        .border(1.dp, RedBorder20, RoundedCornerShape(4.dp))
                        .clickable(enabled = enabled, onClick = onClearAll),
                    contentAlignment = Alignment.Center,
                ) {
                    DarwinIcon(
                        imageVector = LucideTrash2,
                        tint = Red500,
                        size = 14.dp,
                    )
                }
            }
        }
    }
}
