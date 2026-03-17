#include <jni.h>
#import <AppKit/AppKit.h>

#pragma mark - JNI exports

// Loads an SF Symbol by name at 96pt and returns raw RGBA pixel data.
// Layout: [width: int32, height: int32, rgba_pixels...]
// Returns NULL if the symbol is not found or conversion fails.
JNIEXPORT jbyteArray JNICALL
Java_io_github_kdroidfilter_nucleus_ui_apple_macos_icons_SFSymbolBridge_nativeLoadSymbol(
    JNIEnv *env, jclass clazz, jstring jName)
{
    @autoreleasepool {
        const char *utf8 = (*env)->GetStringUTFChars(env, jName, NULL);
        if (!utf8) return NULL;
        NSString *name = [NSString stringWithUTF8String:utf8];
        (*env)->ReleaseStringUTFChars(env, jName, utf8);

        NSImage *image = [NSImage imageWithSystemSymbolName:name
                                   accessibilityDescription:nil];
        if (!image) return NULL;

        // Configure point size (96pt, regular weight)
        NSImageSymbolConfiguration *config =
            [NSImageSymbolConfiguration configurationWithPointSize:96.0
                                                            weight:NSFontWeightRegular];
        NSImage *configured = [image imageWithSymbolConfiguration:config];
        if (!configured) configured = image;

        // NSImage → TIFF → NSBitmapImageRep → premultiplied RGBA pixels
        NSData *tiff = [configured TIFFRepresentation];
        if (!tiff) return NULL;

        NSBitmapImageRep *rep = [NSBitmapImageRep imageRepWithData:tiff];
        if (!rep) return NULL;

        NSInteger w = rep.pixelsWide;
        NSInteger h = rep.pixelsHigh;
        if (w <= 0 || h <= 0) return NULL;

        // Draw into a known-format RGBA bitmap context (premultiplied alpha)
        size_t bytesPerRow = (size_t)w * 4;
        size_t pixelBytes = bytesPerRow * (size_t)h;
        CGColorSpaceRef cs = CGColorSpaceCreateWithName(kCGColorSpaceSRGB);
        CGContextRef ctx = CGBitmapContextCreate(
            NULL, (size_t)w, (size_t)h, 8, bytesPerRow, cs,
            kCGImageAlphaPremultipliedLast | kCGBitmapByteOrder32Big);
        CGColorSpaceRelease(cs);
        if (!ctx) return NULL;

        CGImageRef cgImage = [rep CGImage];
        if (!cgImage) { CGContextRelease(ctx); return NULL; }
        CGContextDrawImage(ctx, CGRectMake(0, 0, w, h), cgImage);

        const void *pixels = CGBitmapContextGetData(ctx);
        if (!pixels) { CGContextRelease(ctx); return NULL; }

        // Pack: 8-byte header (width, height as big-endian int32) + raw RGBA
        jsize totalSize = (jsize)(8 + pixelBytes);
        jbyteArray result = (*env)->NewByteArray(env, totalSize);
        if (!result) { CGContextRelease(ctx); return NULL; }

        // Write header
        int32_t header[2] = {
            (int32_t)OSSwapHostToBigInt32((uint32_t)w),
            (int32_t)OSSwapHostToBigInt32((uint32_t)h)
        };
        (*env)->SetByteArrayRegion(env, result, 0, 8, (const jbyte *)header);

        // Write pixel data
        (*env)->SetByteArrayRegion(env, result, 8, (jsize)pixelBytes, pixels);

        CGContextRelease(ctx);
        return result;
    }
}
