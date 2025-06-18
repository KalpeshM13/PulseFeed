package online.devpulse.pulsefeed.data.remote

import online.devpulse.pulsefeed.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("category") category: String? = null,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): NewsResponse
    
    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): NewsResponse
    
    @GET("top-headlines")
    suspend fun getTopHeadlinesByCategory(
        @Query("country") country: String = "us",
        @Query("category") category: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): NewsResponse
} 