package com.kjh.dietmanagement.ui.foodform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentFoodFormBinding
import com.kjh.dietmanagement.model.Food
import com.kjh.dietmanagement.ui.common.viewmodel.FoodViewModel

class FoodFormFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFoodFormBinding
    private val viewModel: FoodViewModel by viewModels()

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

    // 추가 버튼 클릭
    private fun onAddButtonClick() {
        with(binding) {
            btAdd.setOnClickListener {
                if(!etFoodName.text.isNullOrBlank()) {
                    val food = Food(
                        etFoodName.text.toString(),
                        if(etCalorie.text.isNotEmpty()) etCalorie.text.toString() else "0.0",
                        if(etCarbohydrate.text.isNotEmpty()) etCarbohydrate.text.toString() else "0.0",
                        if(etProtein.text.isNotEmpty()) etProtein.text.toString() else "0.0",
                        if(etFat.text.isNotEmpty()) etFat.text.toString() else "0.0",
                    )
                    viewModel.addFood(food)
                    dialog?.dismiss()
                }else {
                    Toast.makeText(requireContext(), "음식 이름을 입력해 주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}