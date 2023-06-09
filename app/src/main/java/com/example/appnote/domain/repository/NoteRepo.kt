package com.example.appnote.domain.repository

import com.example.appnote.domain.model.Note
import kotlinx.coroutines.flow.Flow


interface NoteRepo {
    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
    fun searchNotesByTitle(query: String): Flow<List<Note>>
}