package com.example.masapp.ui.fragment

import android.content.Context
import android.content.SharedPreferences
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
import com.example.masapp.models.VaccineModel
import com.example.masapp.ui.activity.LoginActivity
import com.example.masapp.utils.DialogConfirm
import com.example.masapp.viewmodels.CivilianViewModel

class FamilyDetailFragment : Fragment() {
    private lateinit var binding: FragmentFamilyDetailBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel : CivilianViewModel
    private var uId: Long = 0
    private var uToken = ""
    private var sex = ""
    private var idCivilian = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFamilyDetailBinding.inflate(layoutInflater)
        sharedPreferences = requireContext().getSharedPreferences(LoginActivity.sharedPrefFile, Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(requireActivity()).get(CivilianViewModel::class.java)
        val member = arguments?.getSerializable("member") as CivilianModel
        val author = arguments?.getString("author")
        Log.d("member", member.toString())
        binding.edtName.setText(member.name)
        binding.edtBirthDay.setText(member.birthDay)
        binding.edtPhone.setText(member.phone)
        binding.edtIdCard.setText(member.cccd)
        sex = member.gender
        idCivilian = member.id

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

        binding.gRadioSex.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                binding.radioBtnMale.id -> sex = "nam"
                binding.radioBtnFemale.id -> sex = "nữ"
                binding.radioBtnNon.id -> sex = "khác"
            }
        }

        binding.btnUpdate.setOnClickListener {
            getUId()
            DialogConfirm.getInstanceDialog()
                .setMessage("Sửa thông tin của thành viên này?")
                .apply {
                    onAccept = { updateMember() }
                }.show(parentFragmentManager, "update")
        }
        binding.btnBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        return binding.root
    }

    private fun updateMember(){
        val listVaccine = mutableListOf<VaccineModel>()
        val name = binding.edtName.text.toString()
        val birthDay = binding.edtBirthDay.text.toString()
        val phone = binding.edtPhone.text.toString()
        val idCard = binding.edtIdCard.text.toString()

        val day1 = binding.editTextDateShotOne.text.toString()
        val nameVaccine1 = binding.edtCompleteNameShotOne.text.toString()
        val day2 = binding.editTextDateShotTwo.text.toString()
        val nameVaccine2 = binding.editCompleteNameShotTwo.text.toString()
        if(checkNotNull(day1,nameVaccine1))
            listVaccine.add(VaccineModel(0,day1,nameVaccine1))
        if (checkNotNull(day2, nameVaccine2))
            listVaccine.add(VaccineModel(0,day2,nameVaccine2))
        if (checkNotNull(name, birthDay)){
            val civilian = CivilianModel(idCivilian,name,birthDay,sex,phone,idCard,uId,listVaccine.size.toString(),listVaccine)
            viewModel.saveFamily(civilian,uToken)
            Log.d("Hung", "saveMember: $civilian")
        }else{
            Toast.makeText(context,"Thông tin chưa đầy đủ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkNotNull(value1: String, value2: String): Boolean{
        if(value1 == "" && value2 == ""){
            return false
        }
        return true
    }

    private fun getUId(){
        uId = sharedPreferences.getLong(LoginActivity.USER_ID,0)
        uToken = sharedPreferences.getString(LoginActivity.USER_TOKEN,"1")!!
    }
}