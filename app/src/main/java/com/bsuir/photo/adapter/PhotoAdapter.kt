package com.bsuir.photo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.domain.retrofit.model.UnsplashPhoto
import com.bsuir.photo.common.util.ImageUtils
import com.bsuir.photo.databinding.RecyclerviewItemBinding


class PhotoAdapter(private val listener: OnItemClickListener) :
    ListAdapter<UnsplashPhoto, PhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class PhotoViewHolder(private val binding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: UnsplashPhoto) {
            ImageUtils.buildGradle(
                itemView.context,
                photo.urlUnsplash?.regular.toString(),
                binding.ivItem
            )
            binding.ivItem.setOnClickListener { listener.onItemClick(photo.id) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: String)
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem.width == newItem.width && oldItem.height == newItem.height &&
                        oldItem.color == newItem.color && oldItem.description == newItem.description &&
                        oldItem.date == newItem.date
        }
    }
}