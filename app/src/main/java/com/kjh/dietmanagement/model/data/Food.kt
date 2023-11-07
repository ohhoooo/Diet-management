package com.kjh.dietmanagement.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val name: String, // 음식 이름
    val calorie: String, // 칼로리
    val carbohydrate: String, // 탄수화물
    val protein: String, // 단백질
    val fat: String, // 지방
) : Parcelable