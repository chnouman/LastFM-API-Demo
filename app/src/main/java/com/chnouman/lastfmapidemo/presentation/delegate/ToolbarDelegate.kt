package com.chnouman.lastfmapidemo.presentation.delegate

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.chnouman.lastfmapidemo.R

interface ToolbarDelegate {
    fun setupToolbar(activity: AppCompatActivity, toolbar: Toolbar, navController: NavController)
}

class ToolbarDelegateImpl : ToolbarDelegate {

    override fun setupToolbar(
        activity: AppCompatActivity,
        toolbar: Toolbar,
        navController: NavController
    ) {
        activity.setSupportActionBar(toolbar)
        activity.findNavController(R.id.navHostFragment).graph
        val config = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(
            navController,
            config
        )
    }

}