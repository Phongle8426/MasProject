package com.example.masapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.masapp.models.ProfileModel
import com.example.masapp.models.User
import com.example.masapp.retofitAPI.massApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
///
/// Project: MasApp
/// Created by pc on 11/10/2021.
///
*/class ProfileViewModel: ViewModel() {
    val profile = MutableLiveData<ProfileModel>()
    val responseMess = MutableLiveData<String>()
    fun getProfile(id: Int, author: String){
        val call = massApi().profileService.getProfile(id,"Bearer $author")
        call.enqueue(object: Callback<ProfileModel>{
            override fun onResponse(call: Call<ProfileModel>, response: Response<ProfileModel>) {
                if(response.code() == 200){
                    profile.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
            }

        })
    }

    fun updateProfile(profile: ProfileModel, author: String){
        val call = massApi().profileService.updateProfile(profile,"Bearer $author")
        call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.code() == 200){
                    responseMess.postValue("Cập nhật thành công!")
                }else{
                    responseMess.postValue("Cập nhật thất bại, vui lòng thử lại!")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                responseMess.postValue("Cập nhật thất bại, vui lòng thử lại!")
            }

        })
    }
}