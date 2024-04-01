package com.nels.master.appdetail.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.nels.master.appdetail.MainActivity
import com.nels.master.appdetail.R


class LocalNotification(private val context: Context) {

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                NOTIFICATION_LOCATION_CHANNEL,
                "Location",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Notificacion para registro de ubicaciones"
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }


    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotificaiton(title: String,contentText: String){

        val activityintent = Intent(context, MainActivity::class.java)
        val pendingintent = PendingIntent.getActivity(context,1,activityintent
        , if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        var notification = NotificationCompat.Builder(context, NOTIFICATION_LOCATION_CHANNEL)
            .setSmallIcon(R.drawable.baseline_location_pin_24)
            .setContentTitle(title)
            .setContentText(contentText)
            .setContentIntent(pendingintent).build()

        notificationManager.notify(1, notification)

    }

    companion object {
        const val NOTIFICATION_LOCATION_CHANNEL = "location_chanel"
    }

}