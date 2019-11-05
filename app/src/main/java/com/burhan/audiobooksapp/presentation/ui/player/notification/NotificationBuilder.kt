package com.burhan.audiobooksapp.presentation.ui.player.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.media.session.PlaybackStateCompat.ACTION_PLAY_PAUSE
import android.support.v4.media.session.PlaybackStateCompat.ACTION_STOP
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.media.session.MediaButtonReceiver
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.presentation.ui.player.service.PlayerService

/**
 * Developed by tcbaras on 2019-11-05.
 */
class NotificationBuilder(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val playPauseAction = NotificationCompat.Action(
        R.drawable.ic_home_black_24dp,
        "Play/Pause",
        MediaButtonReceiver.buildMediaButtonPendingIntent(context, ACTION_PLAY_PAUSE)
    )

    private val stopPendingIntent =
        MediaButtonReceiver.buildMediaButtonPendingIntent(context, ACTION_STOP)

    private val mediaStyle = androidx.media.app.NotificationCompat.MediaStyle()
        .setShowActionsInCompactView(0)
        .setCancelButtonIntent(stopPendingIntent)
        .setShowCancelButton(true)

    fun buildMediaNotification(audioBook: AudioBook): Notification? {
        if (shouldCreateNotificationChannel()) {
            createNotificationChannel()
        }

        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID).apply {
            setContentTitle(audioBook.name)
            setContentText(audioBook.description)
            setSmallIcon(R.drawable.ic_launcher_foreground)
            color = Color.GREEN
            setStyle(mediaStyle)
            addAction(playPauseAction)
            setDeleteIntent(stopPendingIntent)
        }.build()
    }

    private fun shouldCreateNotificationChannel() =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationChannelDoesNotExists()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun notificationChannelDoesNotExists() =
        notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_NAME) == null


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) ?: run {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = context.getString(R.string.notification_channel_description)
                setSound(null, null)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "com.burhan.audiobooks.notification.id"
        const val NOTIFICATION_CHANNEL_NAME = "Audio Books"
    }
}