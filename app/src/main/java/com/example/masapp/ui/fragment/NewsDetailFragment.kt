package com.example.masapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.masapp.R
import com.example.masapp.databinding.FragmentNewsDetailBinding
import com.example.masapp.models.NewsModel

class NewsDetailFragment : Fragment() {
    private lateinit var binding: FragmentNewsDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailBinding.inflate(layoutInflater)
        val news = arguments?.getSerializable("news") as NewsModel
        binding.apply {
            tvTitle.text = news.title
            tvContent.text = news.content
            tvTime.text = news.convertTime()
        }
        binding.btnBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        return binding.root
    }

}