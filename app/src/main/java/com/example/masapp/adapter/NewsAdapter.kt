package com.example.masapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.masapp.databinding.ItemNewsBinding
import com.example.masapp.models.NewsModel
import com.example.masapp.utils.ItemClick

class NewsAdapter(val listNews: List<NewsModel>, val listener: ItemClick):
    RecyclerView.Adapter<NewsAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsModel) {
            binding.news = news
            binding.cardEvent.setOnClickListener {
                listener.itemClick(news)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listNews[position])
    }

    override fun getItemCount(): Int = listNews.size
}