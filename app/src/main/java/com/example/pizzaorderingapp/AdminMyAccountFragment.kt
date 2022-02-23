package com.example.pizzaorderingapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pizzaorderingapp.databinding.FragmentAdminMyAccountBinding

class AdminMyAccountFragment : Fragment() {
    lateinit var binding : FragmentAdminMyAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminMyAccountBinding.inflate(layoutInflater,container,false)
        binding.logout.setOnClickListener {
            startActivity(Intent(activity,MainActivity::class.java))
            activity?.finish()
        }
        binding.toppings.setOnClickListener {

        }
        return binding.root
    }
}

