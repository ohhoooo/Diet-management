package com.kjh.dietmanagement.view.calendar.mealform

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentMealFormBinding
import com.kjh.dietmanagement.model.data.Food
import com.kjh.dietmanagement.view.common.OnClickInterface
import com.kjh.dietmanagement.viewmodel.MainActivityViewModel

class MealFormFragment : Fragment(), OnClickInterface {

    private lateinit var adapter: MealFormAdapter
    private lateinit var binding: FragmentMealFormBinding
    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meal_form, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this.viewLifecycleOwner

        adapter()
        observer()
        onClickButtons()
    }

    // adapter
    private fun adapter() {
        adapter = MealFormAdapter(this)
        binding.recyclerView.adapter = adapter
    }

    // observer
    private fun observer() {
        // food
        viewModel.food.observe(viewLifecycleOwner) { foodList ->
            adapter.submitList(foodList)
            binding.nutrient = Food(
                "",
                foodList.sumOf { it.calorie.toDouble() }.toString(),
                foodList.sumOf { it.carbohydrate.toDouble() }.toString(),
                foodList.sumOf { it.protein.toDouble() }.toString(),
                foodList.sumOf { it.fat.toDouble() }.toString()
            )
        }

        // photo
        viewModel.photo.observe(viewLifecycleOwner) {
            with(binding) {
                if (it == Uri.EMPTY) {
                    ivFood.visibility = View.GONE
                    btDelete.visibility = View.GONE
                    btAddImage.visibility = View.VISIBLE
                } else {
                    ivFood.setImageURI(it)
                    ivFood.visibility = View.VISIBLE
                    btDelete.visibility = View.VISIBLE
                    btAddImage.visibility = View.GONE
                }
            }
        }
    }

    // button click
    private fun onClickButtons() {
        // FoodSearchFragment 로 이동
        binding.btAddFood.setOnClickListener {
            val action = MealFormFragmentDirections.actionMealFormFragmentToFoodSearchFragment()
            findNavController().navigate(action)
        }

        // PhotoDialogFragment 로 이동
        binding.btAddImage.setOnClickListener {
            val action = MealFormFragmentDirections.actionMealFormFragmentToPhotoDialogFragment()
            findNavController().navigate(action)
        }

        // 뒤로 가기
        binding.ivArrowBack.setOnClickListener {
            viewModel.resetFood()
            viewModel.removePhoto()
            findNavController().navigateUp()
        }

        // 이미지 삭제
        binding.btDelete.setOnClickListener {
            viewModel.removePhoto()
        }
    }

    // button click -> position food item remove
    override fun onClick(position: Int) {
        viewModel.removeFood(position)
    }
}