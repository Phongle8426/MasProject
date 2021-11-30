package com.example.masapp.retofitAPI

import com.example.masapp.models.ResponesDistrictModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IDistrictService {
    @GET("ward/{id}")
    fun getWard(@Path("id") id: Int): Call<List<ResponesDistrictModel>>
}