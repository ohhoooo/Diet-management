package com.kjh.dietmanagement.ui.foodsearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentFoodSearchBinding
import com.kjh.dietmanagement.ui.common.viewmodel.FoodViewModel
import com.kjh.dietmanagement.ui.common.OnClickInterface

class FoodSearchFragment : Fragment(), OnClickInterface {

    private lateinit var adapter: FoodSearchAdapter
    private lateinit var binding: FragmentFoodSearchBinding
    private val viewModel: FoodViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter()
        observer()
        onClickButtons()
    }

    // adapter
    private fun adapter() {
        adapter = FoodSearchAdapter(this)
        binding.recyclerView.adapter = adapter
    }

    // observer
    private fun observer() {
        viewModel.food.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    // button click
    private fun onClickButtons() {
        // MealFormFragment 로 이동
        binding.btSave.setOnClickListener {
            findNavController().navigateUp()
        }

        // FoodFormFragment 로 이동
        binding.btAddByYourself.setOnClickListener {
            val action = FoodSearchFragmentDirections.actionFoodSearchFragmentToFoodFormFragment()
            findNavController().navigate(action)
        }
    }

    // button click -> position item remove
    override fun onClick(position: Int) {
        viewModel.removeFood(position)
    }
}