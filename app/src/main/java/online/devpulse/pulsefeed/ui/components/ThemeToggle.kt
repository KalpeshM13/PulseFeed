package online.devpulse.pulsefeed.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import online.devpulse.pulsefeed.ui.theme.ThemeMode

@Composable
fun ThemeToggle(
    currentTheme: ThemeMode,
    onThemeChanged: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    var showThemeMenu by remember { mutableStateOf(false) }
    
    Box(modifier = modifier) {
        IconButton(
            onClick = { showThemeMenu = true }
        ) {
            Icon(
                imageVector = when (currentTheme) {
                    ThemeMode.LIGHT -> Icons.Default.Brightness7
                    ThemeMode.DARK -> Icons.Default.Brightness4
                    ThemeMode.SYSTEM -> Icons.Default.Settings
                },
                contentDescription = "Theme",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        
        DropdownMenu(
            expanded = showThemeMenu,
            onDismissRequest = { showThemeMenu = false },
            modifier = Modifier.background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(12.dp)
            )
        ) {
            ThemeMode.values().forEach { theme ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = when (theme) {
                                    ThemeMode.LIGHT -> Icons.Default.Brightness7
                                    ThemeMode.DARK -> Icons.Default.Brightness4
                                    ThemeMode.SYSTEM -> Icons.Default.Settings
                                },
                                contentDescription = null,
                                tint = if (currentTheme == theme) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                }
                            )
                            Text(
                                text = when (theme) {
                                    ThemeMode.LIGHT -> "Light"
                                    ThemeMode.DARK -> "Dark"
                                    ThemeMode.SYSTEM -> "System"
                                },
                                color = if (currentTheme == theme) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                }
                            )
                        }
                    },
                    onClick = {
                        onThemeChanged(theme)
                        showThemeMenu = false
                    }
                )
            }
        }
    }
}

@Composable
fun AnimatedThemeToggle(
    currentTheme: ThemeMode,
    onThemeChanged: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val rotation by animateFloatAsState(
        targetValue = when (currentTheme) {
            ThemeMode.LIGHT -> 0f
            ThemeMode.DARK -> 180f
            ThemeMode.SYSTEM -> 90f
        },
        animationSpec = tween(durationMillis = 300),
        label = "theme_rotation"
    )
    
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                val nextTheme = when (currentTheme) {
                    ThemeMode.LIGHT -> ThemeMode.DARK
                    ThemeMode.DARK -> ThemeMode.SYSTEM
                    ThemeMode.SYSTEM -> ThemeMode.LIGHT
                }
                onThemeChanged(nextTheme)
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = when (currentTheme) {
                ThemeMode.LIGHT -> Icons.Default.Brightness7
                ThemeMode.DARK -> Icons.Default.Brightness4
                ThemeMode.SYSTEM -> Icons.Default.Settings
            },
            contentDescription = "Theme",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.rotate(rotation)
        )
    }
} 