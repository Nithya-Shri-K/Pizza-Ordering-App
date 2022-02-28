package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.pizzaorderingapp.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {
    lateinit var binding : FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater,container,false)
        binding.buttonRegister.setOnClickListener {
            getUserData()
        }

        return binding.root
    }
    private fun getUserData(){
        val firstname = binding.firstname.text.toString()
        val lastname = binding.lastname.text.toString()
        val phoneNumber = binding.phoneNumber.text.toString()
        val password = binding.password.text.toString()
        registerUser(firstname,lastname,phoneNumber,password)
    }
    private fun registerUser(firstname: String, lastname : String, phoneNumber : String, password : String){
        val user = Users("$firstname $lastname",phoneNumber,password,ArrayList(),false)
        println(user)
        Database.listOfUsers.add(user)
        setFragmentResult(CURRENT_USER_KEY, bundleOf(CURRENT_USER to user))
    }

}