package com.fatalzero.rsshool2021_android_task6_music_app

import android.app.Application
import com.fatalzero.rsshool2021_android_task6_music_app.di.AppComponent
import com.fatalzero.rsshool2021_android_task6_music_app.di.DaggerAppComponent

class MyApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}

