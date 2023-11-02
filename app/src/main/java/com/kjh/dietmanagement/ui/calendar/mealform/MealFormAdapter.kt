package com.kjh.dietmanagement.ui.calendar.mealform

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kjh.dietmanagement.databinding.ItemMealformBinding
import com.kjh.dietmanagement.model.Food
import com.kjh.dietmanagement.ui.calendar.common.FoodDiffCallback
import com.kjh.dietmanagement.ui.calendar.common.OnClickInterface

class MealFormAdapter(private val onClickListener: OnClickInterface) : ListAdapter<Food, MealFormAdapter.ViewHolder>(FoodDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMealformBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemMealformBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Food) {
            with(binding) {
                food = item
                btDelete.setOnClickListener {
                    onClickListener.onClick(layoutPosition)
                }
                executePendingBindings()
            }
        }
    }
}