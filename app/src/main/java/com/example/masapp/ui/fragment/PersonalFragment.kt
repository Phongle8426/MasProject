package com.example.masapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.masapp.databinding.FragmentPersonalBinding

class PersonalFragment : Fragment() {
    private lateinit var binding: FragmentPersonalBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalBinding.inflate(layoutInflater)
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

        return binding.root
    }
}