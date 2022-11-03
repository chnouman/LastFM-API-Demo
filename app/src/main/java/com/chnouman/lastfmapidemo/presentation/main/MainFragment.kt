package com.chnouman.lastfmapidemo.presentation.main

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.paging.LoadState
import com.chnouman.lastfmapidemo.R
import com.chnouman.lastfmapidemo.presentation.main.paging.MainLoadStateAdapter
import com.chnouman.lastfmapidemo.core.util.extensions.hide
import com.chnouman.lastfmapidemo.core.util.extensions.show
import com.chnouman.lastfmapidemo.databinding.FragmentMainBinding
import com.chnouman.lastfmapidemo.presentation.main.adapter.AlbumListAdapter
import com.chnouman.lastfmapidemo.presentation.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private var binding: FragmentMainBinding? = null
    private val adapter: AlbumListAdapter by lazy {
        AlbumListAdapter({
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailFragment(
                    it
                )
            )
        }, {}, {})
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOptionMenu()
        binding?.apply {
            binding?.albumsRecyclerView?.adapter =
                adapter.withLoadStateFooter(MainLoadStateAdapter())
            adapter.addLoadStateListener { loadState ->
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                    albumsRecyclerView.hide()
                    emptyTextView.show()
                } else {
                    albumsRecyclerView.show()
                    emptyTextView.hide()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.data.collectLatest {
                adapter.submitData(it)
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

