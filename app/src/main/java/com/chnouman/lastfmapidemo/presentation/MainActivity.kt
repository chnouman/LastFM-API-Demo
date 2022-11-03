package com.chnouman.lastfmapidemo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.chnouman.lastfmapidemo.R
import com.chnouman.lastfmapidemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val config = AppBarConfiguration(findNavController(R.id.navHostFragment).graph)
        binding.toolbar.setupWithNavController(
            findNavController(R.id.navHostFragment),
            config
        )
    }
}
