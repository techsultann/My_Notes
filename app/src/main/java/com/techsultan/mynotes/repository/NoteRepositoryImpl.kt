package com.techsultan.mynotes.repository

import com.techsultan.mynotes.models.Note


interface NoteRepository {

    fun addNote(noteList : List<Note>)
}
class NoteRepositoryImpl : NoteRepository {

    override fun addNote(noteList: List<Note>) {
        TODO("Not yet implemented")
    }
}
