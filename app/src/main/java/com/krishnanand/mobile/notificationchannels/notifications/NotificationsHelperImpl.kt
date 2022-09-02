package com.krishnanand.mobile.notificationchannels.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.krishnanand.mobile.notificationchannels.R
import javax.inject.Inject

class NotificationsHelperImpl @Inject constructor(
    private val notificationManager: NotificationManager?,
    private val context: Context,
    private val contextResources: Resources
): NotificationsHelper {

    override fun createNotificationChannel() {
        // Don't create a channel if already created
        if (notificationManager?.getNotificationChannel(CASH_REGISTER_CHANNEL_ID) != null) {
            return
        }
        val notificationChannel =
            NotificationChannel(
                CASH_REGISTER_CHANNEL_ID, CASH_REGISTER_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                enableLights(true)
                lightColor = Color.BLUE
                enableVibration(false)

                val audioAttribute: AudioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
                val soundUriAsString = ANDR_RES_PREFIX +  contextResources.getResourcePackageName(R.raw.cash_register) + "/" +  contextResources.getResourceTypeName(R.raw.cash_register) + "/" + contextResources.getResourceEntryName(R.raw.cash_register)
                val uri: Uri = Uri.parse(soundUriAsString)
                context.grantUriPermission("com.android.systemui", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)

                setSound(uri, audioAttribute)
            }
        notificationManager?.createNotificationChannel(notificationChannel)
    }

    override fun createAndDispatchNotification() {
        val notificationBuilder = NotificationCompat.Builder(context, CASH_REGISTER_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_stat_sports_soccer)
            setOnlyAlertOnce(true)
            contextResources.openRawResource(R.raw.robert_lewandowski).use {
                val bitmap = BitmapFactory.decodeStream(it)
                setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
                setLargeIcon(bitmap)
            }
            setContentTitle(context.getString(R.string.notification_title))
            setContentText(context.getString(R.string.notification_text))
        }
        notificationManager?.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {
        internal const val CASH_REGISTER_CHANNEL_ID = "cash_register_channel"
        internal const val CASH_REGISTER_CHANNEL_NAME = "Cash Register"
        internal const val ANDR_RES_PREFIX = "android.resource://"
        internal const val IMAGE_NAME = "robert_lewandowski.jpeg"
        internal const val NOTIFICATION_ID = 100
    }
}