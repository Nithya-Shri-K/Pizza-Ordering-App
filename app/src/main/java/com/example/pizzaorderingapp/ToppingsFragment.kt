package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pizzaorderingapp.databinding.FragmentToppingsBinding


class ToppingsFragment : Fragment() {
    lateinit var binding : FragmentToppingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToppingsBinding.inflate(layoutInflater,container,false)
        binding.buttonAddTopping.setOnClickListener {
            val toppingDialogFragment = ToppingDialogFragment()
            toppingDialogFragment.show(parentFragmentManager,"Topping")

        }

        return binding.root
    }



}