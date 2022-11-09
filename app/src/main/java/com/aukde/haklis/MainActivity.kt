package com.aukde.haklis

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.rilixtech.widget.countrycodepicker.CountryCodePicker


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        val edit_phone: EditText = findViewById(R.id.login_phone)
        val edit_code: EditText = findViewById(R.id.login_code)
        val btn_sms: AppCompatButton = findViewById(R.id.login_btn_sms)
        val login_btn_register: TextView = findViewById(R.id.login_btn_register)
        val login_btn_login: AppCompatButton = findViewById(R.id.login_btn_login)
        var countryCode: String = ""

        val ccp: CountryCodePicker = findViewById(R.id.cpp)
        countryCode = "+"
        countryCode += ccp.selectedCountryCode
        ccp.setOnCountryChangeListener { selectedCountry ->
            countryCode = "+"
            countryCode += selectedCountry.phoneCode
        }

        btn_sms.setOnClickListener {
            edit_code.focusable = View.FOCUSABLE
            edit_code.isFocusableInTouchMode = true
            edit_code.setBackgroundResource(R.drawable.edit_txt_form)

            btn_sms.focusable = View.NOT_FOCUSABLE
            btn_sms.isFocusableInTouchMode = false
            btn_sms.setText("Reintentar")

            login_btn_login.focusable = View.FOCUSABLE
            login_btn_login.isFocusableInTouchMode = true
            login_btn_login.setBackgroundResource(R.drawable.btn_form)
            Toast.makeText(applicationContext,"SMS envíado",Toast.LENGTH_SHORT).show()
        }
        login_btn_register.setOnClickListener{
            goToRegisterScreen()
        }

        login_btn_login.setOnClickListener {
            verifiedLogin(edit_phone.text.toString(), edit_code.text.toString(), countryCode)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val sharedPref = getSharedPreferences(R.string.privateData.toString(), MODE_PRIVATE)
        val userId: String? = sharedPref.getString("pass", "null")
        //Toast.makeText(applicationContext, userId,Toast.LENGTH_SHORT).show()
    }


    private fun goToRegisterScreen(){
        val intent = Intent(this, ScreenRegister::class.java)
        startActivity(intent)
    }

    private fun verifiedLogin(phone: String, code: String, countrycode: String){
        if(
            phone.isNotEmpty() &&
            code.isNotEmpty() &&
            code.length >= 4
        ){
            Toast.makeText(applicationContext,"LOGIN!! $countrycode",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(applicationContext,"NOT LOGIN!! ",Toast.LENGTH_SHORT).show()
        }
    }
}