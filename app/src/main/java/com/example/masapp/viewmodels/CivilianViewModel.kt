package com.example.masapp.viewmodels

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.masapp.models.CivilianModel
import com.example.masapp.models.ProfileModel
import com.example.masapp.retofitAPI.massApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CivilianViewModel: ViewModel() {
    val civilians = MutableLiveData<List<CivilianModel>>()
    val notify = MutableLiveData<String>()
    fun getFamily(id: Int, author: String){
       val call = massApi().civilianService.getFamily(id,"Bearer  $author")
        call.enqueue(object: Callback<List<CivilianModel>> {
            override fun onResponse(
                call: Call<List<CivilianModel>>,
                response: Response<List<CivilianModel>>
            ) {
                if(response.code() == 200){
                    civilians.value = response.body()
                }else{
                    Log.d("error", response.code().toString())
                }
            }

            override fun onFailure(call: Call<List<CivilianModel>>, t: Throwable) {

            }

        })
    }

    fun saveFamily(civilian: CivilianModel, author: String){
        val call = massApi().civilianService.saveFamily(civilian,"Bearer $author")
        call.enqueue(object: Callback<List<CivilianModel>> {
            override fun onResponse(
                call: Call<List<CivilianModel>>,
                response: Response<List<CivilianModel>>
            ) {
                if (response.code() == 200){
                    notify.value = "Thành công"
                }else{
                    notify.value = response.code().toString()
                }
            }

            override fun onFailure(call: Call<List<CivilianModel>>, t: Throwable) {
            }

        })
    }
}