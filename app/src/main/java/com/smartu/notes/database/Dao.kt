package com.smartu.notes.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.smartu.notes.models.Account
import com.smartu.notes.models.Note
import java.util.*

@Dao //(DATA ACCESS OBJECT)
interface Dao { // an interface that specifies to room library the operations used
    @Query("SELECT * FROM account WHERE id=(:id)") //get user info based on his id
    fun getUser(id: UUID): LiveData<Account?>

    @Query("SELECT * from account where email=(:email)") //get user info based on email(for logging in)
    fun getUser(email:String):LiveData<Account?>

    @Query("Select * from note where userId=(:userId)")
    fun getNotes(userId:UUID):LiveData<List<Note>>

    @Query("Select * from note where id=(:noteID)")
    fun getNote(noteID:UUID):LiveData<Note?>

    @Insert
    fun addUser(account: Account)

    @Insert
    fun addNote(note:Note)

    @Update
    fun updateNote(note:Note)

    @Delete
    fun deleteNote(note:Note)


}