package com.example.masapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.masapp.databinding.ActivityButtonNavigationBinding

class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityButtonNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityButtonNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = Navigation.findNavController(this,R.id.navHostFragment)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }
}