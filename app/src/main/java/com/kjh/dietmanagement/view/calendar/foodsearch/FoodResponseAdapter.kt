package com.kjh.dietmanagement.view.calendar.foodsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kjh.dietmanagement.databinding.ItemFoodsearchResponseBinding
import com.kjh.dietmanagement.model.data.Food
import com.kjh.dietmanagement.view.common.FoodDiffCallback

class FoodResponseAdapter(private val callback: (Food) -> Unit) : ListAdapter<Food, FoodResponseAdapter.ViewHolder>(
    FoodDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodsearchResponseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemFoodsearchResponseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Food) {
            with(binding) {
                food = item
                btAdd.setOnClickListener {
                    callback(item)
                }
                executePendingBindings()
            }
        }
    }
}