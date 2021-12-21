package com.example.masapp.ui.fragment

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.masapp.databinding.FragmentProfileBinding
import com.example.masapp.models.ProfileModel
import com.example.masapp.ui.activity.LoginActivity
import com.example.masapp.ui.activity.LoginActivity.Companion.USER_ID
import com.example.masapp.ui.activity.LoginActivity.Companion.USER_TOKEN
import com.example.masapp.viewmodels.AddressViewModel
import com.example.masapp.viewmodels.ProfileViewModel
import kotlin.collections.HashMap


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: AddressViewModel
    private lateinit var viewModelProfile: ProfileViewModel
    private lateinit var animationLoading: Animation
//    private val districtsName = listOf(
//        "Hải Châu",
//        "Cẩm Lệ",
//        "Thanh Khê",
//        "Liên Chiểu",
//        "Ngũ Hành Sơn",
//        "Sơn Trà",
//        "Huyện Hòa Vang"
//    )
//    private val mapDistrict: HashMap<String, Int> = hashMapOf(
//        "Hải Châu" to 1,
//        "Cẩm Lệ" to 2,
//        "Thanh Khê" to 3,
//        "Liên Chiểu" to 4,
//        "Ngũ Hành Sơn" to 5,
//        "Sơn Trà" to 6,
//        "Huyện Hòa Vang" to 7
//    )
    private lateinit var sharedPreferences: SharedPreferences
    private var uId: Long = 0
    private var uToken = ""
//    private var listWard = mutableListOf<String>()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(AddressViewModel::class.java)
        viewModelProfile = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        animationLoading = AnimationUtils.loadAnimation(requireContext(), com.example.masapp.R.anim.blink)
        sharedPreferences = requireContext().getSharedPreferences(
            LoginActivity.sharedPrefFile,
            Context.MODE_PRIVATE
        )
        getUId()
        getProfile()
//        binding.autoCompleteDistrict.setOnClickListener {
//            val arrayAdapterDistrict = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, districtsName)
//            binding.autoCompleteDistrict.setAdapter(arrayAdapterDistrict)
//        }
//        binding.autoCompleteWard.setOnClickListener {
//            getWard()
//        }
        binding.btnUpdate.setOnClickListener {
            getInfo()
        }

        viewModelProfile.responseMess.observe(requireActivity(), {
            it?.let{
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                binding.iconLoading.apply {
                    clearAnimation()
                    visibility = View.GONE
                }
            }
        })
        binding.btnBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        return binding.root
    }

//    private fun getWard() {
//        val currentDistrict = binding.autoCompleteDistrict.text.toString()
//        Log.e("huhu11", currentDistrict)
//        if (currentDistrict != "") {
//            val id = mapDistrict[currentDistrict]
//            Log.e("idd", id.toString())
//            viewModel.getWard(id!!)
//            viewModel.wardRespones.observe(requireActivity(), {
//                Log.e("huhu", it.toString())
//                listWard.clear()
//                for (district in it) {
//                    listWard.add(district.name)
//                }
//                val arrayAdapterWard =
//                    ArrayAdapter(requireActivity(), R.layout.simple_dropdown_item_1line, listWard)
//                binding.autoCompleteWard.setAdapter(arrayAdapterWard)
//                binding.autoCompleteWard.showDropDown()
//            })
//        } else {
//            Toast.makeText(context, "Hãy chọn Quận của bạn", Toast.LENGTH_SHORT).show()
//        }
//    }

    private fun getInfo() {
        val name = binding.edtName.text.toString()
        val phone = binding.edtPhone.text.toString()
        val email = binding.edtEmail.text.toString()
        val district = binding.autoCompleteDistrict.text.toString()
        val ward = binding.autoCompleteWard.text.toString()
        val group = binding.autoCompleteGroup.text.toString()
        val address = binding.edtAddress.text.toString()
        if(name.isBlank()|| phone.isBlank() || email.isBlank() || district.isBlank()
            || ward.isBlank()|| group.isBlank()|| address.isBlank()){
            Toast.makeText(context,"Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
        }else{
            val profile = ProfileModel(uId, name, phone, email, district, ward, group, address)
            Log.d("Profile", "getInfo: $profile")
            viewModelProfile.updateProfile(profile, uToken)
            binding.iconLoading.apply {
                visibility = View.VISIBLE
                startAnimation(animationLoading)
            }
        }
    }

    private fun getProfile() {
        viewModelProfile.getProfile(uId, uToken)
        viewModelProfile.profile.observe(requireActivity(), {
            if (it != null) {
                Log.d("Hung3", "getProfile: ${it}")
                binding.edtName.setText(it.name)
                binding.edtEmail.setText(it.email)
                binding.edtPhone.setText(it.phone)
                binding.autoCompleteDistrict.setText(it.district)
                binding.autoCompleteWard.setText(it.wardName)
                binding.autoCompleteGroup.setText(it.groupNumber)
                binding.edtAddress.setText(it.address)
            }
        })
    }

    private fun getUId() {
        uId = sharedPreferences.getLong(USER_ID, 0)
        uToken = sharedPreferences.getString(USER_TOKEN, "1")!!
    }

}