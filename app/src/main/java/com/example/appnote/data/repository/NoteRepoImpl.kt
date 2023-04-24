package com.example.appnote.data.repository


import com.example.appnote.data.data_source.NoteDao
import com.example.appnote.domain.model.Note
import com.example.appnote.domain.repository.NoteRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepoImpl (
private val dao: NoteDao
) : NoteRepo {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override fun searchNotesByTitle(title: String): Flow<List<Note>> {
        return dao.getNotes().map { notes ->
            notes.filter { it.title.contains(title, ignoreCase = true) }
        }
    }
//    override fun searchNotesByTitle(query: String): Flow<List<Note>> {
//        return dao.searchNotesByTitle(query)
//    }
}