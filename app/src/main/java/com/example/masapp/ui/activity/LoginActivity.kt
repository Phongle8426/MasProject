package com.example.masapp.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.masapp.BottomNavigationActivity
import com.example.masapp.R
import com.example.masapp.databinding.ActivityLoginBinding
import com.example.masapp.databinding.ActivityMainBinding
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        setContentView(binding.root)
         sharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        binding.btnLogin.setOnClickListener {
            login()
            binding.progressBar.visibility = View.VISIBLE
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
                Intent(this, BottomNavigationActivity::class.java).apply {
                    startActivity(this)
                }
            }
        })
        viewModel.messageLoginFailure.observe(this, Observer {
            binding.tvError.apply {
                this.visibility = View.VISIBLE
                this.text = it
            }
            binding.progressBar.visibility = View.GONE
        })
    }

    private fun saveUserToken(token: String, uId: Int){
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString(USER_TOKEN,token)
        editor.putInt(USER_ID,uId)
        editor.apply()
        editor.commit()
    }
}