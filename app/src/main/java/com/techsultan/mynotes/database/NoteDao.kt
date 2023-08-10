package com.techsultan.mynotes.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.techsultan.mynotes.models.Note


@Dao
interface NoteDao {

    @Insert
    fun addNote(vararg note: Note)

    @Delete
    fun deleteNoe(note: Note)

    @Update
    fun updateNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNote(): List<Note>

    @Query("SELECT * FROM notes WHERE noteTitle LIKE note LIKE :query")
    fun searchNote(query : String):List<Note>
}