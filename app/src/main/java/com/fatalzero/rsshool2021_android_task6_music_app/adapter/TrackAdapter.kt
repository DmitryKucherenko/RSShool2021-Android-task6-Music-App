package com.fatalzero.rsshool2021_android_task6_music_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.fatalzero.rsshool2021_android_task6_music_app.databinding.TrackItemBinding

import com.fatalzero.rsshool2021_android_task6_music_app.model.Track


class TrackAdapter(
    private val itemClickListener: ItemClickListener?
):  ListAdapter<Track, TrackHolder>(DiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = TrackItemBinding.inflate(layoutInflater, parent, false)
    return TrackHolder(itemClickListener, binding,parent.context)
}

override fun onBindViewHolder(holder: TrackHolder, position: Int) {
    val track = currentList[position]
    holder.bind(track,position)
}

override fun getItemCount()=currentList.size

}

