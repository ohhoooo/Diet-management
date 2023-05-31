package com.kjh.dietmanagement.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kjh.dietmanagement.model.Food

class SharedViewModel : ViewModel() {

    private val _food = MutableLiveData<List<Food>>()
    val food: LiveData<List<Food>> = _food

    fun addFood(food: Food) {
        _food.value = getFoods().apply { add(food) }
    }

    fun removeFood(position: Int) {
        _food.value = getFoods().apply { removeAt(position) }
    }

    private fun getFoods(): MutableList<Food> {
        // toMutableList 를 통해 항상 새 인스턴스턴스, 즉 다른 주소를 갖는 데이터를 리턴 함으로써 adapter 에서 원활한 갱신이 되도록 한다.
        return food.value?.toMutableList() ?: mutableListOf()
    }
}