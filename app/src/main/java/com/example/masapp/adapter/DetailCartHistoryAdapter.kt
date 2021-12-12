package com.example.masapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.masapp.databinding.ItemProductCartHistoryBinding
import com.example.masapp.models.RequestCartModel
import com.example.masapp.models.RequestProductModel

class DetailCartHistoryAdapter(private val carts: List<RequestProductModel>) :
    RecyclerView.Adapter<DetailCartHistoryAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemProductCartHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: RequestProductModel){
            binding.cartHistory = cart
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductCartHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(carts[position])
    }

    override fun getItemCount(): Int = carts.size
}