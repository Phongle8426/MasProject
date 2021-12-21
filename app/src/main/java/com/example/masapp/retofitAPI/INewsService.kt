package com.example.masapp.retofitAPI

import com.example.masapp.models.AddressModel
import com.example.masapp.models.NewsModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface INewsService {
    @POST("manager/news/all")
    fun getNews(
        @Body address: AddressModel,
        @Header("Authorization") authorization: String
    ): Call<List<NewsModel>>
}