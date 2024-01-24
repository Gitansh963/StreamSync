package com.slayers.streamsync

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.slayers.streamsync.databinding.FileviewBinding

class MediaAdapter(private var dataList: List<MediaFile>) : RecyclerView.Adapter<MediaView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaView{
        val view = FileviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaView(view)
    }

    override fun onBindViewHolder(holder: MediaView, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    fun updateData(newProperties: List<MediaFile>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return dataList.size
            }

            override fun getNewListSize(): Int {
                return newProperties.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return dataList[oldItemPosition].id == newProperties[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return dataList[oldItemPosition] == newProperties[newItemPosition]
            }
        })

        dataList = newProperties
        diffResult.dispatchUpdatesTo(this)
    }
//    fun updateData(newProperties: List<MediaFile>) {
//        dataList = newProperties
//        notifyDataSetChanged()
//    }

}