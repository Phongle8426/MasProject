package com.example.masapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.masapp.databinding.ItemFamilyBinding
import com.example.masapp.databinding.ItemProductBinding
import com.example.masapp.models.CivilianModel
import com.example.masapp.models.ProductModel
import com.example.masapp.models.RequestProductModel
import com.example.masapp.utils.ItemClick


/*
///
/// Project: MasApp
/// Created by pc on 11/27/2021.
///
*/class ProductAdapter(private val products: List<RequestProductModel>, val listener: ItemClick ): RecyclerView.Adapter<ProductAdapter.ViewHolder>()  {
    inner class ViewHolder(val binding: ItemProductBinding):
    RecyclerView.ViewHolder(binding.root){
        var quantity = 0
        @SuppressLint("SetTextI18n")
        fun bind(product: RequestProductModel){
            binding.tvPick.text = product.quantity.toString()
            binding.btnRemove.setOnClickListener {
                Log.e("remove", product.quantity.toString())
                if (product.quantity > 0){
                    product.quantity--
                    binding.tvPick.text = product.quantity.toString()
                    listener.itemClick(product)
                }
            }
            binding.btnAdd.setOnClickListener {
                Log.e("add", product.quantity.toString())
                if (product.quantity < 20){
                    product.quantity++
                    binding.tvPick.text = product.quantity.toString()
                    listener.itemClick(product)
                }
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