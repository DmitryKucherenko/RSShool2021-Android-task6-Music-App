package com.fatalzero.rsshool2021_android_task6_music_app.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fatalzero.rsshool2021_android_task6_music_app.service.AudioService

class TrackInfoViewModel : ViewModel() {


    var mediaServiceBinder: AudioService.MediaServiceBinder? = null
    var activity: FragmentActivity? = null
    var mediaController: MediaControllerCompat? = null
    var callback: MediaControllerCompat.Callback? = null
    var serviceConnection: ServiceConnection? = null


    var id: Int? = 0
    var mediaLiveData = MutableLiveData<MediaDescriptionCompat>()

    fun init() {
        callback = object : MediaControllerCompat.Callback() {
            override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
                state?.let {
                    val playing = it.state == PlaybackStateCompat.STATE_PLAYING

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

            serviceConnection = object : ServiceConnection {

                override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
                    mediaServiceBinder =
                        service as AudioService.MediaServiceBinder
                    try {
                        mediaController = MediaControllerCompat(
                            activity,
                            mediaServiceBinder?.getMediaSessionToken()!!
                        )
                        mediaController?.registerCallback(callback as MediaControllerCompat.Callback)
                        callback?.onPlaybackStateChanged(mediaController?.playbackState)
                        id?.let { playFromPosition(it) }

                    } catch (e: RemoteException) {
                        mediaController = null
                    }
                }

                override fun onServiceDisconnected(className: ComponentName?) {
                    mediaServiceBinder = null
                    if (mediaController != null) {
                        mediaController?.unregisterCallback(callback as MediaControllerCompat.Callback)
                        mediaController = null
                    }
                }
            }
            val playerIntent = Intent(activity, AudioService::class.java)
            activity?.bindService(
                playerIntent,
                serviceConnection!!,
                Context.BIND_AUTO_CREATE
            )

    }


    fun nextTrack() {
        mediaController?.transportControls?.skipToNext()
    }

    fun playFromPosition(position: Int) {
        mediaController?.transportControls?.playFromMediaId(position.toString(), null)
    }

    fun pausePlaying() {
        mediaController?.transportControls?.pause()
    }

    fun stopPlaying() {
        mediaController?.transportControls?.stop()
    }

    fun playTrack() {
        mediaController?.transportControls?.play()

    }

    fun previousTrack() {
        mediaController?.transportControls?.skipToPrevious()
    }

    fun callbackNext() {
        mediaLiveData.value = mediaController?.metadata?.description ?: return
    }

    fun callbackPause() {
        mediaLiveData.value = mediaController?.metadata?.description ?: return

    }

    fun callbackStop() {
        mediaLiveData.value = mediaController?.metadata?.description ?: return
    }

    fun callbackPlay() {
        mediaLiveData.value = mediaController?.metadata?.description ?: return
    }

    fun callbackPrev() {
        mediaLiveData.value = mediaController?.metadata?.description ?: return
    }

    fun callbackUnknown() {
    }

}
