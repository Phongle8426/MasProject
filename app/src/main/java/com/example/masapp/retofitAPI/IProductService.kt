package com.example.masapp.retofitAPI

import com.example.masapp.models.CivilianModel
import com.example.masapp.models.RequestCartModel
import retrofit2.Call
import retrofit2.http.*

interface IProductService {
    @GET("civilians/{id}")
    fun getFamily(
        @Path("id") id: Int,
        @Header("Authorization") authorization: String
    ): Call<List<CivilianModel>>


    @POST("cart")
    fun saveCart(
        @Body cart: RequestCartModel,
        @Header("Authorization") authorization: String
    ): Call<List<RequestCartModel>>
}