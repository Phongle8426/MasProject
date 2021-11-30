package com.example.masapp.models

/*
///
/// Project: MasApp
/// Created by pc on 11/27/2021.
///
*/
data class RequestProductModel(
    val name: String,
    var quantity: Int,
    val price: String,
    var totalPrice: String,
    val productId: Long
)
