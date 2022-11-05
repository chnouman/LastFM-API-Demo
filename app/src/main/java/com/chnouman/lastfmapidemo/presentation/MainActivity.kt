package com.chnouman.lastfmapidemo.presentation

import androidx.navigation.findNavController
import com.chnouman.lastfmapidemo.R
import com.chnouman.lastfmapidemo.databinding.ActivityMainBinding
import com.chnouman.lastfmapidemo.presentation.base.BaseActivity
import com.chnouman.lastfmapidemo.presentation.delegate.ToolbarDelegate
import com.chnouman.lastfmapidemo.presentation.delegate.ToolbarDelegateImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    ToolbarDelegate by ToolbarDelegateImpl() {

    override fun initUserInterface() {
        setupToolbar(this, viewDataBinding.toolbar, findNavController(R.id.navHostFragment))
    }
}
