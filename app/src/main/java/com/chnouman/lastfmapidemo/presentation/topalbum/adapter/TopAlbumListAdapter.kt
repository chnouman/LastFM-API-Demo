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
    private var deleteItemClick: (Album) -> Unit,
    private var saveItemClick: (Album) -> Unit,
) : androidx.recyclerview.widget.ListAdapter<Album, TopAlbumListAdapter.AlbumViewHolder>(
    AlbumDiffUtils()
) {

    inner class AlbumViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(albumsDto: Album) {
            this.apply {
                binding.artistTextView.text = albumsDto.name
                binding.nameTextView.text = albumsDto.name
                if (albumsDto.isDownloaded) {
                    binding.deleteButton.show()
                    binding.saveButton.hide()
                } else {
                    binding.deleteButton.hide()
                    binding.saveButton.show()
                }
                binding.saveButton.setOnClickListener { saveItemClick.invoke(albumsDto) }
                binding.deleteButton.setOnClickListener { deleteItemClick.invoke(albumsDto) }
                itemView.apply {
                    setOnClickListener {
                        itemClick.invoke(albumsDto)
                    }
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
        holder.bind(getItem(position))
    }


    class AlbumDiffUtils : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.isDownloaded == newItem.isDownloaded
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.isDownloaded == newItem.isDownloaded
        }

    }
}

