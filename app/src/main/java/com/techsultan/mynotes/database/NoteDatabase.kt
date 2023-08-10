package com.techsultan.mynotes.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.techsultan.mynotes.models.Note


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao() : NoteDao

    @Volatile
    private var INSTANCE: NoteDatabase? = null

    fun createDatabase(context: Context) : NoteDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}