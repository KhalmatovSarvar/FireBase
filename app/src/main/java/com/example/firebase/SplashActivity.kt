package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import com.example.firebase.managers.AuthManager

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
        initViews()
    }

    fun initViews() {
        countDownTimer()
    }

    fun countDownTimer() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                if (AuthManager.isSignedIn()) {
                    callHomeActivity(context)
                } else {
                    callSignInActivity(context)
                }
            }
        }.start()
    }

    fun callSignInActivity() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}