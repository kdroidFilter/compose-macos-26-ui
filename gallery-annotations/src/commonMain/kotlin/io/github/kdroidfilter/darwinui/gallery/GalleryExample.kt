package io.github.kdroidfilter.darwinui.gallery

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class GalleryExample(
    val page: String,
    val title: String,
)
