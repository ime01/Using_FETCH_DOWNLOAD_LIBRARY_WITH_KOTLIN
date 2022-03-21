package com.example.firstappon2020macbookpro

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.koushikdutta.async.future.Future
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import com.koushikdutta.ion.ProgressCallback
import java.io.File


class IonDownloaderActivity : AppCompatActivity() {

    var progressBar: ProgressBar? = null
    var progressDialog: ProgressDialog? = null

    var downloading: Future<File>? = null
    lateinit var downloadButton: Button
    lateinit var cancelButton: Button
    lateinit var downloadCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ion_downloader)

        // Enable global Ion logging
        Ion.getDefault(this).configure().setLogging("ion-sample", Log.DEBUG);

        val textView: TextView = findViewById(R.id.text_one)
        val downloadUrl: EditText = findViewById(R.id.download_url)
        progressBar = findViewById(R.id.progress_circular)
        downloadCount = findViewById(R.id.download_percentage)
        downloadButton = findViewById(R.id.download_button)
        cancelButton = findViewById(R.id.cancel_download)
        progressBar?.setProgress(0);

        val urlImage = "https://unsplash.com/photos/l_Zboz5Y7Es/download?ixid=MnwxMjA3fDB8MXxhbGx8fHx8fHx8fHwxNjQ3ODYzNDI1&force=true"
        //val urlMp4 = "https://drive.google.com/u/0/uc?id=1RNM3M--VQqBw0BiTNmraylpjfl3XcIbi&export=download"

       // val fileDownloader = IonDownloaderClass()



        downloadButton.setOnClickListener {
            /* if (downloading != null && !downloading.isCancelled()) {
                resetDownload()
                return@setOnClickListener
            }*/

            downloadButton.setText("Cancel")
            progressBar?.visibility = View.VISIBLE


          //  fileDownloader.startDownload(this, url)


             Ion.with(this)
                //.load("http://example.com/really-big-file.zip") // have a ProgressBar get updated automatically with the percent
                .load(urlImage) // have a ProgressBar get updated automatically with the percent
                .progressBar(progressBar) // and a ProgressDialog
                .progressHandler { downloaded, total ->
                    Toast.makeText(this, "Download Started", Toast.LENGTH_LONG).show()

                    downloadCount.setText("Download Count" + downloaded + " / " + total);
                    }
                .progressDialog(progressDialog) // can also use a custom callback
                .progress(ProgressCallback { downloaded, total ->
                    println("$downloaded / $total")
                    Log.d("DOWNLOAD", "DOWNLOAD STARTED $downloaded total left is $total")
                   // Toast.makeText(this, "Download Started $downloaded, and total is $total", Toast.LENGTH_LONG).show()
                })
                //.write(File("/sdcard/really-big-file.zip"))
                //.write(File("/sdcard/really-big-file.mp4"))
                .write(getFileStreamPath("pictureDownloaded.jpg-" + System.currentTimeMillis() + ".jpg"))
                .setCallback(FutureCallback<File?> { e, file ->
                    // download done...
                    // do stuff with the File or error
                    progressBar?.visibility = View.GONE
                    Log.d("FAILED", "DOWNLOAD FAILED $e")
                    //Toast.makeText(this, "Error Downloading $e", Toast.LENGTH_LONG).show()
                })

            progressBar?.visibility = View.GONE
            Toast.makeText(this, "Download Completed", Toast.LENGTH_LONG).show()
            }

            cancelButton.setOnClickListener {
                downloading?.cancel()
                downloading = null
                Toast.makeText(this, "Download Cancelled", Toast.LENGTH_LONG).show()
            }

        }

}

    //fun resetDownload() {
        // cancel any pending download
        /*downloading!!.cancel()
        downloading = null

        // reset the ui
        downloadButton.setText("Download")
        //downloadCount.setText(null)
        progressBar!!.progress = 0*/
