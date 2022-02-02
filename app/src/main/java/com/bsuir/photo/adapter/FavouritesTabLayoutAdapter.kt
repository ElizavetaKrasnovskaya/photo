package com.bsuir.photo.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bsuir.photo.ui.favourites.FavouritesPhotosFragment
import com.bsuir.photo.ui.favourites.FavouritesSearchFragment

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