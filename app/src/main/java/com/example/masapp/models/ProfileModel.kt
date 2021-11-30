package com.example.masapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
///
/// Project: MasApp
/// Created by pc on 11/10/2021.
///
*/class ProfileModel(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("phone")
    @Expose
    val phone: String,
    @SerializedName("email")
    @Expose
    val email: String,
    @SerializedName("district")
    @Expose
    val district: String,
    @SerializedName("wardName")
    @Expose
    val wardName: String,
    @SerializedName("groupNumber")
    @Expose
    val groupNumber: String,
    @SerializedName("address")
    @Expose
    val address: String,
//    val familyName: String,
//    val userName: String,
//    val passWord: String,
//    val age: String,
//    val gender: String,
//    val birthDay: String,
//    val roles: List<RoleModel>,
//    val civilians: List<CivilianModel>,
//    val cartList: List<CartModel>,
//    val vaccineList: List<VaccineModel>,
)