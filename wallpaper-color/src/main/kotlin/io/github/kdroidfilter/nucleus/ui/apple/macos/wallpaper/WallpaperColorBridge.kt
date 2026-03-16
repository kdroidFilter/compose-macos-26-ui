package io.github.kdroidfilter.nucleus.ui.apple.macos.wallpaper

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.logging.Level
import java.util.logging.Logger

private const val LibraryName = "macosui_wallpaper_jni"
private const val ResourcePrefix = "/macosui/native"

object WallpaperColorBridge {

    private val logger = Logger.getLogger(WallpaperColorBridge::class.java.simpleName)

    val isLoaded: Boolean = loadLibrary()

    /** Returns the average wallpaper color as a packed ARGB int. */
    @JvmStatic
    external fun nativeGetWallpaperColorARGB(): Int

    /** Installs a native observer for wallpaper change notifications. */
    @JvmStatic
    external fun nativeInstallObserver()

    /** Returns the number of wallpaper changes detected since observer installation. */
    @JvmStatic
    external fun nativeGetChangeCount(): Int

    /** Removes the wallpaper change observer. */
    @JvmStatic
    external fun nativeRemoveObserver()

    @Suppress("TooGenericExceptionCaught")
    private fun loadLibrary(): Boolean {
        try {
            System.loadLibrary(LibraryName)
            return true
        } catch (_: UnsatisfiedLinkError) { /* fall through */ }

        return try {
            val arch = System.getProperty("os.arch").let {
                if (it == "aarch64" || it == "arm64") "aarch64" else "x64"
            }
            val fileName = "lib$LibraryName.dylib"
            val resourcePath = "$ResourcePrefix/darwin-$arch/$fileName"
            val stream = WallpaperColorBridge::class.java.getResourceAsStream(resourcePath)
                ?: throw UnsatisfiedLinkError("Not found in resources: $resourcePath")

            val cacheDir = Path.of(
                System.getProperty("user.home"), "Library", "Caches",
                "macosui", "native", "darwin-$arch",
            )
            Files.createDirectories(cacheDir)
            val target = cacheDir.resolve(fileName)

            stream.use { input ->
                val tmp = Files.createTempFile(cacheDir, LibraryName, ".tmp")
                Files.copy(input, tmp, StandardCopyOption.REPLACE_EXISTING)
                try {
                    Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
                } catch (_: Exception) {
                    Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING)
                }
            }

            System.load(target.toAbsolutePath().toString())
            true
        } catch (e: Exception) {
            logger.log(Level.WARNING, "Failed to load $LibraryName native library", e)
            false
        }
    }
}
