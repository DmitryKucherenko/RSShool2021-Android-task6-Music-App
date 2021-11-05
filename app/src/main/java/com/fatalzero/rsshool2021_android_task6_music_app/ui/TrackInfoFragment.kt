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
private const val PLAY = "play"
private const val TYPE_BUTTON = "type_button"

class TrackInfoFragment : Fragment() {
    private var _binding: FragmentTrackInfoBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var playButton: ImageButton? = null
    private var pauseButton: ImageButton? = null
    private var stopButton: ImageButton? = null
    private var prevButton: ImageButton? = null
    private var nextButton: ImageButton? = null
    private var artistTextView: TextView? = null
    private var bitmapView: ImageView? = null
    private var titleTextView: TextView? = null
    private var play: Boolean = false
    private var typeButton:Int=0

    private val trackInfoViewModel: TrackInfoViewModel by viewModels {
        TrackInfoViewModel.TrackInfoViewModelFactory(requireActivity().application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            trackInfoViewModel.id = savedInstanceState.getInt(ID)
            play = savedInstanceState.getBoolean(PLAY)
            typeButton = savedInstanceState.getInt(TYPE_BUTTON)

        } else {
            trackInfoViewModel.id = arguments?.getInt(ID) ?: 0
            play = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(
            ID,
            trackInfoViewModel.mediaLiveData.value?.mediaId?.toInt() ?: 0
        )
        outState.putBoolean(
            PLAY,
            false
        )
        outState.putInt(TYPE_BUTTON,typeButton)
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

        trackInfoViewModel.serviceConnection?.connection?.observe(viewLifecycleOwner,
            { connection ->
                connection?.let {
                    if (it && play) {
                        trackInfoViewModel.playFromPosition(trackInfoViewModel.id)
                        typeButton= BUTTON_PLAY
                    }
                }
            })

        prevButton?.setOnClickListener {
            trackInfoViewModel.previousTrack()
            typeButton=BUTTON_PREVIOUS
        }
        playButton?.setOnClickListener {
            trackInfoViewModel.playTrack()
            typeButton=(BUTTON_PLAY)
        }
        stopButton?.setOnClickListener {
            trackInfoViewModel.stopPlaying()
            typeButton=BUTTON_STOP
        }
        pauseButton?.setOnClickListener {
            trackInfoViewModel.pausePlaying()
            typeButton=BUTTON_PAUSE
        }
        nextButton?.setOnClickListener {
            trackInfoViewModel.nextTrack()
            typeButton=BUTTON_NEXT
        }

        trackInfoViewModel.mediaLiveData.observe(viewLifecycleOwner,
            { trackInfo ->
                trackInfo?.let {
                    bitmapView?.setImageBitmap(it.iconBitmap)
                    titleTextView?.text = it.title
                    artistTextView?.text = it.subtitle
                    buttonChangeColor(typeButton)
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

    private fun buttonChangeColor(typeButton: Int) {
        this.typeButton = typeButton
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
