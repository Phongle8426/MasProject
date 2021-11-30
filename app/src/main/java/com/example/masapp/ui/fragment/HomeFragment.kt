package com.example.masapp.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.masapp.R
import com.example.masapp.databinding.FragmentHomeBinding
import com.example.masapp.ui.activity.LoginActivity
import com.example.masapp.viewmodels.ProfileViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModelProfile: ProfileViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private var uId: Int = 0
    private var uToken = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        viewModelProfile = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        sharedPreferences = requireContext().getSharedPreferences(LoginActivity.sharedPrefFile, Context.MODE_PRIVATE)
        getUId()
        getProfile()
        binding.btnSupport.setOnClickListener {
            HomeFragmentDirections.actionHomeFragmentToProductFragment().apply {
                findNavController().navigate(this)
            }
        }
        return binding.root
    }

    private fun getProfile(){
        viewModelProfile.getProfile(uId,uToken)
        viewModelProfile.profile.observe(requireActivity(), androidx.lifecycle.Observer {
            if(it != null){
                binding.tvUserName.setText(it.name)
            }
        })

    }

    private fun getUId(){
        uId = sharedPreferences.getInt(LoginActivity.USER_ID,0)
        uToken = sharedPreferences.getString(LoginActivity.USER_TOKEN,"1")!!
    }

}