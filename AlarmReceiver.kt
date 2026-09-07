package br.com.eleicoes.contagem

import android.app.*
import android.content.*
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(c: Context, i: Intent?) {
        NotificationHelper.createChannel(c)
        val n = Notification.Builder(c, NotificationHelper.CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Eleições 2026")
            .setContentText("São 08:00 — confira a contagem regressiva!")
            .setPriority(Notification.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(0, 500, 250, 500, 250, 800))
            .build()
        c.getSystemService(NotificationManager::class.java).notify(2026, n)
        AlarmScheduler.schedule(c)
    }
}

object NotificationHelper {
    const val CHANNEL_ID = "eleicoes_alarm"

    fun createChannel(c: Context) {
        if (Build.VERSION.SDK_INT >= 26) {
            val sound = Uri.parse("android.resource://${c.packageName}/${R.raw.bell}")
            val attrs = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            val ch = NotificationChannel(CHANNEL_ID, "Alarme das Eleições", NotificationManager.IMPORTANCE_HIGH)
            ch.enableVibration(true)
            ch.setSound(sound, attrs)
            c.getSystemService(NotificationManager::class.java).createNotificationChannel(ch)
        }
    }
}
