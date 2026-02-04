package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.avatar.AvatarData
import io.github.kdroidfilter.darwinui.components.avatar.DarwinAvatar
import io.github.kdroidfilter.darwinui.components.avatar.DarwinAvatarGroup
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("Avatar", "Sizes")
@Composable
fun AvatarSizesExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        DarwinAvatar(name = "Alice Smith")
        DarwinAvatar(name = "Bob Jones", size = 48.dp)
        DarwinAvatar(name = "Carol White", size = 56.dp)
        DarwinAvatar(name = "Dan Brown")
    }
}

@GalleryExample("Avatar", "Avatar Group")
@Composable
fun AvatarGroupExample() {
    DarwinAvatarGroup(
        avatars = listOf(
            AvatarData(name = "Alice Smith"),
            AvatarData(name = "Bob Jones"),
            AvatarData(name = "Carol White"),
            AvatarData(name = "Dan Brown"),
            AvatarData(name = "Eve Taylor"),
            AvatarData(name = "Frank Lee"),
        ),
        maxDisplay = 4,
    )
}

@Composable
internal fun AvatarPage() {
    GalleryPage("Avatar", "An image element with a fallback for representing the user.") {
        SectionHeader("Examples")
        ExampleCard(title = "Sizes", sourceCode = GallerySources.AvatarSizesExample) { AvatarSizesExample() }
        ExampleCard(title = "Avatar Group", sourceCode = GallerySources.AvatarGroupExample) { AvatarGroupExample() }
    }
}
