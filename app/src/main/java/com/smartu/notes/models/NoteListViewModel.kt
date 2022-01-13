package com.smartu.notes.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.smartu.notes.Repository
import java.util.*

class NoteListViewModel : ViewModel() {
    private val noteRepository =Repository.get()

    fun getNoteListLiveData(userId:UUID):LiveData<List<Note>>{
        val noteListLiveData=noteRepository.getNotes(userId)
        return noteListLiveData
    }



}