package com.example.masapp.models

import java.text.DecimalFormat

class RequestProductModel(
    val name: String,
    var quantity: Int,
    val price: String,
    var totalPrice: String,
    val productId: Long
){
    fun convertCurrency(data: String):String{
        val formatCurrency = DecimalFormat("###,###,###")
        return "${formatCurrency.format(data.toLong())}đ"
    }
}
