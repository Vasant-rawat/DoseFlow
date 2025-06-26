package com.vasant.pillpal.ui.components

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.vasant.pillpal.data.db.Medicine
import com.vasant.pillpal.ui.ReminderReceiver

const val REMINDER = "REMINDER"
fun setUpAlarm(context: Context, medicine: Medicine) {
    val intent = Intent(context, ReminderReceiver::class.java).apply {
        putExtra(REMINDER, Gson().toJson(medicine))
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context, medicine.time.toInt(),
        intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    try {
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, medicine.time, pendingIntent)
    } catch (e: SecurityException) {
        Log.d("Alarm", "setUpAlarm: ")
    }
}


fun cancelAlarm(context: Context, medicine: Medicine) {
    val intent = Intent(context, ReminderReceiver::class.java).apply {
        putExtra(REMINDER, Gson().toJson(medicine))
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        medicine.time.toInt(),
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    try {
        alarmManager.cancel(pendingIntent)
    } catch (e: SecurityException) {
        Log.d("Alarm", "cancelAlarm: ${e.message}")
    }

}