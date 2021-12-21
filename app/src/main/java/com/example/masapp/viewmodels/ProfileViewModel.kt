package com.example.masapp.viewmodels

import android.app.Application
import android.app.ProgressDialog
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.masapp.MainActivity
import com.example.masapp.models.ProfileModel
import com.example.masapp.models.User
import com.example.masapp.models.UserRespones
import com.example.masapp.retofitAPI.massApi
import com.example.masapp.utils.SingleLiveEvent
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
    val responseMess = SingleLiveEvent<String>()

    fun getProfile(id: Long, author: String){
        val call = massApi().profileService.getProfile(id,"Bearer $author")
        call.enqueue(object: Callback<ProfileModel>{
            override fun onResponse(call: Call<ProfileModel>, response: Response<ProfileModel>) {
                if(response.code() == 200){
                    profile.postValue(response.body())
                }else{
                    Log.d("HUng", "onResponse: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ProfileModel>, t: Throwable) {
            }

        })
    }

    fun updateProfile(profile: ProfileModel, author: String){
        val call = massApi().profileService.updateProfile(profile,"Bearer $author")
        call.enqueue(object: Callback<UserRespones>{
            override fun onResponse(call: Call<UserRespones>, response: Response<UserRespones>) {
                if (response.code() == 200){
                    responseMess.postValue("Cập nhật thành công!")
                }else{
                    responseMess.postValue("Cập nhật thất bại, vui lòng thử lại!")
                    Log.d("HUng1", "onResponse: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserRespones>, t: Throwable) {
                responseMess.postValue("Cập nhật thất bại!")
            }

        })
    }
}