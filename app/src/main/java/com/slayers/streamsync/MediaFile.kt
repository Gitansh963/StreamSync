package com.slayers.streamsync

import android.graphics.Bitmap

data class MediaFile (
    val id: String,
    val name: String,
    val desc: String,

    val time: String,
    val image: Bitmap?,
    val path: String

)