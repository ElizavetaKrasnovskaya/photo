package com.bsuir.photo.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.domain.retrofit.model.UnsplashPhoto
import com.bsuir.photo.R
import com.bsuir.photo.adapter.PhotoAdapter
import com.bsuir.photo.common.base.BaseFragment
import com.bsuir.photo.databinding.FragmentWallpaperSearchBinding
import com.bsuir.photo.viewmodel.UnsplashViewModel
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlin.system.exitProcess


class WallpaperSearchFragment : BaseFragment(R.layout.fragment_wallpaper_search),
    PhotoAdapter.OnItemClickListener {

    private val args by navArgs<WallpaperSearchFragmentArgs>()
    private lateinit var binding: FragmentWallpaperSearchBinding
    private lateinit var viewModel: UnsplashViewModel
    private var layoutManager: LinearLayoutManager? = null
    private val lastVisibleItemPosition: Int
        get() = layoutManager!!.findLastVisibleItemPosition()
    private lateinit var adapter: PhotoAdapter
    private var backPressed = false

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
        setQuery()
//        if (ImageUtils.isOnline(requireActivity())) {
//            viewModel.getPhotos()
//        } else {
//            //TODO make error page?
//        }
        searchView()
        setObserver()
        backNavigation()
        return binding.root
    }

    private fun setQuery() {
        if (!args.query.isNullOrBlank()) {
            viewModel.searchPhotos(args.query)
            binding.searchView.setQuery(args.query, true)
        } else {
            viewModel.getPhotos()
        }
    }

    private fun setUI() {
        requireActivity().window.statusBarColor = resources.getColor(R.color.white)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
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
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.recyclerView.scrollToPosition(0)
                    if (query != null) {
                        unsplashPhotos.clear()
                        viewModel.saveSearch(query)
                        subscriber.onNext(query)
                    }
                    binding.searchView.clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.length <= 30) {
                        binding.recyclerView.scrollToPosition(0)
                        unsplashPhotos.clear()
                        subscriber.onNext(newText)
                    } else {
                        Toast.makeText(requireContext(), "Max size 30 letters", Toast.LENGTH_SHORT)
                            .show()
                        binding.searchView.setQuery(newText.substring(0, 30), false)
                    }
                    return false
                }
            })
        })
            .subscribe { text ->
                viewModel.searchPhotos(text)
            }
    }

    private fun backNavigation() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (backPressed) {
                        exitProcess(0)
                    } else {
                        backPressed = true
                        toast("Press again to exit")
                    }
                    Handler().postDelayed({ backPressed = false }, 2000)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}