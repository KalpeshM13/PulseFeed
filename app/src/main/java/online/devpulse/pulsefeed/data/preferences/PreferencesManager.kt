package online.devpulse.pulsefeed.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import online.devpulse.pulsefeed.ui.theme.ThemeMode
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "PulseFeedPrefs",
        Context.MODE_PRIVATE
    )
    
    companion object {
        private const val KEY_THEME_MODE = "theme_mode"
    }
    
    fun getThemeMode(): ThemeMode {
        val themeName = prefs.getString(KEY_THEME_MODE, ThemeMode.SYSTEM.name)
        return try {
            ThemeMode.valueOf(themeName ?: ThemeMode.SYSTEM.name)
        } catch (e: IllegalArgumentException) {
            ThemeMode.SYSTEM
        }
    }
    
    fun setThemeMode(themeMode: ThemeMode) {
        prefs.edit { putString(KEY_THEME_MODE, themeMode.name) }
    }
} 