package com.kjh.dietmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kjh.dietmanagement.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        // BottomNavigationView 에게 NavController 설정
        binding.bottomNavigationView.setupWithNavController(navController)

        // 특정 fragment 들을 제외한 나머지 fragment 들의 bottom navigation bar 숨김 처리
        navController.addOnDestinationChangedListener { _, view, _ ->
            if (view.id == R.id.homeFragment) {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }else {
                binding.bottomNavigationView.visibility = View.GONE
            }
        }
    }
}