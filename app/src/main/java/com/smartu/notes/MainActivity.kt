package com.smartu.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginFrag:LoginFragment= LoginFragment()
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