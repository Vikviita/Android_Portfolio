package com.vikvita.first_advanced_project

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.vikvita.first_advanced_project.databinding.ActivityMainBinding
import com.vikvita.first_advanced_project.databinding.ContentMainBinding


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action
    private lateinit var bindingMain: ActivityMainBinding
    private lateinit var bindingContent: ContentMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingMain= ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            setSupportActionBar(it.toolbar)
        }

        bindingContent= ContentMainBinding.bind(findViewById(R.id.main_layout))

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        notificationManager=getSystemService(NotificationManager::class.java) as NotificationManager
        createChannel()
        bindingContent.customButton.setOnClickListener{

            ( it as LoadingButton).move()
        download(when(bindingContent.radioGroup.checkedRadioButtonId){
            R.id.app_download-> APPURL
            R.id.glide_download-> GLIDEURL
            R.id.retrofit_dowmload-> RETROFITURL
            else->""
        }

        )
        }



    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)



            val q = DownloadManager.Query()
            q.setFilterById(downloadID)
            val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            val cursor: Cursor = downloadManager.query(q)
            cursor.moveToFirst()

            val body=when (cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_URI))){
                   GLIDEURL->getString(R.string.glide_image_loading_library_by_bump_tech)
                   APPURL->getString(R.string.loadapp_current_repository_by_udacity)
                   RETROFITURL->getString(R.string.retrofit_type_safe_http_client_for_android_and_java_by_square_inc)
                else->"error"
            }

            val status=when(cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))){
                DownloadManager.STATUS_FAILED->"FAILED"
                DownloadManager.STATUS_SUCCESSFUL->"SUCCESS"
                else->"error"
            }

            sendNotification(body,status)
            bindingContent.customButton.stopAnimation()

        }
    }

    private fun download(url:String) {
        if(url.compareTo("")==0){
            Toast.makeText(this, getString(R.string.please_select_file_to_download), Toast.LENGTH_SHORT).show()
            bindingContent.customButton.stopRepeat()
        return
        }
        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID = downloadManager.enqueue(request)// enqueue puts the download request in the queue.


    }

    companion object {
        private const val APPURL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val GLIDEURL="https://github.com/bumptech/glide"
        private const val RETROFITURL="https://github.com/square/retrofit"
        private const val CHANNEL_ID = "channelId"
        private const val CHANNEL_NAME="CHANNEL"
        private const val NOTIFICATION_ID=0

    }

    fun createChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )



            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.notification_description)

            notificationManager.createNotificationChannel(notificationChannel)
        }


    }

    fun sendNotification(body:String,status:String) {
         val intent=Intent(applicationContext,DetailActivity::class.java)
        intent.putExtra(getString(R.string.bodyextra),body)
        intent.putExtra(getString(R.string.statusextra),status)
       pendingIntent= PendingIntent.getActivity(applicationContext, NOTIFICATION_ID,intent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        action=NotificationCompat.Action.Builder(R.drawable.notification_icon,getString(R.string.notification_button),pendingIntent).build()
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_description))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(action)


        notificationManager.notify(NOTIFICATION_ID,builder.build())





    }

    override fun onRestart() {
        super.onRestart()
        finish()
        startActivity(intent)

    }




}
