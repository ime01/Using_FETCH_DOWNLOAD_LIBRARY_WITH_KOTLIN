package com.example.firstappon2020macbookpro

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService

class AndroidDownloader {


     fun startDownloadingFile(url:String, context: Context ) {
        //val urlJpg = "https://unsplash.com/photos/l_Zboz5Y7Es/download?ixid=MnwxMjA3fDB8MXxhbGx8fHx8fHx8fHwxNjQ3ODYzNDI1&force=true"
        val urlMp4 = "http://sd5ku4esvp9sq9s.poiuytrewqasdfghjkl.cyou/rlink/The_Walking_Dead_-_S11E14_-_Unknown_e4e44e4c9cbec8081280ad75722ea7a4.mp4"




        val request = DownloadManager.Request(Uri.parse(urlMp4))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("Download")
        request.setDescription("Your File is downloading....")

        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${System.currentTimeMillis()}")

        //enqueue
        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
        Log.d("STARTED", "DOWNLOADING FILE ")

    }
}