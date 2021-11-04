package com.fatalzero.rsshool2021_android_task6_music_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.fatalzero.rsshool2021_android_task6_music_app.R
import com.fatalzero.rsshool2021_android_task6_music_app.adapter.ItemClickListener

private const val ID = "id"

class MainActivity : AppCompatActivity(), ItemClickListener {
    private var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = this.findNavController(R.id.fragment_container)
    }

    override fun onItemClick(id: Int) {
        navController?.navigate(
            R.id.action_audioListFragment_to_trackInfoFragment,
            bundleOf(ID to id)
        )
    }
}
