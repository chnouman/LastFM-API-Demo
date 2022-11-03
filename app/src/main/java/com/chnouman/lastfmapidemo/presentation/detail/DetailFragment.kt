package com.chnouman.lastfmapidemo.presentation.detail

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.chnouman.lastfmapidemo.presentation.detail.viewmodel.DetailViewModel
import com.chnouman.lastfmapidemo.R
import com.chnouman.lastfmapidemo.core.util.extensions.hide
import com.chnouman.lastfmapidemo.core.util.extensions.show
import com.chnouman.lastfmapidemo.data.local.entities.Track
import com.chnouman.lastfmapidemo.databinding.FragmentDetailsBinding
import com.chnouman.lastfmapidemo.presentation.detail.adapter.TrackListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var binding: FragmentDetailsBinding? = null
    private val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()
    private val adapter: TrackListAdapter by lazy {
        TrackListAdapter {
            Intent(ACTION_VIEW).apply {
                data = Uri.parse(it.url)
                startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is DetailViewModel.UIEvent.
                    Loading -> {
                        binding?.progressIndicator?.show()
                    }
                    is DetailViewModel.UIEvent.Success -> {
                        binding?.progressIndicator?.hide()
                        event.artists?.let { setupAdapter(it) }
                    }
                    is DetailViewModel.UIEvent.Error -> {
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
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            albumTextView.text = args.album.name
            artistNameTextView.text = args.album.artistName
            playCountTextView.text = args.album.playCount.toString()
            Glide
                .with(requireActivity())
                .load(args.album.image)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(albumImageView)
            tracksRecyclerView.adapter = adapter
        }
        viewModel.getTracks(args.album.name, args.album.artistName)
    }

    private fun setupAdapter(artists: MutableList<Track>) {
        adapter.submitList(artists)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

