package com.smartu.notes.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smartu.notes.R
import com.smartu.notes.fragments.NotesFragment
import java.util.*

const val EXTRA_TAG="com.smartu.notes.EXTRAUUID"

class NotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userID=intent.getSerializableExtra(EXTRA_TAG) as UUID
        val notesFragment=NotesFragment.newInstance(userID)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,notesFragment)
            commit()
        }
    }

    companion object{
        fun newIntent(context:Context?,uuid: UUID):Intent{
            val intent=Intent(context,NotesActivity::class.java)
            intent.putExtra(EXTRA_TAG,uuid)
            return intent
        }
    }
}