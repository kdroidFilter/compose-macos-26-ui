package io.github.kdroidfilter.darwinui.icons

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image
import platform.UIKit.UIImage
import platform.UIKit.UIImageSymbolConfiguration
import platform.Foundation.NSData
import platform.posix.memcpy

private val cache = mutableMapOf<String, ImageBitmap>()
private val missed = mutableSetOf<String>()

@OptIn(ExperimentalForeignApi::class)
actual fun loadPlatformSymbol(name: String): ImageBitmap? {
    if (name.isEmpty() || name in missed) return null
    cache[name]?.let { return it }

    val config = UIImageSymbolConfiguration.configurationWithPointSize(96.0)
    val uiImage = UIImage.systemImageNamed(name, withConfiguration = config)
    if (uiImage == null) {
        missed.add(name)
        return null
    }

    val pngData = platform.UIKit.UIImagePNGRepresentation(uiImage)
    if (pngData == null || pngData.length.toInt() == 0) {
        missed.add(name)
        return null
    }

    val bytes = pngData.toByteArray()
    val skImage = Image.makeFromEncoded(bytes)
    val bitmap = Bitmap()
    bitmap.allocPixels(skImage.imageInfo)
    skImage.readPixels(bitmap, 0, 0)
    val composeBitmap = bitmap.asComposeImageBitmap()
    cache[name] = composeBitmap
    return composeBitmap
}

@OptIn(ExperimentalForeignApi::class)
private fun NSData.toByteArray(): ByteArray {
    val size = length.toInt()
    val bytes = ByteArray(size)
    bytes.usePinned { pinned ->
        memcpy(pinned.addressOf(0), this.bytes, length)
    }
    return bytes
}
