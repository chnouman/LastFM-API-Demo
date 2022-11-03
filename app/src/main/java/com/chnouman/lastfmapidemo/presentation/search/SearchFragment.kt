package com.chnouman.lastfmapidemo.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.chnouman.lastfmapidemo.core.util.extensions.hide
import com.chnouman.lastfmapidemo.core.util.extensions.show
import com.chnouman.lastfmapidemo.databinding.FragmentSearchBinding
import com.chnouman.lastfmapidemo.presentation.main.paging.MainLoadStateAdapter
import com.chnouman.lastfmapidemo.presentation.search.adapter.ArtistListAdapter
import com.chnouman.lastfmapidemo.presentation.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private var binding: FragmentSearchBinding? = null

    private val adapter: ArtistListAdapter by lazy {
        ArtistListAdapter {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToTopAlbumFragment(
                    it
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.artistsRecyclerView?.adapter = adapter.withLoadStateFooter(MainLoadStateAdapter())
        adapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                binding?.artistsRecyclerView?.hide()
                binding?.emptyTextView?.show()
            } else {
                binding?.artistsRecyclerView?.show()
                binding?.emptyTextView?.hide()
            }
        }
        binding?.searchButton?.setOnClickListener {
            lifecycleScope.launch {
                viewModel.searchArtistPaged(binding?.searchEditText?.text.toString())
                    .collect { pagingData ->
                        adapter.submitData(pagingData)
                    }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

