package com.example.masapp.retofitAPI

import com.example.masapp.models.User
import com.example.masapp.models.UserRespones
import retrofit2.Call
import retrofit2.http.*

interface IUserService {
    @POST("auth/login")
    fun login(@Body user: User): Call<UserRespones>
}