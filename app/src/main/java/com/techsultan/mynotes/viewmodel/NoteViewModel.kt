package com.techsultan.mynotes.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.techsultan.mynotes.models.Note
import com.techsultan.mynotes.repository.NoteRepositoryImpl
import com.techsultan.mynotes.work_manager.NoteWorker
import kotlinx.coroutines.launch
import java.time.Duration
import java.util.concurrent.TimeUnit

class NoteViewModel(
    private val repo: NoteRepositoryImpl
) : ViewModel()  {

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes

    fun addNote(note: Note) = viewModelScope.launch {
        Log.d("Note", "Note saved: $note")
        repo.addNote(note)
    }
    fun updateNote(note: Note) = viewModelScope.launch {
        repo.updateNote(note)
    }
    fun deleteNote(note: Note) = viewModelScope.launch {
        repo.deleteNote(note)
    }
    fun getAllNote() = repo.getAllNotes()

    fun searchNote(query: String?) = repo.searchNote(query)






    /*@RequiresApi(Build.VERSION_CODES.O)
    fun saveNoteInBackground(noteText: String) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            //.setTriggerContentMaxDelay(Duration.ofSeconds(10))
            .build()

        val noteWorkRequest = PeriodicWorkRequestBuilder<NoteWorker>(
            repeatInterval = 10, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setInputData(NoteWorker.createInputData(noteText))
            .build()

        WorkManager.getInstance(app).enqueue(noteWorkRequest)

        WorkManager.getInstance(app).getWorkInfoByIdLiveData(noteWorkRequest.id)
            .observeForever { workInfo ->

                if (workInfo != null) {
                    when (workInfo.state) {
                        //Background task succeeded
                        WorkInfo.State.SUCCEEDED -> {

                        }
                        //Background task failed
                        WorkInfo.State.FAILED -> {

                        }

                        else -> {
                            // Do something
                        }
                    }
                }

            }

    }*/

}