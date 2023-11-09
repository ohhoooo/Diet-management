package com.kjh.dietmanagement.view.calendar.homedialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kjh.dietmanagement.databinding.ItemFoodInformationBinding
import com.kjh.dietmanagement.model.data.HomeDialog

class HomeDialogAdapter(private val callback: () -> Unit) : ListAdapter<HomeDialog, HomeDialogAdapter.ViewHolder>(HomeDialogDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodInformationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemFoodInformationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeDialog) {
            with(binding) {
                nutrient = item
                callback()
                executePendingBindings()
            }
        }
    }

    class HomeDialogDiffCallback : DiffUtil.ItemCallback<HomeDialog>() {
        override fun areItemsTheSame(oldItem: HomeDialog, newItem: HomeDialog): Boolean {
            return oldItem.foodName == newItem.foodName && oldItem.dan == newItem.dan
        }

        override fun areContentsTheSame(oldItem: HomeDialog, newItem: HomeDialog): Boolean {
            return oldItem == newItem
        }
    }
}