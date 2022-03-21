package com.example.firstappon2020macbookpro

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tonyodev.fetch2.*
import com.tonyodev.fetch2core.DownloadBlock


class MainActivity : AppCompatActivity() {

    private lateinit var fetch: Fetch
    private lateinit var request: Request


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.text_one)
        val downloadUrl: EditText = findViewById(R.id.download_url)
        val downloadButton: Button = findViewById(R.id.download_button)

        val fetchConfiguration: FetchConfiguration = FetchConfiguration.Builder(this)
            .setDownloadConcurrentLimit(3)
            .build()

        fetch = Fetch.Impl.getInstance(fetchConfiguration)

       // val url = "http:www.example.com/test.txt"
        val url = "http://sd5ku4esvp9sq9s.poiuytrewqasdfghjkl.cyou/rlink/The_Walking_Dead_-_S11E14_-_Unknown_e4e44e4c9cbec8081280ad75722ea7a4.mp4"
        //val url = "https://filesamples.com/samples/document/txt/sample2.txt"
        //val file = "/downloads1/test.txt"
        val file = "/downloads/file1.mp4"

        request = Request(url, file)
        //request.networkType
        //request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG")
        fetch.addListener(DownloadListener())





        downloadButton.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //permission denied, request it

                    //show popup for runtime permission request
                    requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
                    val intent = Intent(this, NormalAndroidDownloaderActivity::class.java)
                    startActivity(intent)


                }else{
                    //permission already granted, perform download

                   // startDownlaod()
                    //startDownloading()
                    val intent = Intent(this, NormalAndroidDownloaderActivity::class.java)
                    startActivity(intent)

                }
            }else{
                //system is less than marshmellow, runtime permission not needed, perform download\
                startDownlaod()
            }


        }


    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            STORAGE_PERMISSION_CODE ->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission from popup dialog granted, start download now
                    startDownlaod()

                }else{
                    //permission from popup dialog Denied, display error message
                    Toast.makeText(this@MainActivity, "Storage Permission Denied, This is needed to Download files", Toast.LENGTH_LONG).show()

                }
            }
        }


    }


    fun startDownlaod() {
        fetch.enqueue(request, { updatedRequest: Request? ->
            Log.d("REQUEST", "$request   $updatedRequest")
            Toast.makeText(this, "Request $updatedRequest Enqueued for Download ", Toast.LENGTH_LONG).show()
        }) { error: Error? ->
            Toast.makeText(this, "Error Downloading $error", Toast.LENGTH_LONG).show()
        }
    }


    private fun startDownloading() {
       /* val fileUri = DocumentFile.fromTreeUri(this )
            .createFile("audio/mpeg", FILE_NAME)!!.uri
        val request = Request(HTTP_URL, fileUri)
        request.enqueueAction = EnqueueAction.UPDATE_ACCORDINGLY
*/
        fetch.enqueue(request, null, { error: Error ->
                Log.d("FetchTest", "Failed to equeue request")
                Log.d("REQUEST2", "$request")
                error.throwable?.printStackTrace()
                Toast.makeText(this, "Failed to equeue request $error", Toast.LENGTH_SHORT).show()
            }
        )
    }







    inner class DownloadListener() : AbstractFetchListener() {

        override fun onAdded(download: Download) {
            if (request.id == download.id) {
                //showDownloadInList(download)
                showNotification(download.id)

                Toast.makeText(this@MainActivity, "Download with ID ${download.id} Added", Toast.LENGTH_SHORT).show()

            }
        }




        override fun onStarted(download: Download, downloadBlocks: List<DownloadBlock>, totalBlocks: Int) {
            super.onStarted(download, downloadBlocks, totalBlocks)
            Toast.makeText(this@MainActivity, "Download with ID ${download.id} STARTED", Toast.LENGTH_SHORT).show()

        }


        override fun onCompleted(download: Download) {
            fetch.remove(download.id)   // Workaround for https://github.com/tonyofrancis/Fetch/issues/553

            Toast.makeText(this@MainActivity, "Download Successfully Completed", Toast.LENGTH_SHORT).show()

        }

        override fun onError(download: Download, error: Error, throwable: Throwable?) {
            Log.d("FetchFail", "Failed to download file")
            throwable?.printStackTrace()
            Toast.makeText(this@MainActivity, "Failed to download file", Toast.LENGTH_SHORT).show()

        }

        override fun onCancelled(download: Download) {

        }

    }

    override fun onResume() {
        super.onResume()
        fetch.addListener(DownloadListener())
    }

    override fun onPause() {
        super.onPause()
        fetch.removeListener(DownloadListener())
    }


    override fun onDestroy() {
        super.onDestroy()
        fetch.close()
    }


    //display downlaoding state
    fun showNotification(id: Int){

    }

    companion object{
        val STORAGE_PERMISSION_CODE = 100
    }




}
