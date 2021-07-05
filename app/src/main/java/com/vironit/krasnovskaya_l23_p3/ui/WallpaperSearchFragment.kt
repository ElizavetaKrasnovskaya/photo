package com.vironit.krasnovskaya_l23_p3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.vironit.domain.model.Photo
import com.vironit.krasnovskaya_l23_p3.R
import com.vironit.krasnovskaya_l23_p3.adapter.ImageItemAdapter
import com.vironit.krasnovskaya_l23_p3.databinding.FragmentWallpaperSearchBinding


class WallpaperSearchFragment : Fragment() {
    private lateinit var binding: FragmentWallpaperSearchBinding
    private lateinit var adapter: ImageItemAdapter
    private lateinit var adapterList: ArrayList<Photo>
    private val viewModel: PhotosViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWallpaperSearchBinding.inflate(inflater, container, false)
        initRecyclerView()
        binding.changeSize.setOnClickListener {
            binding.changeSize.background = resources.getDrawable(R.drawable.grid3)
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        }
        return binding.root
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapterList = viewModel.allPhotos
        adapter = ImageItemAdapter()
        adapter.submitList(adapterList)
        adapter.notifyDataSetChanged()
        binding.recyclerView.adapter = adapter
    }
}