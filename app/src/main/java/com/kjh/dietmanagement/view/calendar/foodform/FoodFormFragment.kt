package com.kjh.dietmanagement.view.calendar.foodform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentFoodFormBinding
import com.kjh.dietmanagement.model.data.Food
import com.kjh.dietmanagement.view.common.ViewModelFactory
import com.kjh.dietmanagement.viewmodel.FoodFormViewModel
import com.kjh.dietmanagement.viewmodel.MainActivityViewModel

class FoodFormFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFoodFormBinding
    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private val viewModel: FoodFormViewModel by viewModels { ViewModelFactory() }

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

        observer()
        onButtonClick()
    }

    // observer
    private fun observer() {
        // 음식 직접 추가
        viewModel.responseString.observe(viewLifecycleOwner) {
            when (it) {
                "음식 추가 성공" -> {
                    with(binding) {
                        activityViewModel.addFood(Food(
                            etFoodName.text.toString(),
                            if(etCalorie.text.isNotEmpty()) etCalorie.text.toString().toDouble() else 0.0,
                            if(etCarbohydrate.text.isNotEmpty()) etCarbohydrate.text.toString().toDouble() else 0.0,
                            if(etProtein.text.isNotEmpty()) etProtein.text.toString().toDouble() else 0.0,
                            if(etFat.text.isNotEmpty()) etFat.text.toString().toDouble() else 0.0,
                        ))
                    }

                    Toast.makeText(requireContext(), "음식을 추가 하였습니다.", Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                }
                "음식 추가 실패" -> {
                    Toast.makeText(requireContext(), "음식을 추가하지 못하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 추가 버튼 클릭
    private fun onButtonClick() {
        with(binding) {
            btAdd.setOnClickListener {
                if(!etFoodName.text.isNullOrBlank()) {
                    lifecycleScope.launchWhenCreated {
                        viewModel.registerFood(
                            Food(
                                etFoodName.text.toString(),
                                if(etCalorie.text.isNotEmpty()) etCalorie.text.toString().toDouble() else 0.0,
                                if(etCarbohydrate.text.isNotEmpty()) etCarbohydrate.text.toString().toDouble() else 0.0,
                                if(etProtein.text.isNotEmpty()) etProtein.text.toString().toDouble() else 0.0,
                                if(etFat.text.isNotEmpty()) etFat.text.toString().toDouble() else 0.0,
                            )
                        )
                    }
                }else {
                    Toast.makeText(requireContext(), "음식 이름을 입력해 주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}