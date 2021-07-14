package com.vironit.krasnovskaya_l23_p3.ui.favourites

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vironit.krasnovskaya_l23_p3.R
import com.vironit.krasnovskaya_l23_p3.adapter.FavouritesTabLayoutAdapter
import com.vironit.krasnovskaya_l23_p3.databinding.FragmentFavouritesBinding


class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var adapter: FavouritesTabLayoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(layoutInflater, container, false)
        initTabs()
        return binding.root
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
}