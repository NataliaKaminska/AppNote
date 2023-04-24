package com.example.appnote.domain.use_case

import com.example.appnote.domain.model.Note
import com.example.appnote.domain.repository.NoteRepo
import kotlinx.coroutines.flow.Flow

class SearchNotesByTitle(private val repository: NoteRepo) {
    operator fun invoke(title: String): Flow<List<Note>> {
        return repository.searchNotesByTitle(title)
    }
}