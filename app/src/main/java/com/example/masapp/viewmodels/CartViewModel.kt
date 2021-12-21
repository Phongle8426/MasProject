package com.example.masapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.masapp.models.ProductModel
import com.example.masapp.models.RequestCartModel
import com.example.masapp.models.RequestProductModel
import com.example.masapp.retofitAPI.massApi
import com.example.masapp.utils.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class CartViewModel : ViewModel() {
    private val _carts = MutableLiveData<List<RequestProductModel>>()
    val carts get() = _carts
    val cartsHistory = MutableLiveData<List<RequestCartModel>>()
    val cartDetailHistory = MutableLiveData<RequestCartModel>()
    val products = MutableLiveData<List<ProductModel>>()
    val totalPrice = MutableLiveData<String>()
    val messageSaveCart = SingleLiveEvent<String>()
    val messageGetCart = SingleLiveEvent<String>()
    val messageGetProduct = SingleLiveEvent<String>()
    val messageGetCartById = SingleLiveEvent<String>()


    fun addProduct(products: List<RequestProductModel>) {
        _carts.postValue(products)
    }
    fun calculatePrice(){
        var total = 0
        if (_carts.value!!.isNotEmpty()){
            for (it in _carts.value!!){
                total += (it.quantity * it.price.toInt())
            }
            totalPrice.postValue(total.toString())
        }
    }

    fun saveCart(cart: RequestCartModel,author: String){
        val call = massApi().cartService.saveCart(cart,"Bearer $author")
        call.enqueue(object : Callback<List<RequestCartModel>>{
            override fun onResponse(call: Call<List<RequestCartModel>>, response: Response<List<RequestCartModel>>) {
                if (response.code() == 200){
                    messageSaveCart.postValue("Đặt hàng thành công!")
                }else{
                    messageSaveCart.postValue("Thất bại, vui lòng thử lại!")
                }
            }

            override fun onFailure(call: Call<List<RequestCartModel>>, t: Throwable) {
                if(t is SocketTimeoutException){
                    messageSaveCart.postValue("Kết nối quá hạn!")
                }else{
                    messageSaveCart.postValue("Yêu cầu không thành công")
                }
            }

        })
    }

    fun getCartHistory(id: Long, author: String){
        val call = massApi().cartService.getCartHistory(id,"Bearer $author")
        call.enqueue(object : Callback<List<RequestCartModel>>{
            override fun onResponse(call: Call<List<RequestCartModel>>, response: Response<List<RequestCartModel>>) {
                if (response.code() == 200){
                    cartsHistory.postValue(response.body())
                }else{
                    messageGetCart.postValue("Thất bại, vui lòng thử lại!")
                }
            }

            override fun onFailure(call: Call<List<RequestCartModel>>, t: Throwable) {
                if(t is SocketTimeoutException){
                    messageGetCart.postValue("Kết nối quá hạn!")
                }else{
                    messageGetCart.postValue("Yêu cầu không thành công")
                }
            }

        })
    }

    fun getProduct(nameDistrict: String,nameWard: String, author: String){
        val call = massApi().cartService.getProduct(nameDistrict,nameWard,"Bearer $author")
        call.enqueue(object : Callback<List<ProductModel>>{
            override fun onResponse(call: Call<List<ProductModel>>, response: Response<List<ProductModel>>) {
                if (response.code() == 200){
                    Log.d("TAG", "onResponse: thanh cong")
                    products.postValue(response.body())
                }else{
                    messageGetProduct.postValue("Thất bại, vui lòng thử lại!")
                }
            }

            override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                if(t is SocketTimeoutException){
                    messageGetProduct.postValue("Kết nối quá hạn!")
                }else{
                    messageGetProduct.postValue("Yêu cầu không thành công")
                }
            }

        })
    }

     fun getCartById(idCart: Long, author: String){
         val call = massApi().cartService.getCartById(idCart,"Bearer $author")
         call.enqueue(object : Callback<RequestCartModel>{
             override fun onResponse(call: Call<RequestCartModel>, response: Response<RequestCartModel>) {
                 if (response.code() == 200){
                     Log.d("TAG", "onResponse: thanh cong")
                     cartDetailHistory.postValue(response.body())
                 }else{
                     messageGetCartById.postValue("Thất bại, vui lòng thử lại!")
                 }
             }

             override fun onFailure(call: Call<RequestCartModel>, t: Throwable) {
                 if(t is SocketTimeoutException){
                     messageGetCartById.postValue("Kết nối quá hạn!")
                 }else{
                     messageGetCartById.postValue("Yêu cầu không thành công")
                 }
             }

         })

    }
}