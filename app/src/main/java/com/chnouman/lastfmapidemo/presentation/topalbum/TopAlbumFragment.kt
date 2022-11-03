package com.chnouman.lastfmapidemo.presentation.topalbum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chnouman.lastfmapidemo.core.util.extensions.hide
import com.chnouman.lastfmapidemo.core.util.extensions.show
import com.chnouman.lastfmapidemo.databinding.FragmentMainBinding
import com.chnouman.lastfmapidemo.presentation.topalbum.adapter.TopAlbumListAdapter
import com.chnouman.lastfmapidemo.presentation.topalbum.viewmodel.TopAlbumViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopAlbumFragment : Fragment() {
    private val viewModel: TopAlbumViewModel by viewModels()
    private var binding: FragmentMainBinding? = null
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.albumsRecyclerView?.adapter = adapter
        lifecycleScope.launch {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is TopAlbumViewModel.UIEvent.
                    Loading -> {
                        binding?.progressIndicator?.show()
                    }
                    is TopAlbumViewModel.UIEvent.Success -> {
                        binding?.progressIndicator?.hide()
                        event.artists?.let { adapter.submitList(it) }
                    }
                    is TopAlbumViewModel.UIEvent.ItemSaved -> {
                        updateItem(event.position)
                    }
                    is TopAlbumViewModel.UIEvent.ItemDeleted -> {
                        updateItem(event.position)
                    }
                    is TopAlbumViewModel.UIEvent.Error -> {
                        binding?.progressIndicator?.hide()
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
        viewModel.getTopAlbums(args.artist.name)
    }

    private fun updateItem(position: Int) {
        binding?.progressIndicator?.hide()
        adapter.notifyItemChanged(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

