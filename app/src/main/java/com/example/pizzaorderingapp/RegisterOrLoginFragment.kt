package com.example.pizzaorderingapp

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.pizzaorderingapp.databinding.FragmentMyAccountBinding
import com.example.pizzaorderingapp.databinding.FragmentRegisterOrLoginBinding

class RegisterOrLoginFragment : Fragment() {
    lateinit var binding : FragmentRegisterOrLoginBinding
    private lateinit var userId : ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterOrLoginBinding.inflate(layoutInflater,container,false)
        binding.buttonLogin.setOnClickListener {
        setFragmentResult(REQUEST_KEY, bundleOf(OPERATION to LOGIN))
        }
        binding.buttonRegister.setOnClickListener {
            setFragmentResult(REQUEST_KEY, bundleOf(OPERATION to REGISTER))
        }
        return binding.root
    }


}