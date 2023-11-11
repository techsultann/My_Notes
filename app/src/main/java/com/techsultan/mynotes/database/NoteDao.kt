package com.techsultan.mynotes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.techsultan.mynotes.models.Note


@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNote(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE noteTitle LIKE note LIKE :query")
    fun searchNote(query : String?):List<Note>
}