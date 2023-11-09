package com.kjh.dietmanagement.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kjh.dietmanagement.model.data.ResponseHomeDialog
import com.kjh.dietmanagement.model.repository.HomeDialogRepository
import kotlinx.coroutines.launch

class HomeDialogViewModel(private val homeDialogRepository: HomeDialogRepository) : ViewModel() {

    private val _responseMeal = MutableLiveData<ResponseHomeDialog>()
    val responseMeal: LiveData<ResponseHomeDialog> = _responseMeal

    private val _messageCheck = MutableLiveData<String>()
    val messageCheck: LiveData<String> = _messageCheck

    // 식단 조회
    fun getMeals(date: String) {
        viewModelScope.launch {
            homeDialogRepository.getMeals(date) { response, message ->
                when (message) {
                    "응답 성공" -> {
                        _responseMeal.value = response
                    }
                    "응답 실패" -> {
                        _messageCheck.value = "응답 실패"
                    }
                    "연결 실패" -> {
                        _messageCheck.value = "연결 실패"
                    }
                }
            }
        }
    }
}