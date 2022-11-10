package com.aukde.haklis

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rilixtech.widget.countrycodepicker.CountryCodePicker

val database: DatabaseReference = FirebaseDatabase.getInstance().reference

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_register)

        supportActionBar?.hide()

        val phoneInput: EditText = findViewById(R.id.register_phone)
        val namesInput: EditText = findViewById(R.id.register_names)
        val emailInput: EditText = findViewById(R.id.register_email)
        val passwordInput: EditText = findViewById(R.id.register_pass)



        val register_btn_login: TextView = findViewById(R.id.register_btn_login)
        val register_btn_back: ImageButton = findViewById(R.id.register_back)
        val register_btn_register: AppCompatButton = findViewById(R.id.register_btn_register)

        var countryCode: String = ""

        val ccp: CountryCodePicker = findViewById(R.id.reg_cpp)
        countryCode = "+"
        countryCode += ccp.selectedCountryCode
        ccp.setOnCountryChangeListener { selectedCountry ->
            countryCode = "+"
            countryCode += selectedCountry.phoneCode
        }

        register_btn_login.setOnClickListener {
            goToLoginScreen()
        }
        register_btn_back.setOnClickListener {
            goToLoginScreen()
        }

        //Register
        register_btn_register.setOnClickListener {

            val phone = countryCode + phoneInput.text.toString()
            val names = namesInput.text.toString()
            val email =emailInput.text.toString()
            val pass =passwordInput.text.toString()

            ValidateData(
                phone,
                names,
                email,
                pass
            )
        }
    }

    fun goToLoginScreen(){
        finish()
    }

    fun ValidateData(phone: String, names: String, email: String, pass: String){
        val data = object {
            val phone = phone
            val names = names
            val email = email
            val pass = pass
        }

        if(data.phone.isNotEmpty() && data.names.isNotEmpty() && data.email.isNotEmpty() && data.pass.isNotEmpty()){
            if(data.pass.length >= 8){
                var t=0
                for (c in data.pass){
                    if(c.isLowerCase()){
                        t++
                        break;
                    }
                }
                for (c in data.pass){
                    if(c.isUpperCase()){
                        t++
                        break;
                    }
                }
                if(t >= 2){
                    //OPERACIONES CON REALTIME DATABASE
                    val ref = database.child("users")
                    val key = ref.push().key
                    if (key != null) {
                        ref.child(key).child("phone").setValue(data.phone)
                        ref.child(key).child("fullname").setValue(data.names)
                        ref.child(key).child("email").setValue(data.email)
                        ref.child(key).child("pass").setValue(data.pass)
                        ref.child(key).child("id").setValue(key)
                    }
                }else{
                    Toast.makeText(
                        applicationContext,
                        "La contraseña debe contener una mayúscula y una minúscula",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else{
                Toast.makeText(
                    applicationContext,
                    "La contraseña debe contener 8 o más dígitos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }else{
            Toast.makeText(
                applicationContext,
                "Rellene todos los campos",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}