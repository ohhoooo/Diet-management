package com.kjh.dietmanagement.view.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kjh.dietmanagement.model.ApiClient
import com.kjh.dietmanagement.model.repository.FoodFormRepository
import com.kjh.dietmanagement.model.repository.FoodSearchRepository
import com.kjh.dietmanagement.model.repository.HomeDialogRepository
import com.kjh.dietmanagement.model.repository.JoinRepository
import com.kjh.dietmanagement.model.repository.LoginRepository
import com.kjh.dietmanagement.model.repository.MealFormRepository
import com.kjh.dietmanagement.model.repository.RankingRepository
import com.kjh.dietmanagement.model.source.FoodFormDataSource
import com.kjh.dietmanagement.model.source.FoodSearchDataSource
import com.kjh.dietmanagement.model.source.HomeDialogDataSource
import com.kjh.dietmanagement.model.source.JoinDataSource
import com.kjh.dietmanagement.model.source.LoginDataSource
import com.kjh.dietmanagement.model.source.MealFormDataSource
import com.kjh.dietmanagement.model.source.RankingDataSource
import com.kjh.dietmanagement.viewmodel.FoodFormViewModel
import com.kjh.dietmanagement.viewmodel.FoodSearchViewModel
import com.kjh.dietmanagement.viewmodel.HomeDialogViewModel
import com.kjh.dietmanagement.viewmodel.JoinViewModel
import com.kjh.dietmanagement.viewmodel.LoginViewModel
import com.kjh.dietmanagement.viewmodel.MealFormViewModel
import com.kjh.dietmanagement.viewmodel.RankingViewModel

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
            modelClass.isAssignableFrom(HomeDialogViewModel::class.java) -> {
                val repository = HomeDialogRepository(HomeDialogDataSource(ApiClient.create()))
                HomeDialogViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MealFormViewModel::class.java) -> {
                val repository = MealFormRepository(MealFormDataSource(ApiClient.create()))
                MealFormViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FoodSearchViewModel::class.java) -> {
                val repository = FoodSearchRepository(FoodSearchDataSource(ApiClient.create()))
                FoodSearchViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FoodFormViewModel::class.java) -> {
                val repository = FoodFormRepository(FoodFormDataSource(ApiClient.create()))
                FoodFormViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RankingViewModel::class.java) -> {
                val repository = RankingRepository(RankingDataSource(ApiClient.create()))
                RankingViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}