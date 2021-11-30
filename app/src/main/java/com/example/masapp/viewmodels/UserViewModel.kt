package com.example.masapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.masapp.models.User
import com.example.masapp.models.UserRespones
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
/// Created by pc on 11/07/2021.
///
*/class UserViewModel: ViewModel() {

    val userRespones = MutableLiveData<UserRespones>()
    val messageLoginFailure = MutableLiveData<String>()
    fun getUser(user: User){
        val call = massApi().userService.login(user)
       call.enqueue(object : Callback<UserRespones> {
           override fun onResponse(call: Call<UserRespones>, response: Response<UserRespones>) {
               if (response.code() == 200){
                   CoroutineScope(Dispatchers.Main).launch {
                       userRespones.value = response.body()
                   }
               }
               if(response.code() == 400){
                   messageLoginFailure.postValue("Số điện thoại hoặc mật khẩu không đúng!")
               }else
                   Log.d("all",response.code().toString())
           }

           override fun onFailure(call: Call<UserRespones>, t: Throwable) {
           }

       })
    }
}