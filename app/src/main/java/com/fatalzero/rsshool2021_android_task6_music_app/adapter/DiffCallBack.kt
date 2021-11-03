package com.fatalzero.rsshool2021_android_task6_music_app.adapter

import androidx.recyclerview.widget.DiffUtil
import com.fatalzero.rsshool2021_android_task6_music_app.model.Track

object DiffCallBack : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }

}
