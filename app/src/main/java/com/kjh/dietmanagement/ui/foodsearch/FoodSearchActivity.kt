package com.kjh.dietmanagement.ui.foodsearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.ActivityFoodSearchBinding

class FoodSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}