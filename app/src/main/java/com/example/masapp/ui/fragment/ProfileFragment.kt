package com.example.masapp.ui.fragment

import android.R
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import java.util.*
import kotlin.collections.HashMap


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: AddressViewModel
    private lateinit var viewModelProfile: ProfileViewModel
    private val vaccines = listOf(
        "AstraZeneca", "Gam-COVID-Vac", "Vero Cell", "Spikevax (Moderna)", "Comirnaty (Pfizer/BioNTech)", "Janssen","Abdala")
    private val districtsName = listOf(
        "Hải Châu",
        "Cẩm Lệ",
        "Thanh Khê",
        "Liên Chiểu",
        "Ngũ Hành Sơn",
        "Sơn Trà",
        "Huyện Hòa Vang"
    )
    private val mapDistrict: HashMap<String, Int> = hashMapOf(
        "Hải Châu" to 1,
        "Cẩm Lệ" to 2,
        "Thanh Khê" to 3,
        "Liên Chiểu" to 4,
        "Ngũ Hành Sơn" to 5,
        "Sơn Trà" to 6,
        "Huyện Hòa Vang" to 7
    )
    private lateinit var sharedPreferences: SharedPreferences
    private var uId: Int = 0
    private var uToken = ""
    private lateinit var arrayAdapterWard : ArrayAdapter<String>
    private lateinit var listWard: List<String>
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(AddressViewModel::class.java)
        viewModelProfile = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        sharedPreferences = requireContext().getSharedPreferences(LoginActivity.sharedPrefFile, Context.MODE_PRIVATE)
        getUId()
        getProfile()
        val arrayAdapterDistrict =
            ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, districtsName)
        binding.autoCompleteDistrict.setAdapter(arrayAdapterDistrict)
        binding.autoCompleteWard.setOnClickListener {
             arrayAdapterWard =
                ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, getWard())
            arrayAdapterWard.notifyDataSetChanged()
            binding.autoCompleteWard.setAdapter(arrayAdapterWard)

        }

        binding.btnUpdate.setOnClickListener {
            getInfo()
        }

        viewModelProfile.responseMess.observe(requireActivity(), androidx.lifecycle.Observer {
            if (it != ""){
                Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }

    private fun getWard():  MutableList<String> {
        val wards = mutableListOf<String>()
        val currentDistrict = binding.autoCompleteDistrict.text.toString()
        val id = mapDistrict[currentDistrict]
        Log.e("idd", id.toString())
        viewModel.getWard(id!!)
        viewModel.wardRespones.observe(requireActivity(), androidx.lifecycle.Observer {
            Log.e("huhu", it.toString())
            for (district in it) {
                wards.add(district.name)
            }
        })
        binding.btnBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        return wards
    }

    private fun getInfo(){
        val name = binding.edtName.text.toString()
        val phone = binding.edtPhone.text.toString()
        val email = binding.edtEmail.text.toString()
        val district = binding.autoCompleteDistrict.text.toString()
        val ward = binding.autoCompleteWard.text.toString()
        val group = binding.autoCompleteGroup.text.toString()
        val address = binding.autoCompleteAddress.text.toString()
        val profile = ProfileModel(uId,name, phone, email, district,ward, group,address)
        viewModelProfile.updateProfile(profile,uToken)
    }

    private fun getProfile(){
        viewModelProfile.getProfile(uId,uToken)
        viewModelProfile.profile.observe(requireActivity(), androidx.lifecycle.Observer {
            if(it != null){
                binding.edtName.setText(it.name)
                binding.edtEmail.setText(it.email)
                binding.edtPhone.setText(it.phone)
                binding.autoCompleteDistrict.setText(it.district)
                binding.autoCompleteWard.setText(it.wardName)
                binding.autoCompleteGroup.setText(it.groupNumber)
                binding.autoCompleteAddress.setText(it.address)
            }
        })

    }

    private fun getUId(){
        uId = sharedPreferences.getInt(USER_ID,0)
        uToken = sharedPreferences.getString(USER_TOKEN,"1")!!
    }
}