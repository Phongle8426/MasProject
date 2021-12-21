package com.example.masapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.masapp.models.AddressModel
import com.example.masapp.models.NewsModel
import com.example.masapp.models.RequestCartModel
import com.example.masapp.retofitAPI.massApi
import com.example.masapp.utils.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class NewsViewModel: ViewModel() {
    val listNews = MutableLiveData<List<NewsModel>>()
    val messListNews = MutableLiveData<String>()
    fun getNews(address: AddressModel, author: String){
        val call = massApi().newsService.getNews(address,"Bearer $author")
        call.enqueue(object : Callback<List<NewsModel>> {
            override fun onResponse(call: Call<List<NewsModel>>, response: Response<List<NewsModel>>) {
                Log.d("hunggg", "onResponse: ${response.code()} ")
                if (response.code() == 200){
                    listNews.postValue(response.body())
                    messListNews.postValue("Tin Tức")
                }else{
                    messListNews.postValue("Thất bại, vui lòng thử lại!")
                }
            }

            override fun onFailure(call: Call<List<NewsModel>>, t: Throwable) {
                if(t is SocketTimeoutException){
                    messListNews.postValue("Kết nối quá hạn!")
                }else{
                    messListNews.postValue("Yêu cầu không thành công")
                }
            }

        })
    }
}