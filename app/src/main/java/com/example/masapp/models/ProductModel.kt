package com.example.masapp.models

import android.util.Log
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class ProductModel(
    val id: Long,
    val name: String,
    val code: String,
    val quantity: Long,
    val price: String,
    val catalogType: String,
    val unit: String
){
    fun convertCurrency():String{
        val formatCurrency = DecimalFormat("###,###,###")
        return "${formatCurrency.format(price.toLong())}đ"
    }
}
