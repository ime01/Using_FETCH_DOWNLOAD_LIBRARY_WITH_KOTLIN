package com.example.firstappon2020macbookpro

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.net.UrlQuerySanitizer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.net.URL

class NormalAndroidDownloaderActivity : AppCompatActivity() {

    private lateinit var downloaderClass: AndroidDownloader
   // private lateinit var enteredDownloadLink: URL
    val urlJpg = "https://unsplash.com/photos/l_Zboz5Y7Es/download?ixid=MnwxMjA3fDB8MXxhbGx8fHx8fHx8fHwxNjQ3ODYzNDI1&force=true"
    //val urlMp4 = "http://sd5ku4esvp9sq9s.poiuytrewqasdfghjkl.cyou/rlink/The_Walking_Dead_-_S11E14_-_Unknown_e4e44e4c9cbec8081280ad75722ea7a4.mp4"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_android_downloader)

        val textView: TextView = findViewById(R.id.text_one)
        val downloadUrl: EditText = findViewById(R.id.download_url)
        val downloadButton: Button = findViewById(R.id.download_button)






        downloaderClass = AndroidDownloader()


        downloadButton.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //permission denied, request it

                    //show popup for runtime permission request
                    requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        MainActivity.STORAGE_PERMISSION_CODE
                    )
                    //downloaderClass.startDownloadingFile(urlMp4, this)

                }else{
                    //permission already granted, perform download
                    downloaderClass.startDownloadingFile(urlJpg, this)

                }
            }else{
                //system is less than marshmellow, runtime permission not needed, perform download\
                downloaderClass.startDownloadingFile(urlJpg, this)
            }

        }

    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            MainActivity.STORAGE_PERMISSION_CODE ->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission from popup dialog granted, start download now
                    downloaderClass.startDownloadingFile(urlJpg, this)

                }else{
                    //permission from popup dialog Denied, display error message
                    Toast.makeText(this, "Storage Permission Denied, This is needed to Download files", Toast.LENGTH_LONG).show()

                }
            }
        }


    }













    private fun startDownloadingFile() {
        val urlJpg = "https://unsplash.com/photos/l_Zboz5Y7Es/download?ixid=MnwxMjA3fDB8MXxhbGx8fHx8fHx8fHwxNjQ3ODYzNDI1&force=true"
        val urlMp4 = "http://sd5ku4esvp9sq9s.poiuytrewqasdfghjkl.cyou/rlink/The_Walking_Dead_-_S11E14_-_Unknown_e4e44e4c9cbec8081280ad75722ea7a4.mp4"




        val request = DownloadManager.Request(Uri.parse(urlJpg))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("Download")
        request.setDescription("Your File is downloading....")

        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${System.currentTimeMillis()}")

        //enqueue
        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
        Log.d("STARTED", "DOWNLOADING FILE ")


    }
}

