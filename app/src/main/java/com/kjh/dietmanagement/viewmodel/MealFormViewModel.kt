package com.kjh.dietmanagement.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kjh.dietmanagement.model.data.MealForm
import com.kjh.dietmanagement.model.repository.MealFormRepository
import kotlinx.coroutines.launch

class MealFormViewModel(private val mealFormRepository: MealFormRepository) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    // 식단 저장
    fun saveMeal(meal: MealForm) {
        viewModelScope.launch {
            mealFormRepository.saveMeal(meal) { responseMeal, message ->
                when (message) {
                    "응답 성공" -> {
                        if(responseMeal?.message == "식단 정보가 성공적으로 입력되었습니다.") {
                            _message.value = "응답 성공"
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
}