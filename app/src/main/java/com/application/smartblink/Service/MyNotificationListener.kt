package com.application.smartblink.Service

import android.content.ComponentName
import android.content.Context
import android.provider.Settings
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.application.smartblink.WifiModule.SendDataTask

class MyNotificationListener : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)
        val packageName = sbn.packageName

        // Log.d("NotificationListener", "Notification Posted: ${sbn.notification}")
        Log.d("NotificationListener",packageName)

        val notificationText = sbn.notification?.tickerText?.toString() ?: ""
        //com.android.mms   com.instagram.android  com.whatsapp com.facebook.orca
        if(notificationText!="") {
            if (packageName.equals("com.whatsapp"))
                SendDataTask("GREEN").execute()
            else if (packageName.equals("com.facebook.katana"))
                SendDataTask("BLUE").execute()

            else if (packageName.equals("com.instagram.android"))
                SendDataTask("PURPLE").execute()
            else if (packageName == "com.android.phone" && notificationText.contains("Incoming call"))
                SendDataTask("CALL").execute()

        }

    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        super.onNotificationRemoved(sbn)
    }
    companion object {
        fun isNotificationServiceEnabled(context: Context): Boolean {
            val cn = ComponentName(context, MyNotificationListener::class.java)
            val flat: String = Settings.Secure.getString(
                context.contentResolver,
                "enabled_notification_listeners"
            )
            return flat != null && flat.contains(cn.flattenToString())
        }
    }
}