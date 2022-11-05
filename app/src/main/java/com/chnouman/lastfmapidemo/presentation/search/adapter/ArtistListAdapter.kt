package com.chnouman.lastfmapidemo.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chnouman.lastfmapidemo.R
import com.chnouman.lastfmapidemo.data.local.entities.Artist
import com.chnouman.lastfmapidemo.databinding.ItemArtistBinding

class ArtistListAdapter(
    private var itemClick: (Artist) -> Unit
) : PagingDataAdapter<Artist, ArtistListAdapter.ArtistViewHolder>(
    CategoryMainDiffUtils()
) {

    inner class ArtistViewHolder(private val binding: ItemArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist) {
            this.apply {
                binding.apply {
                    artistNameTextView.text = artist.name
                    listenersTextView.text = artist.listeners
                    itemView.setOnClickListener {
                        itemClick.invoke(artist)
                    }
                    Glide
                        .with(artistImageView.context)
                        .load(artist.image)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(artistImageView)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = ItemArtistBinding.inflate(
            LayoutInflater.from
                (parent.context),
            parent,
            false
        )
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    class CategoryMainDiffUtils : DiffUtil.ItemCallback<Artist>() {
        override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem.name == newItem.name
        }

    }
}
