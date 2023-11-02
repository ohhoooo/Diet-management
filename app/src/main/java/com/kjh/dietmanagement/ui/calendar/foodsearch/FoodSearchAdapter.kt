package com.kjh.dietmanagement.ui.calendar.foodsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kjh.dietmanagement.databinding.ItemFoodsearchBinding
import com.kjh.dietmanagement.model.Food
import com.kjh.dietmanagement.ui.calendar.common.FoodDiffCallback
import com.kjh.dietmanagement.ui.calendar.common.OnClickInterface

class FoodSearchAdapter(private val onClickListener: OnClickInterface) : ListAdapter<Food, FoodSearchAdapter.ViewHolder>(
    FoodDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodsearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemFoodsearchBinding) : RecyclerView.ViewHolder(binding.root) {
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