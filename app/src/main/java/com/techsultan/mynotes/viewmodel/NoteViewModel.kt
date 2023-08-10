package com.techsultan.mynotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.techsultan.mynotes.repository.NoteRepository

class NoteViewModel(
    private val app: Application,
    private val repository: NoteRepository
) : AndroidViewModel(app) {

}