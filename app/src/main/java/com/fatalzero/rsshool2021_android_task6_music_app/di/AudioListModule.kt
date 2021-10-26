package com.fatalzero.rsshool2021_android_task6_music_app.di

import android.content.Context
import com.fatalzero.rsshool2021_android_task6_music_app.AudioList
import com.fatalzero.rsshool2021_android_task6_music_app.model.Track
import dagger.Module
import dagger.Provides


@Module
class AudioListModule {
    @Provides
    fun provideTracks(context: Context): AudioList {
        return AudioList(context)
    }
}
