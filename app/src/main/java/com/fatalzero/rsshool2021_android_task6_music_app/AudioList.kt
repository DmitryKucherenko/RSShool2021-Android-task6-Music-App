package com.fatalzero.rsshool2021_android_task6_music_app

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.fatalzero.rsshool2021_android_task6_music_app.model.Track
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class AudioList(
    var context: Context,
    var jsonFile: String = context.resources.openRawResource(R.raw.playlist).bufferedReader()
        .use { it.readText() }
) {
    private var _listTrack: List<Track>? = null
    private val listTrack: List<Track> get() = requireNotNull(_listTrack)
    val bitmaps = HashMap<String, Bitmap>(5)
    init {
        val moshi = Moshi.Builder().build()
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            Track::class.java
        )

        val trackAdapter: JsonAdapter<List<Track>> = moshi.adapter(listMyData)
        _listTrack = trackAdapter.fromJson(jsonFile)

        GlobalScope.launch(Dispatchers.Default) {
            try {
                _listTrack?.forEach {
                    val bitmap = Glide.with(context).asBitmap().load(it.bitmapUri).into(200, 200).get()
                    bitmaps[it.bitmapUri] = bitmap
                }
            }
            catch (e: Exception) {}
        }

    }


    val maxTrackIndex = listTrack.size - 1
    var currentTrackIndex = 0
    val countTracks = listTrack.size

    var currentTrack = listTrack[currentTrackIndex]
        get() = listTrack[currentTrackIndex]
        private set

    fun next(): Track {
        if (currentTrackIndex == maxTrackIndex) {
            currentTrackIndex = 0
        } else {
            currentTrackIndex++
        }
        return currentTrack
    }

    fun previous(): Track {
        if (currentTrackIndex == 0) {
            currentTrackIndex = maxTrackIndex
        } else {
            currentTrackIndex--
        }
        return currentTrack
    }

    fun getTrackByIndex(index: Int) = listTrack[index]
    fun getTrackCatalog() = listTrack
}
