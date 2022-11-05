package com.chnouman.lastfmapidemo.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chnouman.lastfmapidemo.data.local.entities.Track
import com.chnouman.lastfmapidemo.databinding.ItemTrackBinding
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class TrackListAdapter(
    private var urlItemClick: (Track) -> Unit
) : androidx.recyclerview.widget.ListAdapter<Track, TrackListAdapter.TrackViewHolder>(
    AlbumDiffUtils()
) {

    inner class TrackViewHolder(private val binding: ItemTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track, position: Int) {
            binding.apply {
                trackNumberTextView.text = position.plus(1).toString()
                trackNameTextView.text = track.name
                durationTextView.text =
                    track.duration.toDuration(DurationUnit.SECONDS).toString()
                urlImageView.setOnClickListener { urlItemClick.invoke(track) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = ItemTrackBinding.inflate(
            LayoutInflater.from
                (parent.context),
            parent,
            false
        )
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    class AlbumDiffUtils : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }
    }

    override fun submitList(list: List<Track>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}
