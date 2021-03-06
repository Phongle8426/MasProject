package com.example.masapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.masapp.databinding.ActivityMainBinding
import com.example.masapp.ui.fragment.HomeFragment
import com.example.masapp.ui.fragment.PersonalFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val personalFragment = PersonalFragment()

        makeCurrentFragment(homeFragment)
        binding.bottomNavigation.setOnNavigationItemReselectedListener {
            when(it.itemId) {
                R.id.home -> makeCurrentFragment(homeFragment)
                R.id.personal_profile -> makeCurrentFragment(personalFragment)
            }
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
    }
}