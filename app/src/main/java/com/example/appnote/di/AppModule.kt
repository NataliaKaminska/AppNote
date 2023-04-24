package com.example.appnote.di

import android.app.Application
import androidx.room.Room
import com.example.appnote.data.data_source.NoteDatabase
import com.example.appnote.data.repository.NoteRepoImpl
import com.example.appnote.domain.repository.NoteRepo
import com.example.appnote.domain.use_case.AddNote
import com.example.appnote.domain.use_case.DeleteNote
import com.example.appnote.domain.use_case.GetNote
import com.example.appnote.domain.use_case.GetNotes
import com.example.appnote.domain.use_case.NoteUseCases

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepo {
        return NoteRepoImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepo): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}