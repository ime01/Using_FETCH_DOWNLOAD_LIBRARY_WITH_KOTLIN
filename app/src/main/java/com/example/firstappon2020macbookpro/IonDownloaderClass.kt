package com.example.firstappon2020macbookpro

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import java.io.File
import java.net.URL
import java.security.AccessControlContext

class IonDownloaderClass() {



    fun startDownload(context: Context, url: String){

        Ion.with(context)
            //.load("http://example.com/really-big-file.zip") // have a ProgressBar get updated automatically with the percent
            .load(url) // have a ProgressBar get updated automatically with the percent
            //.progressBar(progressBar) // and a ProgressDialog
            .progressHandler { downloaded, total ->
               // Toast.makeText(this, "Download Started", Toast.LENGTH_LONG).show()

                //downloadCount.setText("Download Count" + downloaded + " / " + total);
            }
            //.progressDialog(progressDialog) // can also use a custom callback
            //.progress(ProgressCallback { downloaded, total ->
               // println("$downloaded / $total")
               // Log.d("DOWNLOAD", "DOWNLOAD STARTED $downloaded total left is $total")
                // Toast.makeText(this, "Download Started $downloaded, and total is $total", Toast.LENGTH_LONG).show()
            //})
            //.write(File("/sdcard/really-big-file.zip"))
            .write(File("/sdcard/downloadedfile.mp4"))
            //.write(getFileStreamPath("mp4-" + System.currentTimeMillis() + ".mp4"))
            .setCallback(FutureCallback<File?> { e, file ->
                // download done...
                // do stuff with the File or error
                //progressBar?.visibility = View.GONE
                Log.d("FAILED", "DOWNLOAD FAILED $e")
                //Toast.makeText(this, "Error Downloading $e", Toast.LENGTH_LONG).show()
            })

    }
}