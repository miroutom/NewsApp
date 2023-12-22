package com.example.newsapp.network

import com.squareup.moshi.Json

data class NewsResponse(
    @Json(name = "status") val status: String,
    @Json(name = "totalResults") val totalResults: Int,
    @Json(name = "articles") val articles: List<NewsData>
)

data class NewsData(
    @Json(name = "source") val source: Source?,
    @Json(name = "author") val author: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "url") val url: String?,
    @Json(name = "urlToImage") val urlToImage: String?,
    @Json(name = "publishedAt") val publishedAt: String?,
    @Json(name = "content") val content: String?
) {
    data class Source(
        @Json(name = "id") val id: String?,
        @Json(name = "name") val name: String?
    )
}
