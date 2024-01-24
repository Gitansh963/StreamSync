package com.slayers.streamsync

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.slayers.streamsync.databinding.OnlineviewBinding

class OnlineView (private val binding: OnlineviewBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(file: OnlineFile){
        binding.nameOnline.text = file.name
        binding.descOnline.text = file.desc
        binding.duration.text = file.time
        binding.imageOnline.setImageBitmap(file.image)
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, VideoActivity::class.java)
            val videoPath=  file.url
            intent.putExtra("VIDEO_PATH", videoPath)
            itemView.context.startActivity(intent)
        }

    }
}