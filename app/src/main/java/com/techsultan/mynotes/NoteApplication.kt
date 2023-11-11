package com.techsultan.mynotes

import android.app.Application
import com.techsultan.mynotes.database.NoteDatabase
import com.techsultan.mynotes.repository.NoteRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NoteApplication : Application() {

    val database by lazy { NoteDatabase.createDatabase(this) }

    val repository by lazy { NoteRepositoryImpl(database.noteDao())}

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NoteApplication)
            modules()
        }
    }
}