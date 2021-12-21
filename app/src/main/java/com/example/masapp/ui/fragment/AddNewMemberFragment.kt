package com.example.masapp.ui.fragment

import android.R
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.masapp.databinding.FragmentAddNewMemberBinding
import com.example.masapp.models.CivilianModel
import com.example.masapp.models.VaccineModel
import com.example.masapp.ui.activity.LoginActivity
import com.example.masapp.utils.DialogConfirm
import com.example.masapp.viewmodels.CivilianViewModel
import java.util.*

class AddNewMemberFragment : Fragment() {
    private lateinit var binding: FragmentAddNewMemberBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel : CivilianViewModel
    private var uId: Long = 0
    private var uToken = ""
    private var sex = ""
    private val vaccines = listOf(
        "AstraZeneca", "Gam-COVID-Vac", "Vero Cell", "Spikevax (Moderna)", "Comirnaty (Pfizer/BioNTech)", "Janssen","Abdala")

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewMemberBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(CivilianViewModel::class.java)
        sharedPreferences = requireContext().getSharedPreferences(LoginActivity.sharedPrefFile, Context.MODE_PRIVATE)
        getUId()
        viewModel.messSaveFamily.observe(requireActivity(), {
            it?.let{ Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show() }
        })
        binding.edtBirthDay.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(
                requireContext(), { _, year, monthOfYear, dayOfMonth ->
                    binding.edtBirthDay.setText("$dayOfMonth/$monthOfYear/$year")
                },
                year,
                month,
                day
            )
            dpd.show()
        }
        binding.edtCompleteNameShotOne.setAdapter(ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, vaccines))
        binding.editCompleteNameShotTwo.setAdapter(ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, vaccines))
        binding.gRadioVaccine.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == binding.radioBtnOneShot.id){
                binding.textInputDayVaccine1.visibility = View.VISIBLE
                binding.textInputNameVaccine1.visibility = View.VISIBLE
            }
            if (checkedId == binding.radioBtnTwoShot.id){
                binding.textInputDayVaccine1.visibility = View.VISIBLE
                binding.textInputNameVaccine1.visibility = View.VISIBLE
                binding.textInputDayVaccine2.visibility = View.VISIBLE
                binding.textInputNameVaccine2.visibility = View.VISIBLE
            }
        }

        binding.gRadioSex.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                binding.radioBtnMale.id -> sex = "nam"
                binding.radioBtnFemale.id -> sex = "nữ"
                binding.radioBtnNon.id -> sex = "khác"
            }
        }

        binding.btnSave.setOnClickListener {
            DialogConfirm.getInstanceDialog()
                .setMessage("Bạn muốn thêm người này vào thành viên gia đình ?")
                .apply {
                    onAccept = {saveMember()}
                }.show(parentFragmentManager,"save")
        }

        viewModel.messSaveFamily.observe(requireActivity(),{
            it?.let{ Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show() }
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

    private fun saveMember(){
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
            val civilian = CivilianModel(0,name,birthDay,sex,phone,idCard,uId,listVaccine.size.toString(),listVaccine)
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
}