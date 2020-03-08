package com.spartahack.spartahack_android.tools

import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.spartahack.spartahack_android.R
import com.spartahack.spartahack_android.ScheduleActivity

class FirebaseNotification : FirebaseMessagingService() {
    /* This class extends FirebaseMessagingService to allow the app to receive notifications from
    * our Firebase server. This class is only necessary because it would probably be a good idea to
    * display notifications to our users even when they are in the app, so that they don't miss
    * special activity notifications. This class is unnecessary if it is decided that foreground
    * notifications are not needed. */

    private val CHANNEL_ID = getString(R.string.events_id)
    private val intent = Intent(this, ScheduleActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    private val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

    override fun onMessageReceived(message: RemoteMessage) {
         // Retrieves the message from Firebase and creates a notification using the built-in
         // Notification Builder.
        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            // ?. is Kotlin's way of checking for NULL (message.notification can return NULL)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationId = 1  // I have no idea what this is supposed to be.

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, notificationBuilder.build())
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }
}