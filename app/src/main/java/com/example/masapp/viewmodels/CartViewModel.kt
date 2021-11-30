package com.example.masapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.masapp.models.ProductModel
import com.example.masapp.models.RequestCartModel
import com.example.masapp.models.RequestProductModel
import com.example.masapp.retofitAPI.massApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
///
/// Project: MasApp
/// Created by pc on 11/27/2021.
///
*/class CartViewModel : ViewModel() {
    val _carts = MutableLiveData<List<RequestProductModel>>()
    val carts get() = _carts
    val cart get() = _carts.value
    val totalPrice = MutableLiveData<String>()
    val responseMess = MutableLiveData<String>()

    fun addProduct(products: List<RequestProductModel>) {
        _carts.value = listOf()
        _carts.postValue(products)
    }
    fun calculatePrice(){
        var total = 0
        for (it in _carts.value!!){
            total += (it.quantity * it.price.toInt())
        }
        totalPrice.value = total.toString()
    }

    fun saveCart(cart: RequestCartModel,author: String){
        val call = massApi().cartService.saveCart(cart,"Bearer $author")
        call.enqueue(object : Callback<List<RequestCartModel>>{
            override fun onResponse(call: Call<List<RequestCartModel>>, response: Response<List<RequestCartModel>>) {
                if (response.code() == 200){
                    responseMess.postValue("Đặt hàng thành công!")
                }else{
                    responseMess.postValue("Thất bại, vui lòng thử lại!")
                }
            }

            override fun onFailure(call: Call<List<RequestCartModel>>, t: Throwable) {

            }

        })
    }
}