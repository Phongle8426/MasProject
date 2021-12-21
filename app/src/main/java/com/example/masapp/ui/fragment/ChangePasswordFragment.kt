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
import com.example.masapp.R
import com.example.masapp.databinding.FragmentChangePasswordBinding
import com.example.masapp.ui.activity.LoginActivity
import com.example.masapp.viewmodels.CivilianViewModel
import com.example.masapp.viewmodels.UserViewModel

class ChangePasswordFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentChangePasswordBinding
    private lateinit var animationLoading: Animation
    private lateinit var viewModel: UserViewModel
    private var uId: Long = 0
    private var uToken = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePasswordBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        animationLoading = AnimationUtils.loadAnimation(requireActivity(), R.anim.blink)
        sharedPreferences = requireContext().getSharedPreferences(LoginActivity.sharedPrefFile, Context.MODE_PRIVATE)
        getUId()
        binding.btnSubmit.setOnClickListener {
            binding.iconLoading.apply {
                visibility = View.VISIBLE
                startAnimation(animationLoading)
            }
            changePassword()
        }
        binding.btnBack.setOnClickListener {
            this.findNavController().popBackStack()
        }

        viewModel.messageChangePass.observe(requireActivity(),{
            it?.let {
                Toast.makeText(context,it ,Toast.LENGTH_SHORT).show()
            }
            binding.iconLoading.apply {
                clearAnimation()
                visibility = View.GONE
            }
        })
        return binding.root
    }


    private fun changePassword(){
        val newPass = binding.edtNewPassword.text.toString()
        val rePass = binding.edtRePassword.text.toString()
        if (newPass == rePass) {
            viewModel.changePassword(uId, newPass, uToken)
        }else{
            Toast.makeText(context,"Nhập lại mật khẩu không đúng", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUId(){
        uId = sharedPreferences.getLong(LoginActivity.USER_ID,0)
        uToken = sharedPreferences.getString(LoginActivity.USER_TOKEN,"1")!!
    }

}