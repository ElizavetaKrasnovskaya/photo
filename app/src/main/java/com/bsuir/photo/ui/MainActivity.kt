package com.bsuir.photo.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bsuir.photo.R
import com.bsuir.photo.databinding.ActivityMainBinding
import com.paulrybitskyi.persistentsearchview.utils.Utils.getLocale
import java.util.*

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
        binding.locale.setOnClickListener {
            changeLocale()
        }
        binding.bottomBar.setupWithNavController(navController)
    }

    private fun showBottomNav() {
        binding.bottomBar.visibility = View.VISIBLE
        binding.locale.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomBar.visibility = View.GONE
        binding.locale.visibility = View.GONE
    }

    private fun changeLocale() {
        val locale = if (getLocale(this).language == "ru") {
            Locale("en")
        } else {
            Locale("ru")
        }
        val config = Configuration(resources.configuration)
        Locale.setDefault(locale)
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
        binding.bottomBar.menu.clear()
        binding.bottomBar.inflateMenu(R.menu.menu_bottom)
    }
}