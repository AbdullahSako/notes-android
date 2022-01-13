package com.smartu.notes.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate

import androidx.fragment.app.Fragment
import com.smartu.notes.NoteApplication.Companion.LANGUAGE_ARABIC
import com.smartu.notes.NoteApplication.Companion.LANGUAGE_ENGLISH
import com.smartu.notes.R
import com.smartu.notes.UserPrefrences
import com.smartu.notes.activities.MainActivity
import com.yariksoffice.lingver.Lingver
import java.util.*


class SettingsFragment:Fragment(R.layout.fragment_settings) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val themeSpinner:Spinner=view.findViewById(R.id.theme_spinner)
        val languageRadioGroup:RadioGroup=view.findViewById(R.id.language_radio_group)
        val logOutBtn:Button=view.findViewById(R.id.log_out_btn)


        //logout button
        logOutBtn.setOnClickListener {
            UserPrefrences.clearUserID(activity as Context)
            startActivity(Intent(context,MainActivity::class.java))
        }

        // Theme change Spinner
        val options= arrayOf("Default","Dark", "Light")
        themeSpinner.adapter=ArrayAdapter(activity as Context, android.R.layout.simple_spinner_dropdown_item, options)
        themeSpinner.setSelection(UserPrefrences.getThemeSpinnerPos(activity as Context).toInt()) //set already selected theme by user on the spinner

        themeSpinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) { //change theme and save theme spinner position to saved preferences
                when(p2){
                    0->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        UserPrefrences.setThemeSpinnerPos(activity as Context,0)
                    }
                    1->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        UserPrefrences.setThemeSpinnerPos(activity as Context,1)
                    }
                    2->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        UserPrefrences.setThemeSpinnerPos(activity as Context,2)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }


        }


        // Language change radio
        checkDefaultLangRadioButton(languageRadioGroup) // check radio button of current application language
        languageRadioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            val radio:RadioButton=view.findViewById(i)

            if(radio.text==resources.getString(R.string.arabic)){
                Lingver.getInstance().setLocale(activity as Context, LANGUAGE_ARABIC) //change app language to arabic
                restart()
            }
            else if(radio.text==resources.getString(R.string.english)){
                Lingver.getInstance().setLocale(activity as Context, LANGUAGE_ENGLISH)//change app language to english
                restart()
            }
        })



        super.onViewCreated(view, savedInstanceState)

    }


    private fun restart(){ //restart app
        val i = Intent(activity, MainActivity::class.java)
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    private fun checkDefaultLangRadioButton(languageRadioGroup:RadioGroup){ // check radio button of current application language
        if(Locale.getDefault().language=="ar"){
            languageRadioGroup.check(R.id.arabic_radio)
        }
        else{
            languageRadioGroup.check(R.id.english_radio)
        }
    }


    companion object{
        fun newInstance():SettingsFragment {
            val args = Bundle()

            val fragment = SettingsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}