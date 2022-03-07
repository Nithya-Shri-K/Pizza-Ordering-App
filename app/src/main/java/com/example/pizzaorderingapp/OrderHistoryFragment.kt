package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pizzaorderingapp.databinding.FragmentOrderHistoryBinding


class OrderHistoryFragment : Fragment() {

    lateinit var binding :FragmentOrderHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderHistoryBinding.inflate(layoutInflater,container,false)

        return binding.root
    }


}