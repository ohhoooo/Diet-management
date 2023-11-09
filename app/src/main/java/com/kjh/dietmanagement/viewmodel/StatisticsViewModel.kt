package com.kjh.dietmanagement.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kjh.dietmanagement.model.data.ResponseStatistics
import com.kjh.dietmanagement.model.repository.StatisticsRepository
import kotlinx.coroutines.launch

class StatisticsViewModel(private val statisticsRepository: StatisticsRepository) : ViewModel() {

    private val _responseStatistics = MutableLiveData<ResponseStatistics>()
    val responseStatistics: LiveData<ResponseStatistics> = _responseStatistics

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    // 통계 조회
    fun getStatistics() {
        viewModelScope.launch {
            statisticsRepository.getStatistics { response, message ->
                when (message) {
                    "응답 성공" -> {
                        _responseStatistics.value = response
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