package com.example.masapp.ui.fragment

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
import com.example.masapp.adapter.PreviewProductAdapter
import com.example.masapp.databinding.FragmentPreviewProductBinding
import com.example.masapp.models.CategoryModel
import com.example.masapp.models.ProductModel
import com.example.masapp.models.RequestProductModel
import com.example.masapp.utils.ItemClick
import com.example.masapp.viewmodels.CartViewModel

class ProductFragment : Fragment() {
    private lateinit var binding: FragmentPreviewProductBinding
    private var listRequestProduct = mutableListOf<RequestProductModel>()
    private lateinit var viewModel : CartViewModel

    private val listProducts = listOf(
        ProductModel(1,"Bún đậu nắm tôm","bundau",20,"60000","","mẹt"),
        ProductModel(2,"bun cha ca","bundau",20,"60000","","mẹt"),
        ProductModel(3,"Thit heo","bundau",20,"60000","","mẹt"),
        ProductModel(4,"Thit ga","bundau",20,"60000","","mẹt"),
        ProductModel(5,"Gao","bundau",20,"60000","","mẹt"),
        ProductModel(6,"Bun","bundau",20,"60000","","mẹt"),
        ProductModel(7,"Ca","bundau",20,"60000","","mẹt"),
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPreviewProductBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        Log.d("TAG", "onCreateView: klalalal")
//       viewModel.cart?.let { listRequestProduct.addAll(it) }
        if(viewModel.carts.value!= null)
            listRequestProduct = viewModel.carts.value as MutableList<RequestProductModel>
        binding.rcvProduct.adapter = PreviewProductAdapter(listProducts,listRequestProduct,callback)
        binding.rcvProduct.layoutManager = LinearLayoutManager(context)
        binding.btnOrder.setOnClickListener {
            Log.e("huhu", "oder: ${listRequestProduct.size}", )
            if(listRequestProduct.size > 0){
                viewModel.addProduct(listRequestProduct)
                ProductFragmentDirections.actionProductFragmentToRequestSupportFragment().apply {
                    findNavController().navigate(this)
                }
            }else{
                Toast.makeText(context,"bạn chưa chọn sản phẩm",Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private val callback = object: ItemClick {
        override fun itemClick(model: Any) {
            val product = model as ProductModel
            var isExists = false
            for(it in listRequestProduct){
                if(product.id == it.productId){
                    listRequestProduct.remove(it)
                    isExists = true
                    Log.e("huhu", "remove: ${listRequestProduct.size}", )
                }
            }
            if (!isExists){
                val name = product.name
                val price = product.price
                val productId = product.id
                listRequestProduct.add(RequestProductModel(name,1,price,"",productId))
                Log.e("huhu", "add: ${listRequestProduct.size}", )
            }
        }

    }
}