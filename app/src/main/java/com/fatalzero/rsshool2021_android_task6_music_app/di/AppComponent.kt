package com.fatalzero.rsshool2021_android_task6_music_app.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.media.MediaBrowserServiceCompat
import com.fatalzero.rsshool2021_android_task6_music_app.AudioService
import com.fatalzero.rsshool2021_android_task6_music_app.ui.AudioListFragment
import com.fatalzero.rsshool2021_android_task6_music_app.ui.MainActivity
import com.fatalzero.rsshool2021_android_task6_music_app.ui.TrackInfoFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module


@Component(modules = [AudioListModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }


    fun inject(fragment: AudioListFragment)
    fun inject(service: AudioService)


}

