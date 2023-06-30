package com.kjh.dietmanagement.ui.foodsearch

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.ActivityFoodSearchBinding
import com.kjh.dietmanagement.model.Food
import com.kjh.dietmanagement.ui.common.FoodViewModel
import com.kjh.dietmanagement.ui.common.OnClickInterface
import com.kjh.dietmanagement.ui.foodform.FoodFormFragment
import com.kjh.dietmanagement.ui.mealform.MealFormActivity

class FoodSearchActivity : AppCompatActivity(), OnClickInterface {

    private lateinit var adapter: FoodSearchAdapter
    private lateinit var binding: ActivityFoodSearchBinding
    private val viewModel: FoodViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_food_search)

        binding.lifecycleOwner = this

        setAdapter()
        setFoodList()
        onMoveButton()
        onSaveButton()
    }

    // 설정 : adapter
    private fun setAdapter() {
        adapter = FoodSearchAdapter(this)
        binding.recyclerView.adapter = adapter

        viewModel.food.observe(this) {
            adapter.submitList(it)
        }
    }

    // 설정 : FoodList
    private fun setFoodList() {
        val foodList =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableArrayListExtra("toFoodSearchActivity", Food::class.java) ?: listOf()
            }else {
                @Suppress("DEPRECATION")
                intent.getParcelableArrayListExtra<Food>("toFoodSearchActivity") ?: listOf()
            }

        viewModel.setFood(foodList)
    }

    // 이동 : FoodFormFragment
    private fun onMoveButton() {
        binding.btAddByYourself.setOnClickListener {
            val foodFormFragment = FoodFormFragment()
            foodFormFragment.show(supportFragmentManager, "food_form_dialog")
        }
    }

    // 이동 : MealFormFragment
    private fun onSaveButton() {
        binding.btSave.setOnClickListener {
            if(viewModel.food.value != null) {
                val intent = Intent(this, MealFormActivity::class.java).apply {
                    putParcelableArrayListExtra("toMealFormFragment", viewModel.food.value?.let { value -> ArrayList(value) } ?: arrayListOf())
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    // 이벤트 : 해당 버튼을 클릭
    override fun onClick(position: Int) {
        onRemoveItem(position)
    }

    // 삭제 : 해당 position
    private fun onRemoveItem(position: Int) {
        viewModel.removeFood(position)
    }
}