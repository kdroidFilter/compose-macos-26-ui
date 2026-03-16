#!/bin/bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
SRC="$SCRIPT_DIR/MacosWindowBridge.m"
RESOURCE_DIR="$SCRIPT_DIR/../../resources/macosui/native"
OUT_DIR_ARM64="$RESOURCE_DIR/darwin-aarch64"
OUT_DIR_X64="$RESOURCE_DIR/darwin-x64"

# Detect JAVA_HOME
if [ -z "${JAVA_HOME:-}" ]; then
    JAVA_HOME=$(/usr/libexec/java_home 2>/dev/null || true)
fi
if [ -z "${JAVA_HOME:-}" ]; then
    echo "ERROR: JAVA_HOME not set" >&2; exit 1
fi

JNI_INCLUDE="$JAVA_HOME/include"
JNI_INCLUDE_DARWIN="$JAVA_HOME/include/darwin"
if [ ! -d "$JNI_INCLUDE" ]; then
    echo "ERROR: JNI headers not found at $JNI_INCLUDE" >&2; exit 1
fi

mkdir -p "$OUT_DIR_ARM64" "$OUT_DIR_X64"

# Clear cached dylibs so the next run picks up the freshly built ones
CACHE_DIR="$HOME/Library/Caches/macosui/native"
if [ -d "$CACHE_DIR" ]; then
    echo "Clearing native library cache ($CACHE_DIR)..."
    rm -rf "$CACHE_DIR"
fi

COMMON_FLAGS=(
    -dynamiclib
    -I"$JNI_INCLUDE" -I"$JNI_INCLUDE_DARWIN"
    -framework Cocoa -framework QuartzCore
    -mmacosx-version-min=10.13
    -fobjc-arc -Oz -flto -fvisibility=hidden
    -Wl,-dead_strip -Wl,-x
)

echo "Building arm64..."
clang -arch arm64 "${COMMON_FLAGS[@]}" -o "$OUT_DIR_ARM64/libmacosui_jni.dylib" "$SRC"
strip -x "$OUT_DIR_ARM64/libmacosui_jni.dylib"

echo "Building x86_64..."
clang -arch x86_64 "${COMMON_FLAGS[@]}" -o "$OUT_DIR_X64/libmacosui_jni.dylib" "$SRC"
strip -x "$OUT_DIR_X64/libmacosui_jni.dylib"

echo "Done."
