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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vironit.data.retrofit.model.UnsplashPhoto
import com.vironit.krasnovskaya_l23_p3.R
import com.vironit.krasnovskaya_l23_p3.adapter.PhotoAdapter
import com.vironit.krasnovskaya_l23_p3.common.util.ImageUtils
import com.vironit.krasnovskaya_l23_p3.databinding.FragmentWallpaperSearchBinding
import com.vironit.krasnovskaya_l23_p3.viewmodel.UnsplashViewModel
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

class WallpaperSearchFragment : Fragment(), PhotoAdapter.OnItemClickListener {

    private lateinit var binding: FragmentWallpaperSearchBinding
    private lateinit var viewModel: UnsplashViewModel
    private var layoutManager: LinearLayoutManager? = null
    private val lastVisibleItemPosition: Int
        get() = layoutManager!!.findLastVisibleItemPosition()
    private lateinit var adapter: PhotoAdapter

    private val unsplashPhotos = ArrayList<UnsplashPhoto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWallpaperSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(UnsplashViewModel::class.java)
        setUI()
        initRecyclerView()
        changeManager()
        if (ImageUtils.isOnline(requireActivity())) {
            viewModel.getPhotos()
        } else {
            //TODO make error page?
        }
        searchView()
        setObserver()
        return binding.root
    }

    private fun setUI() {
        (activity as AppCompatActivity).supportActionBar?.hide()
        requireActivity().window.statusBarColor = resources.getColor(R.color.white)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_bar).isVisible = true
    }

    private fun setObserver() {
        viewModel.unsplashPhotos.observe(viewLifecycleOwner, { photos ->
            unsplashPhotos.addAll(photos)
            adapter.notifyDataSetChanged()
        })
        viewModel.showProgress.observe(viewLifecycleOwner, { showProgress ->
            binding.progressBar.visibility = if (showProgress) View.VISIBLE else View.GONE
        })
        viewModel.requestError.observe(viewLifecycleOwner, { requestError ->
            Toast.makeText(requireContext(), requestError, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initRecyclerView() {
        adapter = PhotoAdapter(this)
        adapter.submitList(unsplashPhotos)
        binding.recyclerView.itemAnimator = null
        layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.layoutManager = layoutManager
        val layoutAnimationController: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right)
        binding.recyclerView.layoutAnimation = layoutAnimationController
        binding.recyclerView.adapter = adapter
        setRecyclerViewScrollListener()
    }

    private fun setRecyclerViewScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    viewModel.getPhotos()
                }
            }
        })
    }

    override fun onItemClick(id: String) {
        val action = WallpaperSearchFragmentDirections.actionSearchFragmentToDetailsFragment(id)
        findNavController().navigate(action)
    }

    private fun changeManager() {
        binding.btnManager.setOnClickListener {
            if (binding.btnManager.background.constantState == resources.getDrawable(R.drawable.ic_grid2).constantState) {
                binding.btnManager.background = resources.getDrawable(R.drawable.ic_grid3)
                binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            } else {
                binding.btnManager.background = resources.getDrawable(R.drawable.ic_grid2)
                binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }

    }

    @SuppressLint("CheckResult")
    private fun searchView() {
        binding.searchView.setIconifiedByDefault(false)
        val closeBtn = binding.searchView.findViewById(R.id.search_close_btn) as ImageView
        closeBtn.isEnabled = false
        closeBtn.setImageDrawable(null)
        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.recyclerView.scrollToPosition(0)
                    binding.searchView.clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.length <= 10) {
                        binding.recyclerView.scrollToPosition(0)
                        unsplashPhotos.clear()
                        subscriber.onNext(newText)
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
                viewModel.searchPhotos(text)
            }
    }
}