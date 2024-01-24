package com.slayers.streamsync

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.slayers.streamsync.databinding.ActivityOptionBinding

class OptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbarOption))
        getSupportActionBar()?.setTitle("Stream Sync");


        binding.navbar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_option1 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainerView.id, Online())
                        .commit()
                    true
                }
                R.id.nav_option2 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.fragmentContainerView.id, Media())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}