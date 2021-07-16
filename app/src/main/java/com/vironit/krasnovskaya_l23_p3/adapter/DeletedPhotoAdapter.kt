package com.vironit.krasnovskaya_l23_p3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vironit.domain.retrofit.model.UnsplashPhoto
import com.vironit.krasnovskaya_l23_p3.common.util.ImageUtils
import com.vironit.krasnovskaya_l23_p3.databinding.DeletedRecyclerviewItemBinding

class DeletedPhotoAdapter(private val listener: OnItemClickListener) :
    ListAdapter<UnsplashPhoto, DeletedPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            DeletedRecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeletedPhotoAdapter.PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem, position)
        }
    }

    inner class PhotoViewHolder(private val binding: DeletedRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: UnsplashPhoto, position: Int) {
            ImageUtils.buildGradle(
                itemView.context,
                photo.urlUnsplash?.regular.toString(),
                binding.ivItem
            )
            binding.ivItem.setOnClickListener { listener.onItemClick(photo.id) }
            binding.delete.setOnClickListener { listener.onDeleteClick(photo.id, position) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(photoId: String)
        fun onDeleteClick(photoId: String, position: Int)
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