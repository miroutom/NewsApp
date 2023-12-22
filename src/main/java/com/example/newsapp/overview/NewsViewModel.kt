package com.example.newsapp.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.network.NewsApi
import com.example.newsapp.network.News
import kotlinx.coroutines.launch

private const val TAG = "Main Activity"

enum class NewsApiStatus { LOADING, ERROR, DONE }

class NewsViewModel : ViewModel(){

    private val _news = MutableLiveData<List<News.Article>>()
    val news : LiveData<List<News.Article>> = _news

    private val _newsItem = MutableLiveData<News.Article>()
    val newsItem : LiveData<News.Article> = _newsItem

    private val _status = MutableLiveData<NewsApiStatus>()
    val status : LiveData<NewsApiStatus> = _status

    private val _filteredNews = MutableLiveData<List<News.Article>>()
    val filteredNews : LiveData<List<News.Article>> = _filteredNews

   init {
       _filteredNews.value = emptyList()
       getNewsList()
   }

    fun getNewsList(){
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val response = NewsApi.getNewsArticles()
                _news.value = response
                _status.value = NewsApiStatus.DONE
            } catch (e: java.lang.Exception){
                _status.value = NewsApiStatus.ERROR
                _news.value = listOf()
                Log.e(TAG, "Error: ${e.message}")
            }
        }
    }

    fun onNewsCLicked(newItem: News.Article){
        _newsItem.value = newItem
    }

    fun searchNewsByKeyword(keyword: String){
        viewModelScope.launch {
            val allNews = _news.value ?: return@launch

            val filtered = allNews.filter { article ->
                article.title?.contains(keyword, ignoreCase = true) == true  ||
                article.description?.contains(keyword, ignoreCase = true) == true
            }

            filtered.forEach { article ->
                Log.d("FilteredNews", "Title: ${article.title}, Description: ${article.description}")
            }

            _filteredNews.value = filtered
        }
    }
}
