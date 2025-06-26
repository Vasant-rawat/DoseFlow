package com.vasant.pillpal.ui

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.vasant.pillpal.MEDICINE_CHANNEL_ID
import com.vasant.pillpal.R
import com.vasant.pillpal.data.db.Medicine
import com.vasant.pillpal.repository.MedicineRepo
import com.vasant.pillpal.ui.components.REMINDER
import com.vasant.pillpal.ui.components.cancelAlarm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

const val DONE_ACTION = "DONE_ACTION"
const val REJECT_ACTION = "REJECT_ACTION"

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {
    @Inject
    lateinit var updateMedicine: MedicineRepo
    private lateinit var mediaPlayer: MediaPlayer
    override fun onReceive(context: Context, intent: Intent) {

        mediaPlayer = MediaPlayer.create(context, R.raw.alarm)


        val reminderJson = intent.getStringExtra(REMINDER)
        val reminder = Gson().fromJson(reminderJson, Medicine::class.java)

        val doneIntent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(REMINDER, reminderJson)
            action = DONE_ACTION
        }
        val donependingIntent = PendingIntent.getBroadcast(
            context, reminder.time.toInt(), doneIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val closeIntent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(REMINDER, reminderJson)
            action = REJECT_ACTION
        }
        val closePendingIntent = PendingIntent.getBroadcast(
            context, reminder.time.toInt(), closeIntent, PendingIntent.FLAG_IMMUTABLE
        )

        when (intent.action) {
            DONE_ACTION -> {
                runBlocking { updateMedicine.updateMedicine(reminder.copy(isCompleted = true)) }
                cancelAlarm(context, reminder)
            }

            REJECT_ACTION -> {
                runBlocking { updateMedicine.updateMedicine(reminder.copy(isCompleted = true)) }
                cancelAlarm(context, reminder)
            }

            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            POST_NOTIFICATIONS
                        ) != android.content.pm.PackageManager.PERMISSION_GRANTED
                    ) {
                        val notification =
                            NotificationCompat.Builder(context, MEDICINE_CHANNEL_ID)
                                .setSmallIcon(R.drawable.framemedicine)
                                .setContentTitle("Medicine Reminder")
                                .setContentText(reminder.medName.plus(" is due now!"))
                                .addAction(
                                    R.drawable.potion,
                                    "Take ${reminder.dosage}",
                                    donependingIntent
                                )
                                .addAction(R.drawable.arrow, "Reject", closePendingIntent)
                                .build()
                        NotificationManagerCompat.from(context).notify(1, notification)
                    } else {
                        val notification =
                            NotificationCompat.Builder(context, MEDICINE_CHANNEL_ID)
                                .setSmallIcon(R.drawable.framemedicine)
                                .setContentTitle("Medicine Reminder")
                                .setContentText(reminder.medName.plus(" is due now!"))
                                .addAction(
                                    R.drawable.potion,
                                    "Take ${reminder.dosage}",
                                    donependingIntent
                                )
                                .addAction(R.drawable.arrow, "Reject", closePendingIntent)
                                .build()
                        NotificationManagerCompat.from(context).notify(1, notification)

                    }
                    mediaPlayer.setOnCompletionListener {
                        mediaPlayer.release()
                    }
                    mediaPlayer.start()
                }

            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    POST_NOTIFICATIONS
                ) != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                val notification =
                    NotificationCompat.Builder(context, MEDICINE_CHANNEL_ID)
                        .setSmallIcon(R.drawable.framemedicine)
                        .setContentTitle("Medicine Reminder")
                        .setContentText(reminder.medName.plus(" is due now!"))
                        .addAction(R.drawable.potion, "Take ${reminder.dosage}", donependingIntent)
                        .addAction(R.drawable.arrow, "Reject", closePendingIntent)
                        .build()
                NotificationManagerCompat.from(context).notify(1, notification)
            } else {
                val notification =
                    NotificationCompat.Builder(context, MEDICINE_CHANNEL_ID)
                        .setSmallIcon(R.drawable.framemedicine)
                        .setContentTitle("Medicine Reminder")
                        .setContentText(reminder.medName.plus(" is due now!"))
                        .addAction(R.drawable.potion, "Take ${reminder.dosage}", donependingIntent)
                        .addAction(R.drawable.arrow, "Reject", closePendingIntent)
                        .build()
                NotificationManagerCompat.from(context).notify(1, notification)

            }
        }
    }
}