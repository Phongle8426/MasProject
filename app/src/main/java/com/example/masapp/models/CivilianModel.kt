package com.example.masapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CivilianModel(
    @SerializedName("id")
    @Expose
    val id: Long,
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
    val familyId: Long,
    @SerializedName("vaccineStatus")
    @Expose
    val vaccineStatus: String,
    @SerializedName("vaccineList")
    @Expose
    val vaccineList: List<VaccineModel>
): Serializable{
    override fun toString(): String {
        return "CivilianModel(name='$name', birthDay='$birthDay', gender='$gender', phone='$phone', cccd='$cccd', familyId=$familyId, vaccineStatus='$vaccineStatus', vaccineList=$vaccineList)"
    }
}