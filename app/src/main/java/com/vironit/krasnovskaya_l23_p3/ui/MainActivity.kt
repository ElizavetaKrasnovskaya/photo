package com.vironit.krasnovskaya_l23_p3.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.vironit.krasnovskaya_l23_p3.R
import com.vironit.krasnovskaya_l23_p3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.main_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splash_screen_fragment -> hideBottomNav()
                R.id.detailsFragment -> hideBottomNav()
                else -> showBottomNav()
            }
        }
        binding.bottomBar.setupWithNavController(navController)
    }

    private fun showBottomNav() {
        binding.bottomBar.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        binding.bottomBar.visibility = View.GONE

    }
}