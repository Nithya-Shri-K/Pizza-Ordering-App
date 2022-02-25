package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.pizzaorderingapp.databinding.FragmentMyAccountBinding


class MyAccountFragment(user : Users?) : Fragment() {
    private lateinit var binding : FragmentMyAccountBinding
    val currentUser = user

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyAccountBinding.inflate(layoutInflater,container,false)
        binding.userName.setText(currentUser?.name, TextView.BufferType.EDITABLE)
        binding.userPhoneno.setText(currentUser?.phoneNumber, TextView.BufferType.EDITABLE)
        binding.logout.setOnClickListener {
        setFragmentResult("logout", bundleOf("operation" to "logout"))
        }

       return binding.root
    }

}