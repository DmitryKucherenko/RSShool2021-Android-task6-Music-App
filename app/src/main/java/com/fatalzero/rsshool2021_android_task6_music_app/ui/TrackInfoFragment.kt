package com.fatalzero.rsshool2021_android_task6_music_app.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fatalzero.rsshool2021_android_task6_music_app.databinding.FragmentTrackInfoBinding

private const val ID = "id"

class TrackInfoFragment : Fragment() {
    private var _binding: FragmentTrackInfoBinding? = null
    private val binding get() = requireNotNull(_binding)

    var playButton: ImageButton? = null
    var pauseButton: ImageButton? = null
    var stopButton: ImageButton? = null
    var prevButton: ImageButton? = null
    var nextButton: ImageButton? = null
    var artistTextView: TextView? = null
    var bitmapView: ImageView? = null
    var titleTextView: TextView? = null

    private val trackInfoViewModel: TrackInfoViewModel by viewModels {
        TrackInfoViewModel.TrackInfoViewModelFactory(requireActivity().application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState!=null)trackInfoViewModel.id= savedInstanceState.getInt(ID)else
        trackInfoViewModel.id = arguments?.getInt(ID) ?: 0
         println(arguments?.getInt(ID) ?: 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ID,trackInfoViewModel.serviceConnection?.mediaController?.metadata?.description?.mediaId?.toInt()?:0)
     //   arguments?.putInt(ID,trackInfoViewModel.id)
        println("save instance vork!")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playButton = binding.playButton
        pauseButton = binding.pauseButton
        stopButton = binding.stopButton
        prevButton = binding.previousButton
        nextButton = binding.nextButton
        bitmapView = binding.BitmapView
        artistTextView = binding.ArtrtistTextView
        titleTextView = binding.TitleTextView

//        if(trackInfoViewModel.id != -1) {
//            trackInfoViewModel.playFromPosition(trackInfoViewModel.id)
//
//        }

        trackInfoViewModel.serviceConnection?.connection?.observe(viewLifecycleOwner,
            { connection ->
                connection?.let {
                    if (it) {
                        trackInfoViewModel.playFromPosition(trackInfoViewModel.id)
                        println("--------------${trackInfoViewModel.id}----------------------------")
                    }
                }
            })



        prevButton?.setOnClickListener {
            trackInfoViewModel.previousTrack()
            buttonChangeColor(BUTTON_PREVIOUS)
        }
        playButton?.setOnClickListener {
            trackInfoViewModel.playTrack()
            buttonChangeColor(BUTTON_PLAY)
        }
        stopButton?.setOnClickListener {
            trackInfoViewModel.stopPlaying()
            buttonChangeColor(BUTTON_STOP)
        }
        pauseButton?.setOnClickListener {
            trackInfoViewModel.pausePlaying()
            buttonChangeColor(BUTTON_PAUSE)
        }
        nextButton?.setOnClickListener {
            trackInfoViewModel.nextTrack()
            buttonChangeColor(BUTTON_NEXT)
        }

        trackInfoViewModel.mediaLiveData.observe(viewLifecycleOwner,
            { trackInfo ->
                trackInfo?.let {
                    bitmapView?.setImageBitmap(it.iconBitmap)
                    titleTextView?.text = it.title
                    artistTextView?.text = it.subtitle
                  //  trackInfoViewModel.id=it.mediaId?.toInt()?:0
                   // println("trackInfoViewModel")
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            BUTTON_PLAY -> playButton?.setBackgroundColor(Color.GREEN)
            BUTTON_PAUSE -> pauseButton?.setBackgroundColor(Color.GREEN)
            BUTTON_STOP -> stopButton?.setBackgroundColor(Color.GREEN)
            BUTTON_NEXT -> nextButton?.setBackgroundColor(Color.GREEN)
            BUTTON_PREVIOUS -> prevButton?.setBackgroundColor(Color.GREEN)
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
