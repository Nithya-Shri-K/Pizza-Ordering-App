package com.example.pizzaorderingapp

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.pizzaorderingapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        binding.buttonLogin.setOnClickListener {
            val phoneNumber = binding.phoneNumber.text.toString()
            val password = binding.password.text.toString()
            val currentUser = Authentication().login(phoneNumber, password)
            if (currentUser != null) {
                setFragmentResult(CURRENT_USER_KEY, bundleOf(CURRENT_USER to currentUser ) )
            }else{
                Toast.makeText(context,getString(R.string.login_error_message),Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root

    }
}