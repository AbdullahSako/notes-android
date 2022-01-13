package com.smartu.notes.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smartu.notes.R
import com.smartu.notes.UserPrefrences
import com.smartu.notes.fragments.NotesFragment
import com.smartu.notes.fragments.SettingsFragment
import java.util.*

const val EXTRA_TAG="com.smartu.notes.EXTRAUUID"

class NotesActivity : AppCompatActivity() {
    private var quitOnBackPress:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)


        val userID=intent.getSerializableExtra(EXTRA_TAG) as UUID //get user id sent from splash screen activity or login activity with intent
        val notesFragment=NotesFragment.newInstance(userID) //send user id to notes fragment
        val bottomNav:BottomNavigationView=findViewById(R.id.bottomNavigationView)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,notesFragment)
            commit()
        }

        bottomNav.setOnItemSelectedListener { //bottom navigation bar items
            when(it.itemId){
                R.id.home_btn->{
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragment_container,notesFragment)
                        commit()
                    }
                    true
                }
                R.id.settings_btn->{
                    val settingsFragment=SettingsFragment.newInstance()
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragment_container,settingsFragment)
                        commit()
                    }
                    true
                }
                else->true

            }
        }



    }


    override fun onBackPressed() { // click twice to close application
        if(quitOnBackPress==true){
            ActivityCompat.finishAffinity(this) // close application
        }
        else{
            Toast.makeText(this,R.string.back_press_check,Toast.LENGTH_SHORT).show()
            quitOnBackPress=true
        }
    }

    companion object{
        fun newIntent(context:Context?,userID:UUID):Intent{
            val intent=Intent(context,NotesActivity::class.java)
            intent.putExtra(EXTRA_TAG,userID)
            return intent
        }
    }
}