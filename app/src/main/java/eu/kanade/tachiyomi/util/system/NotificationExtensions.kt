package eu.kanade.tachiyomi.util.system

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationChannelGroupCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.getSystemService
import eu.kanade.tachiyomi.R

val Context.notificationManager: NotificationManager
    get() = getSystemService()!!

fun Context.notify(id: Int, channelId: String, block: (NotificationCompat.Builder.() -> Unit)? = null) {
    if (PermissionChecker.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PermissionChecker.PERMISSION_GRANTED) {
        return
    }

    val notification = notificationBuilder(channelId, block).build()
    NotificationManagerCompat.from(this).notify(id, notification)
}

/**
 * Helper method to create a notification builder.
 *
 * @param id the channel id.
 * @param block the function that will execute inside the builder.
 * @return a notification to be displayed or updated.
 */
fun Context.notificationBuilder(channelId: String, block: (NotificationCompat.Builder.() -> Unit)? = null): NotificationCompat.Builder {
    val builder = NotificationCompat.Builder(this, channelId)
        .setColor(getColor(R.color.accent_blue))
    if (block != null) {
        builder.block()
    }
    return builder
}

/**
 * Helper method to build a notification channel group.
 *
 * @param channelId the channel id.
 * @param block the function that will execute inside the builder.
 * @return a notification channel group to be displayed or updated.
 */
fun buildNotificationChannelGroup(
    channelId: String,
    block: (NotificationChannelGroupCompat.Builder.() -> Unit),
): NotificationChannelGroupCompat {
    val builder = NotificationChannelGroupCompat.Builder(channelId)
    builder.block()
    return builder.build()
}

/**
 * Helper method to build a notification channel.
 *
 * @param channelId the channel id.
 * @param channelImportance the channel importance.
 * @param block the function that will execute inside the builder.
 * @return a notification channel to be displayed or updated.
 */
fun buildNotificationChannel(
    channelId: String,
    channelImportance: Int,
    block: (NotificationChannelCompat.Builder.() -> Unit),
): NotificationChannelCompat {
    val builder = NotificationChannelCompat.Builder(channelId, channelImportance)
    builder.block()
    return builder.build()
}
