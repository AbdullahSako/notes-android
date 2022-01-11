package com.smartu.notes

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Room
import com.smartu.notes.database.Database
import com.smartu.notes.models.Account
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME="Notes-Database"

class Repository private constructor(context:Context){

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