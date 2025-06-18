# PulseFeed - Build and Distribution Instructions

## 🚀 Creating a Signed Release APK

### Step 1: Generate a Keystore
```bash
keytool -genkey -v -keystore pulsefeed.keystore -alias pulsefeed -keyalg RSA -keysize 2048 -validity 10000
```

### Step 2: Configure Signing in build.gradle.kts
Add to `app/build.gradle.kts`:
```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("pulsefeed.keystore")
            storePassword = "your-store-password"
            keyAlias = "pulsefeed"
            keyPassword = "your-key-password"
        }
    }
    
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

### Step 3: Build Signed APK
```bash
./gradlew assembleRelease
```

The signed APK will be at: `app/build/outputs/apk/release/app-release.apk`

## 📱 Distribution Options

### Option 1: Google Play Store (Recommended)
1. Create a Google Play Console account
2. Upload the signed APK
3. Fill in app details, screenshots, and description
4. Submit for review

### Option 2: Direct APK Sharing
1. Upload the signed APK to Google Drive, Dropbox, or similar
2. Share the download link with colleagues
3. They can install directly on their devices

### Option 3: GitHub Releases
1. Create a GitHub release
2. Upload the signed APK as a release asset
3. Share the release URL

## 🔧 Setup Instructions for Users

### Prerequisites:
- Android 6.0 (API level 23) or higher
- Internet connection for news updates
- Enable "Install from Unknown Sources" if not from Play Store

### Installation:
1. Download the APK file
2. Open the file on your Android device
3. Follow the installation prompts
4. Launch PulseFeed and enjoy!

### First Time Setup:
- The app will automatically load the latest US news
- Use the category chips to filter news by topic
- Use the search bar to find specific articles
- Toggle between light/dark themes using the theme button

## 🛠️ For Developers

### Clone and Build:
```bash
git clone <repository-url>
cd PulseFeed
./gradlew assembleDebug
```

### Required Setup:
1. Get NewsAPI key from [newsapi.org](https://newsapi.org/)
2. Add API key to `NetworkModule.kt`
3. Sync project in Android Studio

### Project Structure:
- `app/src/main/java/online/devpulse/pulsefeed/` - Main source code
- `app/src/main/res/` - Resources and assets
- `app/build.gradle.kts` - Build configuration

## 📞 Support

For issues or questions:
1. Check the README.md file
2. Review the code comments
3. Create an issue on GitHub
4. Contact the development team

## 🔒 Security Notes

- The app requires internet permission for news updates
- No personal data is collected or stored
- All news content comes from NewsAPI.org
- The app follows Android security best practices 