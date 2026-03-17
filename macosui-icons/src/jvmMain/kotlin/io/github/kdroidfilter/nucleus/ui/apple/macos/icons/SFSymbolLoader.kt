package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image
import java.util.concurrent.ConcurrentHashMap

internal object SFSymbolLoader {
    private val cache = ConcurrentHashMap<String, ImageBitmap>()
    private val missed = ConcurrentHashMap.newKeySet<String>()

    fun get(name: String): ImageBitmap? {
        if (name in missed) return null
        cache[name]?.let { return it }
        val bitmap = loadSymbol(name)
        if (bitmap != null) {
            cache[name] = bitmap
        } else {
            missed.add(name)
        }
        return bitmap
    }

    private fun loadSymbol(name: String): ImageBitmap? {
        if (!SFSymbolBridge.isLoaded) return null
        val pngBytes = SFSymbolBridge.nativeLoadSymbol(name) ?: return null
        return try {
            val skImage = Image.makeFromEncoded(pngBytes)
            val bitmap = Bitmap()
            bitmap.allocPixels(skImage.imageInfo)
            skImage.readPixels(bitmap, 0, 0)
            bitmap.asComposeImageBitmap()
        } catch (_: Exception) {
            null
        }
    }
}
