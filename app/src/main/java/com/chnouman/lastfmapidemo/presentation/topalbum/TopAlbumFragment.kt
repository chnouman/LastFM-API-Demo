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
import com.chnouman.lastfmapidemo.data.local.entities.Album
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
        }, {
            //delete action
            Toast.makeText(requireContext(), "item deleted", Toast.LENGTH_SHORT).show()
            viewModel.deleteAlbum(it)
        }, {
            //save action
            Toast.makeText(requireContext(), "item Saved", Toast.LENGTH_SHORT).show()
            viewModel.saveAlbum(adapter.currentList.toMutableList(),it, args.artist)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is TopAlbumViewModel.UIEvent.
                    Loading -> {
                        binding?.progressIndicator?.show()
                    }
                    is TopAlbumViewModel.UIEvent.Success -> {
                        binding?.progressIndicator?.hide()
                        event.artists?.let { setupAdapter(it) }
                    }
                    is TopAlbumViewModel.UIEvent.ItemSaved -> {
                        binding?.progressIndicator?.hide()
                        updateAdapter(event.artists)
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
        binding?.albumsRecyclerView?.adapter = adapter
        viewModel.getTopAlbums(args.artist.name)
    }

    private fun setupAdapter(artists: MutableList<Album>) {
        adapter.submitList(artists)
    }

    private fun updateAdapter(albumsDto: Album) {
        adapter.submitList(viewModel.currentItems.toList().toMutableList().let {
            it.forEach {
                if (it.name == albumsDto.name){
                    it.copy(isDownloaded = true)
                }
            }
/*            it[index] = it[index].copy(property = newvalue) // To update a property on an item
            it.add(newItem) // To add a new item
            it.removeAt[index] // To remove an item*/
            // and so on....
            it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

