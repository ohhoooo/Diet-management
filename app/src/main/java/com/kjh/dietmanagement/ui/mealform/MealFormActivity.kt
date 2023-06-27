package com.kjh.dietmanagement.ui.mealform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.ActivityMealFormBinding

class MealFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal_form)
    }
}