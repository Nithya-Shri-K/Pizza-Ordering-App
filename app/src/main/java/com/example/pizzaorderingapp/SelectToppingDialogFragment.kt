package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaorderingapp.databinding.FragmentSelectToppingDialogBinding

class SelectToppingDialogFragment : Fragment() {
    private lateinit var binding : FragmentSelectToppingDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectToppingDialogBinding.inflate(layoutInflater,container,false)
        val recyclerView = binding.recyclerviewSelectTopping
        val toppings = ToppingAdapter(Database.listOfToppings)
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = toppings
        return binding.root
    }


}