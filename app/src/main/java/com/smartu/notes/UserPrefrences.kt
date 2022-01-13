package com.smartu.notes

import android.content.Context
import androidx.preference.PreferenceManager
import java.util.*

private const val PREF_USER_ID="userid"
private const val PREF_THEME_POS="themePos"
object UserPrefrences{  // this method of logging in is not secure
//save userid and theme position to savedprefrences
    fun getUserID(context: Context): String{
        val prefs=PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(PREF_USER_ID,"")!!
    }

    fun setUserID(context: Context,userId:UUID){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_USER_ID,userId.toString()).apply()
    }

    fun clearUserID(context: Context){
        PreferenceManager.getDefaultSharedPreferences(context).edit().remove(PREF_USER_ID).apply()
    }

    fun setThemeSpinnerPos(context: Context,pos:Int){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_THEME_POS,pos.toString()).apply()
    }

    fun getThemeSpinnerPos(context: Context): String{
        val prefs=PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(PREF_THEME_POS,"")!!

    }
}