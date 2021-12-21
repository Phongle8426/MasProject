package com.example.masapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.masapp.databinding.ItemProductBinding
import com.example.masapp.models.RequestProductModel
import com.example.masapp.utils.ItemClick
import com.example.masapp.viewmodels.CartViewModel

class ProductAdapter(val products: List<RequestProductModel>,
                       val cartViewModel: CartViewModel,
                       val listener: ItemClick
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(product: RequestProductModel) {
            binding.tvPick.text = product.quantity.toString()
            binding.btnRemove.setOnClickListener {
                Log.e("remove", product.quantity.toString())
                if (product.quantity > 1) {
                    product.quantity--
                    binding.tvPick.text = product.quantity.toString()
                    listener.itemClickWithCount(product,product.quantity)
                }
            }
            binding.btnAdd.setOnClickListener {
                Log.e("add", product.quantity.toString())
                if (product.quantity < 5) {
                    product.quantity++
                    binding.tvPick.text = product.quantity.toString()
                }
                listener.itemClickWithCount(product,product.quantity)
            }
            binding.btnDelete.setOnClickListener {
                (products as MutableList).remove(product)
                cartViewModel.addProduct(products)
                cartViewModel.calculatePrice()
                notifyDataSetChanged()
            }
            binding.product = product
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size
}