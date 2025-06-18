package online.devpulse.pulsefeed.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import online.devpulse.pulsefeed.data.model.Article
import online.devpulse.pulsefeed.data.preferences.PreferencesManager
import online.devpulse.pulsefeed.data.repository.NewsRepository
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _selectedCategory = MutableStateFlow("general")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()
    
    init {
        loadTopHeadlines()
    }
    
    private fun loadTopHeadlines() {
        viewModelScope.launch {
            try {
                println("DEBUG: Loading headlines for country: us, category: ${_selectedCategory.value}")
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                newsRepository.getTopHeadlines(
                    country = "us",
                    category = _selectedCategory.value
                )
                    .onSuccess { response ->
                        println("DEBUG: Successfully loaded ${response.articles.size} articles")
                        _uiState.value = _uiState.value.copy(
                            articles = response.articles,
                            isLoading = false,
                            error = null
                        )
                    }
                    .onFailure { exception ->
                        println("DEBUG: Failed to load headlines: ${exception.message}")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = exception.message ?: "Unknown error occurred"
                        )
                    }
            } catch (e: Exception) {
                println("DEBUG: Exception in loadTopHeadlines: ${e.message}")
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
    
    fun searchNews(query: String) {
        if (query.isBlank()) {
            loadTopHeadlines()
            return
        }
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            newsRepository.searchNews(query = query)
                .onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        articles = response.articles,
                        isLoading = false,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Unknown error occurred"
                    )
                }
        }
    }
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun updateSelectedCategory(category: String) {
        _selectedCategory.value = category
        loadTopHeadlines()
    }
    
    fun refreshNews() {
        if (_searchQuery.value.isBlank()) {
            loadTopHeadlines()
        } else {
            searchNews(_searchQuery.value)
        }
    }
}

data class NewsUiState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) 