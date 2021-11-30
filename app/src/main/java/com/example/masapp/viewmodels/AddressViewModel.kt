package com.example.masapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.masapp.models.ResponesDistrictModel
import com.example.masapp.retofitAPI.massApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressViewModel: ViewModel()  {
    val wardRespones = MutableLiveData<List<ResponesDistrictModel>>()
    fun getWard(id: Int){
        val call = massApi().wardService.getWard(id)
        call.enqueue(object: Callback<List<ResponesDistrictModel>>{
            override fun onResponse(
                call: Call<List<ResponesDistrictModel>>,
                response: Response<List<ResponesDistrictModel>>
            ) {
                Log.e("hic", response.code().toString())
                Log.d("hiho",response.body().toString())
                if (response.code() == 200){
                    wardRespones.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<ResponesDistrictModel>>, t: Throwable) {

            }

        })
    }
}