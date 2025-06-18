package online.devpulse.pulsefeed.ui.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    
    fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            
            val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            outputFormat.format(date ?: Date())
        } catch (e: Exception) {
            dateString
        }
    }
    
    fun formatTime(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            
            val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            outputFormat.format(date ?: Date())
        } catch (e: Exception) {
            ""
        }
    }
    
    fun getTimeAgo(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            
            val date = inputFormat.parse(dateString)
            val now = Date()
            val diffInMillis = now.time - (date?.time ?: 0)
            
            val diffInMinutes = diffInMillis / (1000 * 60)
            val diffInHours = diffInMillis / (1000 * 60 * 60)
            val diffInDays = diffInMillis / (1000 * 60 * 60 * 24)
            
            when {
                diffInMinutes < 60 -> "${diffInMinutes}m ago"
                diffInHours < 24 -> "${diffInHours}h ago"
                diffInDays < 7 -> "${diffInDays}d ago"
                else -> formatDate(dateString)
            }
        } catch (e: Exception) {
            dateString
        }
    }
} 