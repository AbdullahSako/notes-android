package com.smartu.notes

import android.app.Application

class NoteApplication : Application() { //application inheritance allows access to application on create
    override fun onCreate() {
        super.onCreate()
        Repository.initialize(this)
    }
}