package com.example.masapp.retofitAPI

import com.example.masapp.models.CivilianModel
import com.example.masapp.models.ProfileModel
import com.example.masapp.models.User
import com.example.masapp.models.UserRespones
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ICivilianService {
    @GET("civilians/{id}")
    fun getFamily(
        @Path("id") id: Long,
        @Header("Authorization") authorization: String
    ): Call<List<CivilianModel>>


    @POST("civilians/family")
    fun saveFamily(
        @Body civilian: CivilianModel ,
        @Header("Authorization") authorization: String
    ): Call<List<CivilianModel>>

    @DELETE("civilians/family/{civilianId}")
    fun deleteMember(
        @Path("civilianId") civilianId: Long,
        @Header("Authorization") authorization: String
    ): Call<ResponseBody>
}