package com.example.appnote.domain.use_case

import com.example.appnote.domain.model.Note
import com.example.appnote.domain.repository.NoteRepo


class GetNote(
    private val repository: NoteRepo
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}