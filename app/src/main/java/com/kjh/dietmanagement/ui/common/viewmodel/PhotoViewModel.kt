package com.kjh.dietmanagement.ui.common.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PhotoViewModel : ViewModel() {

    private val _photo = MutableLiveData<Uri>()
    val photo: LiveData<Uri> = _photo

    fun setPhoto(photo: Uri) {
        _photo.value = photo
    }

    fun removePhoto() {
        _photo.value = Uri.EMPTY
    }
}