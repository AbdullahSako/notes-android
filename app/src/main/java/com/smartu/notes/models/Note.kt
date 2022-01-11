package com.smartu.notes.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(foreignKeys = [ForeignKey(entity = Account::class, parentColumns = arrayOf("id"),childColumns = arrayOf("userId"),onDelete = ForeignKey.CASCADE)])
data class Note constructor(@PrimaryKey val id:UUID=UUID.randomUUID(),val userId:UUID, var title:String="", var noteBody:String="") {
}
