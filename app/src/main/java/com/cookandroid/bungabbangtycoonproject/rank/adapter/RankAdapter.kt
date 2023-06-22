package com.cookandroid.bungabbangtycoonproject.rank.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cookandroid.bungabbangtycoonproject.databinding.ItemRecyclerRankBinding
import com.cookandroid.bungabbangtycoonproject.rank.room.Rank
import java.text.DecimalFormat

class RankAdapter: RecyclerView.Adapter<RankAdapter.ViewHolder>() {
    var recordList = mutableListOf<Rank>()
    inner class ViewHolder(private val binding: ItemRecyclerRankBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(rankData: Rank, position: Int) {
            binding.tvRank.text = (position + 1).toString()
            binding.tvName.text = rankData.name
            binding.tvRevenue.text = DecimalFormat("#,###").format(rankData.revenue) + "원"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recordList[position], position)
    }

    override fun getItemCount(): Int {
        var max: Int = recordList.size
        if(max > 10) max = 10
        return max
    }
}