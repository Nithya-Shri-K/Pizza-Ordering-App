package com.example.pizzaorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.pizzaorderingapp.databinding.ActivityRegisterOrLoginBinding

class RegisterOrLoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegisterOrLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterOrLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.root.visibility = View.GONE
        setFragment(RegisterOrLoginFragment())
        supportFragmentManager.setFragmentResultListener("requestKey",this){
            requestKey,bundle ->
            val operation = bundle.getString("operation")
            binding.toolbar.root.visibility = View.VISIBLE
            when(operation){
                "login" -> setFragment(LoginFragment())
                "register" -> setFragment(RegisterFragment())
            }
        }
        supportFragmentManager.setFragmentResultListener("userId",this){
                requestKey,bundle ->
            println(bundle.getSerializable("currentUser"))
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