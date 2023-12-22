package com.example.newsapp.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ListViewItemBinding
import com.example.newsapp.network.News

class NewsListAdapter(val clickListener: NewsListener) :
    ListAdapter<News.Article, NewsListAdapter.NewsViewHolder>(DiffCallback) {

    class NewsViewHolder(
        val binding: ListViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(clickListener: NewsListener, newsItem: News.Article){
                binding.newsItem = newsItem
                binding.clickListener = clickListener
                binding.executePendingBindings()
            }
        }

    companion object DiffCallback : DiffUtil.ItemCallback<News.Article>() {
        override fun areItemsTheSame(oldItem: News.Article, newItem: News.Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: News.Article, newItem: News.Article): Boolean {
            return oldItem.description == newItem.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : NewsViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(
            ListViewItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int){
        val newsItem = getItem(position)
        holder.bind(clickListener, newsItem)

        holder.itemView.setOnClickListener {
            clickListener.onClick(newsItem)
        }
    }
}

class NewsListener(val clickListener : (newsItem : News.Article) -> Unit){
    fun onClick(newsItem: News.Article) = clickListener(newsItem)
}