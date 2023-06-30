package com.kjh.dietmanagement.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val name: String = "", // 음식 이름
    var calorie: String = "0.0", // 칼로리
    var carbohydrate: String = "0.0", // 탄수화물
    var protein: String = "0.0", // 단백질
    var fat: String = "0.0", // 지방
) : Parcelable