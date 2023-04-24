package com.example.appnote.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appnote.domain.model.Note
import com.example.appnote.domain.use_case.NoteUseCases
import com.example.appnote.domain.util.NoteOrder
import com.example.appnote.domain.util.OrderType

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder!!.javaClass == event.noteOrder.javaClass &&
                    state.value.noteOrder!!.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNotes(event.noteOrder)
            }

            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }

            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }

            is NotesEvent.SearchNotes -> {
                getNotesJob?.cancel()
                getNotesJob = noteUseCases.searchNotesByTitle(event.query)
                    .onEach { notes ->
                        _state.value = state.value.copy(
                            notes = notes,
                            noteOrder = null
                        )
                    }
                    .launchIn(viewModelScope)
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                val filteredNotes = if (_searchQuery.value.isEmpty()) {
                    notes
                } else {
                    notes.filter { it.title.contains(_searchQuery.value, ignoreCase = true) }
                }
                _state.value = state.value.copy(
                    notes = filteredNotes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        state.value.noteOrder?.let { getNotes(it) }
    }
}
//    private fun getNotes(noteOrder: NoteOrder) {
//        getNotesJob?.cancel()
//        getNotesJob = noteUseCases.getNotes(noteOrder)
//            .onEach { notes ->
//                _state.value = state.value.copy(
//                    notes = notes,
//                    noteOrder = noteOrder
//                )
//            }
//            .launchIn(viewModelScope)
//    }
//}

//@HiltViewModel
//class NotesViewModel @Inject constructor(
//    private val noteUseCases: NoteUseCases
//) : ViewModel() {
//
//    private val _state = mutableStateOf(NotesState())
//    val state: State<NotesState> = _state
//
//    private var recentlyDeletedNote: Note? = null
//
//    private var getNotesJob: Job? = null
//
//    init {
//        getNotes(NoteOrder.Date(OrderType.Descending))
//    }
//
//    fun onEvent(event: NotesEvent) {
//        when (event) {
//            is NotesEvent.Order -> {
//                if (state.value.noteOrder::class == event.noteOrder::class &&
//                    state.value.noteOrder.orderType == event.noteOrder.orderType
//                ) {
//                    return
//                }
//                getNotes(event.noteOrder)
//            }
//            is NotesEvent.DeleteNote -> {
//                viewModelScope.launch {
//                    noteUseCases.deleteNote(event.note)
//                    recentlyDeletedNote = event.note
//                }
//            }
//            is NotesEvent.RestoreNote -> {
//                viewModelScope.launch {
//                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
//                    recentlyDeletedNote = null
//                }
//            }
//            is NotesEvent.ToggleOrderSection -> {
//                _state.value = state.value.copy(
//                    isOrderSectionVisible = !state.value.isOrderSectionVisible
//                )
//            }
//        }
//    }
//
//    private fun getNotes(noteOrder: NoteOrder) {
//        getNotesJob?.cancel()
//        getNotesJob = noteUseCases.getNotes(noteOrder)
//            .onEach { notes ->
//                _state.value = state.value.copy(
//                    notes = notes,
//                    noteOrder = noteOrder
//                )
//            }
//            .launchIn(viewModelScope)
//    }
//}