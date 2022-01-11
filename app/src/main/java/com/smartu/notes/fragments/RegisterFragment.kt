package com.smartu.notes.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.smartu.notes.R
import com.smartu.notes.Repository
import com.smartu.notes.models.Account
import org.mindrot.jbcrypt.BCrypt

class RegisterFragment:Fragment(R.layout.fragment_register) {
    lateinit var emailExists:String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mSignUpButton:Button=view.findViewById(R.id.sign_up_btn)
        val mEmail:EditText=view.findViewById(R.id.email_register)
        val mPassword:EditText=view.findViewById(R.id.register_password)
        val mPasswordConfirm:EditText=view.findViewById(R.id.register_confirm_password)
        val sRepo:Repository= Repository.get()


        mEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) { //get ema
                emailExists = ""
                sRepo.getUser(mEmail.text.toString()).observe(viewLifecycleOwner,
                    Observer { user ->
                        user?.let {
                            emailExists = user.email
                        }
                    })
            }

        })


        mSignUpButton.setOnClickListener { //register field validation
            if(mEmail.text.isEmpty() || mPassword.text.isEmpty() || mPasswordConfirm.text.isEmpty()){
                Toast.makeText(activity, R.string.register_empty_validation_toast, Toast.LENGTH_LONG).show()
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(mEmail.text.toString()).matches()){
                Toast.makeText(activity, R.string.register_valid_email_validation_toast, Toast.LENGTH_LONG).show()
                mEmail.background=resources.getDrawable(R.drawable.rounded_corner_red);
            }
            else if(mPassword.text.length<8){
                Toast.makeText(activity, R.string.register_password_length_validation_toast, Toast.LENGTH_LONG).show()
                mPassword.background=resources.getDrawable(R.drawable.rounded_corner_red);
            }
            else if(!mPassword.text.toString().equals(mPasswordConfirm.text.toString())){
                Toast.makeText(activity, R.string.register_password_confirmation_validation_toast, Toast.LENGTH_LONG).show()
                mPasswordConfirm.background=resources.getDrawable(R.drawable.rounded_corner_red);
            }

            else{
                if(emailExists.isEmpty()){
                    //hash password before inserting into database
                    val userPassword=BCrypt.hashpw(mPassword.text.toString(), BCrypt.gensalt())
                    val newAccount=Account(email = mEmail.text.toString(), password = userPassword)
                    sRepo.addUser(newAccount)
                    Toast.makeText(activity, R.string.register_sign_up_complete, Toast.LENGTH_SHORT).show()
                    activity?.supportFragmentManager?.popBackStack();
                }
                else{
                    Toast.makeText(activity, R.string.register_email_exists_validation_toast, Toast.LENGTH_SHORT).show()
                    mEmail.background=resources.getDrawable(R.drawable.rounded_corner_red);

                }

            }
        }

        mEmail.setOnFocusChangeListener { view, b ->
            if(view.hasFocus())
            {
                mEmail.background=resources.getDrawable(R.drawable.rounded_corner_white);
            }
        }
        mPassword.setOnFocusChangeListener { view, b ->
            if(view.hasFocus())
            {
                mPassword.background=resources.getDrawable(R.drawable.rounded_corner_white);
            }
        }
        mPasswordConfirm.setOnFocusChangeListener { view, b ->
            if(view.hasFocus())
            {
                mPasswordConfirm.background=resources.getDrawable(R.drawable.rounded_corner_white);
            }
        }
    }


}
