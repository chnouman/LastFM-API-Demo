package com.chnouman.lastfmapidemo.presentation.topalbum

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chnouman.lastfmapidemo.core.util.extensions.hide
import com.chnouman.lastfmapidemo.core.util.extensions.show
import com.chnouman.lastfmapidemo.databinding.FragmentTopalbumsBinding
import com.chnouman.lastfmapidemo.presentation.base.BaseFragment
import com.chnouman.lastfmapidemo.presentation.topalbum.adapter.TopAlbumListAdapter
import com.chnouman.lastfmapidemo.presentation.topalbum.viewmodel.TopAlbumViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        }, { album, position ->
            //delete action
            viewModel.deleteAlbum(album, position)
        }, { album, position ->
            //save action
            viewModel.saveAlbum(position, album, args.artist)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.apply {
            albumsRecyclerView.adapter = adapter
            lifecycleScope.launch {
                viewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        is TopAlbumViewModel.UIEvent.
                        Loading -> {
                            progressIndicator.show()
                        }
                        is TopAlbumViewModel.UIEvent.Success -> {
                            progressIndicator.hide()
                            event.artists?.let { adapter.submitList(it) }
                        }
                        is TopAlbumViewModel.UIEvent.ItemSaved -> {
                            updateItem(event.position)
                        }
                        is TopAlbumViewModel.UIEvent.ItemDeleted -> {
                            updateItem(event.position)
                        }
                        is TopAlbumViewModel.UIEvent.Error -> {
                            progressIndicator.hide()
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

    private fun updateItem(position: Int) {
        viewDataBinding?.progressIndicator?.hide()
        adapter.notifyItemChanged(position)
    }
}

