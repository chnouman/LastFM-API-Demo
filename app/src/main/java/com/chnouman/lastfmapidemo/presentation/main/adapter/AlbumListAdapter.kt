package com.chnouman.lastfmapidemo.presentation.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.databinding.ItemAlbumBinding

class AlbumListAdapter(
    private var itemClick: (Album) -> Unit,
    private var deleteItemClick: (Album) -> Unit,
    private var saveItemClick: (Album) -> Unit,
) : PagingDataAdapter<Album, AlbumListAdapter.AlbumViewHolder>(
    AlbumDiffUtils()
) {

    inner class AlbumViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(Album: Album) {
            this.apply {
                binding.apply {
                    artistTextView.text = Album.name
                    nameTextView.text = Album.name
                    saveButton.setOnClickListener { saveItemClick.invoke(Album) }
                    deleteButton.setOnClickListener { deleteItemClick.invoke(Album) }
                    itemView.setOnClickListener {
                        itemClick.invoke(Album)
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
        getItem(position)?.let { holder.bind(it) }
    }


    class AlbumDiffUtils : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            Log.d("TAG", Thread.currentThread().name)
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            Log.d("TAG", Thread.currentThread().name)
            return oldItem == newItem
        }

    }
}
