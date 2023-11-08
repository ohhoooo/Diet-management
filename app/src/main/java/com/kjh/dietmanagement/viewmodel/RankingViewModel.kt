package com.kjh.dietmanagement.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kjh.dietmanagement.model.data.Rank
import com.kjh.dietmanagement.model.repository.RankingRepository
import kotlinx.coroutines.launch

class RankingViewModel(private val rankingRepository: RankingRepository) : ViewModel() {

    private val _responseRanking = MutableLiveData<Rank>()
    val responseRanking: LiveData<Rank> = _responseRanking

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    // 랭킹 조회
    fun checkRanking() {
        viewModelScope.launch {
            rankingRepository.checkRanking { response, message ->
                when (message) {
                    "응답 성공" -> {
                        _responseRanking.value = response
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