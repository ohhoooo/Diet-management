package com.kjh.dietmanagement.model

data class HomeDialog(
    val date: String, // 날짜
    val meals: List<Meal>, // 식사 리스트
)

data class Meal(
    val image: String, // 음식 이미지
    val classification: Classification, // 분류
    val foods: List<Food>, // 음식 리스트
)

data class Food(
    val name: String, // 음식 이름
    val calorie: String, // 칼로리
    val carbohydrate: String, // 탄수화물
    val protein: String, // 단백질
    val fat: String, // 지방
)

enum class Classification(val classification: String)
{
    MORNING("아침"),
    LUNCH("점심"),
    Dinner("저녁"),
    SNACK("간식"),
}