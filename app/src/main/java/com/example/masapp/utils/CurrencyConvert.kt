package com.example.masapp.utils

import android.util.Log
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/*
///
/// Project: MasApp
/// Created by pc on 12/21/2021.
///
*/
class CurrencyConvert {
    fun convertCurrency(data: String):String{
        val formatCurrency = DecimalFormat("###,###,###")
        return "${formatCurrency.format(data.toLong())}đ"
    }
}