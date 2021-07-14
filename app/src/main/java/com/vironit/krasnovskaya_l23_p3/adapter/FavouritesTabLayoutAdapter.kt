package com.vironit.krasnovskaya_l23_p3.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vironit.krasnovskaya_l23_p3.ui.favourites.FavouritesPhotosFragment
import com.vironit.krasnovskaya_l23_p3.ui.favourites.FavouritesSearchFragment

class FavouritesTabLayoutAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                FavouritesPhotosFragment()
            }
            else -> {
                FavouritesSearchFragment()
            }
        }
    }
}