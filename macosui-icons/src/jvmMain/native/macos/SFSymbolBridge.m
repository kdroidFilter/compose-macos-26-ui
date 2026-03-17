#include <jni.h>
#import <AppKit/AppKit.h>

#pragma mark - JNI exports

// Loads an SF Symbol by name at 96pt and returns its PNG representation as a byte array.
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

        // NSImage → TIFF → NSBitmapImageRep → PNG
        NSData *tiff = [configured TIFFRepresentation];
        if (!tiff) return NULL;

        NSBitmapImageRep *rep = [NSBitmapImageRep imageRepWithData:tiff];
        if (!rep) return NULL;

        NSData *pngData = [rep representationUsingType:NSBitmapImageFileTypePNG
                                            properties:@{}];
        if (!pngData || pngData.length == 0) return NULL;

        jbyteArray result = (*env)->NewByteArray(env, (jsize)pngData.length);
        if (!result) return NULL;
        (*env)->SetByteArrayRegion(env, result, 0, (jsize)pngData.length, pngData.bytes);
        return result;
    }
}
