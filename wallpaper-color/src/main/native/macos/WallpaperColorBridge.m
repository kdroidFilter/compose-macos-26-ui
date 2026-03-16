#include <jni.h>
#import <AppKit/AppKit.h>
#import <CoreGraphics/CoreGraphics.h>
#import <stdatomic.h>

// Atomic change counter incremented on wallpaper change notifications
static atomic_int g_changeCount = 0;
static id g_wallpaperObserver = nil;
static id g_spaceObserver = nil;

#pragma mark - Desktop color extraction

// Extracts the average color of the actual desktop as displayed, by capturing
// the desktop window layer via CGWindowListCreateImage. This works regardless
// of whether the wallpaper is an image, a solid color, or a dynamic/video
// wallpaper — it captures what the user actually sees.
static jint desktopAverageColor(void) {
    jint argb = 0xFF808080; // fallback gray

    NSScreen *screen = [NSScreen mainScreen];
    if (!screen) return argb;

    CGRect screenRect = NSRectToCGRect(screen.frame);

    // Capture only the desktop wallpaper layer (below all windows).
    // kCGWindowID(0) + kCGWindowListOptionOnScreenBelowWindow captures the
    // desktop background without any application windows.
    CGImageRef desktopImage = CGWindowListCreateImage(
        screenRect,
        kCGWindowListOptionOnScreenBelowWindow,
        kCGNullWindowID,
        kCGWindowImageDefault
    );
    if (!desktopImage) return argb;

    // Downscale to 1×1 pixel — CG computes the average automatically
    uint8_t pixel[4] = {0};
    CGColorSpaceRef colorSpace = CGColorSpaceCreateDeviceRGB();
    CGContextRef ctx = CGBitmapContextCreate(
        pixel, 1, 1, 8, 4, colorSpace,
        kCGImageAlphaPremultipliedLast | kCGBitmapByteOrder32Big
    );
    CGColorSpaceRelease(colorSpace);
    if (!ctx) {
        CGImageRelease(desktopImage);
        return argb;
    }

    CGContextDrawImage(ctx, CGRectMake(0, 0, 1, 1), desktopImage);
    CGContextRelease(ctx);
    CGImageRelease(desktopImage);

    // pixel layout: [R, G, B, A] (premultipliedLast)
    int r = pixel[0], g = pixel[1], b = pixel[2], a = pixel[3];

    // Un-premultiply
    if (a > 0 && a < 255) {
        r = r * 255 / a;
        g = g * 255 / a;
        b = b * 255 / a;
    }

    argb = (0xFF << 24) | (r << 16) | (g << 8) | b;
    return argb;
}

#pragma mark - JNI exports

// Returns the average desktop color as an ARGB int.
JNIEXPORT jint JNICALL
Java_io_github_kdroidfilter_nucleus_ui_apple_macos_wallpaper_WallpaperColorBridge_nativeGetWallpaperColorARGB(
    JNIEnv *env, jclass clazz)
{
    @autoreleasepool {
        __block jint result = 0xFF808080;

        void (^work)(void) = ^{
            result = desktopAverageColor();
        };

        if ([NSThread isMainThread]) {
            work();
        } else {
            dispatch_sync(dispatch_get_main_queue(), work);
        }

        return result;
    }
}

// Installs observers for wallpaper/space change notifications.
JNIEXPORT void JNICALL
Java_io_github_kdroidfilter_nucleus_ui_apple_macos_wallpaper_WallpaperColorBridge_nativeInstallObserver(
    JNIEnv *env, jclass clazz)
{
    @autoreleasepool {
        dispatch_async(dispatch_get_main_queue(), ^{
            if (g_wallpaperObserver != nil) return; // already installed

            NSNotificationCenter *center = [NSWorkspace sharedWorkspace].notificationCenter;

            g_spaceObserver = [center
                addObserverForName:NSWorkspaceActiveSpaceDidChangeNotification
                            object:nil
                             queue:[NSOperationQueue mainQueue]
                        usingBlock:^(NSNotification *note) {
                            atomic_fetch_add(&g_changeCount, 1);
                        }];

            g_wallpaperObserver = [center
                addObserverForName:@"NSWorkspaceDesktopImageDidChangeNotification"
                            object:nil
                             queue:[NSOperationQueue mainQueue]
                        usingBlock:^(NSNotification *note) {
                            atomic_fetch_add(&g_changeCount, 1);
                        }];
        });
    }
}

// Returns the current change counter value.
JNIEXPORT jint JNICALL
Java_io_github_kdroidfilter_nucleus_ui_apple_macos_wallpaper_WallpaperColorBridge_nativeGetChangeCount(
    JNIEnv *env, jclass clazz)
{
    return atomic_load(&g_changeCount);
}

// Removes observers.
JNIEXPORT void JNICALL
Java_io_github_kdroidfilter_nucleus_ui_apple_macos_wallpaper_WallpaperColorBridge_nativeRemoveObserver(
    JNIEnv *env, jclass clazz)
{
    @autoreleasepool {
        dispatch_async(dispatch_get_main_queue(), ^{
            NSNotificationCenter *center = [NSWorkspace sharedWorkspace].notificationCenter;
            if (g_wallpaperObserver != nil) {
                [center removeObserver:g_wallpaperObserver];
                g_wallpaperObserver = nil;
            }
            if (g_spaceObserver != nil) {
                [center removeObserver:g_spaceObserver];
                g_spaceObserver = nil;
            }
        });
    }
}
