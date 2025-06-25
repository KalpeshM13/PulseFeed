# PulseFeed

A modern, beautiful Android news app built with Jetpack Compose, focused on delivering the latest US news with a premium reading experience.

---

## ✨ Features

- **US News Only**: Curated, real-time news headlines from the United States
- **Category Browsing**: Instantly filter by General, Business, Technology, Sports, Entertainment, Health, and Science
- **Smart Search**: Find articles by keyword with instant results
- **In-App Article Reading**: Read full news stories directly in the app—no browser required
- **Rich Article Cards**: Eye-catching cards with images, source, time, and author
- **Dark & Light Mode**: Toggle between beautiful dark and light themes
- **Animated Theme Switching**: Smooth fade transition when changing theme modes
- **Modern Material Design 3**: Clean, accessible, and responsive UI
- **Persistent Preferences**: Theme choice is saved and restored automatically
- **Fast & Fluid**: Optimized for performance and smooth scrolling
- **Error & Empty States**: Friendly messages and retry options
- **Pull-to-Refresh**: Instantly update news with a tap

---

## 🚀 Tech Stack

- **Jetpack Compose** (UI)
- **MVVM Architecture**
- **Hilt** (Dependency Injection)
- **Retrofit + OkHttp** (Networking)
- **Coil** (Image Loading)
- **Kotlin Coroutines & StateFlow** (Async & State)
- **Material Design 3** (Theming)

---

## 🛠️ Getting Started

### 👥 For Visitors ###

**Download the APK File**
   - Enable "Install from Unknown Sources"
   - Find the latest release file [here](https://github.com/KalpeshM13/PulseFeed/releases/tag/Release)
   - Once downloaded, Install the app

### 💻 For Developers ###

1. **Clone the repo**
   ```bash
   git clone https://github.com/KalpeshM13/PulseFeed
   cd PulseFeed
   ```
2. **Get a NewsAPI key**
   - Sign up at [newsapi.org](https://newsapi.org/)
   - Add your API key in `NetworkModule.kt` as described in the code comments
3. **Open in Android Studio**
   - Sync Gradle and run on device or emulator

---

## 📂 Project Structure

```
app/src/main/java/online/devpulse/pulsefeed/
├── data/           # Models, API, repository, preferences
├── di/             # Dependency injection
├── ui/
│   ├── components/ # ArticleCard, CategoryChips, SearchBar, ThemeToggle
│   ├── screens/    # NewsScreen, ArticleDetailScreen
│   ├── theme/      # Color, Theme, Typography
│   └── viewmodel/  # NewsViewModel
└── PulseFeedApplication.kt
```

---

## 🌟 Highlights

- **Animated Navigation**: Smooth transitions between news list and article details
- **Fade Theme Switch**: Beautiful fade animation when toggling dark/light mode
- **Accessibility**: High-contrast, readable cards and headers in all themes
- **Persistent User Preferences**: Theme and settings are remembered
- **No External Browser**: All news is readable in-app

---

## 🤝 Contributing

Pull requests are welcome! Please open an issue first to discuss major changes.

---

## 📄 License

MIT License. See [LICENSE](https://github.com/KalpeshM13/PulseFeed/blob/master/LICENSE) for details.

---

## 🙏 Acknowledgments

- [NewsAPI.org](https://newsapi.org/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Coil](https://coil-kt.github.io/coil/) 
