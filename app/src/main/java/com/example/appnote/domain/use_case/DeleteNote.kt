package com.example.appnote.domain.use_case

import com.example.appnote.domain.model.Note
import com.example.appnote.domain.repository.NoteRepo


class DeleteNote (
    private val repository: NoteRepo
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}