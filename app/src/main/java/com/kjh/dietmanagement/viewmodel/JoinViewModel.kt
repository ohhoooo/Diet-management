package com.kjh.dietmanagement.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kjh.dietmanagement.model.data.Join
import com.kjh.dietmanagement.model.repository.JoinRepository
import kotlinx.coroutines.launch

class JoinViewModel(private val joinRepository: JoinRepository) : ViewModel() {

    private val _responseString = MutableLiveData<String>()
    val responseString: LiveData<String> = _responseString

    // 회원 가입
    fun joinUser(join: Join) {
        viewModelScope.launch {
            joinRepository.joinUser(join) { response ->
                _responseString.value = response
            }
        }
    }
}