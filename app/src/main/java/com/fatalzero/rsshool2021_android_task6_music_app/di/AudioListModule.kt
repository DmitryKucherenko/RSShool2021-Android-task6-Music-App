package com.fatalzero.rsshool2021_android_task6_music_app.di

import android.content.Context
import com.fatalzero.rsshool2021_android_task6_music_app.repository.AudioList
import dagger.Module
import javax.inject.Singleton


@Module
interface AudioListModule {
    @Singleton
    fun getAudioList(context: Context): AudioList

}
