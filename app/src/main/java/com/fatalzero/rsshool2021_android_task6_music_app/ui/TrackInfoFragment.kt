package com.fatalzero.rsshool2021_android_task6_music_app.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.fatalzero.rsshool2021_android_task6_music_app.AudioService
import com.fatalzero.rsshool2021_android_task6_music_app.R
import com.fatalzero.rsshool2021_android_task6_music_app.databinding.FragmentTrackInfoBinding

private const val ID = "id"
class TrackInfoFragment : Fragment() {
   var id:Int? = null
    private var _binding: FragmentTrackInfoBinding? = null
    private val binding get() = _binding!!
    var callback: MediaControllerCompat.Callback? = null
    var playButton: ImageButton? = null
    var pauseButton: ImageButton? = null
    var stopButton: ImageButton? = null
    var prevButton: ImageButton? = null
    var nextButton: ImageButton? = null
    var bitmapView: ImageView? = null
    var titleTextView: TextView? = null
    var artistTextView: TextView? = null
    var albumTextView: TextView? = null
    private val trackInfoViewModel: TrackInfoViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trackInfoViewModel.activity = activity
        trackInfoViewModel.init()
        trackInfoViewModel.id = arguments?.getInt("id")?:0
if(trackInfoViewModel.mediaServiceBinder!=null) {
   // stopPlaying()
    trackInfoViewModel.playFromPosition(trackInfoViewModel.id!!)
    println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
}




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playButton= binding.playButton
        pauseButton=binding.pauseButton
        stopButton=binding.stopButton
        prevButton=binding.previousButton
        nextButton=binding.nextButton
        bitmapView=binding.BitmapView
        albumTextView=binding.ArtrtistTextView
        titleTextView=binding.TitleTextView
        artistTextView=binding.AlbumTextView



        prevButton?.setOnClickListener {
            trackInfoViewModel.previousTrack()
            val description = trackInfoViewModel.mediaController?.metadata?.description
            bitmapView?.setImageBitmap(description?.iconBitmap)
            titleTextView?.text = description?.title
            buttonChangeColor(TrackInfoFragment.BUTTON_NEXT)

        }
        playButton?.setOnClickListener { trackInfoViewModel.playTrack() }
        stopButton?.setOnClickListener { trackInfoViewModel.stopPlaying() }
        pauseButton?.setOnClickListener { trackInfoViewModel.pausePlaying() }
        nextButton?.setOnClickListener { trackInfoViewModel.nextTrack()
            val description = trackInfoViewModel.mediaController?.metadata?.description
             bitmapView?.setImageBitmap(description?.iconBitmap)
             titleTextView?.text = description?.title
             buttonChangeColor(TrackInfoFragment.BUTTON_NEXT)
        }
        val description = trackInfoViewModel.mediaController?.metadata?.description
        bitmapView?.setImageBitmap(description?.iconBitmap)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackInfoBinding.inflate(inflater, container, false)

        return binding.root
    }



    fun buttonChangeColor(typeButton: Int) {
        pauseButton?.setBackgroundResource(android.R.drawable.btn_default)
        playButton?.setBackgroundResource(android.R.drawable.btn_default)
        stopButton?.setBackgroundResource(android.R.drawable.btn_default)
        nextButton?.setBackgroundResource(android.R.drawable.btn_default)
        prevButton?.setBackgroundResource(android.R.drawable.btn_default)
        when (typeButton) {
            BUTTON_PLAY -> playButton?.setBackgroundResource(R.color.colorButtonClick)
            BUTTON_PAUSE -> pauseButton?.setBackgroundResource(R.color.colorButtonClick)
            BUTTON_STOP -> stopButton?.setBackgroundResource(R.color.colorButtonClick)
            BUTTON_NEXT -> nextButton?.setBackgroundResource(R.color.colorButtonClick)
            BUTTON_PREVIOUS -> prevButton?.setBackgroundResource(R.color.colorButtonClick)
        }
    }

    override fun onResume() {
        super.onResume()

        println("GET POSITION ${arguments?.getInt("position")}")

    }

    override fun onDestroy() {
        super.onDestroy()

   /*   mediaServiceBinder = null
        if (mediaController != null) {
            mediaController?.unregisterCallback(callback as MediaControllerCompat.Callback)
            mediaController = null
        }
        activity?.unbindService(serviceConnection!!)
*/
    }



    companion object {

        private const val BUTTON_PLAY = 1
        private const val BUTTON_STOP = 2
        private const val BUTTON_PAUSE = 3
        private const val BUTTON_NEXT = 4
        private const val BUTTON_PREVIOUS = 5
    }

}
