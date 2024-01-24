package com.slayers.streamsync

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slayers.streamsync.databinding.OnlineviewBinding

class OnlineAdapter ( private var dataList: List<OnlineFile>) : RecyclerView.Adapter<OnlineView>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnlineView {
            val view = OnlineviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return OnlineView(view)
        }

        override fun onBindViewHolder(holder: OnlineView, position: Int) {
            holder.bind(dataList[position])
        }

        override fun getItemCount(): Int {
            return dataList.size
        }
        fun updateData(newList: List<OnlineFile>) {
            dataList = newList
            notifyDataSetChanged()
        }



}