package com.example.firebase


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.firebase.managers.AuthHandler
import com.example.firebase.managers.AuthManager
import com.example.firebase.utils.Extensions.toast
import java.lang.Exception

class SignInActivity:BaseActivity() {

    val TAG = SignInActivity::class.java.toString()
    lateinit var et_email: EditText
    lateinit var et_password:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        initViews()
    }

    fun initViews(){
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)

        val b_signin = findViewById<Button>(R.id.b_signin)

        b_signin.setOnClickListener {
            val email  = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            firebaseSignIn(email,password)
        }


        val tv_signup = findViewById<TextView>(R.id.tv_signup)
        tv_signup.setOnClickListener { callSignUpActivity() }
    }

    fun firebaseSignIn(email:String,password:String){
        AuthManager.signIn(email,password,object : AuthHandler {
            override fun onSuccess() {
                toast("Signed in succcessfully")
                callHomeActivity(context)
            }

            override fun onError(exception: Exception?) {
                toast("Sign in failed")
            }


        })
    }

    fun callSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

}