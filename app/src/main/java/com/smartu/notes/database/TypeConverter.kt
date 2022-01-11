package com.smartu.notes.database

import androidx.room.TypeConverter
import java.util.*

class TypeConverter {   //converts data types that ROOM cannot deal with to and from string
    @TypeConverter
    fun fromUUID(uuid:UUID?):String?{
        return uuid?.toString();
    }

    @TypeConverter
    fun toUUID(uuid : String?):UUID?{
        return UUID.fromString(uuid);
    }
}