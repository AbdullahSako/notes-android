package com.smartu.notes

import android.app.Application
import com.yariksoffice.lingver.Lingver
import com.yariksoffice.lingver.store.PreferenceLocaleStore
import java.util.*

class NoteApplication : Application() { //application inheritance allows access to application on create
    override fun onCreate() {
        super.onCreate()
        //create db instance
        Repository.initialize(this)

        //set default language
        val store = PreferenceLocaleStore(this, Locale(LANGUAGE_ENGLISH))
        Lingver.init(this,store)
    }

    companion object {
        const val LANGUAGE_ENGLISH = "en"
        const val LANGUAGE_ENGLISH_COUNTRY = "US"
        const val LANGUAGE_ARABIC = "ar"
    }
}