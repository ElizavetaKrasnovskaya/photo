package com.vironit.krasnovskaya_l23_p3.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
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
        supportActionBar?.hide()
        setContentView(binding.root)
        val navController = findNavController(R.id.main_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.search_fragment, R.id.history_fragment, R.id.favourites_fragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomBar.setupWithNavController(navController)
    }
}