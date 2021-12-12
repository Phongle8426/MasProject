package com.example.masapp.retofitAPI

import com.example.masapp.models.ProfileModel
import com.example.masapp.models.User
import com.example.masapp.models.UserRespones
import retrofit2.Call
import retrofit2.http.*

interface IProfileService {

    @PUT("users")
    fun updateProfile(
        @Body profile: ProfileModel,
        @Header("Authorization") authorization: String
    ): Call<UserRespones>

    @GET("users/id/{id}")
    fun getProfile(
        @Path("id") id: Long,
        @Header("Authorization") authorization: String
    ): Call<ProfileModel>
}