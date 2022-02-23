package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.pizzaorderingapp.databinding.FragmentMyAccountBinding


class MyAccountFragment(userId : Int?) : Fragment() {
    private lateinit var binding : FragmentMyAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyAccountBinding.inflate(layoutInflater,container,false)
        binding.logout.setOnClickListener {
        setFragmentResult("logout", bundleOf("operation" to "logout"))
        }

       return binding.root
    }

}