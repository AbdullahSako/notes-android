package com.smartu.notes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smartu.notes.models.Account
import com.smartu.notes.models.Note

@Database(entities = arrayOf(Account::class, Note::class),version = 2) //specify to room which model class to use
@TypeConverters(TypeConverter::class)   //specify type converter to room
abstract class Database:RoomDatabase() {
    abstract fun Dao():Dao
}