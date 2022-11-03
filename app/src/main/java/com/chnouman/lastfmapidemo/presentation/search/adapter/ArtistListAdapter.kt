package com.chnouman.lastfmapidemo.presentation.search.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chnouman.lastfmapidemo.data.local.entities.Artist
import com.chnouman.lastfmapidemo.databinding.ItemArtistBinding

class ArtistListAdapter(
    private var itemClick: (Artist) -> Unit
) : PagingDataAdapter<Artist, ArtistListAdapter.ArtistViewHolder>(
    CategoryMainDiffUtils()
) {

    inner class ArtistViewHolder(private val binding: ItemArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(albumsDto: Artist) {
            this.apply {
                binding.artistTextView.text = albumsDto.name
                binding.artistNameTextView.text = albumsDto.name
                itemView.apply {
                    setOnClickListener {
                        itemClick.invoke(albumsDto)
                    }
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
            Log.d("TAG", Thread.currentThread().name)
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            Log.d("TAG", Thread.currentThread().name)
            return oldItem == newItem
        }

    }
}
