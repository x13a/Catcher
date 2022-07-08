package me.lucky.catcher

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationManager(private val ctx: Context) {
    companion object {
        private const val CHANNEL_ID = "alert"
        private const val GROUP_KEY = "alert"
        private const val NOTIFICATION_ID = 1000
    }

    private val manager = NotificationManagerCompat.from(ctx)

    fun createNotificationChannels() =
        manager.createNotificationChannel(
            NotificationChannelCompat.Builder(
                CHANNEL_ID,
                NotificationManagerCompat.IMPORTANCE_DEFAULT,
            ).setName(ctx.getString(R.string.notification_channel)).build())

    fun notify(packageName: String) =
        manager.notify(
            NOTIFICATION_ID,
            NotificationCompat.Builder(ctx, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(ctx.getString(R.string.notification_title))
                .setContentText(formatText(packageName))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_STATUS)
                .setShowWhen(true)
                .setAutoCancel(true)
                .setGroup(GROUP_KEY)
                .setGroupSummary(true)
                .build(),
        )

    private fun formatText(packageName: String): String {
        var app: CharSequence = ctx.getString(R.string.unknown_app)
        try {
            app = ctx.packageManager
                .getApplicationLabel(ctx.packageManager.getApplicationInfo(packageName, 0))
        } catch (exc: PackageManager.NameNotFoundException) {}
        return ctx.getString(R.string.notification_text, app.toString(), packageName)
    }
}