package com.fatalzero.rsshool2021_android_task6_music_app.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatalzero.rsshool2021_android_task6_music_app.repository.AudioList
import com.fatalzero.rsshool2021_android_task6_music_app.MyApplication
import com.fatalzero.rsshool2021_android_task6_music_app.adapter.ItemClickListener
import com.fatalzero.rsshool2021_android_task6_music_app.adapter.TrackAdapter
import com.fatalzero.rsshool2021_android_task6_music_app.databinding.FragmentAudioListBinding
import javax.inject.Inject


class AudioListFragment : Fragment() {
    private var _binding: FragmentAudioListBinding? = null
    private val binding get() = _binding!!
    private lateinit var audioRecyclerView: RecyclerView
    private var adapter: TrackAdapter? = null
    private var itemClickListener: ItemClickListener? = null

    @Inject
    lateinit var audioList: AudioList


    override fun onAttach(context: Context) {
        super.onAttach(context)
        itemClickListener = context as ItemClickListener
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAudioListBinding.inflate(inflater, container, false)
        val view = binding.root
        audioRecyclerView = binding.audioRecyclerView
        audioRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = TrackAdapter(itemClickListener)
        audioRecyclerView.adapter = adapter
        adapter?.submitList(audioList.getTrackCatalog())
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
