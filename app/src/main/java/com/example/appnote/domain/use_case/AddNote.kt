package com.example.appnote.domain.use_case

import com.example.appnote.domain.model.InvalidNoteException
import com.example.appnote.domain.model.Note
import com.example.appnote.domain.repository.NoteRepo


class AddNote (
    private val repository: NoteRepo
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("The title can't be empty.")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("The content can't be empty.")
        }
        repository.insertNote(note)
    }
}