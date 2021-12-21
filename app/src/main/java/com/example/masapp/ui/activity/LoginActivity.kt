package com.example.masapp.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.masapp.BottomNavigationActivity
import com.example.masapp.R
import com.example.masapp.databinding.ActivityLoginBinding
import com.example.masapp.models.User
import com.example.masapp.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {
    companion object {
        const val sharedPrefFile = "saveTokenSharedPre"
        val USER_TOKEN = "USER_TOKEN"
        val USER_ID = "USER_ID"
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var userName: String
    private lateinit var password: String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var animationLoading: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        setContentView(binding.root)
         sharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        animationLoading = AnimationUtils.loadAnimation(applicationContext, R.anim.blink)
        Log.d("TAG11", "onCreate: $animationLoading")
        binding.btnLogin.setOnClickListener {
            binding.iconLoading.apply {
                visibility = View.VISIBLE
                startAnimation(animationLoading)
            }
            login()
        }

    }

    @SuppressLint("ShowToast")
    private fun login() {
        userName = binding.edtUserName.text.toString()
        password = binding.edtPassword.text.toString()
        val authen = User(userName, password)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getUser(authen)
        }
        viewModel.userRespones.observe(this, Observer {
            if (it != null) {
                saveUserToken(it.token, it.id)
                animationLoading.cancel()
                Intent(this, BottomNavigationActivity::class.java).apply {
                    startActivity(this)
                }
            }
        })
        viewModel.messageLoginFailure.observe(this, Observer {
            it?.let{
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
                binding.iconLoading.apply {
                    clearAnimation()
                    visibility = View.GONE
                }
            }
        })
    }

    private fun saveUserToken(token: String, uId: Long){
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString(USER_TOKEN,token)
        editor.putLong(USER_ID,uId)
        editor.apply()
        editor.commit()
    }
}