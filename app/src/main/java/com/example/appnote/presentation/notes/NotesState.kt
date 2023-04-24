package com.example.appnote.presentation.notes

import com.example.appnote.domain.model.Note
import com.example.appnote.domain.util.NoteOrder
import com.example.appnote.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
