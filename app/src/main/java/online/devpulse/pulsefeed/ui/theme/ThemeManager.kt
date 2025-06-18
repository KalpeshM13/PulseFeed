package online.devpulse.pulsefeed.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}

class ThemeManager {
    var themeMode by mutableStateOf(ThemeMode.SYSTEM)
        private set
    
    fun updateThemeMode(mode: ThemeMode) {
        themeMode = mode
    }
    
    fun isDarkTheme(): Boolean {
        return when (themeMode) {
            ThemeMode.LIGHT -> false
            ThemeMode.DARK -> true
            ThemeMode.SYSTEM -> true // Default to dark for system
        }
    }
}

val LocalThemeManager = staticCompositionLocalOf { ThemeManager() }

@Composable
fun rememberThemeManager(): ThemeManager {
    return LocalThemeManager.current
} 