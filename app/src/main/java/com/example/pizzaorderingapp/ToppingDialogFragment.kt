package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.pizzaorderingapp.databinding.FragmentToppingDialogBinding


class ToppingDialogFragment : DialogFragment() {
    lateinit var binding : FragmentToppingDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToppingDialogBinding.inflate(layoutInflater,container,false)

        return binding.root
    }


}