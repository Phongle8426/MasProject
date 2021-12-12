package com.example.masapp.retofitAPI

import com.example.masapp.models.CivilianModel
import com.example.masapp.models.ProductModel
import com.example.masapp.models.RequestCartModel
import retrofit2.Call
import retrofit2.http.*

interface IProductService {

    @GET("product/{district}/{wardName}")
    fun getProduct(
        @Path("district") district: String,
        @Path("wardName") wardName: String,
        @Header("Authorization") authorization: String
    ): Call<List<ProductModel>>

    @GET("cart/user/{id}")
    fun getCartHistory(
        @Path("id") id: Long,
        @Header("Authorization") authorization: String
    ): Call<List<RequestCartModel>>

    @GET("cart/{idCart}")
    fun getCartById(
        @Path("idCart") id: Long,
        @Header("Authorization") authorization: String
    ): Call<RequestCartModel>

    @POST("cart")
    fun saveCart(
        @Body cart: RequestCartModel,
        @Header("Authorization") authorization: String
    ): Call<List<RequestCartModel>>


}