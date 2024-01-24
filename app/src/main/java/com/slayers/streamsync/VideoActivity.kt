package com.slayers.streamsync

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.slayers.streamsync.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val videoPath = intent.getStringExtra("VIDEO_PATH")
        val videoUri = Uri.parse(videoPath)
        initializePlayer3(videoUri)
    }


    private fun initializePlayer2(videoUri: Uri) {
        binding.videoView.setVideoPath(videoUri.toString())
        val mediaController = MediaController(this)
        binding.videoView.setMediaController(mediaController)
        mediaController.setAnchorView(binding.videoView)
        binding.videoView.start()
    }
    private fun initializePlayer3(videoUri: Uri) {
        binding.videoView.setVideoURI(videoUri)
        val mediaController = MediaController(this)
        binding.videoView.setMediaController(mediaController)
        mediaController.setAnchorView(binding.videoView)
        binding.videoView.start()
    }

    private fun initializePlayer(videoUri: Uri) {
        val scheme = videoUri.scheme
        if (scheme == "file") {
            binding.videoView.setVideoURI(videoUri)
        } else {
            binding.videoView.setVideoPath(videoUri.toString())
        }

        val mediaController = MediaController(this)
        binding.videoView.setMediaController(mediaController)
        mediaController.setAnchorView(binding.videoView)
        binding.videoView.start()
    }

}
