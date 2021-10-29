package com.fatalzero.rsshool2021_android_task6_music_app.di

import android.content.Context
import com.fatalzero.rsshool2021_android_task6_music_app.service.AudioService
import com.fatalzero.rsshool2021_android_task6_music_app.ui.AudioListFragment
import com.fatalzero.rsshool2021_android_task6_music_app.ui.AudioListViewModel
import dagger.BindsInstance
import dagger.Component


@Component(modules = [AudioListModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }


    fun inject(fragment: AudioListFragment)
    fun inject(service: AudioService)
    fun inject(audioListViewModel: AudioListViewModel)


}

