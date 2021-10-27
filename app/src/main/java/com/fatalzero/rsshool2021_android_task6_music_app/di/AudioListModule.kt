package com.fatalzero.rsshool2021_android_task6_music_app.di

import android.content.Context
import com.fatalzero.rsshool2021_android_task6_music_app.AudioList
import com.fatalzero.rsshool2021_android_task6_music_app.model.Track
import com.fatalzero.rsshool2021_android_task6_music_app.ui.AudioListFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
interface AudioListModule {
    @Singleton
    fun getAudioList(context: Context): AudioList

}
