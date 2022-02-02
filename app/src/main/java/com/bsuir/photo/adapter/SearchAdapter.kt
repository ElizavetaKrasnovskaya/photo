package com.bsuir.photo.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.domain.database.model.SearchEntity
import com.bsuir.photo.R
import com.bsuir.photo.databinding.SearchRecyclerviewItemBinding
import java.text.SimpleDateFormat
import java.util.*

class SearchAdapter(private val listener: OnItemClickListener) :
    ListAdapter<SearchEntity, SearchAdapter.ViewHolder>(SEARCH_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    interface OnItemClickListener {
        fun onItemClick(query: String)
        fun onDeleteClick(searchId: Int, position: Int)
    }

    inner class ViewHolder(private val binding: SearchRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(searchEntity: SearchEntity, position: Int) {
            binding.query.text = searchEntity.query
            binding.totalAndDate.text =
                "${searchEntity.total} ${itemView.context.resources.getString(R.string.results)}, ${
                    formatPubDate(
                        searchEntity.date
                    )
                }"
            binding.cardView.setOnClickListener {
                searchEntity.query?.let { it1 ->
                    listener.onItemClick(
                        it1
                    )
                }
            }
            binding.delete.setOnClickListener { listener.onDeleteClick(searchEntity.id, position) }
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
                oldItem.id == newItem.id
        }
    }
}