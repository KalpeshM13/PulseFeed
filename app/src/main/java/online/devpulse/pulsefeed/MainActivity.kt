package online.devpulse.pulsefeed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.OnBackPressedCallback
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import online.devpulse.pulsefeed.data.model.Article
import online.devpulse.pulsefeed.data.preferences.PreferencesManager
import online.devpulse.pulsefeed.ui.components.ExitConfirmationDialog
import online.devpulse.pulsefeed.ui.screens.ArticleDetailScreen
import online.devpulse.pulsefeed.ui.screens.NewsScreen
import online.devpulse.pulsefeed.ui.theme.PulseFeedTheme
import online.devpulse.pulsefeed.ui.theme.ThemeMode
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentTheme by remember { mutableStateOf(preferencesManager.getThemeMode()) }
            var selectedArticle by remember { mutableStateOf<Article?>(null) }
            var showExitDialog by remember { mutableStateOf(false) }
            
            // Handle back button press
            LaunchedEffect(selectedArticle) {
                onBackPressedDispatcher.addCallback(
                    this@MainActivity,
                    object : OnBackPressedCallback(true) {
                        override fun handleOnBackPressed() {
                            when {
                                selectedArticle != null -> {
                                    // If on article detail screen, go back to news list
                                    selectedArticle = null
                                }
                                showExitDialog -> {
                                    // If exit dialog is showing, dismiss it
                                    showExitDialog = false
                                }
                                else -> {
                                    // If on main screen, show exit confirmation dialog
                                    showExitDialog = true
                                }
                            }
                        }
                    }
                )
            }
            
            // Animated theme transition
            AnimatedContent(
                targetState = currentTheme,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
                },
                label = "theme_transition"
            ) { theme ->
                PulseFeedTheme(
                    darkTheme = when (theme) {
                        ThemeMode.LIGHT -> false
                        ThemeMode.DARK -> true
                        ThemeMode.SYSTEM -> true // Default to dark
                    }
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AnimatedContent(
                            targetState = selectedArticle,
                            transitionSpec = {
                                if (targetState != null) {
                                    // Slide in from right when navigating to article detail
                                    slideInHorizontally(
                                        animationSpec = tween(300),
                                        initialOffsetX = { fullWidth -> fullWidth }
                                    ) + fadeIn(
                                        animationSpec = tween(300)
                                    ) togetherWith slideOutHorizontally(
                                        animationSpec = tween(300),
                                        targetOffsetX = { fullWidth -> -fullWidth }
                                    ) + fadeOut(
                                        animationSpec = tween(300)
                                    )
                                } else {
                                    // Slide in from left when going back to news list
                                    slideInHorizontally(
                                        animationSpec = tween(300),
                                        initialOffsetX = { fullWidth -> -fullWidth }
                                    ) + fadeIn(
                                        animationSpec = tween(300)
                                    ) togetherWith slideOutHorizontally(
                                        animationSpec = tween(300),
                                        targetOffsetX = { fullWidth -> fullWidth }
                                    ) + fadeOut(
                                        animationSpec = tween(300)
                                    )
                                }
                            },
                            label = "navigation"
                        ) { article ->
                            when {
                                article != null -> {
                                    ArticleDetailScreen(
                                        article = article,
                                        onBackPressed = { selectedArticle = null }
                                    )
                                }
                                else -> {
                                    NewsScreen(
                                        onNavigateToArticle = { article -> selectedArticle = article },
                                        currentTheme = currentTheme,
                                        onThemeChanged = { theme ->
                                            currentTheme = theme
                                            preferencesManager.setThemeMode(theme)
                                        }
                                    )
                                }
                            }
                        }
                        
                        // Exit confirmation dialog
                        if (showExitDialog) {
                            ExitConfirmationDialog(
                                onConfirm = {
                                    showExitDialog = false
                                    finish()
                                },
                                onDismiss = {
                                    showExitDialog = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
} 