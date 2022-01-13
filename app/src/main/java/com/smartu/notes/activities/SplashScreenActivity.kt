package com.smartu.notes.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.smartu.notes.R
import com.smartu.notes.UserPrefrences
import java.util.*
import kotlin.concurrent.schedule

class SplashScreenActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide() // hide action bar

        if(UserPrefrences.getThemeSpinnerPos(this).isEmpty()){ //if theme saved preference is not found add default theme spinner pos(0) for theme spinner in settings
            UserPrefrences.setThemeSpinnerPos(this,0)
        }
        else{ //if theme saved preference is set change theme
            when(UserPrefrences.getThemeSpinnerPos(this).toInt()){
                0->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)


                }
                1->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                }
                2->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                }
            }
        }

        val intent:Intent
        if(UserPrefrences.getUserID(this).isEmpty()){ // if user id is not saved in preference go to login activity after about a second

            intent=Intent(this,MainActivity::class.java)
            Timer("settingUp",false).schedule(1200){
                startActivity(intent)
            }

        }
        else{// if user id is saved in preference go to notes activity after about a second
            intent=NotesActivity.newIntent(this, UUID.fromString(UserPrefrences.getUserID(this))) //send user id with intent
            Timer("settingUp",false).schedule(1200){
                startActivity(intent)
            }

        }

    }
}