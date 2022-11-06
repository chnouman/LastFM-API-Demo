package com.chnouman.lastfmapidemo.presentation.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.chnouman.lastfmapidemo.R
import com.chnouman.lastfmapidemo.core.util.extensions.hide
import com.chnouman.lastfmapidemo.core.util.extensions.show
import com.chnouman.lastfmapidemo.databinding.FragmentSearchBinding
import com.chnouman.lastfmapidemo.presentation.base.BaseFragment
import com.chnouman.lastfmapidemo.presentation.main.paging.MainLoadStateAdapter
import com.chnouman.lastfmapidemo.presentation.search.adapter.ArtistListAdapter
import com.chnouman.lastfmapidemo.presentation.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Populate the list of artists based on user query
 * List will provide pagination support also
 * */
@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val viewModel: SearchViewModel by viewModels()

    private val adapter: ArtistListAdapter by lazy {
        ArtistListAdapter {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToTopAlbumFragment(
                    it
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.apply {
            adapter.addLoadStateListener { loadState -> manageLoadingState(loadState) }
            artistsRecyclerView.adapter =
                adapter.withLoadStateFooter(MainLoadStateAdapter())
            searchEditText.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchButton.performClick()
                    return@OnEditorActionListener true
                }
                false
            })
            searchButton.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.searchArtistPaged(searchEditText.text.toString())
                        .collect { pagingData ->
                            adapter.submitData(pagingData)
                        }
                }
            }
        }
    }

    private fun manageLoadingState(loadState: CombinedLoadStates) {
        viewDataBinding?.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    // Show first time loading UI
                    artistsRecyclerView.show()
                    emptyTextView.hide()
                }

                loadState.append is LoadState.Loading -> {
                    // Show little loading UI as we already have some items and we're loading
                }

                loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1 -> {
                    // Show empty UI as there's no data available
                    artistsRecyclerView.hide()
                    emptyTextView.show()
                }

                loadState.refresh is LoadState.Error -> {
                    // Failed to load data in first try
                    emptyTextView.text = (loadState.refresh as LoadState.Error).error.message
                    emptyTextView.show()
                    artistsRecyclerView.hide()
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
}
