package com.kjh.dietmanagement.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kjh.dietmanagement.model.data.Food
import com.kjh.dietmanagement.model.repository.FoodFormRepository
import kotlinx.coroutines.launch

class FoodFormViewModel(private val foodFormRepository: FoodFormRepository) : ViewModel() {

    private val _responseString = MutableLiveData<String>()
    val responseString: LiveData<String> = _responseString

    // 음식 직접 추가
    fun registerFood(food: Food) {
        viewModelScope.launch {
            foodFormRepository.registerFood(food) { response ->
                _responseString.value = response
            }
        }
    }
}