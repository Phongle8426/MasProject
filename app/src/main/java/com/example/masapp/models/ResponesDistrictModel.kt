package com.example.masapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
///
/// Project: MasApp
/// Created by pc on 11/14/2021.
///
*/
data class ResponesDistrictModel(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String
)