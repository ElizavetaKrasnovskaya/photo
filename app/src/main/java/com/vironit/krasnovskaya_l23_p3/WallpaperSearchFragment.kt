package com.vironit.krasnovskaya_l23_p3

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vironit.krasnovskaya_l23_p3.databinding.FragmentWallpaperSearchBinding


class WallpaperSearchFragment : Fragment() {
    private lateinit var binding: FragmentWallpaperSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWallpaperSearchBinding.inflate(inflater, container, false)

        return inflater.inflate(R.layout.fragment_wallpaper_search, container, false)
    }
}