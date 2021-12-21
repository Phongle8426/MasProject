package com.example.masapp.retofitAPI

import com.example.masapp.models.User
import com.example.masapp.models.UserRespones
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface IUserService {
    @POST("auth/login")
    fun login(@Body user: User): Call<UserRespones>

    @PUT("users/password/{id}/{newPassword}")
    fun changePass(
        @Path("id") id: Long,
        @Path("newPassword") newPassword: String,
        @Header("Authorization") authorization: String
    ): Call<ResponseBody>
}