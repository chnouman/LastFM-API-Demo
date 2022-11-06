package com.chnouman.lastfmapidemo.presentation.delegate

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.chnouman.lastfmapidemo.R

interface OptionMenuDelegate {
    fun setupOptionMenu(
        activity: FragmentActivity,
        fragment: Fragment,
        viewLifecycleOwner: LifecycleOwner
    )
}

class OptionMenuDelegateImpl : OptionMenuDelegate {
    override fun setupOptionMenu(
        activity: FragmentActivity,
        fragment: Fragment,
        viewLifecycleOwner: LifecycleOwner
    ) {
        val menuHost: MenuHost = activity
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.action_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return NavigationUI.onNavDestinationSelected(
                    menuItem,
                    fragment.requireView().findNavController()
                )
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}
