package com.slayers.streamsync

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.slayers.streamsync.databinding.FileviewBinding

class MediaView (private val binding: FileviewBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(file: MediaFile){
        binding.fileName.text = file.name
        binding.duration.text = file.time
        binding.folderName.text = file.path
        binding.imageFile.setImageBitmap(file.image)
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, VideoActivity::class.java)
            val videoPath=  file.path
            intent.putExtra("VIDEO_PATH", videoPath)
            itemView.context.startActivity(intent)
        }
    }
}