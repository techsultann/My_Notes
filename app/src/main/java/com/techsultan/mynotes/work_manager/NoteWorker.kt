package com.techsultan.mynotes.work_manager

import android.Manifest
import android.app.Notification.BADGE_ICON_LARGE
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.techsultan.mynotes.R
import kotlinx.coroutines.delay

class NoteWorker(
    context: Context, workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        try {
            showStatusNotification(applicationContext)

            //delay(1000)
            saveNoteToDatabase()
            Log.d("Note Worker", "Note is displaying")
            Toast.makeText(applicationContext, "Note saved successfully", Toast.LENGTH_SHORT).show()
            return Result.success()

        } catch (e: Exception) {
            Log.e("SaveNoteWorker", "Work failed", e)
            return Result.failure()
        }
    }

    private fun saveNoteToDatabase() {}

    private fun showStatusNotification(context: Context) {

        val channelId = "note_saving"
        val note = "Saving note"

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Saving")
            .setContentText(note)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.ic_save)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
            .build()

        val notificationManager = NotificationManagerCompat.from(applicationContext)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(1, notification)
    }

    companion object {
        const val NOTE_TEXT_KEY = "note_text"

        fun createInputData(noteText: String): Data {
            return Data.Builder()
                .putString(NOTE_TEXT_KEY, noteText)
                .build()
        }
    }

}