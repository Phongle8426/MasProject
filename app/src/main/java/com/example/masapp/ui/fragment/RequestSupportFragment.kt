package com.example.masapp.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.masapp.adapter.ProductAdapter
import com.example.masapp.databinding.FragmentRequestSupportBinding
import com.example.masapp.models.RequestCartModel
import com.example.masapp.models.RequestProductModel
import com.example.masapp.ui.activity.LoginActivity
import com.example.masapp.utils.CurrencyConvert
import com.example.masapp.utils.DialogConfirm
import com.example.masapp.utils.ItemClick
import com.example.masapp.viewmodels.CartViewModel
import com.example.masapp.viewmodels.ProfileViewModel
import java.util.*

class RequestSupportFragment : Fragment() {
    companion object{
        const val DATE_REQUEST = "day_request"
        const val DATE = "today"
    }
    private lateinit var binding: FragmentRequestSupportBinding
    private lateinit var viewModel : CartViewModel
    private lateinit var viewModelProfile: ProfileViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPrfsDateRequest: SharedPreferences
    private var uId: Long = 0
    private var uToken = ""
    private lateinit var totalPrice: String
    private  var quantityProduct: String =""
    private lateinit var listProduct: List<RequestProductModel>

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestSupportBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        sharedPreferences = requireContext().getSharedPreferences(LoginActivity.sharedPrefFile, Context.MODE_PRIVATE)
        sharedPrfsDateRequest = requireContext().getSharedPreferences(DATE_REQUEST, Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        viewModelProfile = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        checkDateRequest()
        getUId()
        viewModel.carts.observe(requireActivity(), {
            Log.d("requestSupportProduct", it.toString())
            binding.rcvProduct.adapter = ProductAdapter(it,viewModel,callback)
            binding.rcvProduct.layoutManager = LinearLayoutManager(context)
            calculatePrice(it)
            quantityProduct = it.size.toString()
            listProduct = it
        })
        viewModel.calculatePrice()
        viewModel.totalPrice.observe(requireActivity(), Observer {
            binding.tvTotal.text = CurrencyConvert().convertCurrency(it)
            totalPrice = it
        })

        viewModel.messageSaveCart.observe(requireActivity(),{
            it?.let{
                Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnRequest.setOnClickListener {
            showDialogConfirm()
        }

        binding.btnBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        return binding.root
    }

    fun calculatePrice(listProduct: List<RequestProductModel>){
        for (it in listProduct){
            it.totalPrice = (it.quantity * it.price.toInt()).toString()
        }
    }

    private fun getUId(){
        uId = sharedPreferences.getLong(LoginActivity.USER_ID,0)
        uToken = sharedPreferences.getString(LoginActivity.USER_TOKEN,"1")!!
    }
    private fun checkDateRequest(){
        val requestDate = sharedPrfsDateRequest.getString(DATE,"")
        if(requestDate == getCurrentDay() && requestDate != ""){
            binding.btnRequest.isEnabled = false
        }
    }

    private fun showDialogConfirm(){
        DialogConfirm.getInstanceDialog()
            .setMessage("Đặt mua những món trong danh sách này?"). apply {
                onAccept = {
                    requestProduct()
                }
            }.show(parentFragmentManager, "RequestSupportFragment")
    }

    private fun requestProduct(){
        if(quantityProduct != ""){
            var name = ""
            var phone = ""
            var district =""
            var wardName =""
            var groupNumber: Long = 0

            viewModelProfile.getProfile(uId,uToken)
            viewModelProfile.profile.observe(requireActivity(), {
                if(it != null){
                    name = it.name
                    phone = it.phone
                    district = it.district
                    wardName = it.wardName
                    groupNumber = it.groupNumber.toLong()
                }
            })
            val note = binding.edtNote.text.toString()
            val cart = RequestCartModel(0,uId,name,totalPrice,quantityProduct.toInt(),phone,wardName,district,groupNumber,listProduct,note,0,"")
            viewModel.saveCart(cart,uToken)
            saveDateRequestCart()
        }else{
            Toast.makeText(context,"Bạn chưa chọn sản phẩm!", Toast.LENGTH_SHORT).show()
        }
    }



    fun saveDateRequestCart(){
        val editor:SharedPreferences.Editor =  sharedPrfsDateRequest.edit()
        editor.putString(DATE,getCurrentDay())
        editor.apply()
        editor.commit()
    }

    private fun getCurrentDay(): String = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()

    private val callback = object: ItemClick {
        override fun itemClick(model: Any) {
        }

        override fun itemClickWithCount(model: Any, countProduct: Int) {
            if (countProduct < 5)
                viewModel.calculatePrice()
            else
                Toast.makeText(requireContext(),"Bạn đã đạt số lượng tối đa!", Toast.LENGTH_SHORT).show()
        }
    }

}