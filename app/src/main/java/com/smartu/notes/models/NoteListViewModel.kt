package com.smartu.notes.models

import androidx.lifecycle.ViewModel
import java.util.*

class NoteListViewModel : ViewModel() {
    val notes = mutableListOf<Note>()
    init {
        for(i in 0 until 100){
            val note=Note(userId = UUID.randomUUID())
            note.title="note #$i"
            note.noteBody="$i"
            notes+=note
        }
    }
}