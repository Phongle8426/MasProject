package com.example.masapp.viewmodels

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.masapp.models.CivilianModel
import com.example.masapp.models.ProfileModel
import com.example.masapp.retofitAPI.massApi
import com.example.masapp.utils.SingleLiveEvent
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class CivilianViewModel: ViewModel() {
    val civilians = MutableLiveData<List<CivilianModel>>()
    val notify = MutableLiveData<String>()
    val messSaveFamily = MutableLiveData<String>()
    val messDeleteMember = SingleLiveEvent<String>()
    fun getFamily(id: Long, author: String){
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
                if(t is SocketTimeoutException){
                    notify.postValue("Kết nối quá hạn!")
                }else{
                    notify.postValue("Yêu cầu không thành công")
                }
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
                    Log.d("hung3", "onResponse: ${response.code()} ")
                    messSaveFamily.postValue("Thành công")
                }else{
                    messSaveFamily.postValue("Thất bại")
                }
            }

            override fun onFailure(call: Call<List<CivilianModel>>, t: Throwable) {
                if(t is SocketTimeoutException){
                    messSaveFamily.postValue("Kết nối quá hạn!")
                }
            }

        })
    }

    fun deleteMember(idMember: Long, author: String){
        val call = massApi().civilianService.deleteMember(idMember,"Bearer $author")
        call.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.code() == 200)
                    messDeleteMember.postValue("200")
                else
                    messDeleteMember.postValue("Thất bại")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                if(t is SocketTimeoutException){
                    messDeleteMember.postValue("Kết nối quá hạn!")
                }else{
                    messDeleteMember.postValue("Yêu cầu không thành công")
                }
            }
        })
    }
}