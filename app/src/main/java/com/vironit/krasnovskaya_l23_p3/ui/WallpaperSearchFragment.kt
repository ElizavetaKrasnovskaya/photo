package com.vironit.krasnovskaya_l23_p3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.vironit.krasnovskaya_l23_p3.R
import com.vironit.krasnovskaya_l23_p3.adapter.UnsplashPhotoAdapter
import com.vironit.krasnovskaya_l23_p3.data.UnsplashPhoto
import com.vironit.krasnovskaya_l23_p3.databinding.FragmentWallpaperSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WallpaperSearchFragment : Fragment(),  UnsplashPhotoAdapter.OnItemClickListener {

    private var _binding: FragmentWallpaperSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<GalleryViewModel>()
    private var isGrid2: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWallpaperSearchBinding.inflate(inflater, container, false)
        setAdapter()
        searchView()
        changeManager()
        return binding.root
    }

    override fun onItemClick(photo: UnsplashPhoto) {
        TODO("Not yet implemented")
    }

    private fun changeManager(){
        binding.btnManager.setOnClickListener {
            if(isGrid2){
                binding.btnManager.background = resources.getDrawable(R.drawable.ic_grid3)
                binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
                isGrid2 = false
            } else{
                binding.btnManager.background = resources.getDrawable(R.drawable.ic_grid2)
                binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                isGrid2 = true
            }
        }

    }

    private fun setAdapter(){
        val adapter = UnsplashPhotoAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerView.adapter = adapter
            buttonRetry.setOnClickListener { adapter.retry() }
        }
        submitData(adapter)

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }
    }

    private fun submitData(adapter: UnsplashPhotoAdapter){
        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun searchView() {
        binding.searchView.setIconifiedByDefault(false)
        binding.searchView.clearFocus()
        val searchViewIcon: ImageView =
            binding.searchView.findViewById(R.id.search_close_btn) as ImageView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!=null){
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    binding.searchView.clearFocus()
                }
                searchViewIcon.visibility = View.GONE
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchPhotos(it) }
                searchViewIcon.visibility = View.GONE
                return true
            }
        })
    }

}