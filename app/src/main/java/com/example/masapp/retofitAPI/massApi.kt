package com.example.masapp.retofitAPI

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
///
/// Project: MasApp
/// Created by pc on 11/07/2021.
///
*/class massApi {
    companion object{
        fun getData(): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .baseUrl("http://192.168.19.7:8080/api/v1/")
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
}