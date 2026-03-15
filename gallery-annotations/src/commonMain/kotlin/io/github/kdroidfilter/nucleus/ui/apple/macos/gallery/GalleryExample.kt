package io.github.kdroidfilter.nucleus.ui.apple.macos.gallery

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class GalleryExample(
    val page: String,
    val title: String,
)
