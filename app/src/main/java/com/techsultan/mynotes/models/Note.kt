package com.techsultan.mynotes.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val noteTitle: String,
    val note: String,
    val date: String
)
