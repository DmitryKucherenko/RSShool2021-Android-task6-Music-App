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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.fatalzero.rsshool2021_android_task6_music_app.AudioList
import com.fatalzero.rsshool2021_android_task6_music_app.AudioService
import com.fatalzero.rsshool2021_android_task6_music_app.R
import com.fatalzero.rsshool2021_android_task6_music_app.databinding.FragmentTrackInfoBinding
import com.fatalzero.rsshool2021_android_task6_music_app.model.Track

private const val ID = "id"
class TrackInfoFragment : Fragment() {
   var id:Int? = null
    private var _binding: FragmentTrackInfoBinding? = null
    private val binding get() = _binding!!

    var playButton: ImageButton? = null
    var pauseButton: ImageButton? = null
    var stopButton: ImageButton? = null
    var prevButton: ImageButton? = null
    var nextButton: ImageButton? = null
    var artistTextView: TextView? = null
    var albumTextView: TextView? = null
    private val trackInfoViewModel: TrackInfoViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        trackInfoViewModel.id = arguments?.getInt("id")?:0
        trackInfoViewModel.activity = activity
        trackInfoViewModel.init()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playButton= binding.playButton
        pauseButton=binding.pauseButton
        stopButton=binding.stopButton
        prevButton=binding.previousButton
        nextButton=binding.nextButton
        trackInfoViewModel.bitmapView=binding.BitmapView
        albumTextView=binding.ArtrtistTextView
        trackInfoViewModel.titleTextView=binding.TitleTextView
        artistTextView=binding.AlbumTextView


        if(trackInfoViewModel.mediaServiceBinder!=null) {
            trackInfoViewModel.playFromPosition(trackInfoViewModel.id!!)
        }



        prevButton?.setOnClickListener { trackInfoViewModel.previousTrack()
            buttonChangeColor(TrackInfoFragment.BUTTON_PREVIOUS)
        }
        playButton?.setOnClickListener { trackInfoViewModel.playTrack()
            buttonChangeColor(TrackInfoFragment.BUTTON_PLAY)
        }
        stopButton?.setOnClickListener { trackInfoViewModel.stopPlaying()
            buttonChangeColor(TrackInfoFragment.BUTTON_STOP)
        }
        pauseButton?.setOnClickListener { trackInfoViewModel.pausePlaying()
            buttonChangeColor(TrackInfoFragment.BUTTON_PAUSE)
        }
        nextButton?.setOnClickListener { trackInfoViewModel.nextTrack()
             buttonChangeColor(TrackInfoFragment.BUTTON_NEXT)
        }



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





    companion object {

        private const val BUTTON_PLAY = 1
        private const val BUTTON_STOP = 2
        private const val BUTTON_PAUSE = 3
        private const val BUTTON_NEXT = 4
        private const val BUTTON_PREVIOUS = 5
    }

}
