package com.kjh.dietmanagement.view.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kjh.dietmanagement.model.ApiClient
import com.kjh.dietmanagement.model.repository.FoodFormRepository
import com.kjh.dietmanagement.model.repository.JoinRepository
import com.kjh.dietmanagement.model.repository.LoginRepository
import com.kjh.dietmanagement.model.source.FoodFormDataSource
import com.kjh.dietmanagement.model.source.JoinDataSource
import com.kjh.dietmanagement.model.source.LoginDataSource
import com.kjh.dietmanagement.viewmodel.FoodFormViewModel
import com.kjh.dietmanagement.viewmodel.JoinViewModel
import com.kjh.dietmanagement.viewmodel.LoginViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(JoinViewModel::class.java) -> {
                val repository = JoinRepository(JoinDataSource(ApiClient.create()))
                JoinViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                val repository = LoginRepository(LoginDataSource(ApiClient.create()))
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FoodFormViewModel::class.java) -> {
                val repository = FoodFormRepository(FoodFormDataSource(ApiClient.create()))
                FoodFormViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}