package com.example.masapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.masapp.databinding.ItemFamilyBinding
import com.example.masapp.databinding.ItemPreviewProductBinding
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
*/class PreviewProductAdapter(
    private val products: List<ProductModel>,
    private val previewProducts: List<RequestProductModel>,
    val listener: ItemClick
) : RecyclerView.Adapter<PreviewProductAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemPreviewProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductModel) {
            binding.previewProduct = product
            for (i in previewProducts){
                if (i.productId == product.id)
                    binding.ckbSelectProduct.isChecked = true
            }
            binding.cardMyevent.setOnClickListener {
                if (!binding.ckbSelectProduct.isChecked) {
                    listener.itemClick(product)
                    binding.ckbSelectProduct.isChecked = true
                } else {
                    binding.ckbSelectProduct.isChecked = false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPreviewProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size
}