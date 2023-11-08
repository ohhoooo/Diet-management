package com.kjh.dietmanagement.view.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kjh.dietmanagement.databinding.ItemRankBinding
import com.kjh.dietmanagement.model.data.RankList

class RankingAdapter : ListAdapter<RankList, RankingAdapter.ViewHolder>(RankDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ViewHolder(private val binding: ItemRankBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RankList, position: Int) {
            with(binding) {
                rank = item
                count = position + 1
                executePendingBindings()
            }
        }
    }

    class RankDiffCallback : DiffUtil.ItemCallback<RankList>() {
        override fun areItemsTheSame(oldItem: RankList, newItem: RankList): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: RankList, newItem: RankList): Boolean {
            return oldItem == newItem
        }
    }
}