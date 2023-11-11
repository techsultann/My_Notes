package com.techsultan.mynotes.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.techsultan.mynotes.repository.NoteRepositoryImpl

class NoteViewModelFactory(
    private val repositoryImpl: NoteRepositoryImpl
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(repositoryImpl) as T
    }
}