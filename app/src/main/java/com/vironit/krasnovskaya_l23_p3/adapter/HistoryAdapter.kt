package com.vironit.krasnovskaya_l23_p3.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vironit.data.database.model.SearchEntity
import com.vironit.krasnovskaya_l23_p3.R
import com.vironit.krasnovskaya_l23_p3.databinding.HistoryRecyclerviewItemBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(private val listener: OnItemClickListener) :
    ListAdapter<SearchEntity, HistoryAdapter.ViewHolder>(SEARCH_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnItemClickListener {
        fun onItemClick(query: String)
        fun onLikeClick(id: Int, isFavourite: Int)
    }

    inner class ViewHolder(private val binding: HistoryRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(searchEntity: SearchEntity) {
            binding.query.text = searchEntity.query
            binding.totalAndDate.text =
                "${searchEntity.total} результатов, ${formatPubDate(searchEntity.date)}"
            binding.cardView.setOnClickListener {
                searchEntity.query?.let { it1 ->
                    listener.onItemClick(
                        it1
                    )
                }
            }
            if (searchEntity.isFavourite) {
                binding.like.background =
                    itemView.context.resources.getDrawable(R.drawable.ic_blue_favorite)
            } else {
                binding.like.background =
                    itemView.context.resources.getDrawable(R.drawable.ic_favorite)
            }
            binding.like.setOnClickListener {
                listener.onLikeClick(
                    searchEntity.id,
                    if (searchEntity.isFavourite) 0 else 1
                )
            }
        }

        private fun formatPubDate(pubDate: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val date = dateFormat.parse(pubDate)
            return DateUtils.getRelativeTimeSpanString(
                date.time, Calendar.getInstance().timeInMillis,
                DateUtils.SECOND_IN_MILLIS
            ).toString()
        }
    }

    companion object {
        private val SEARCH_COMPARATOR = object : DiffUtil.ItemCallback<SearchEntity>() {
            override fun areItemsTheSame(oldItem: SearchEntity, newItem: SearchEntity) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: SearchEntity, newItem: SearchEntity) =
                oldItem.id == newItem.id && oldItem.isFavourite == newItem.isFavourite
        }
    }
}