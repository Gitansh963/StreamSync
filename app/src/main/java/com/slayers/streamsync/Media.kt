package com.slayers.streamsync

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.slayers.streamsync.databinding.FragmentDownloadBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Media : Fragment() {

    private lateinit var binding: FragmentDownloadBinding
    private var myAdapter: MediaAdapter? = null
    private val viewModel: MediaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDownloadBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myAdapter = MediaAdapter(ArrayList())
        binding.recyclerDownload.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerDownload.adapter = myAdapter


        if (viewModel.mediaFiles == null) {
            // If mediaFiles is not cached, fetch and cache it
            showLoadingIndicator()
            lifecycleScope.launch {
                val mediaFiles = withContext(Dispatchers.IO) {
                    getMediaFiles()
                }

                viewModel.mediaFiles = mediaFiles

                withContext(Dispatchers.Main) {
                    hideLoadingIndicator()
                    myAdapter?.updateData(mediaFiles)
                }
            }
        } else {
            // If mediaFiles is already cached, use it
            myAdapter?.updateData(viewModel.mediaFiles!!)
        }
    }


    private suspend fun getMediaFiles(): ArrayList<MediaFile> = withContext(Dispatchers.IO) {
        val mediaFiles = ArrayList<MediaFile>()

        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.SIZE

        )
        val contentResolver = requireActivity().contentResolver

        contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
                val path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                val metadataRetriever = MediaMetadataRetriever()
                metadataRetriever.setDataSource(path)

                val desc = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE)
                    ?: "Default Description"

                val time = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                    ?: "Default Time"

                val firstFrame = metadataRetriever.getFrameAtTime(0)

                metadataRetriever.release()

                val mediaFile = MediaFile(id.toString(), name, desc, time, firstFrame?:getDefaultBitmap(), path)
                mediaFiles.add(mediaFile)
            }
            cursor.close()
        }

        return@withContext mediaFiles.also {
            Log.d("MediaFragment", "MediaFiles count: ${it.size}")
        }
    }

    private fun getDefaultBitmap(): Bitmap {
        val width = 100
        val height = 100
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    }

    private fun showLoadingIndicator() {
        binding.loadingIndicator.visibility = View.VISIBLE
    }

    private fun hideLoadingIndicator() {
        binding.loadingIndicator.visibility = View.GONE

    }
}
