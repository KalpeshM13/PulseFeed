package online.devpulse.pulsefeed.data.repository

import online.devpulse.pulsefeed.data.model.NewsResponse
import online.devpulse.pulsefeed.data.remote.NewsApiService
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApiService: NewsApiService
) {
    
    suspend fun getTopHeadlines(country: String = "us", category: String? = null): Result<NewsResponse> {
        return try {
            val response = if (category != null) {
                newsApiService.getTopHeadlinesByCategory(country = country, category = category)
            } else {
                newsApiService.getTopHeadlines(country = country, category = category)
            }
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun searchNews(query: String): Result<NewsResponse> {
        return try {
            val response = newsApiService.searchNews(query = query)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getTopHeadlinesByCategory(
        category: String,
        country: String = "us",
        page: Int = 1
    ): Result<NewsResponse> {
        return try {
            val response = newsApiService.getTopHeadlinesByCategory(country, category, page)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 