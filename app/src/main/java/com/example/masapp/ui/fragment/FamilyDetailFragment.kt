package com.example.masapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.masapp.R
import com.example.masapp.databinding.FragmentFamilyDetailBinding
import com.example.masapp.models.CivilianModel

class FamilyDetailFragment : Fragment() {
    private lateinit var binding: FragmentFamilyDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFamilyDetailBinding.inflate(layoutInflater)
        val member = arguments?.getSerializable("member") as CivilianModel
        Log.d("member", member.toString())
        binding.edtName.setText(member.name)
        binding.edtBirthDay.setText(member.birthDay)
        binding.edtPhone.setText(member.phone)
        binding.edtIdCard.setText(member.cccd)

        when(member.gender){
            "nam" -> binding.radioBtnMale.isChecked = true
            "nữ" -> binding.radioBtnFemale.isChecked = true
            "khac" -> binding.radioBtnNon.isChecked = true
        }

        when(member.vaccineStatus){
            "ONE_SHOT" -> {
                binding.radioBtnOneShot.isChecked = true
                binding.editTextDateShotOne.setText(member.vaccineList[0].date)
                binding.edtCompleteNameShotOne.setText(member.vaccineList[0].vaccineName)
                binding.textInputDayVaccine1.visibility = View.VISIBLE
                binding.textInputNameVaccine1.visibility = View.VISIBLE
            }
            "TWO_SHOT" -> {
                binding.radioBtnTwoShot.isChecked = true
                binding.editTextDateShotOne.setText(member.vaccineList[0].date)
                binding.edtCompleteNameShotOne.setText(member.vaccineList[0].vaccineName)
                binding.editTextDateShotTwo.setText(member.vaccineList[1].date)
                binding.editCompleteNameShotTwo.setText(member.vaccineList[1].vaccineName)
                binding.textInputDayVaccine1.visibility = View.VISIBLE
                binding.textInputNameVaccine1.visibility = View.VISIBLE
                binding.textInputDayVaccine2.visibility = View.VISIBLE
                binding.textInputNameVaccine2.visibility = View.VISIBLE
            }
        }

        binding.btnBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        return binding.root
    }

}