package com.example.masapp.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.masapp.R
import com.example.masapp.adapter.CartHistoryAdapter
import com.example.masapp.databinding.FragmentOderHistoryBinding
import com.example.masapp.models.CivilianModel
import com.example.masapp.models.RequestCartModel
import com.example.masapp.ui.activity.LoginActivity
import com.example.masapp.utils.ItemClick
import com.example.masapp.viewmodels.CartViewModel
import com.example.masapp.viewmodels.ProfileViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OderHistoryFragment : Fragment() {
    private lateinit var binding: FragmentOderHistoryBinding
    private lateinit var viewModel : CartViewModel
    private lateinit var viewModelProfile: ProfileViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private var uId: Long = 0
    private var uToken = ""
    private lateinit var animationLoading: Animation
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOderHistoryBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        viewModelProfile = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        sharedPreferences = requireContext().getSharedPreferences(LoginActivity.sharedPrefFile, Context.MODE_PRIVATE)
        animationLoading = AnimationUtils.loadAnimation(requireActivity(), R.anim.blink)
        binding.iconLoading.apply {
            visibility = View.VISIBLE
            startAnimation(animationLoading)
        }
        getUId()
        viewModel.getCartHistory(uId,uToken)
        viewModel.cartsHistory.observe(requireActivity(),{
            lifecycleScope.launch {
                delay(300)
                binding.rcvHistoryCart.adapter = CartHistoryAdapter(it,callback)
                binding.rcvHistoryCart.layoutManager = LinearLayoutManager(context)
                if (it.isEmpty())
                    binding.imgEmpty.visibility = View.VISIBLE
                binding.iconLoading.apply {
                    clearAnimation()
                    visibility = View.GONE
                }
            }
        })

        viewModel.messageGetCart.observe(requireActivity(),{
            it?.let { Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show() }
        })

        binding.btnBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        return binding.root
    }

    private fun getUId(){
        uId = sharedPreferences.getLong(LoginActivity.USER_ID,0)
        uToken = sharedPreferences.getString(LoginActivity.USER_TOKEN,"1")!!
    }

    private val callback = object : ItemClick{
        override fun itemClick(model: Any) {
            val cartsHistory = model as RequestCartModel
            val bundle = Bundle()
            cartsHistory.id?.let { bundle.putLong("idCart", it) }
            OderHistoryFragmentDirections.actionOderHistoryFragmentToDetailHistoryFragment().apply {
                findNavController().navigate(R.id.action_oderHistoryFragment_to_detailHistoryFragment,bundle)
            }
        }

        override fun itemClickWithCount(model: Any, countProduct: Int) {
        }

    }
}