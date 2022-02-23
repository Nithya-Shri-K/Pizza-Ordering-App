package com.example.pizzaorderingapp

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pizzaorderingapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
                val data = activity?.intent?.putExtra("currentUserId",currentUser.id)
                activity?.setResult(Activity.RESULT_OK,data)
                activity?.finish()
            }
        }
        return binding.root

    }
}