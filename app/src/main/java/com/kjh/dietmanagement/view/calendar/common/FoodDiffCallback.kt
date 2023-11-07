package com.kjh.dietmanagement.view.calendar.common

import androidx.recyclerview.widget.DiffUtil
import com.kjh.dietmanagement.model.data.Food

class FoodDiffCallback : DiffUtil.ItemCallback<Food>() {
    override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem == newItem
    }
}