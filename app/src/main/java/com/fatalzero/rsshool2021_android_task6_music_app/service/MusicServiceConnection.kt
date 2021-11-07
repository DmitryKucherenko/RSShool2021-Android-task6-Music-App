package com.fatalzero.rsshool2021_android_task6_music_app.service

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.support.v4.media.session.MediaControllerCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class MusicServiceConnection (var application: Application, var callback: MediaControllerCompat.Callback?): ServiceConnection  {

    var mediaServiceBinder: AudioService.MediaServiceBinder? = null
    var mediaController: MediaControllerCompat? = null
    var connection: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var progressPosition: MutableLiveData<Long> = MutableLiveData<Long>(0)


        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            mediaServiceBinder =
                service as AudioService.MediaServiceBinder
            try {

                mediaController = MediaControllerCompat(
                    application.applicationContext,
                    mediaServiceBinder?.getMediaSessionToken()!!

                )
                mediaController?.registerCallback(callback as MediaControllerCompat.Callback)
                callback?.onPlaybackStateChanged(mediaController?.playbackState)


                connection.postValue(true)


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
            connection.postValue(false)
        }


}
