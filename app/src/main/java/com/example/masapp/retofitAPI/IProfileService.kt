package com.example.masapp.retofitAPI

import com.example.masapp.models.ProfileModel
import com.example.masapp.models.User
import retrofit2.Call
import retrofit2.http.*

interface IProfileService {

    @PUT("users")
    fun updateProfile(
        @Body profile: ProfileModel,
        @Header("Authorization") authorization: String
    ): Call<String>

    @GET("users/id/{id}")
    fun getProfile(
        @Path("id") id: Int,
        @Header("Authorization") authorization: String
    ): Call<ProfileModel>
}