package com.fatalzero.rsshool2021_android_task6_music_app.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.fatalzero.rsshool2021_android_task6_music_app.R
import com.fatalzero.rsshool2021_android_task6_music_app.databinding.TrackItemBinding
import com.fatalzero.rsshool2021_android_task6_music_app.model.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TrackHolder(
    private val itemClickListener: ItemClickListener?,
    binding: TrackItemBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {
    private var track: Track? = null
    private val artist: TextView = binding.ArtrtistTextView
    private val album: TextView = binding.AlbumTextView
    private val title: TextView = binding.TitleTextView
    private val bitmapView: ImageView = binding.BitmapView


    fun bind(track: Track, position: Int) {
        this.track = track
        track.id = position
        artist.text = track.artist
        album.text = track.album
        title.text = track.title
        val id = requireNotNull(track.id)

        try {
            Glide.with(context)
                .load(track.bitmapUri)
                .placeholder(R.drawable.ic_music)
                .optionalTransform(CenterCrop())
                .into(bitmapView)

            itemView.setOnClickListener {
                itemClickListener?.onItemClick(id)
            }
        } catch (e: Exception) {
        }


    }

}
