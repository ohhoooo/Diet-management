package com.kjh.dietmanagement.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.ActivityMainBinding
import com.kjh.dietmanagement.viewmodel.ClassificationViewModel
import com.kjh.dietmanagement.viewmodel.FoodViewModel
import com.kjh.dietmanagement.viewmodel.PhotoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val foodViewModel: FoodViewModel by viewModels()
    private val photoViewModel: PhotoViewModel by viewModels()
    private val classificationViewModel: ClassificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        // BottomNavigationView 에게 NavController 설정
        binding.bottomNavigationView.setupWithNavController(navController)

        // 특정 fragment 들을 제외한 나머지 fragment 들의 bottom navigation bar 숨김 처리
        navController.addOnDestinationChangedListener { _, view, _ ->
            if (view.id == R.id.homeFragment || view.id == R.id.statisticsFragment || view.id == R.id.rankingFragment) {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }else {
                binding.bottomNavigationView.visibility = View.GONE
            }
        }
    }
}