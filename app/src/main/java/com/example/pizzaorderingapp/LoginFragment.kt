package com.example.pizzaorderingapp


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
        val databaseHelper = DatabaseHelper(requireContext())
        binding.buttonLogin.setOnClickListener {
            val authentication = Authentication()
            val phoneNumber = binding.phoneNumber.text.toString()
            val password = binding.password.text.toString()
            if (phoneNumber.isNotEmpty() && password.isNotEmpty()) {
                val currentUserId = authentication.login(phoneNumber, password, databaseHelper)
                if (currentUserId > 0) {
                    setFragmentResult(CURRENT_USER_KEY, bundleOf(CURRENT_USER_ID to currentUserId))

                } else if (currentUserId == 0) {
                    Toast.makeText(
                        context,
                        getString(R.string.incorrect_password),
                        Toast.LENGTH_SHORT
                    ).show()
                } else
                    Toast.makeText(
                        context,
                        getString(R.string.invalid_account),
                        Toast.LENGTH_SHORT
                    ).show()
            } else
                Toast.makeText(context, getString(R.string.save_error_message), Toast.LENGTH_SHORT)
                    .show()
        }

        return binding.root

    }
}