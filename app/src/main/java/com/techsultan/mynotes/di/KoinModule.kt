package com.techsultan.mynotes.di

import com.techsultan.mynotes.repository.NoteRepository
import com.techsultan.mynotes.repository.NoteRepositoryImpl
import org.koin.dsl.module

class KoinModule() {

    val myModule = module {

        single<NoteRepository> { NoteRepositoryImpl() }
        factory { KoinModule() }
    }
}