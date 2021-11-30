package com.example.masapp.retofitAPI

import com.example.masapp.models.CivilianModel
import com.example.masapp.models.ProfileModel
import com.example.masapp.models.User
import com.example.masapp.models.UserRespones
import retrofit2.Call
import retrofit2.http.*

interface ICivilianService {
    @GET("civilians/{id}")
    fun getFamily(
        @Path("id") id: Int,
        @Header("Authorization") authorization: String
    ): Call<List<CivilianModel>>


    @POST("civilians/family")
    fun saveFamily(
        @Body civilian: CivilianModel ,
        @Header("Authorization") authorization: String
    ): Call<List<CivilianModel>>
}