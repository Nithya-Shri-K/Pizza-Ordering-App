package com.example.pizzaorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class RegisterOrLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_or_login)
        setFragment(RegisterOrLoginFragment())
        supportFragmentManager.setFragmentResultListener("requestKey",this){
            requestKey,bundle ->
            val operation = bundle.getString("operation")
            println(operation)
            when(operation){
                "login" -> setFragment(LoginFragment())
                "register" -> setFragment(RegisterFragment())
            }
        }
        supportFragmentManager.setFragmentResultListener("userId",this){
                requestKey,bundle ->
            println("user id returned")
            intent.putExtra("user",bundle.getSerializable("currentUser"))
            setResult(RESULT_OK,intent)
            finish()

        }

    }
     fun setFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.register_or_login_container,fragment).commit()

    }

}