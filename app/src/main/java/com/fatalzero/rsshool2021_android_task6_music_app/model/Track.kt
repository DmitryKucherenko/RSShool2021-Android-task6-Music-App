package com.fatalzero.rsshool2021_android_task6_music_app.model

import com.squareup.moshi.JsonClass


typealias trackList = List<Track>


@JsonClass(generateAdapter = true)
data class Track(
    var id: Int?,
    val title: String?,
    val artist: String?,
    val album: String?,
    val bitmapUri: String,
    val trackUri: String?,
    val duration: Long?
)
