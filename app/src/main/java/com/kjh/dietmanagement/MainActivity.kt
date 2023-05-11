package com.kjh.dietmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kjh.dietmanagement.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // NavController 획득
        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()
        navController?.let {
            binding.bottomNavigationView.setupWithNavController(it)
        }
    }
}