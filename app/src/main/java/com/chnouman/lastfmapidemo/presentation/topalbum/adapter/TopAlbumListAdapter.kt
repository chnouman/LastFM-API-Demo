package com.chnouman.lastfmapidemo.presentation.topalbum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chnouman.lastfmapidemo.core.util.extensions.hide
import com.chnouman.lastfmapidemo.core.util.extensions.show
import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.databinding.ItemAlbumBinding

class TopAlbumListAdapter(
    private var itemClick: (Album) -> Unit,
    private var deleteItemClick: (Album, Int) -> Unit,
    private var saveItemClick: (Album, Int) -> Unit,
) : androidx.recyclerview.widget.ListAdapter<Album, TopAlbumListAdapter.AlbumViewHolder>(
    AlbumDiffUtils()
) {

    inner class AlbumViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(albumsDto: Album, position: Int) {
            binding.apply {
                artistTextView.text = albumsDto.name
                nameTextView.text = albumsDto.name
                if (albumsDto.isDownloaded) {
                    deleteButton.show()
                    saveButton.hide()
                } else {
                    deleteButton.hide()
                    saveButton.show()
                }
                saveButton.setOnClickListener { saveItemClick.invoke(albumsDto, position) }
                deleteButton.setOnClickListener { deleteItemClick.invoke(albumsDto, position) }
                itemView.setOnClickListener {
                    itemClick.invoke(albumsDto)
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

