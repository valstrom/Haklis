package com.aukde.haklis

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.aukde.haklis.MainActivity


class ScreenRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_register)

        supportActionBar?.hide()

        val register_btn_login: TextView = findViewById(R.id.register_btn_login)
        val register_btn_back: ImageButton = findViewById(R.id.register_back)

        register_btn_login.setOnClickListener {
            goToLoginScreen()
        }
        register_btn_back.setOnClickListener {
            goToLoginScreen()
        }
    }

    fun goToLoginScreen(){
        finish()
    }
}