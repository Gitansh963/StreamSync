package com.slayers.streamsync

import android.graphics.Bitmap

data class OnlineFile(
    val name: String,
    val time: String,
    val image: Bitmap?,
    val desc: String,
    val url: String,
)
