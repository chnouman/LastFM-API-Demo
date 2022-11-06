package com.chnouman.lastfmapidemo.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.chnouman.lastfmapidemo.R
import com.chnouman.lastfmapidemo.core.util.extensions.hide
import com.chnouman.lastfmapidemo.core.util.extensions.show
import com.chnouman.lastfmapidemo.databinding.FragmentMainBinding
import com.chnouman.lastfmapidemo.presentation.base.BaseFragment
import com.chnouman.lastfmapidemo.presentation.delegate.OptionMenuDelegate
import com.chnouman.lastfmapidemo.presentation.delegate.OptionMenuDelegateImpl
import com.chnouman.lastfmapidemo.presentation.main.adapter.AlbumListAdapter
import com.chnouman.lastfmapidemo.presentation.main.paging.MainLoadStateAdapter
import com.chnouman.lastfmapidemo.presentation.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate),
    OptionMenuDelegate by OptionMenuDelegateImpl() {
    private val viewModel: MainViewModel by viewModels()
    private var adapter: AlbumListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AlbumListAdapter({
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailFragment(
                    it
                )
            )
        }, { album ->
            viewModel.deleteAlbum(album)
        })
        setupOptionMenu(requireActivity(), this, viewLifecycleOwner)
        viewDataBinding?.apply {
            albumsRecyclerView.adapter =
                adapter?.apply {
                    withLoadStateFooter(MainLoadStateAdapter())
                    addLoadStateListener { loadState ->
                        manageLoadingState(loadState)
                    }
                }
        }
        lifecycleScope.launch {
            viewModel.data.collectLatest {
                adapter?.submitData(it)
            }
        }
    }

    private fun manageLoadingState(loadState: CombinedLoadStates) {
        viewDataBinding?.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    // Show first time loading UI
                    albumsRecyclerView.show()
                    emptyTextView.hide()
                }

                loadState.append is LoadState.Loading -> {
                    // Show little loading UI as we already have some items and we're loading
                }

                loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter?.itemCount!! < 1 -> {
                    // Show empty UI as there's no data available
                    albumsRecyclerView.hide()
                    emptyTextView.show()
                }

                loadState.refresh is LoadState.Error -> {
                    // Failed to load data in first try
                    emptyTextView.text = (loadState.refresh as LoadState.Error).error.message
                    emptyTextView.show()
                    albumsRecyclerView.hide()
                }

                loadState.append is LoadState.Error -> {
                    // Failed to load data while appending to existing items
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.no_more_records),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        viewDataBinding?.albumsRecyclerView?.adapter = null
        adapter = null
        super.onDestroyView()
    }
}
