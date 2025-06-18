#!/bin/bash

echo "🚀 Building PulseFeed Release APK..."

# Clean previous builds
echo "🧹 Cleaning previous builds..."
./gradlew clean

# Build release APK
echo "🔨 Building release APK..."
./gradlew assembleRelease

# Check if build was successful
if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo "📱 APK location: app/build/outputs/apk/release/app-release-unsigned.apk"
    echo "📏 APK size: $(du -h app/build/outputs/apk/release/app-release-unsigned.apk | cut -f1)"
    
    # Check if keystore exists for signed build
    if [ -f "pulsefeed.keystore" ]; then
        echo "🔐 Keystore found. Building signed APK..."
        ./gradlew assembleRelease
        echo "✅ Signed APK ready: app/build/outputs/apk/release/app-release.apk"
    else
        echo "⚠️  No keystore found. APK is unsigned."
        echo "📖 See BUILD_INSTRUCTIONS.md for signing instructions."
    fi
else
    echo "❌ Build failed!"
    exit 1
fi

echo "🎉 Build process completed!" 