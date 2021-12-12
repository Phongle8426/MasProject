package com.example.masapp.models

/*
///
/// Project: MasApp
/// Created by pc on 11/27/2021.
///
*/
data class RequestCartModel(
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
    val status: Int,
    val createdDate: String

)