package com.bsuir.photo.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsuir.domain.retrofit.model.UnsplashPhoto
import com.bsuir.photo.adapter.DeletedPhotoAdapter
import com.bsuir.photo.databinding.FragmentFavouritesPhotosBinding
import com.bsuir.photo.viewmodel.FavouritesViewModel

class FavouritesPhotosFragment : Fragment(), DeletedPhotoAdapter.OnItemClickListener {

    private lateinit var binding: FragmentFavouritesPhotosBinding
    private lateinit var viewModel: FavouritesViewModel
    private lateinit var adapter: DeletedPhotoAdapter
    private val unsplashPhotos = ArrayList<UnsplashPhoto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesPhotosBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)
        initRecyclerview()
        setObserver()
        viewModel.getPhotos()
        return binding.root
    }

    private fun initRecyclerview() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = DeletedPhotoAdapter(this)
        adapter.submitList(unsplashPhotos)
        binding.recyclerView.adapter = adapter
    }

    private fun setObserver() {
        viewModel.unsplashPhotos.observe(viewLifecycleOwner, { photos ->
            unsplashPhotos.clear()
            unsplashPhotos.addAll(photos)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onItemClick(photoId: String) {
        val action =
            FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(photoId)
        findNavController().navigate(action)
    }

    override fun onDeleteClick(photoId: String, position: Int) {
        unsplashPhotos.removeAt(position)
        adapter.notifyDataSetChanged()
        viewModel.deleteFavorite(photoId)
        setObserver()
    }
}