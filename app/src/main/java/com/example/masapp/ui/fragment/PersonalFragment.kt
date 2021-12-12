package com.example.masapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.masapp.databinding.FragmentPersonalBinding
import com.example.masapp.ui.activity.LoginActivity
import com.example.masapp.viewmodels.ProfileViewModel

class PersonalFragment : Fragment() {
    private lateinit var binding: FragmentPersonalBinding
    private lateinit var viewModelProfile: ProfileViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private var uId: Long = 0
    private var uToken = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalBinding.inflate(layoutInflater)
        viewModelProfile = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        sharedPreferences = requireContext().getSharedPreferences(LoginActivity.sharedPrefFile, Context.MODE_PRIVATE)
        getUId()
        getProfile()
        binding.cardInfoPersonal.setOnClickListener {
            PersonalFragmentDirections.actionPersonalFragmentToProfileFragment2().apply {
                findNavController().navigate(this)
            }
        }

        binding.cardFamily.setOnClickListener {
            PersonalFragmentDirections.actionPersonalFragmentToFamilyFragment().apply {
                findNavController().navigate(this)
            }
        }

        binding.cardOderHistory.setOnClickListener {
            PersonalFragmentDirections.actionPersonalFragmentToOderHistoryFragment().apply {
                findNavController().navigate(this)
            }
        }

        return binding.root
    }

    private fun getProfile(){
        viewModelProfile.getProfile(uId,uToken)
        viewModelProfile.profile.observe(requireActivity(), androidx.lifecycle.Observer {
            if(it != null){
                binding.tvUserName.text = it.name
                binding.tvPhone.text = it.phone
            }
        })

    }

    private fun getUId(){
        uId = sharedPreferences.getLong(LoginActivity.USER_ID,0)
        uToken = sharedPreferences.getString(LoginActivity.USER_TOKEN,"1")!!
    }
}