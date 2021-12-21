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
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.masapp.R
import com.example.masapp.adapter.NewsAdapter
import com.example.masapp.databinding.FragmentNewsBinding
import com.example.masapp.models.AddressModel
import com.example.masapp.models.NewsModel
import com.example.masapp.models.RequestCartModel
import com.example.masapp.ui.activity.LoginActivity
import com.example.masapp.utils.ItemClick
import com.example.masapp.viewmodels.NewsViewModel
import com.example.masapp.viewmodels.ProfileViewModel

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var viewModelProfile: ProfileViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var animationLoading: Animation
    private var uId: Long = 0
    private var uToken = ""
    private var district = ""
    private var ward = ""
    private var group = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        viewModelProfile = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        animationLoading = AnimationUtils.loadAnimation(requireActivity(), R.anim.blink)
        sharedPreferences = requireContext().getSharedPreferences(
            LoginActivity.sharedPrefFile,
            Context.MODE_PRIVATE
        )
        getUId()
        getProfile()
        binding.iconLoading.apply {
            visibility = View.VISIBLE
            startAnimation(animationLoading)
        }
        viewModel.getNews(AddressModel(district,ward, group), uToken)
        viewModel.listNews.observe(requireActivity(),{
            it?.let{
                Log.d("Thang", "onCreateView: $it ")
                binding.rcvNews.adapter = NewsAdapter(it,callback)
                if (it.isEmpty())
                    binding.imgEmpty.visibility = View.VISIBLE
            }
        })
        viewModel.messListNews.observe(requireActivity(),{
            binding.iconLoading.apply {
                clearAnimation()
                visibility = View.GONE
            }
        })
        binding.btnBack.setOnClickListener {
            this.findNavController().popBackStack()
        }
        return binding.root
    }

    private fun getUId() {
        uId = sharedPreferences.getLong(LoginActivity.USER_ID, 0)
        uToken = sharedPreferences.getString(LoginActivity.USER_TOKEN, "1")!!
    }

    private fun getProfile() {
        viewModelProfile.getProfile(uId, uToken)
        viewModelProfile.profile.observe(requireActivity(), {
           it?.let{
               district = it.district
               ward =it.wardName
               group = it.groupNumber.toLong()
           }
        })

    }

    private val callback = object : ItemClick {
        override fun itemClick(model: Any) {
            val news = model as NewsModel
            val bundle = Bundle()
            bundle.putSerializable("news", news)
            NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment().apply {
                findNavController().navigate(R.id.action_newsFragment_to_newsDetailFragment,bundle)
            }
        }

        override fun itemClickWithCount(model: Any, countProduct: Int) {
        }

    }
}