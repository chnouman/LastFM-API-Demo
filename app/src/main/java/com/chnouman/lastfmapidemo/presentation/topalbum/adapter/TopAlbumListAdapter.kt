package com.chnouman.lastfmapidemo.presentation.topalbum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chnouman.lastfmapidemo.R
import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.databinding.ItemAlbumBinding

class TopAlbumListAdapter(
    private var itemClick: (Album) -> Unit,
    private var deleteItemClick: (Album, Int) -> Unit,
    private var saveItemClick: (Album, Int) -> Unit
) : androidx.recyclerview.widget.ListAdapter<Album, TopAlbumListAdapter.AlbumViewHolder>(
    AlbumDiffUtils()
) {

    inner class AlbumViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album, position: Int) {
            binding.apply {
                artistTextView.text = album.artistName
                albumNameTextView.text = album.name
                actionImageView.setImageResource(
                    if (album.isDownloaded) R.drawable.ic_delete else R.drawable.ic_download
                )
                actionImageView.setOnClickListener {
                    if (album.isDownloaded) {
                        deleteItemClick.invoke(album, position)
                    } else {
                        saveItemClick.invoke(album, position)
                    }
                }
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
        holder.bind(getItem(position), position)
    }

    class AlbumDiffUtils : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.name == newItem.name && oldItem.isDownloaded == newItem.isDownloaded
        }
    }
}
