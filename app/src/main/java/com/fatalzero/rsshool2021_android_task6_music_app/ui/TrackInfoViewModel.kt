package com.fatalzero.rsshool2021_android_task6_music_app.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.fatalzero.rsshool2021_android_task6_music_app.AudioService

class TrackInfoViewModel :ViewModel() {

    var bitmapView: ImageView? = null
    var titleTextView: TextView? = null
     var mediaServiceBinder: AudioService.MediaServiceBinder? = null
     var activity: FragmentActivity? = null
     var mediaController: MediaControllerCompat? = null
     var callback: MediaControllerCompat.Callback? = null
     var serviceConnection: ServiceConnection? = null
     var id: Int? = 0
fun init(){
    callback = object : MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            state?.let {
                val playing = it.state == PlaybackStateCompat.STATE_PLAYING
               // playButton?.isEnabled = !playing
             //   pauseButton?.isEnabled = playing
             //   stopButton?.isEnabled = playing

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
    if(mediaServiceBinder==null) {
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
                    // mediaController?.transportControls?.play()
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


}



     fun nextTrack() {
        mediaController?.transportControls?.skipToNext()
    }

    fun playFromPosition(position: Int){

        mediaController?.transportControls?.playFromMediaId(position.toString(),null)
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
        val description = mediaController?.metadata?.description ?: return

       bitmapView?.setImageBitmap(description.iconBitmap)
        titleTextView?.text = description.title
    }

     fun callbackPause() {
         val description = mediaController?.metadata?.description ?: return

         bitmapView?.setImageBitmap(description.iconBitmap)
         titleTextView?.text = description.title

    }

     fun callbackStop() {
         val description = mediaController?.metadata?.description ?: return
         bitmapView?.setImageBitmap(description.iconBitmap)
         titleTextView?.text = description.title
    }

     fun callbackPlay() {
         val description = mediaController?.metadata?.description ?: return
         bitmapView?.setImageBitmap(description.iconBitmap)
        titleTextView?.text = description.title

    }

     fun callbackPrev() {
        val description = mediaController?.metadata?.description ?: return

        bitmapView?.setImageBitmap(description.iconBitmap)
        titleTextView?.text = description.title

    }

     fun callbackUnknown() {

    }

}
