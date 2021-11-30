package com.example.masapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
///
/// Project: MasApp
/// Created by pc on 11/24/2021.
///
*/class ResponeCivilianModel(
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("birthDay")
    @Expose
    val birthDay: String,
    @SerializedName("gender")
    @Expose
    val gender: String,
    @SerializedName("phone")
    @Expose
    val phone: String,
    @SerializedName("cccd")
    @Expose
    val cccd: String,
    @SerializedName("familyId")
    @Expose
    val familyId: Int,
    @SerializedName("vaccineList")
    @Expose
    val vaccineList: List<VaccineModel>
)