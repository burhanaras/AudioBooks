package com.burhan.audiobooksapp.presentation.ui.player.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.support.v4.media.session.PlaybackStateCompat.ACTION_STOP
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.media.session.MediaButtonReceiver
import com.bumptech.glide.Glide
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.presentation.ui.player.service.PlayerService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Developed by tcbaras on 2019-11-05.
 */
class NotificationBuilder(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val stopPendingIntent =
        MediaButtonReceiver.buildMediaButtonPendingIntent(context, ACTION_STOP)

    private val mediaStyle = androidx.media.app.NotificationCompat.MediaStyle()
        .setShowActionsInCompactView(0, 1)
        .setCancelButtonIntent(stopPendingIntent)
        .setShowCancelButton(true)

    fun buildMediaNotification(
        audioBook: AudioBook,
        isPlaying: Boolean = true,
        callback: (notification: Notification) -> Unit
    ) {
        if (shouldCreateNotificationChannel()) {
            createNotificationChannel()
        }

        GlobalScope.launch(Dispatchers.IO) {
            val futureTarget = Glide.with(context).asBitmap().load(audioBook.imageUrl).submit()
            val largeIconBitmap = futureTarget.get()
            Glide.with(context).clear(futureTarget)

            launch(Dispatchers.Main) {

                val notification =
                    NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID).apply {
                        setContentTitle(audioBook.name)
                        setContentText(audioBook.author)
                        setSmallIcon(R.drawable.ic_launcher_foreground)
                        setLargeIcon(largeIconBitmap)
                        setColorized(true)
                        color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
                        setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        setStyle(mediaStyle)
                        setOnlyAlertOnce(true)
                        addAction(getTimeShiftBackward10SecsAction(audioBook))
                        addAction(
                            if (isPlaying) getPauseAction(audioBook)
                            else getPlayAction(audioBook)
                        )
                        setDeleteIntent(stopPendingIntent)
                    }.build()

                callback(notification)
            }
        }
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

    private fun getPlayAction(audioBook: AudioBook): NotificationCompat.Action {
        return NotificationCompat.Action(
            R.drawable.ic_play,
            "Play",
            PendingIntent.getService(
                context, 10,
                PlayerService.newIntentForTogglePlayPause(context, audioBook),
                0
            )
        )
    }

    private fun getTimeShiftBackward10SecsAction(audioBook: AudioBook): NotificationCompat.Action {
        return NotificationCompat.Action(
            R.drawable.ic_replay_10,
            "Rewind 10 sc.",
            PendingIntent.getService(
                context,
                11,
                PlayerService.newIntentForTimeShiftWithAmount(context, audioBook, -10),
                0
            )
        )
    }

    private fun getPauseAction(audioBook: AudioBook): NotificationCompat.Action {
        return NotificationCompat.Action(
            R.drawable.ic_pause,
            "Pause",
            PendingIntent.getService(
                context, 12,
                PlayerService.newIntentForTogglePlayPause(context, audioBook),
                0
            )
        )
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "com.burhan.audiobooks.notification.id"
        const val NOTIFICATION_CHANNEL_NAME = "Audio Books"
    }
}