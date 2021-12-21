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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.masapp.R
import com.example.masapp.adapter.DetailCartHistoryAdapter
import com.example.masapp.databinding.FragmentDetailHistoryBinding
import com.example.masapp.models.CivilianModel
import com.example.masapp.ui.activity.LoginActivity
import com.example.masapp.viewmodels.CartViewModel
import com.example.masapp.viewmodels.ProfileViewModel

class DetailHistoryFragment : Fragment() {
    private lateinit var binding: FragmentDetailHistoryBinding
    private lateinit var viewModelProfile: ProfileViewModel
    private lateinit var viewModel : CartViewModel
    private lateinit var animationLoading: Animation
    private lateinit var sharedPreferences: SharedPreferences
    private var uId: Long = 0
    private var uToken = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailHistoryBinding.inflate(layoutInflater)
        val idCart = arguments?.getLong("idCart")
        Log.d("idCart", "onCreateView: $idCart")
        viewModelProfile = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        animationLoading = AnimationUtils.loadAnimation(requireContext(), com.example.masapp.R.anim.blink)
        sharedPreferences = requireContext().getSharedPreferences(
            LoginActivity.sharedPrefFile,
            Context.MODE_PRIVATE
        )
        getUId()
        if (idCart != null) {
            viewModel.getCartById(idCart,uToken)
        }
        viewModel.cartDetailHistory.observe(requireActivity(),{
            Log.d("Sang", "onCreateView: ${it.listProduct}")
            binding.rcvDetailHistoryCart.adapter = DetailCartHistoryAdapter(it.listProduct)
            binding.detailOrder = it
            when(it.status){
                0 -> {
                    binding.imgDone.setImageResource(R.drawable.ic_ok_dis)
                    binding.imgCancel.setImageResource(R.drawable.ic_cancel_dis)
                    binding.imgPicked.setImageResource(R.drawable.ic_picked_dis)
                    binding.imgInprogress.setImageResource(R.drawable.ic_wait)
                }
                1 -> {
                    binding.imgInprogress.setImageResource(R.drawable.ic_wait_dis)
                    binding.imgCancel.setImageResource(R.drawable.ic_cancel_dis)
                    binding.imgPicked.setImageResource(R.drawable.ic_picked_dis)
                    binding.imgDone.setImageResource(R.drawable.ic_ok)
                }
                2 -> {
                    binding.imgInprogress.setImageResource(R.drawable.ic_wait_dis)
                    binding.imgDone.setImageResource(R.drawable.ic_ok_dis)
                    binding.imgPicked.setImageResource(R.drawable.ic_picked_dis)
                    binding.imgCancel.setImageResource(R.drawable.ic_cancel)
                }
                3 -> {
                    binding.imgInprogress.setImageResource(R.drawable.ic_wait_dis)
                    binding.imgDone.setImageResource(R.drawable.ic_ok_dis)
                    binding.imgCancel.setImageResource(R.drawable.ic_cancel_dis)
                    binding.imgPicked.setImageResource(R.drawable.ic_picked)
                }
            }
        })

        binding.btnBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getUId() {
        uId = sharedPreferences.getLong(LoginActivity.USER_ID, 0)
        uToken = sharedPreferences.getString(LoginActivity.USER_TOKEN, "1")!!
    }
}