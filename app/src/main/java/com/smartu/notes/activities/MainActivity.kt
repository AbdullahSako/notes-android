package com.smartu.notes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.smartu.notes.R
import com.smartu.notes.fragments.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginFrag: LoginFragment = LoginFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,loginFrag)
            commit()
        }

    }

    override fun onBackPressed() {
        //allow to return to login fragment from register fragment on back press
        supportFragmentManager.popBackStack();
    }
}