package kz.home.converter

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kz.home.converter.presentation.MainActivity
import kz.home.converter.presentation.news.NewsFragment

private const val CHANNEL_ID = "1234"

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        createNotificationChannel()
        displayNotification(message)
    }

    override fun onNewToken(token: String) = Unit

    private fun displayNotification(message: RemoteMessage) {

        val title = message.data["title"]
        val body = message.data["body"]

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.email_icon)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(getPendingIntent())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this).notify(Int.MIN_VALUE, notification)
    }

    private fun getPendingIntent(): PendingIntent = NavDeepLinkBuilder(this)
        .setGraph(R.navigation.nav_graph)
        .setDestination(R.id.newsFragment)
        .createPendingIntent()

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Lesson 27 Channel"
            val descriptionText = "This is our first channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}