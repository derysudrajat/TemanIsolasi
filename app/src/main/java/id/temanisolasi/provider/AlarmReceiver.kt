package id.temanisolasi.provider

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.temanisolasi.R
import id.temanisolasi.data.model.Isolation
import id.temanisolasi.data.model.Report
import id.temanisolasi.ui.base.MainActivity
import id.temanisolasi.ui.finishisolation.FinishIsolationActivity
import id.temanisolasi.utils.COLLECTION
import id.temanisolasi.utils.DataHelpers
import id.temanisolasi.utils.Helpers.dayFrom
import id.temanisolasi.utils.Helpers.isDarkMode
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        intent.extras?.getInt(EXTRA_NOTIFICATION_ID)?.let { notificationId ->
            var isIsolationFinish: Boolean
            if (notificationId == NOTIFICATION_ID.MORNING) getActiveIsolation {
                val dayDiff = it.startIsolation?.dayFrom(Timestamp.now())
                val isNewDay = dayDiff?.toInt() ?: 1 > it.passedDay ?: 1
                if (isNewDay) postNewReport(it.id, dayDiff)
                isIsolationFinish = (isNewDay && it.passedDay == 14)
                showAlarmNotification(context, notificationId, isIsolationFinish, it.id)
            } else {
                isIsolationFinish = false
                showAlarmNotification(context, notificationId, isIsolationFinish)
            }
        }
    }

    private fun showAlarmNotification(
        context: Context,
        notificationId: Int,
        isIsolationFinish: Boolean,
        id: String? = null
    ) {
        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val intent = if (isIsolationFinish) Intent(context, FinishIsolationActivity::class.java)
            .apply { putExtra(FinishIsolationActivity.EXTRA_ISOLATION, id) }
        else Intent(context, MainActivity::class.java)

        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

        val pendingIntent = PendingIntent.getActivity(
            context, NOTIFICATION_REQUEST_CODE,
            intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val message = getMessage(notificationId, isIsolationFinish)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo_notification)
            .setContentTitle(getNotificationTitle(isIsolationFinish))
            .setAutoCancel(true)
            .setColor(
                ContextCompat.getColor(
                    context,
                    if ((context as Activity).isDarkMode()) R.color.white else R.color.primary
                )
            )
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(CHANNEL_ID)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManagerCompat.notify(notificationId, notification)
    }

    private fun getMessage(notificationId: Int, isolationFinish: Boolean): String =
        if (isolationFinish) "Hebat, kamu udah berjuang selama ini, Akhirnya bisa bertemu orang tersayang, tapi tetap patuhi protokol kesahatan, ya"
        else DataHelpers.notificationMessage[notificationId - 101]

    fun setAlarm(context: Context, notificationId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_NOTIFICATION_ID, notificationId)

        val calendar = Calendar.getInstance().apply {
            set(
                Calendar.HOUR_OF_DAY, when (notificationId) {
                    NOTIFICATION_ID.MORNING -> 7
                    NOTIFICATION_ID.NOON -> 13
                    NOTIFICATION_ID.NIGHT -> 19
                    else -> 7
                }
            )
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )
    }

    private fun getActiveIsolation(onSuccess: (Isolation) -> Unit) {
        val id = Firebase.auth.currentUser?.uid ?: ""
        Firebase.firestore.collection(COLLECTION.ISOLATION)
            .whereEqualTo("userId", id)
            .whereEqualTo("active", true)
            .get()
            .addOnSuccessListener {
                it.toObjects(Isolation::class.java).first()
                    ?.let { data -> onSuccess(data) }
            }
    }

    private fun postNewReport(id: String?, dayDiff: Long?) {
        val docId = id ?: ""
        val newPassedDay = (dayDiff ?: 1).toInt()
        Firebase.firestore.collection(COLLECTION.ISOLATION).document(docId).update(
            mapOf(
                "listReport" to FieldValue.arrayUnion(Report()),
                "passedDay" to newPassedDay
            )
        )
            .addOnSuccessListener { Log.d("TAG", "postNewReport: success") }
            .addOnFailureListener { Log.d("TAG", "postNewReport: failed = ${it.message}") }
    }

    fun cancelAlarm(context: Context, notificationId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
    }

    fun isAlarmSet(context: Context, notificationId: Int): Boolean {
        val intent = Intent(context, AlarmReceiver::class.java)

        return PendingIntent.getBroadcast(
            context, notificationId, intent, PendingIntent.FLAG_NO_CREATE
        ) != null
    }

    private fun getNotificationTitle(isIsolationFinish: Boolean): String =
        if (isIsolationFinish) "Isolasi Selesai"
        else "Penginat Isolasi"

    companion object {
        const val NOTIFICATION_REQUEST_CODE = 102
        const val EXTRA_NOTIFICATION_ID = "extra_notification_id"
        const val CHANNEL_ID = "Pengingat"
        const val CHANNEL_NAME = "Mulai Isolasi"

        object NOTIFICATION_ID {
            const val MORNING = 101
            const val NOON = 102
            const val NIGHT = 103
        }
    }
}