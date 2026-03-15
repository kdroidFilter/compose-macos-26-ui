package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.AccentColor
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import kotlin.math.absoluteValue

// ===========================================================================
// Color hashing
// ===========================================================================

/**
 * Avatar color palette used for deterministic background assignment.
 * Uses Apple macOS 26 system accent colors.
 */
private val AvatarColors = listOf(
    AccentColor.Blue.light, AccentColor.Green.light, AccentColor.Purple.light, AccentColor.Orange.light,
    AccentColor.Red.light, AccentColor.Cyan.light, AccentColor.Pink.light, AccentColor.Teal.light,
)

/**
 * Returns a deterministic color from [AvatarColors] based on the hash of [name].
 */
private fun hashColor(name: String): Color {
    val hash = name.hashCode().absoluteValue
    return AvatarColors[hash % AvatarColors.size]
}

// ===========================================================================
// Initials extraction
// ===========================================================================

/**
 * Extracts up to two uppercase initials from [name].
 *
 * - If the name contains multiple words, returns the first letter of the first
 *   and last word (e.g. "John Doe" -> "JD").
 * - If the name is a single word, returns its first letter (e.g. "Alice" -> "A").
 * - Returns an empty string if [name] is blank.
 */
private fun extractInitials(name: String): String {
    val parts = name.trim().split("\\s+".toRegex())
    return when {
        parts.isEmpty() || parts.first().isEmpty() -> ""
        parts.size == 1 -> parts.first().first().uppercase()
        else -> "${parts.first().first()}${parts.last().first()}".uppercase()
    }
}

// ===========================================================================
// Avatar
// ===========================================================================

/**
 * A circular user avatar following the macOS UI design system.
 *
 * Displays the user's avatar. When [imageUrl] is provided the component is
 * ready for a platform image loader; the initials fallback derived from
 * [name] is shown otherwise. The background color is chosen by hashing the
 * name across 8 accent colors.
 *
 * Usage:
 * ```
 * Avatar(name = "John Doe", size = 48.dp)
 * ```
 *
 * @param name      Full name of the user, used to generate initials and the
 *                  deterministic background color.
 * @param modifier  Modifier applied to the root container.
 * @param imageUrl  URL of the user's avatar image. When non-null the
 *                  component exposes it via semantics content description
 *                  so image-loader integrations can pick it up.
 * @param size      Diameter of the avatar. Defaults to 40dp.
 * @param onClick   Optional click handler. When non-null the avatar becomes
 *                  clickable.
 */
@Composable
fun Avatar(
    name: String,
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    size: Dp = 40.dp,
    onClick: (() -> Unit)? = null,
) {
    val colors = MacosTheme.colorScheme
    val backgroundColor = hashColor(name)
    val borderColor = if (colors.isDark) Color(0xFF18181B) else Color.White
    val initials = extractInitials(name)
    val fontSize = (size.value * 0.4f).sp

    val description = imageUrl ?: name
    val avatarModifier = modifier
        .semantics { contentDescription = description }
        .size(size)
        .clip(CircleShape)
        .background(backgroundColor, CircleShape)
        .border(width = 2.dp, color = borderColor, shape = CircleShape)
        .then(
            if (onClick != null) {
                Modifier.clickable(role = Role.Button, onClick = onClick)
            } else {
                Modifier
            }
        )

    Box(
        modifier = avatarModifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initials,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize,
        )
    }
}

// ===========================================================================
// AvatarGroup
// ===========================================================================

/**
 * Data class describing a single avatar within a [AvatarGroup].
 *
 * @param name     Full name (used for initials and color).
 * @param imageUrl Optional image URL.
 * @param onClick  Optional click handler for this specific avatar.
 */
@Immutable
data class AvatarData(
    val name: String,
    val imageUrl: String? = null,
    val onClick: (() -> Unit)? = null,
)

/**
 * Displays a horizontal group of overlapping [Avatar] composables.
 *
 * If the number of [avatars] exceeds [maxDisplay], the extra avatars are
 * replaced by a "+N" overflow indicator rendered as the last circle.
 *
 * Usage:
 * ```
 * AvatarGroup(
 *     avatars = listOf(
 *         AvatarData(name = "Alice Smith"),
 *         AvatarData(name = "Bob Jones"),
 *         AvatarData(name = "Carol White"),
 *         AvatarData(name = "Dan Brown"),
 *     ),
 *     maxDisplay = 3,
 * )
 * ```
 *
 * @param avatars    List of [AvatarData] items to display.
 * @param modifier   Modifier applied to the row container.
 * @param maxDisplay Maximum number of avatar slots to show (including the
 *                   overflow indicator). Defaults to 4.
 * @param size       Diameter of each avatar and the overflow circle.
 */
@Composable
fun AvatarGroup(
    avatars: List<AvatarData>,
    modifier: Modifier = Modifier,
    maxDisplay: Int = 4,
    size: Dp = 40.dp,
) {
    val colors = MacosTheme.colorScheme
    val borderColor = if (colors.isDark) Color(0xFF18181B) else Color.White
    val overlapDp = 12.dp // how much each avatar overlaps the previous one

    val totalCount = avatars.size
    val visibleCount = if (totalCount > maxDisplay) maxDisplay - 1 else totalCount
    val overflowCount = totalCount - visibleCount

    Box(modifier = modifier) {
        // Render visible avatars with offset and z-index for stacking
        for (index in 0 until visibleCount) {
            val avatar = avatars[index]
            val xOffset = (size - overlapDp) * index

            Box(
                modifier = Modifier
                    .offset(x = xOffset)
                    .zIndex((totalCount - index).toFloat()),
            ) {
                Avatar(
                    name = avatar.name,
                    imageUrl = avatar.imageUrl,
                    size = size,
                    onClick = avatar.onClick,
                )
            }
        }

        // Overflow indicator
        if (overflowCount > 0) {
            val xOffset = (size - overlapDp) * visibleCount
            val fontSize = (size.value * 0.35f).sp

            Box(
                modifier = Modifier
                    .offset(x = xOffset)
                    .zIndex(0f)
                    .size(size)
                    .clip(CircleShape)
                    .background(Color(0xFF27272A), CircleShape)
                    .border(width = 2.dp, color = borderColor, shape = CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "+$overflowCount",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize,
                )
            }
        }
    }
}

@Preview
@Composable
private fun AvatarPreview() {
    MacosTheme {
        Avatar(name = "John Doe")
    }
}
