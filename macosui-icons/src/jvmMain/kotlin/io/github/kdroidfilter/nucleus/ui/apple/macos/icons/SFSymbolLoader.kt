package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.NativeLibrary
import com.sun.jna.Pointer
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image
import java.util.concurrent.ConcurrentHashMap

internal object SFSymbolLoader {
    private val cache = ConcurrentHashMap<String, ImageBitmap>()
    private val missed = ConcurrentHashMap.newKeySet<String>()

    fun get(name: String): ImageBitmap? {
        if (name in missed) return null
        cache[name]?.let { return it }
        val bitmap = bridge?.loadSymbol(name)
        if (bitmap != null) {
            cache[name] = bitmap
        } else {
            missed.add(name)
        }
        return bitmap
    }

    private val bridge: ObjCBridge? by lazy {
        try {
            ObjCBridge()
        } catch (_: Exception) {
            null
        }
    }
}

/**
 * Typed JNA interface for objc_msgSend variants.
 * Using a typed interface avoids arm64 variadic calling convention issues
 * where double arguments get passed in the wrong registers.
 */
private interface ObjCMsgSend : Library {
    // Standard pointer-returning objc_msgSend
    fun objc_msgSend(obj: Pointer, sel: Pointer, vararg args: Any?): Pointer?

    companion object {
        val INSTANCE: ObjCMsgSend = Native.load("objc", ObjCMsgSend::class.java)
    }
}

/**
 * Typed interface specifically for objc_msgSend calls that take double arguments.
 * On arm64, non-variadic doubles go in FP registers (correct behavior).
 */
private interface ObjCMsgSendDouble2 : Library {
    @Suppress("FunctionName")
    fun objc_msgSend(obj: Pointer, sel: Pointer, d1: Double, d2: Double): Pointer?

    companion object {
        val INSTANCE: ObjCMsgSendDouble2 = Native.load("objc", ObjCMsgSendDouble2::class.java)
    }
}

private class ObjCBridge {
    init {
        // AppKit must be loaded before NSImage can resolve SF Symbols
        NativeLibrary.getInstance("AppKit")
    }

    private val lib = NativeLibrary.getInstance("objc")
    private val fnGetClass = lib.getFunction("objc_getClass")
    private val fnSelRegister = lib.getFunction("sel_registerName")
    private val fnMsgSend = lib.getFunction("objc_msgSend")

    private fun cls(name: String): Pointer = fnGetClass.invokePointer(arrayOf(name))
    private fun sel(name: String): Pointer = fnSelRegister.invokePointer(arrayOf(name))

    private fun msg(obj: Pointer, sel: Pointer, vararg args: Any?): Pointer? =
        fnMsgSend.invokePointer(arrayOf(obj, sel, *args))

    private fun msgLong(obj: Pointer, sel: Pointer, vararg args: Any?): Long =
        fnMsgSend.invokeLong(arrayOf(obj, sel, *args))

    // Cached class lookups
    private val clsNSString = cls("NSString")
    private val clsNSImage = cls("NSImage")
    private val clsNSImageSymbolConfig = cls("NSImageSymbolConfiguration")
    private val clsNSBitmapImageRep = cls("NSBitmapImageRep")
    private val clsNSDictionary = cls("NSDictionary")
    private val clsNSAutoreleasePool = cls("NSAutoreleasePool")

    // Cached selectors
    private val selNew = sel("new")
    private val selDrain = sel("drain")
    private val selStringWithUTF8 = sel("stringWithUTF8String:")
    private val selImageWithSymbol = sel("imageWithSystemSymbolName:accessibilityDescription:")
    private val selConfigPointSize = sel("configurationWithPointSize:weight:")
    private val selWithConfig = sel("imageWithSymbolConfiguration:")
    private val selTIFF = sel("TIFFRepresentation")
    private val selRepWithData = sel("imageRepWithData:")
    private val selDictionary = sel("dictionary")
    private val selRepUsingType = sel("representationUsingType:properties:")
    private val selBytes = sel("bytes")
    private val selLength = sel("length")

    fun loadSymbol(name: String): ImageBitmap? {
        val pool = msg(clsNSAutoreleasePool, selNew) ?: return null
        return try {
            val nsStr = msg(clsNSString, selStringWithUTF8, name) ?: return null
            val image = msg(clsNSImage, selImageWithSymbol, nsStr, null) ?: return null

            // Configure point size via typed interface (avoids arm64 variadic double issue)
            val configured = runCatching {
                val config = ObjCMsgSendDouble2.INSTANCE.objc_msgSend(
                    clsNSImageSymbolConfig, selConfigPointSize, 96.0, 0.0,
                )
                if (config != null) msg(image, selWithConfig, config) ?: image else image
            }.getOrDefault(image)

            // TIFF → NSBitmapImageRep → PNG
            val tiff = msg(configured, selTIFF) ?: return null
            val rep = msg(clsNSBitmapImageRep, selRepWithData, tiff) ?: return null
            val props = msg(clsNSDictionary, selDictionary) ?: return null
            // 4L = NSBitmapImageFileTypePNG
            val pngData = msg(rep, selRepUsingType, 4L, props) ?: return null

            val bytesPtr = msg(pngData, selBytes) ?: return null
            val length = msgLong(pngData, selLength)
            if (length <= 0) return null
            val pngBytes = bytesPtr.getByteArray(0, length.toInt())

            // Decode PNG via Skia
            val skImage = Image.makeFromEncoded(pngBytes)
            val bitmap = Bitmap()
            bitmap.allocPixels(skImage.imageInfo)
            skImage.readPixels(bitmap, 0, 0)
            bitmap.asComposeImageBitmap()
        } catch (_: Exception) {
            null
        } finally {
            msg(pool, selDrain)
        }
    }
}
