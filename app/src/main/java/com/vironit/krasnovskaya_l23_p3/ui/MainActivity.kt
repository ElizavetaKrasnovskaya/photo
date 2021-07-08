package com.vironit.krasnovskaya_l23_p3.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.vironit.krasnovskaya_l23_p3.R
import com.vironit.krasnovskaya_l23_p3.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            Thread.sleep(700)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val navController = findNavController(R.id.main_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.search_fragment, R.id.history_fragment, R.id.favourites_fragment
            )
        )
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.search_fragment -> showBottomNav()
//                R.id.search_fragment -> showBottomNav()
//                else -> hideBottomNav()
//            }
//        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomBar.setupWithNavController(navController)
    }

//    private fun showBottomNav() {
//        binding.bottomBar.visibility = View.VISIBLE
//
//    }
//
//    private fun hideBottomNav() {
//        binding.bottomBar.visibility = View.GONE
//    }
}