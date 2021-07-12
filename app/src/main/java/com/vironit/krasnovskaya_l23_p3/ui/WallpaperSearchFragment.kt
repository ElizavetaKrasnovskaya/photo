package com.vironit.krasnovskaya_l23_p3.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vironit.domain.model.unsplash.Photo
import com.vironit.krasnovskaya_l23_p3.R
import com.vironit.krasnovskaya_l23_p3.adapter.UnsplashPhotoAdapter
import com.vironit.krasnovskaya_l23_p3.databinding.FragmentWallpaperSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class WallpaperSearchFragment : Fragment(), UnsplashPhotoAdapter.OnItemClickListener {

    private var _binding: FragmentWallpaperSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<GalleryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWallpaperSearchBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.hide()
        requireActivity().window.statusBarColor = resources.getColor(R.color.white)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_bar).isVisible = true
        setAdapter()
        searchView()
//        changeManager()
        return binding.root
    }

//    private fun changeManager() {
//        binding.btnManager.setOnClickListener {
//            if (binding.btnManager.background.constantState == resources.getDrawable(R.drawable.ic_grid2).constantState) {
//                binding.btnManager.background = resources.getDrawable(R.drawable.ic_grid3)
//                binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
//            } else {
//                binding.btnManager.background = resources.getDrawable(R.drawable.ic_grid2)
//                binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
//            }
//        }
//
//    }

    private fun setAdapter() {
        val adapter = UnsplashPhotoAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            val layoutAnimationController: LayoutAnimationController =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right)
            binding.recyclerView.layoutAnimation = layoutAnimationController
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

    private fun submitData(adapter: UnsplashPhotoAdapter) {
        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    @SuppressLint("CheckResult")
    private fun searchView() {
        binding.searchView.setIconifiedByDefault(false)
        val closeBtn = binding.searchView.findViewById(R.id.search_close_btn) as ImageView
        closeBtn.isEnabled = false
        var counter = 0
        closeBtn.setImageDrawable(null)
        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.recyclerView.scrollToPosition(0)
                    binding.searchView.clearFocus()
                    counter++
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.length <= 10) {
                        binding.recyclerView.scrollToPosition(0)
                        subscriber.onNext(newText!!)
                    } else {
                        Toast.makeText(requireContext(), "Max size 10 letters", Toast.LENGTH_SHORT)
                            .show()
                        binding.searchView.setQuery(newText.substring(0, 10), false)
                    }
                    return false
                }
            })
        })
            .subscribe { text ->
                if(counter<2) {
                    viewModel.searchPhotos(text)
                }else{
                    Toast.makeText(requireContext(), "Limit of requestes is 20. Wait an hour", Toast.LENGTH_SHORT).show()
                    binding.searchView.setQuery(text.substring(0, 0), false)
                }
            }
    }

    override fun onItemClick(photo: Photo) {
        val action = WallpaperSearchFragmentDirections.actionSearchFragmentToDetailsFragment(photo)
        findNavController().navigate(action)
    }
}