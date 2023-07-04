package com.kjh.dietmanagement.ui.mealform

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.ActivityMealFormBinding
import com.kjh.dietmanagement.model.Food
import com.kjh.dietmanagement.ui.common.FoodViewModel
import com.kjh.dietmanagement.ui.common.OnClickInterface
import com.kjh.dietmanagement.ui.common.PhotoViewModel
import com.kjh.dietmanagement.ui.foodsearch.FoodSearchActivity
import com.kjh.dietmanagement.ui.photodialog.PhotoDialogFragment

class MealFormActivity : AppCompatActivity(), OnClickInterface {

    private lateinit var adapter: MealFormAdapter
    private lateinit var binding: ActivityMealFormBinding
    private lateinit var getFood: ActivityResultLauncher<Intent>
    private val foodViewModel: FoodViewModel by viewModels()
    private val photoViewModel: PhotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal_form)

        binding.lifecycleOwner = this

        setAdapter()
        setCallback()
        observeViewModel()
        setNutrientsTotal()
        onClickAddImageButton()
        onClickDeleteImageButton()
        onClickMoveActivityButton()
    }

    // adapter
    private fun setAdapter() {
        adapter = MealFormAdapter(this)
        binding.recyclerView.adapter = adapter
    }

    // callback
    private fun setCallback() {
        // food
        getFood = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK){
                result.data?.let {
                    setFoodList(it)
                }
            }
        }
    }

    // observe
    private fun observeViewModel() {
        // photo
        photoViewModel.photo.observe(this) {
            with(binding) {
                ivFood.setImageURI(it)
                ivFood.visibility = VISIBLE
                btDelete.visibility = VISIBLE
                btAddImage.visibility = GONE
            }
        }
        // food
        foodViewModel.food.observe(this) {
            adapter.submitList(it)
        }
    }

    // 각 영양소의 합계 반영
    private fun setNutrientsTotal(foodList: List<Food> = listOf(Food())) {
        binding.nutrient = Food(
            "",
            foodList.sumOf { it.calorie.toDouble() }.toString(),
            foodList.sumOf { it.carbohydrate.toDouble() }.toString(),
            foodList.sumOf { it.protein.toDouble() }.toString(),
            foodList.sumOf { it.fat.toDouble() }.toString()
        )
    }

    // 클릭 시 이미지 추가
    private fun onClickAddImageButton() {
        binding.btAddImage.setOnClickListener {
            val photoDialogFragment = PhotoDialogFragment()
            photoDialogFragment.show(supportFragmentManager, "photo_dialog")
        }
    }

    // 클릭 시 이미지 삭제
    private fun onClickDeleteImageButton() {
        binding.btDelete.setOnClickListener {
            photoViewModel.removePhoto()
            with(binding) {
                ivFood.visibility = GONE
                btDelete.visibility = GONE
                btAddImage.visibility = VISIBLE
            }
        }
    }

    // 클릭 시 FoodSearchActivity 로 이동
    private fun onClickMoveActivityButton() {
        binding.btAddFood.setOnClickListener {
            val intent = Intent(this, FoodSearchActivity::class.java).apply {
                putParcelableArrayListExtra("toFoodSearchActivity", foodViewModel.food.value?.let { value -> ArrayList(value) } ?: arrayListOf())
            }
            getFood.launch(intent)
        }
    }

    // FoodSearchActivity 로 부터 받아온 Food 리스트를 반영
    private fun setFoodList(intent: Intent) {
        val foodList =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableArrayListExtra("toMealFormFragment", Food::class.java) ?: listOf()
            }else {
                @Suppress("DEPRECATION")
                intent.getParcelableArrayListExtra("toMealFormFragment") ?: listOf()
            }
        // recyclerView 갱신
        foodViewModel.setFood(foodList)

        // NutrientsTotal 갱신
        setNutrientsTotal(foodList)
    }

    // 클릭 시 해당 position item 삭제
    override fun onClick(position: Int) {
        foodViewModel.removeFood(position)
        setNutrientsTotal(foodViewModel.food.value!!)
    }
}