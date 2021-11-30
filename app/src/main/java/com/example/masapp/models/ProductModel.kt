package com.example.masapp.models

data class ProductModel(
    val id: Long,
    val name: String,
    val code: String,
    val quantity: Long,
    val price: String,
    val catalogType: String,
    val unit: String
)
