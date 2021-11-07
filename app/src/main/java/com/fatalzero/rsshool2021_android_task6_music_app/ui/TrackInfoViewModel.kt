package com.fatalzero.rsshool2021_android_task6_music_app.ui

import android.app.Application
import android.content.Context
import android.content.Intent
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fatalzero.rsshool2021_android_task6_music_app.service.AudioService
import com.fatalzero.rsshool2021_android_task6_music_app.service.MusicServiceConnection
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrackInfoViewModel @Inject constructor(var application: Application) : ViewModel() {
    private var callback: MediaControllerCompat.Callback? = null
    var serviceConnection: MusicServiceConnection? = null
    var id: Int = -1
    var mediaLiveData = MutableLiveData<MediaDescriptionCompat>()
    var updateProgressTask: Job? = null
var trackProgress = MutableLiveData<Long>(0)

    init {
        callback = object : MediaControllerCompat.Callback() {


            override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {

                state?.let {
                    when (it.state) {
                        PlaybackStateCompat.STATE_PLAYING -> callbackPlay()
                        PlaybackStateCompat.STATE_PAUSED -> callbackPause()
                        PlaybackStateCompat.STATE_STOPPED -> callbackStop()
                        PlaybackStateCompat.STATE_SKIPPING_TO_NEXT -> callbackNext()
                        PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS -> callbackPrev()
                        else -> callbackUnknown()
                    }
                }
            }
        }

        serviceConnection = MusicServiceConnection(application, callback)
        val playerIntent = Intent(application.applicationContext, AudioService::class.java)
        application.applicationContext.bindService(
            playerIntent,
            serviceConnection!!,
            Context.BIND_AUTO_CREATE
        )
    }


    fun nextTrack() {
        serviceConnection?.mediaController?.transportControls?.skipToNext()
        updateSeekBar()
    }

    fun playFromPosition(position: Int) {
        serviceConnection?.mediaController?.transportControls?.playFromMediaId(
            position.toString(),
            null
        )
        updateSeekBar()
    }

    fun updateSeekBar(){
        updateProgressTask = viewModelScope.launch{
            while(true) {
                delay(1000)
                trackProgress.postValue(  serviceConnection?.mediaController?.playbackState?.position)
            }
        }
    }

    fun stopUdateSeekBar(){
        updateProgressTask?.cancel()
        updateProgressTask?.invokeOnCompletion {     trackProgress.postValue(0) }
    }


    fun pauseUpdateSeekBar(){
        updateProgressTask?.cancel()

    }

    fun pausePlaying() {
        serviceConnection?.mediaController?.transportControls?.pause()
        pauseUpdateSeekBar()


    }

    fun stopPlaying() {
        serviceConnection?.mediaController?.transportControls?.stop()
        stopUdateSeekBar()
    }

    fun playTrack() {
        serviceConnection?.mediaController?.transportControls?.play()
        updateSeekBar()
      }

    fun previousTrack() {
        serviceConnection?.mediaController?.transportControls?.skipToPrevious()
        updateSeekBar()
    }

    fun callbackNext() {
        mediaLiveData.value = serviceConnection?.mediaController?.metadata?.description ?: return

    }

    fun callbackPause() {
        mediaLiveData.value = serviceConnection?.mediaController?.metadata?.description ?: return

    }

    fun callbackStop() {
        mediaLiveData.value = serviceConnection?.mediaController?.metadata?.description ?: return
    }

    fun callbackPlay() {
        mediaLiveData.value = serviceConnection?.mediaController?.metadata?.description ?: return
    }

    fun callbackPrev() {
        mediaLiveData.value = serviceConnection?.mediaController?.metadata?.description ?: return
    }

    fun callbackUnknown() {
    }

    class TrackInfoViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TrackInfoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TrackInfoViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
