package com.smartu.notes

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Room
import com.smartu.notes.database.Database
import com.smartu.notes.models.Account
import com.smartu.notes.models.Note
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME="Notes-Database"

class Repository private constructor(context:Context){ //access db through this class

    private val database:Database= Room.databaseBuilder(context.applicationContext,Database::class.java,DATABASE_NAME).build()
    private val notesDao=database.Dao()
    private val executor=Executors.newSingleThreadExecutor()

    fun getUser(id:UUID):LiveData<Account?> =notesDao.getUser(id)
    fun getUser(email:String):LiveData<Account?> =notesDao.getUser(email)

    fun addUser(account:Account){
        executor.execute {
            notesDao.addUser(account)
        }
    }

    fun getNotes(userId:UUID):LiveData<List<Note>> =notesDao.getNotes(userId)
    fun getNote(noteID:UUID):LiveData<Note?> =notesDao.getNote(noteID)

    fun addNote(note: Note){
        executor.execute {
            notesDao.addNote(note)
        }
    }

    fun updateNote(note: Note){
        executor.execute {
            notesDao.updateNote(note)
        }
    }

    fun deleteNote(note: Note){
        executor.execute {
            notesDao.deleteNote(note)
        }
    }


    companion object{
        private var INSTANCE:Repository?=null
        fun initialize(context: Context){
            if(INSTANCE==null){
                INSTANCE=Repository(context)
            }
        }

        fun get():Repository{
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized");
        }

    }



}