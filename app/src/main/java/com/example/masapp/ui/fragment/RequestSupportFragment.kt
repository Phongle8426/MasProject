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
import com.example.masapp.R
import com.example.masapp.adapter.FamilyAdapter
import com.example.masapp.adapter.ProductAdapter
import com.example.masapp.databinding.FragmentRequestSupportBinding
import com.example.masapp.models.CategoryModel
import com.example.masapp.models.ProductModel
import com.example.masapp.models.RequestCartModel
import com.example.masapp.models.RequestProductModel
import com.example.masapp.ui.activity.LoginActivity
import com.example.masapp.utils.ItemClick
import com.example.masapp.viewmodels.CartViewModel
import com.example.masapp.viewmodels.ProfileViewModel
import com.example.masapp.viewmodels.UserViewModel

class RequestSupportFragment : Fragment() {
    private lateinit var binding: FragmentRequestSupportBinding
    private lateinit var viewModel : CartViewModel
    private lateinit var viewModelProfile: ProfileViewModel
    private lateinit var sharedPreferences: SharedPreferences
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
        viewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        viewModelProfile = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        getUId()

        viewModel.carts.observe(requireActivity(), {
            Log.d("requestSupportProduct", it.toString())
            binding.rcvProduct.adapter = ProductAdapter(it,callback)
            binding.rcvProduct.layoutManager = LinearLayoutManager(context)
            calculatePrice(it)
            quantityProduct = it.size.toString()
            listProduct = it
        })
        viewModel.calculatePrice()
        viewModel.totalPrice.observe(requireActivity(), Observer {
            binding.tvTotal.text = "$it đ"
            totalPrice = it
        })

        viewModel.responseMess.observe(requireActivity(),{
            if(it!=null)
                Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
            else Toast.makeText(requireContext(),"không có phản hồi",Toast.LENGTH_SHORT).show()
        })

        binding.btnRequest.setOnClickListener {
            requestProduct()
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

            val cart = RequestCartModel(0,uId,name,totalPrice,quantityProduct.toInt(),phone,wardName,district,groupNumber,listProduct,0,"")
            viewModel.saveCart(cart,uToken)
        }else{
            Toast.makeText(context,"Bạn chưa chọn sản phẩm!", Toast.LENGTH_SHORT).show()
        }
    }

    private val callback = object: ItemClick {
        override fun itemClick(model: Any) {
            viewModel.calculatePrice()
        }
    }

}