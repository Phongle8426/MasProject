package com.example.masapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * user Model
 **/
class UserRespones(
    @SerializedName("token")
    @Expose
    val token: String,
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("userName")
    @Expose
    val userName: String,
    @SerializedName("wardName")
    @Expose
    val wardName:String,
    @SerializedName("district")
    @Expose
    val district: String,
    @SerializedName("groupNumber")
    @Expose
    val groupNumber: Int,
    @SerializedName("roles")
    @Expose
    val roles: List<RoleModel>
){
    override fun toString(): String {
        return "UserRespones(token='$token', id=$id, userName='$userName', wardName='$wardName', groupNumber=$groupNumber, roles=$roles)"
    }
}