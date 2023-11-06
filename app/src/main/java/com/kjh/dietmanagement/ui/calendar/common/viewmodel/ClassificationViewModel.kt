package com.kjh.dietmanagement.ui.calendar.common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClassificationViewModel : ViewModel() {

    private val _classification = MutableLiveData<Int>()
    val classification: LiveData<Int> = _classification

    fun setClassification(num: Int) {
        _classification.value = num
    }
}