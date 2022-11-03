package com.chnouman.lastfmapidemo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.chnouman.lastfmapidemo.R
import com.chnouman.lastfmapidemo.databinding.ActivityMainBinding
import com.chnouman.lastfmapidemo.presentation.delegate.ToolbarDelegate
import com.chnouman.lastfmapidemo.presentation.delegate.ToolbarDelegateImpl
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ToolbarDelegate by ToolbarDelegateImpl() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar(this, binding.toolbar, findNavController(R.id.navHostFragment))
    }
}
