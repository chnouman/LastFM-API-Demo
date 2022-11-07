package com.chnouman.lastfmapidemo.presentation.topalbum

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chnouman.lastfmapidemo.databinding.FragmentTopalbumsBinding
import com.chnouman.lastfmapidemo.presentation.base.BaseFragment
import com.chnouman.lastfmapidemo.presentation.topalbum.adapter.TopAlbumListAdapter
import com.chnouman.lastfmapidemo.presentation.topalbum.viewmodel.TopAlbumViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Populate the list of Top Albums based on user selected Artist
 * User can save the album for later use
 * User can delete the album if it's already stored locally
 * */
@AndroidEntryPoint
class TopAlbumFragment : BaseFragment<FragmentTopalbumsBinding>(FragmentTopalbumsBinding::inflate) {
    private val viewModel: TopAlbumViewModel by viewModels()
    private val args by navArgs<TopAlbumFragmentArgs>()
    private val adapter: TopAlbumListAdapter by lazy {
        TopAlbumListAdapter({
            findNavController().navigate(
                TopAlbumFragmentDirections.actionTopAlbumFragmentToDetailFragment(
                    it
                )
            )
        }, { position ->
            adapter.currentList[position]?.apply {
                if (isDownloaded) {
                    viewModel.deleteAlbum(this, position)
                } else {
                    viewModel.saveAlbum(position, this, args.artist)
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.apply {
            albumsRecyclerView.adapter = adapter
            lifecycleScope.launch {
                viewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        is TopAlbumViewModel.UIEvent
                        .Loading -> {
                        }
                        is TopAlbumViewModel.UIEvent.Success -> {
                            event.artists?.let { adapter.submitList(it) }
                        }
                        is TopAlbumViewModel.UIEvent.ItemSaved -> {

                            adapter.currentList.toMutableList().apply {
                                this[event.position] =
                                    this[event.position].copy(isDownloaded = true)
                                adapter.submitList(this)
                            }
                        }
                        is TopAlbumViewModel.UIEvent.ItemDeleted -> {
                            adapter.currentList.toMutableList().apply {
                                this[event.position] =
                                    this[event.position].copy(isDownloaded = false)
                                adapter.submitList(this)
                            }
                        }
                        is TopAlbumViewModel.UIEvent.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "error: ${event.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }
        }
        viewModel.getTopAlbums(args.artist.name)
    }
}
