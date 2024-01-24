package com.slayers.streamsync

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.extensions.android.json.AndroidJsonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.YouTubeRequestInitializer
import com.google.api.services.youtube.model.SearchResult
import com.slayers.streamsync.databinding.FragmentOnilneBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLDecoder


class Online : Fragment() {

    private lateinit var binding: FragmentOnilneBinding
    private var myAdapter: OnlineAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnilneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myAdapter = OnlineAdapter(ArrayList())
        binding.recyclerOnline.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerOnline.adapter = myAdapter
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    performSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle changes in the search query (e.g., as the user types)
                return true
            }
        })

        showLoadingIndicator()
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val files = search("Cars")

                withContext(Dispatchers.Main) {
                    hideLoadingIndicator()
                    myAdapter?.updateData(files)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    hideLoadingIndicator()
                }
            }
        }



    }
    private fun performSearch(query: String) {
        showLoadingIndicator()
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val files = search(query)

                withContext(Dispatchers.Main) {
                    hideLoadingIndicator()
                    myAdapter?.updateData(files)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    hideLoadingIndicator()
                }
            }
        }
    }
    private suspend fun search(query:String): List<OnlineFile> = withContext(Dispatchers.IO) {

        val youtubeService = YouTube.Builder(
            AndroidHttp.newCompatibleTransport(),
            AndroidJsonFactory.getDefaultInstance(),
            null
        ).setApplicationName("streamsync")
            .setYouTubeRequestInitializer(YouTubeRequestInitializer("ADd-your api key"))
            .build()

//        val query = "Cards"
        val searchRequest = youtubeService.search().list(listOf("id","snippet"))
        searchRequest.q = query
        searchRequest.type = listOf("video")
        searchRequest.videoSyndicated = "true"
        searchRequest.maxResults = 10
        val searchResponse = searchRequest.execute()
        val onlineFileList = searchResponse.items.map { it.toOnlineFile() }
        return@withContext onlineFileList

    }

    suspend fun SearchResult.toOnlineFile(): OnlineFile {
        val name = this.snippet.title
        val time = this.snippet.publishedAt.toString()
        val image = urlToBitmap(this.snippet.thumbnails.default.url)
        val desc = this.snippet.description
//        val url = extractDirectVideoUrl(this.id.videoId)
        val url = "https://www.youtube.com/watch?v=${this.id.videoId}"
        println(OnlineFile(name, time, image, desc, url))
        return OnlineFile(name, time, image, desc, url)
    }

    private suspend fun extractDirectVideoUrl(videoId: String): String = withContext(Dispatchers.IO) {
        try {
            val url = "https://www.youtube.com/get_video_info?video_id=$videoId"
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()

            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    val params = body?.split("&")

                    for (param in params.orEmpty()) {
                        val pair = param.split("=")

                        if (pair[0] == "url_encoded_fmt_stream_map") {
                            val value = URLDecoder.decode(pair[1], "UTF-8")
                            val formats = value.split(",")

                            for (format in formats) {
                                val attributes = format.split("&")

                                var itag = ""
                                var videoUrl = ""
                                for (attribute in attributes) {
                                    val pair2 = attribute.split("=")

                                    if (pair2[0] == "itag") {
                                        itag = pair2[1]
                                    }

                                    if (pair2[0] == "url") {
                                        videoUrl = pair2[1]
                                    }
                                }

                                if (itag == "18") {
                                    return@withContext videoUrl
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext ""
    }



    private fun urlToBitmap(url: String): Bitmap? {
        return try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    private fun showLoadingIndicator() {
        binding.loadingIndicator.visibility = View.VISIBLE
    }

    private fun hideLoadingIndicator() {
        binding.loadingIndicator.visibility = View.GONE

    }
}
