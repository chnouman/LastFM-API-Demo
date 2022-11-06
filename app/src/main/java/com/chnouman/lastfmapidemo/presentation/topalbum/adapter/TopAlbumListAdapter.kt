package com.chnouman.lastfmapidemo.presentation.topalbum.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chnouman.lastfmapidemo.R
import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.databinding.ItemAlbumBinding
import java.util.Collections

private const val ARG_CHANGED = "arg.change"

class TopAlbumListAdapter(
    private var itemClick: (Album) -> Unit,
    private var action: (Int) -> Unit
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
                    action.invoke(position)
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

        fun update(bundle: Bundle) {
            if (bundle.containsKey(ARG_CHANGED)) {
                val checked = bundle.getBoolean(ARG_CHANGED)
                binding.actionImageView.setImageResource(
                    if (checked) R.drawable.ic_delete else R.drawable.ic_download
                )
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

    override fun onBindViewHolder(holder: AlbumViewHolder, pos: Int) {
        onBindViewHolder(holder, pos, Collections.emptyList())
    }

    override fun onBindViewHolder(viewHolder: AlbumViewHolder, pos: Int, payload: List<Any>) {
        val item = getItem(pos)

        if (payload.isEmpty() || payload[0] !is Bundle) {
            viewHolder.bind(item, pos)
        } else {
            val bundle = payload[0] as Bundle
            viewHolder.update(bundle)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class AlbumDiffUtils : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: Album, newItem: Album): Any? {
            if (oldItem.name == newItem.name) {
                return if (oldItem.isDownloaded == newItem.isDownloaded) {
                    super.getChangePayload(oldItem, newItem)
                } else {
                    val diff = Bundle()
                    diff.putBoolean(ARG_CHANGED, newItem.isDownloaded)
                    diff
                }
            }
            return super.getChangePayload(oldItem, newItem)
        }
    }
}
