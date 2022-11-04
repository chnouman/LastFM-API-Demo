package com.chnouman.lastfmapidemo.presentation.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.chnouman.lastfmapidemo.R
import com.chnouman.lastfmapidemo.core.util.extensions.hide
import com.chnouman.lastfmapidemo.core.util.extensions.show
import com.chnouman.lastfmapidemo.databinding.FragmentMainBinding
import com.chnouman.lastfmapidemo.presentation.base.BaseFragment
import com.chnouman.lastfmapidemo.presentation.main.adapter.AlbumListAdapter
import com.chnouman.lastfmapidemo.presentation.main.paging.MainLoadStateAdapter
import com.chnouman.lastfmapidemo.presentation.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val viewModel: MainViewModel by viewModels()
    private val adapter: AlbumListAdapter by lazy {
        AlbumListAdapter({
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailFragment(
                    it
                )
            )
        }, {}, {})
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOptionMenu()
        viewDataBinding?.apply {
            albumsRecyclerView.adapter =
                adapter.apply {
                    withLoadStateFooter(MainLoadStateAdapter())
                    addLoadStateListener { loadState ->
                        manageLoadingState(loadState)
                    }
                }
        }
        lifecycleScope.launch {
            viewModel.data.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun manageLoadingState(loadState: CombinedLoadStates) {
        viewDataBinding?.apply {
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                albumsRecyclerView.hide()
                emptyTextView.show()
            } else {
                albumsRecyclerView.show()
                emptyTextView.hide()
            }
        }
    }

    private fun setupOptionMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.action_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return NavigationUI.onNavDestinationSelected(
                    menuItem,
                    requireView().findNavController()
                )

            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}

