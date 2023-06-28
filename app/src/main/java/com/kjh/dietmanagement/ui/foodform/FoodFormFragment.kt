package com.kjh.dietmanagement.ui.foodform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentFoodFormBinding
import com.kjh.dietmanagement.model.Food
import com.kjh.dietmanagement.ui.common.FoodViewModel

class FoodFormFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFoodFormBinding
    private val viewModel: FoodViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_form, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onAddButtonClick()
    }

    private fun onAddButtonClick() {
        with(binding) {
            btAdd.setOnClickListener {
                if(!etFoodName.text.isNullOrBlank()) {
                    val food = Food(etFoodName.text.toString())
                    if(etCalorie.text.isNotEmpty()) food.calorie = etCalorie.text.toString()
                    if(etCarbohydrate.text.isNotEmpty()) food.carbohydrate = etCarbohydrate.text.toString()
                    if(etProtein.text.isNotEmpty()) food.protein = etProtein.text.toString()
                    if(etFat.text.isNotEmpty()) food.fat = etFat.text.toString()

                    viewModel.addFood(food)
                    dialog?.dismiss()
                }else {
                    Toast.makeText(requireContext(), "음식 이름을 입력해 주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}