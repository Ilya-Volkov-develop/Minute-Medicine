package com.example.minutemedicine.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.minutemedicine.R

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val id = inputData.getInt("id", 0)
        val title = inputData.getString("title")
        val desc = inputData.getString("desc")
        Log.d("mylog", "$id $title $desc")
        displayNotification(title!!, desc!!, id)

        return Result.Success.success()
    }

    private fun displayNotification(task: String, desc: String, id: Int) {
        val channelId = "all_notifications"
        val mChannel = NotificationChannel(channelId,
            "General Notifications",
            NotificationManager.IMPORTANCE_DEFAULT)
        mChannel.description = "This is default channel used for all other notifications"
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
        val builder = NotificationCompat.Builder(applicationContext, "all_notifications")
            .setContentTitle(task)
            .setContentText(desc)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
        notificationManager.notify(id, builder.build())

    }
}