package com.example.masapp.models

import android.annotation.SuppressLint
import java.sql.Date
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class RequestCartModel(
    val id: Long? = null,
    val userId: Long,
    val ownerName: String,
    val totalPrice: String,
    val quantityProduct: Int,
    val phoneNumber: String,
    val wardName: String,
    val district: String,
    val groupNumber: Long,
    val listProduct: List<RequestProductModel>,
    val note: String,
    val status: Int,
    val createdDate: String
){
    fun getPrice(): String{
        return (totalPrice.toInt() / quantityProduct).toString()
    }
    @SuppressLint("SimpleDateFormat")
    fun convertTime(): String{
        val sdf = SimpleDateFormat("dd/MM/yy")
        val netDate = Date(createdDate.toLong())
        return sdf.format(netDate)
    }
    fun convertCurrency():String{
        val formatCurrency = DecimalFormat("###,###,###")
        return "${formatCurrency.format(totalPrice.toLong())}đ"
    }
}