package com.smartu.notes.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Account(@PrimaryKey val id:UUID=UUID.randomUUID(), var email:String="", var password:String="") {

}

