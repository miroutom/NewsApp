package com.example.newsapp.network

import android.util.Log
import com.example.newsapp.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val NEWS_API_KEY = BuildConfig.NEWS_API_KEY

private const val BASE_URL =
    "https://newsapi.org/"

private const val FOR_GET =
    "v2/everything?q=technology&apiKey=${NEWS_API_KEY}"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NewsApiService {
    @GET(FOR_GET)
    suspend fun getNews(): News
}

object NewsApi {
    val retrofitService: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }

    suspend fun getNewsArticles(): List<News.Article> {
        return try {
            val response = retrofitService.getNews()
            response.articles
        } catch (e: Exception) {
            Log.e("NewsApi", "Error fetching news: ${e.message}")
            emptyList()
        }
    }
}
