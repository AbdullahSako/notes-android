package com.smartu.notes

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class LoginFragment:Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clickable:TextView=view.findViewById(R.id.clickable_text_create);
        val registerFrag:RegisterFragment= RegisterFragment();
        clickable.setOnClickListener { v->
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container,registerFrag).addToBackStack("tag");
                commit()
            }
        }
    }

}