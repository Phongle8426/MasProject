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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.masapp.R
import com.example.masapp.adapter.FamilyAdapter
import com.example.masapp.databinding.FragmentFamilyBinding
import com.example.masapp.models.CivilianModel
import com.example.masapp.ui.activity.LoginActivity
import com.example.masapp.utils.ItemClick
import com.example.masapp.viewmodels.CivilianViewModel

class familyFragment : Fragment() {
    private lateinit var binding: FragmentFamilyBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel : CivilianViewModel
    private lateinit var animationLoading: Animation
    private var uId: Long = 0
    private var uToken = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFamilyBinding.inflate(layoutInflater)
        animationLoading = AnimationUtils.loadAnimation(requireActivity(), R.anim.blink)
        viewModel = ViewModelProvider(requireActivity()).get(CivilianViewModel::class.java)
        sharedPreferences = requireContext().getSharedPreferences(LoginActivity.sharedPrefFile, Context.MODE_PRIVATE)
        getUId()
        viewModel.getFamily(uId,uToken)
        binding.iconLoading.apply {
            visibility = View.VISIBLE
            startAnimation(animationLoading)
        }
        viewModel.civilians.observe(requireActivity(), androidx.lifecycle.Observer {
            if (it != null){
                Log.d("lis", it.toString())
                binding.rcvFamilyMember.adapter = FamilyAdapter(it,callback)
                binding.rcvFamilyMember.layoutManager = LinearLayoutManager(context)
                binding.iconLoading.apply {
                    clearAnimation()
                    visibility = View.GONE
                }
            }
        })

        binding.fabButton.setOnClickListener {
            familyFragmentDirections.actionFamilyFragmentToAddNewMemberFragment().apply {
                findNavController().navigate(this)
            }
        }
        binding.btnBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        return binding.root
    }

    private fun getUId(){
        uId = sharedPreferences.getLong(LoginActivity.USER_ID,0)
        uToken = sharedPreferences.getString(LoginActivity.USER_TOKEN,"1")!!

        Log.d("lala", "$uId / $uToken")
    }

    private val callback = object: ItemClick{
        override fun itemClick(model: Any) {
            val civilianModel = model as CivilianModel
            val bundle = Bundle()
            bundle.putSerializable("member", civilianModel)
            familyFragmentDirections.actionFamilyFragmentToFamilyDetailFragment().apply {
                findNavController().navigate(R.id.action_familyFragment_to_familyDetailFragment,bundle)
            }
        }
    }
}