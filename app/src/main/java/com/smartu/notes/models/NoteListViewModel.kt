package com.smartu.notes.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.smartu.notes.Repository
import java.util.*

class NoteListViewModel : ViewModel() { // used by notes list fragment to access list data
    private val noteRepository =Repository.get()

    fun getNoteListLiveData(userId:UUID):LiveData<List<Note>>{
        val noteListLiveData=noteRepository.getNotes(userId)
        return noteListLiveData
    }

    fun deleteNote(note:Note){
        noteRepository.deleteNote(note)
    }



}