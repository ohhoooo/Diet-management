package com.kjh.dietmanagement.view.calendar.foodsearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentFoodSearchBinding
import com.kjh.dietmanagement.model.data.Food
import com.kjh.dietmanagement.view.common.OnClickInterface
import com.kjh.dietmanagement.view.common.ViewModelFactory
import com.kjh.dietmanagement.viewmodel.FoodSearchViewModel
import com.kjh.dietmanagement.viewmodel.MainActivityViewModel

class FoodSearchFragment : Fragment(), OnClickInterface {

    private lateinit var foods: List<Food>
    private lateinit var adapter: FoodSearchAdapter
    private lateinit var binding: FragmentFoodSearchBinding
    private val viewModel: FoodSearchViewModel by viewModels { ViewModelFactory() }
    private val activityViewModel: MainActivityViewModel by activityViewModels()

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

        // 음식 리스트 상태 저장 -> 뒤로 가기 버튼 누를 때 반영
        foods = activityViewModel.food.value ?: emptyList()
    }

    // adapter
    private fun adapter() {
        adapter = FoodSearchAdapter(this)
        binding.recyclerView.adapter = adapter
    }

    // observer
    private fun observer() {
        // 음식 리스트
        activityViewModel.food.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // 음식 리스트(검색)
        viewModel.foods.observe(viewLifecycleOwner) {
                val adapter = FoodResponseAdapter { food ->
                    activityViewModel.addFood(food)
                }
                binding.rvSearchFood.adapter = adapter
                adapter.submitList(it)
        }

        // 메세지
        viewModel.message.observe(viewLifecycleOwner) {
            when (it) {
                "검색 실패" -> {
                    Toast.makeText(requireContext(), "검색 결과가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
                "응답 실패" -> {
                    Toast.makeText(requireContext(), "응답에 실패 했습니다.", Toast.LENGTH_SHORT).show()
                }
                "연결 실패" -> {
                    Toast.makeText(requireContext(), "연결에 실패 했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
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

        // 뒤로 가기
        binding.ivArrowBack.setOnClickListener {
            activityViewModel.setFood(foods)
            findNavController().navigateUp()
        }

        // 음식 검색
        binding.etSearchFood.setOnEditorActionListener { textView, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                lifecycleScope.launchWhenCreated {
                    viewModel.resetFoods()
                    adapter.submitList(emptyList())
                    viewModel.searchFoods(textView.text.toString())
                }
                true
            }
            false
        }
    }

    // button click -> position item remove
    override fun onClick(position: Int) {
        activityViewModel.removeFood(position)
    }
}