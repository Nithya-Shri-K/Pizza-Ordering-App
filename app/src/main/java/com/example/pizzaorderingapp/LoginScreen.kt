package com.example.pizzaorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class LoginScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        if(intent.getStringExtra("operation") == "login")
        {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.authentication_container,LoginFragment()).commit()
        }


    }

}