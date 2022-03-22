package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.pizzaorderingapp.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        databaseHelper = DatabaseHelper(requireContext())
        binding.buttonRegister.setOnClickListener {
            getUserData()
        }

        return binding.root
    }

    private fun getUserData() {
        val firstname = binding.firstname.text.toString()
        val lastname = binding.lastname.text.toString()
        val phoneNumber = binding.phoneNumber.text.toString()
        val password = binding.password.text.toString()
        if (firstname.isNotEmpty() && lastname.isNotEmpty() && phoneNumber.isNotEmpty() && password.isNotEmpty()) {
            registerUser(firstname, lastname, phoneNumber, password)
        } else
            Toast.makeText(context, getString(R.string.save_error_message), Toast.LENGTH_SHORT)
                .show()
    }

    private fun registerUser(
        firstname: String,
        lastname: String,
        phoneNumber: String,
        password: String
    ) {
        val isAccountExist = databaseHelper.isAccountExist(phoneNumber)
        if (isAccountExist == -1) {
            val userId =
                UserHandler.createUser(firstname, lastname, phoneNumber, password, databaseHelper)
            setFragmentResult(CURRENT_USER_KEY, bundleOf(CURRENT_USER_ID to userId))
        } else
            Toast.makeText(context, getString(R.string.account_exist), Toast.LENGTH_SHORT)
                .show()

    }

}