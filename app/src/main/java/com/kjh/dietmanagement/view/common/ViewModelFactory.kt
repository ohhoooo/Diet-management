package com.kjh.dietmanagement.view.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kjh.dietmanagement.model.ApiClient
import com.kjh.dietmanagement.model.repository.JoinRepository
import com.kjh.dietmanagement.model.source.JoinDataSource
import com.kjh.dietmanagement.viewmodel.JoinViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(JoinViewModel::class.java) -> {
                val repository = JoinRepository(JoinDataSource(ApiClient.create()))
                JoinViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}