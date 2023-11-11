package com.techsultan.mynotes.repository

import com.techsultan.mynotes.database.NoteDao
import com.techsultan.mynotes.models.Note


class NoteRepositoryImpl(private val dao: NoteDao) {

    suspend fun addNote(note: Note) = dao.addNote(note)
    suspend fun updateNote(note: Note) = dao.updateNote(note)
    suspend fun deleteNote(note: Note) = dao.deleteNote(note)
    fun getAllNotes() = dao.getAllNote()
    fun searchNote(query: String?) = dao.searchNote(query)

}
