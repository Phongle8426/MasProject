package com.example.masapp.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.masapp.adapter.PreviewProductAdapter
import com.example.masapp.databinding.FragmentPreviewProductBinding
import com.example.masapp.models.ProductModel
import com.example.masapp.models.RequestProductModel
import com.example.masapp.ui.activity.LoginActivity
import com.example.masapp.utils.ItemClick
import com.example.masapp.viewmodels.CartViewModel
import com.example.masapp.viewmodels.ProfileViewModel

class ProductFragment : Fragment() {
    private lateinit var binding: FragmentPreviewProductBinding
    private lateinit var viewModelProfile: ProfileViewModel
    private lateinit var animationLoading: Animation
    private var listRequestProduct = mutableListOf<RequestProductModel>()
    private lateinit var viewModel : CartViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private var uId: Long = 0
    private var uToken = ""
    private var district = ""
    private var ward = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPreviewProductBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        viewModelProfile = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        animationLoading = AnimationUtils.loadAnimation(requireContext(), com.example.masapp.R.anim.blink)
        sharedPreferences = requireContext().getSharedPreferences(
            LoginActivity.sharedPrefFile,
            Context.MODE_PRIVATE
        )
        getUId()
        getProfile()
        if(viewModel.carts.value!= null)
            listRequestProduct = viewModel.carts.value as MutableList<RequestProductModel>
        viewModel.getProduct(district,ward,uToken)
        binding.iconLoading.apply {
            visibility = View.VISIBLE
            startAnimation(animationLoading)
        }
        viewModel.products.observe(requireActivity(),{
            Log.d("Hung12", "onCreateView: ${listRequestProduct.size}")
            binding.rcvProduct.adapter = PreviewProductAdapter(it,listRequestProduct,callback)
            binding.rcvProduct.layoutManager = LinearLayoutManager(context)
            if (it.isEmpty())
                binding.imgEmpty.visibility = View.VISIBLE
            binding.iconLoading.apply {
                clearAnimation()
                visibility = View.GONE
            }
        })
        binding.btnOrder.setOnClickListener {
            Log.e("huhu", "oder: ${listRequestProduct.size}" )
            if(listRequestProduct.size > 0){
                viewModel.addProduct(listRequestProduct)
                ProductFragmentDirections.actionProductFragmentToRequestSupportFragment().apply {
                    findNavController().navigate(this)
                }
            }else{
                Toast.makeText(context,"bạn chưa chọn sản phẩm",Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        return binding.root
    }

    private val callback = object: ItemClick {
        override fun itemClick(model: Any) {
            val product = model as ProductModel
            var isExists = false
            Log.e("Sang", "itemClick: ${product.id}" )
            for(it in listRequestProduct){
                if(product.id == it.productId){
                    Log.e("Sang1", "itemClick: ${it.productId}" )
                    listRequestProduct.remove(it)
                    isExists = true
                    Log.e("Hung13", "remove: ${listRequestProduct.size}", )
                    break
                }
            }
            if (!isExists){
                val name = product.name
                val price = product.price
                val productId = product.id
                listRequestProduct.add(RequestProductModel(name,1,price,"",productId))
                Log.e("Hung14", "add: ${listRequestProduct.size}", )
            }
        }

        override fun itemClickWithCount(model: Any, countProduct: Int) {
        }
    }

    private fun getProfile() {
        viewModelProfile.getProfile(uId, uToken)
        viewModelProfile.profile.observe(requireActivity(), {
            if (it != null) {
                district = it.district
                ward = it.wardName
            }
        })
    }

    private fun getUId() {
        uId = sharedPreferences.getLong(LoginActivity.USER_ID, 0)
        uToken = sharedPreferences.getString(LoginActivity.USER_TOKEN, "1")!!
    }
}