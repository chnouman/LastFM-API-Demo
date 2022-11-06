package com.chnouman.lastfmapidemo.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chnouman.lastfmapidemo.R
import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.databinding.ItemAlbumBinding

class AlbumListAdapter(
    private var itemClick: (Album) -> Unit,
    private var action: (Album) -> Unit
) : PagingDataAdapter<Album, AlbumListAdapter.AlbumViewHolder>(
    AlbumDiffUtils()
) {

    inner class AlbumViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            this.apply {
                binding.apply {
                    artistTextView.text = album.artistName
                    albumNameTextView.text = album.name
                    actionImageView.setOnClickListener { action.invoke(album) }
                    itemView.setOnClickListener {
                        itemClick.invoke(album)
                    }
                    Glide
                        .with(albumImageView.context)
                        .load(album.image)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(albumImageView)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemAlbumBinding.inflate(
            LayoutInflater.from
                (parent.context),
            parent,
            false
        )
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class AlbumDiffUtils : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }
}
