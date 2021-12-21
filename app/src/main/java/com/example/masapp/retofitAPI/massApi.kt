package com.example.masapp.retofitAPI

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class massApi {
    companion object{
        var okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
        fun getData(): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .baseUrl("http://192.168.0.23:8080/api/v1/")
                .client(okHttpClient)
                .build()
        }
    }
    val userService: IUserService by lazy {
        getData().create(IUserService::class.java)
    }
    val profileService: IProfileService by lazy{
        getData().create(IProfileService::class.java)
    }
    val wardService: IDistrictService by lazy{
        getData().create(IDistrictService::class.java)
    }
    val civilianService: ICivilianService by lazy{
        getData().create(ICivilianService::class.java)
    }
    val cartService: IProductService by lazy{
        getData().create(IProductService::class.java)
    }
    val newsService: INewsService by lazy{
        getData().create(INewsService::class.java)
    }
}