package com.fatalzero.rsshool2021_android_task6_music_app.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fatalzero.rsshool2021_android_task6_music_app.MyApplication
import com.fatalzero.rsshool2021_android_task6_music_app.model.Track
import com.fatalzero.rsshool2021_android_task6_music_app.repository.AudioList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioListViewModel @Inject constructor(application: Application): ViewModel(){

    @Inject
    lateinit var audioList: AudioList
    var audioListLiveData = MutableLiveData<List<Track>>()

    init{
        (application as MyApplication).appComponent.inject(this)
        audioListLiveData.value=audioList.getTrackCatalog()
    }

    class AudioListViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AudioListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AudioListViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
