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
    val id: Long,
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
){
    override fun toString(): String {
        return "ProfileModel(id=$id, name='$name', phone='$phone', email='$email', district='$district', wardName='$wardName', groupNumber='$groupNumber', address='$address')"
    }
}