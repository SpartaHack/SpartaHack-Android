package com.spartahack.spartahack_android.tools

//import android.app.Notification
//import android.app.NotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotification : FirebaseMessagingService() {
    /* This class extends FirebaseMessagingService to allow the app to receive notifications from
    * our Firebase server. This class is only necessary because it would probably be a good idea to
    * display notifications to our users even when they are in the app, so that they don't miss
    * special activity notifications. This class is unnecessary if it is decided that foreground
    * notifications are not needed.*/

    override fun onMessageReceived(message: RemoteMessage) {
        /* Retrieves the message from Firebase and creates a notification using the built-in
           Notification.Builder.*/
        /*val notificationBuilder = Notification.Builder()
            // ?. is Kotlin's way of checking for NULL (message.notification can return NULL)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .build()*/


    }
}