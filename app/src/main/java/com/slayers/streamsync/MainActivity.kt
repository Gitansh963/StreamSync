package com.slayers.streamsync

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.slayers.streamsync.databinding.ActivityMainBinding
import android.Manifest
import android.os.Build.VERSION
import android.util.Log
import com.google.android.material.snackbar.Snackbar

//
//class MainActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMainBinding
//    private val REQUEST_CODE_MANAGE_EXTERNAL_STORAGE = 1
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        if (checkStoragePermissions()) {
//            startOptionActivity()
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
//                intent.addCategory("android.intent.category.DEFAULT")
//                intent.data = Uri.parse(String.format("package:%s", applicationContext.packageName))
//                startActivityForResult(intent, REQUEST_CODE_MANAGE_EXTERNAL_STORAGE)
//            } else {
//                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_MANAGE_EXTERNAL_STORAGE)
//            }
//        }
//    }
//
//    private fun startOptionActivity() {
//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this, OptionActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 3000)
//    }
//
//    fun checkStoragePermissions(): Boolean {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            Environment.isExternalStorageManager()
//        } else {
//            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            REQUEST_CODE_MANAGE_EXTERNAL_STORAGE -> {
//                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    startOptionActivity()
//                } else {
//                    Snackbar.make(binding.root, "Permission denied. Please grant the necessary permission to proceed.", Snackbar.LENGTH_LONG).show()
//                }
//                return
//            }
//            else -> {
//                Log.e("MainActivity", "Unexpected request code: $requestCode")            }
//        }
//    }
//}

//
//class MainActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMainBinding
//    private val REQUEST_CODE_READ_EXTERNAL_STORAGE = 1
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        if (checkStoragePermissions()) {
//            startOptionActivity()
//        } else {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ_EXTERNAL_STORAGE)
//        }
//    }
//
//    private fun startOptionActivity() {
//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this, OptionActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 3000)
//    }
//
//    private fun checkStoragePermissions(): Boolean {
//        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            REQUEST_CODE_READ_EXTERNAL_STORAGE -> {
//                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    startOptionActivity()
//                } else {
//                    Snackbar.make(binding.root, "Permission denied. Please grant the necessary permission to proceed.", Snackbar.LENGTH_LONG).show()
//                }
//                return
//            }
//            else -> {
//                Log.e("MainActivity", "Unexpected request code: $requestCode")
//            }
//        }
//    }
//}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val REQUEST_CODE_READ_MEDIA = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkMediaPermissions()) {
            startOptionActivity()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO", "android.permission.READ_MEDIA_AUDIO"), REQUEST_CODE_READ_MEDIA)
        }
    }

    private fun startOptionActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, OptionActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    private fun checkMediaPermissions(): Boolean {
        return if(android.os.Build.VERSION.PREVIEW_SDK_INT > 33) {
            ContextCompat.checkSelfPermission(
                this,
                "android.permission.READ_MEDIA_IMAGES"
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        this,
                        "android.permission.READ_MEDIA_VIDEO"
                    ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        this,
                        "android.permission.READ_MEDIA_AUDIO"
                    ) == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_READ_MEDIA -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    startOptionActivity()
                } else {
                    Snackbar.make(binding.root, "Permission denied. Please grant the necessary permission to proceed.", Snackbar.LENGTH_LONG).show()
                }
                return
            }
            else -> {
                Log.e("MainActivity", "Unexpected request code: $requestCode")
            }
        }
    }
}
