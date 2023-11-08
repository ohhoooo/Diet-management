package com.kjh.dietmanagement.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kjh.dietmanagement.model.data.Food
import com.kjh.dietmanagement.model.repository.FoodSearchRepository
import kotlinx.coroutines.launch

class FoodSearchViewModel(private val foodSearchRepository: FoodSearchRepository) : ViewModel() {

    private val _foods = MutableLiveData<List<Food>>()
    val foods: LiveData<List<Food>> = _foods

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    // 음식 검색
    fun searchFoods(food: String) {
        viewModelScope.launch {
            foodSearchRepository.searchFoods(food) { foods, message ->
                when (message) {
                    "응답 성공" -> {
                        if (foods!!.result != null && foods.result.isNotEmpty()) {
                            _foods.value = foods.result
                        } else {
                            _message.value = "검색 실패"
                        }
                    }
                    "응답 실패" -> {
                        _message.value = "응답 실패"
                    }
                    "연결 실패" -> {
                        _message.value = "연결 실패"
                    }
                }
            }
        }
    }

    // 리스트 초기화
    fun resetFoods() {
        _foods.value = emptyList()
    }
}