package com.vironit.krasnovskaya_l23_p3.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.vironit.krasnovskaya_l23_p3.R
import com.vironit.krasnovskaya_l23_p3.databinding.FragmentSplashScreenBinding


class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.blue)
        }
        Handler().postDelayed(Runnable {
//            val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToWallpaperSearchFragment()
//            Navigation.findNavController(requireView()).navigate(action)
        }, 3000)
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

}