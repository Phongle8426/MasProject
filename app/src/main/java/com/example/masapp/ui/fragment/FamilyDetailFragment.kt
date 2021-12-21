package com.example.masapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.masapp.R
import com.example.masapp.databinding.FragmentFamilyDetailBinding
import com.example.masapp.models.CivilianModel
import com.example.masapp.utils.DialogConfirm
import com.example.masapp.viewmodels.CivilianViewModel

class FamilyDetailFragment : Fragment() {
    private lateinit var binding: FragmentFamilyDetailBinding
    private lateinit var viewModel : CivilianViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFamilyDetailBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(CivilianViewModel::class.java)
        val member = arguments?.getSerializable("member") as CivilianModel
        val author = arguments?.getString("author")
        Log.d("member", member.toString())
        binding.edtName.setText(member.name)
        binding.edtBirthDay.setText(member.birthDay)
        binding.edtPhone.setText(member.phone)
        binding.edtIdCard.setText(member.cccd)

        when(member.gender){
            "nam" -> binding.radioBtnMale.isChecked = true
            "nữ" -> binding.radioBtnFemale.isChecked = true
            "khác" -> binding.radioBtnNon.isChecked = true
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

        binding.btnDelete.setOnClickListener {
            DialogConfirm.getInstanceDialog().setMessage("Bạn có muốn xóa thành viên này không?").apply {
                onAccept = {
                    viewModel.deleteMember(member.id,author!!)
                    this.findNavController().popBackStack()
                }
            }.show(parentFragmentManager, "FamilyDetailFragment")
        }
//        viewModel.messDeleteMember.observe(requireActivity(),{
//            if (it == "200")
//
//            else
//                Toast.makeText(requireContext(),"Thất bại",Toast.LENGTH_SHORT).show()
//        })
        binding.btnBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        return binding.root
    }

}