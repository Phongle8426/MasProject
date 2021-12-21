package com.example.masapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.masapp.R
import com.example.masapp.databinding.ItemCartHistoryBinding
import com.example.masapp.models.ProductModel
import com.example.masapp.models.RequestCartModel
import com.example.masapp.models.RequestProductModel
import com.example.masapp.utils.ItemClick
import com.example.masapp.viewmodels.CartViewModel

class CartHistoryAdapter(private val carts: List<RequestCartModel>, val listener: ItemClick) :
    RecyclerView.Adapter<CartHistoryAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemCartHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: RequestCartModel){
            binding.cartHistory = cart
            when(cart.status){
                0 -> binding.imgStatus.setImageResource(R.drawable.ic_wait)
                1 -> binding.imgStatus.setImageResource(R.drawable.ic_ok)
                2 -> binding.imgStatus.setImageResource(R.drawable.ic_cancel)
                3 -> binding.imgStatus.setImageResource(R.drawable.ic_picked)
            }
            binding.cartCLickEvent.setOnClickListener {
                listener.itemClick(cart)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(carts[position])
    }

    override fun getItemCount(): Int = carts.size

}