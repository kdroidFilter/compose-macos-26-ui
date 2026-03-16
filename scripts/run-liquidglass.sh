#!/bin/bash
# Patches the JVM binary to claim macOS SDK 26.0, enabling Liquid Glass
# window decorations in AppKit. Caches the patched binary and mirrors the
# JAVA_HOME directory structure so @loader_path rpaths resolve correctly.
set -euo pipefail

JAVA_HOME="${JAVA_HOME:-$(/usr/libexec/java_home 2>/dev/null)}"
if [ -z "$JAVA_HOME" ]; then
    echo "ERROR: JAVA_HOME not set and java_home not found" >&2
    exit 1
fi

JAVA_BIN="$JAVA_HOME/bin/java"
if [ ! -f "$JAVA_BIN" ]; then
    echo "ERROR: java binary not found at $JAVA_BIN" >&2
    exit 1
fi

CACHE_DIR="$HOME/Library/Caches/macosui/patched-jvm"
PATCHED="$CACHE_DIR/bin/java"
STAMP="$CACHE_DIR/.source_hash"

# Compute hash of the source binary to detect JVM updates
SOURCE_HASH=$(shasum -a 256 "$JAVA_BIN" | cut -d' ' -f1)
CACHED_HASH=""
[ -f "$STAMP" ] && CACHED_HASH=$(cat "$STAMP")

if [ ! -f "$PATCHED" ] || [ "$SOURCE_HASH" != "$CACHED_HASH" ]; then
    echo "Patching JVM binary for macOS SDK 26.0 (Liquid Glass)..."
    mkdir -p "$CACHE_DIR/bin"

    # Mirror the JAVA_HOME/lib directory so @loader_path/../lib resolves
    ln -sfn "$JAVA_HOME/lib" "$CACHE_DIR/lib"

    cp "$JAVA_BIN" "$PATCHED"
    codesign --remove-signature "$PATCHED" 2>/dev/null || true
    vtool -set-build-version macos 11.0 26.0 -tool ld 0.0 \
          -replace -output "$PATCHED" "$PATCHED"
    codesign -s - -f "$PATCHED" 2>/dev/null

    echo "$SOURCE_HASH" > "$STAMP"
    echo "Patched binary cached at $PATCHED"
fi

# Forward all arguments to the patched JVM
exec "$PATCHED" "$@"
