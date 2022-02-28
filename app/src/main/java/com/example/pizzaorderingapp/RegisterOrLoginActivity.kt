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
        supportFragmentManager.setFragmentResultListener(REQUEST_KEY,this){
            requestKey,bundle ->
            val operation = bundle.getString(OPERATION)
            binding.toolbar.root.visibility = View.VISIBLE
            when(operation){
                LOGIN -> setFragment(LoginFragment())
                REGISTER -> setFragment(RegisterFragment())
            }
        }
        supportFragmentManager.setFragmentResultListener(CURRENT_USER_KEY,this){
                requestKey,bundle ->
            intent.putExtra(CURRENT_USER,bundle.getSerializable(CURRENT_USER))
            setResult(RESULT_OK,intent)
            finish()

        }

    }
     private fun setFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.register_or_login_container,fragment).commit()

    }

    override fun onBackPressed() {
        super.onBackPressed()


    }
}