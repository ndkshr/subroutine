package me.ndkshr.subroutine.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.PendingIntent.getActivity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import me.ndkshr.subroutine.R
import me.ndkshr.subroutine.modal.HabitDataItem
import me.ndkshr.subroutine.view.MainActivity

const val HABIT_ITEM = "habit_item"
const val NOTIFICATION_ID = 100
const val NOTIFICATION_CHANNEL = "subroutine_notification"
class AlarmReceiver : BroadcastReceiver() {
    private var notificationManager: NotificationManagerCompat? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        val habitItem = intent?.getSerializableExtra(HABIT_ITEM) as HabitDataItem
        // tapResultIntent gets executed when user taps the notification
        val tapResultIntent = Intent(context, MainActivity::class.java)
        tapResultIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent: PendingIntent =
            getActivity(context, 0, tapResultIntent, FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE)

        val notification = context?.let {
            NotificationCompat.Builder(it, "to_do_list")
                .setChannelId(NOTIFICATION_CHANNEL)
                .setContentTitle("Habit Reminder")
                .setContentText(habitItem.habitName)
                .setSmallIcon(R.drawable.subroutine)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()
        }
        notificationManager = context?.let { NotificationManagerCompat.from(it) }
        notificationManager?.createNotificationChannel(NotificationChannel(
            NOTIFICATION_CHANNEL, "Habit Reminder", IMPORTANCE_HIGH
        ))
        notification?.let {
            habitItem.let { it1 ->
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                    return
                }
                notificationManager?.notify(NOTIFICATION_ID, it)
            }
        }
    }
}