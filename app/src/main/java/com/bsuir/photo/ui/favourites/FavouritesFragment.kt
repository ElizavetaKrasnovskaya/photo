package com.bsuir.photo.ui.favourites

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import com.bsuir.photo.R
import com.bsuir.photo.adapter.FavouritesTabLayoutAdapter
import com.bsuir.photo.common.base.BaseFragment
import com.bsuir.photo.databinding.FragmentFavouritesBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.system.exitProcess


class FavouritesFragment : BaseFragment(R.layout.fragment_favourites) {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var adapter: FavouritesTabLayoutAdapter
    private var backPressed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(layoutInflater, container, false)
        setUI()
        initTabs()
        backNavigation()
        return binding.root
    }

    private fun setUI() {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun initTabs() {
        adapter = FavouritesTabLayoutAdapter(this)
        binding.viewPager.adapter = adapter
        binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.blue))
        binding.tabLayout.setBackgroundColor(Color.WHITE)
        binding.tabLayout.tabTextColors =
            ContextCompat.getColorStateList(requireContext(), R.color.blue)
        binding.tabLayout.tabMode = TabLayout.MODE_FIXED
        binding.tabLayout.isInlineLabel = true

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.ic_image)
                }
                else -> {
                    tab.setIcon(R.drawable.ic_bookmark)
                }
            }
        }.attach()

        binding.tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.icon!!.setColorFilter(resources.getColor(R.color.blue), PorterDuff.Mode.SRC_IN)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.icon!!.setColorFilter(
                    resources.getColor(R.color.iconColor),
                    PorterDuff.Mode.SRC_IN
                )
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
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