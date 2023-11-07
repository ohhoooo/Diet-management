package com.kjh.dietmanagement.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kjh.dietmanagement.model.data.Login
import com.kjh.dietmanagement.model.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _responseString = MutableLiveData<String>()
    val responseString: LiveData<String> = _responseString

    // 로그인
    fun loginUser(login: Login) {
        viewModelScope.launch {
            loginRepository.loginUser(login) { response ->
                _responseString.value = response
            }
        }
    }
}