package com.smartu.notes.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.smartu.notes.R
import com.smartu.notes.Repository
import com.smartu.notes.UserPrefrences
import com.smartu.notes.activities.NotesActivity
import org.mindrot.jbcrypt.BCrypt

class LoginFragment:Fragment(R.layout.fragment_login) {
    lateinit var userInfoDb:com.smartu.notes.models.Account
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mClickable: TextView = view.findViewById(R.id.clickable_text_create);
        val mRegisterFrag: RegisterFragment = RegisterFragment();
        val mEmail: EditText = view.findViewById(R.id.email_login)
        val mPassword: EditText = view.findViewById(R.id.password_login)
        val mLoginBtn: Button = view.findViewById(R.id.log_in_btn)
        val sRepo: Repository = Repository.get()

        mEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) { //get info of user from db based on email entered as he finished typing so there is time for the query
                sRepo.getUser(mEmail.text.toString()).observe(viewLifecycleOwner,
                    Observer { user ->
                        user?.let {
                            userInfoDb = user
                        }
                    })
            }

        })


        mLoginBtn.setOnClickListener {
            if (mEmail.text.toString().isEmpty() || mPassword.text.toString().isEmpty()) {
                Toast.makeText(
                    activity,
                    R.string.register_empty_validation_toast,
                    Toast.LENGTH_LONG
                ).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail.text.toString()).matches()) { //check email validation
                Toast.makeText(
                    activity,
                    R.string.register_valid_email_validation_toast,
                    Toast.LENGTH_LONG
                ).show()
                mEmail.background = resources.getDrawable(R.drawable.rounded_corner_red);
            } else {
                if (BCrypt.checkpw(mPassword.text.toString(), userInfoDb.password)) { //check password by hashing entered password and comparing it with the one from query

                    val intent=NotesActivity.newIntent(context,userInfoDb.id)
                    UserPrefrences.setUserID(activity as Context,userInfoDb.id)
                    startActivity(intent)


                } else {
                    Toast.makeText(
                        activity,
                        R.string.login_email_or_password_wrong,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


        mClickable.setOnClickListener { //go to register fragment
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, mRegisterFrag).addToBackStack("tag");
                commit()
            }
        }

        mEmail.setOnFocusChangeListener { view, b -> //change email field border on focus in case it was turned to red on validation
            if (view.hasFocus()) {
                mEmail.background = resources.getDrawable(R.drawable.rounded_corner_white);
            }
        }
        mPassword.setOnFocusChangeListener { view, b -> //change password field border on focus in case it was turned to red on validation
            if (view.hasFocus()) {
                mPassword.background = resources.getDrawable(R.drawable.rounded_corner_white);
            }
        }
    }

}