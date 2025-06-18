package online.devpulse.pulsefeed.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import online.devpulse.pulsefeed.data.model.Article
import online.devpulse.pulsefeed.ui.components.ArticleCard
import online.devpulse.pulsefeed.ui.components.CategoryChips
import online.devpulse.pulsefeed.ui.components.SearchBar
import online.devpulse.pulsefeed.ui.components.ThemeToggle
import online.devpulse.pulsefeed.ui.theme.ThemeMode
import online.devpulse.pulsefeed.ui.viewmodel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    viewModel: NewsViewModel = hiltViewModel(),
    onNavigateToArticle: (Article) -> Unit = {},
    currentTheme: ThemeMode = ThemeMode.SYSTEM,
    onThemeChanged: (ThemeMode) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "PulseFeed",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                actions = {
                    // Theme Toggle
                    Box(Modifier.width(56.dp)) {
                        ThemeToggle(
                            currentTheme = currentTheme,
                            onThemeChanged = onThemeChanged
                        )
                    }
                    // Refresh Button
                    IconButton(
                        onClick = { viewModel.refreshNews() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { viewModel.updateSearchQuery(it) },
                onSearch = { viewModel.searchNews(searchQuery) },
                modifier = Modifier.padding(16.dp)
            )
            
            // Category Chips
            CategoryChips(
                selectedCategory = selectedCategory,
                onCategorySelected = { viewModel.updateSelectedCategory(it) },
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            // Content
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                
                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Error",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.error
                            )
                            Text(
                                text = uiState.error ?: "Unknown error occurred",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Button(
                                onClick = { viewModel.refreshNews() }
                            ) {
                                Text("Retry")
                            }
                        }
                    }
                }
                
                uiState.articles.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No articles found",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.articles) { article ->
                            ArticleCard(
                                article = article,
                                onClick = { onNavigateToArticle(article) }
                            )
                        }
                    }
                }
            }
        }
    }
} 
 