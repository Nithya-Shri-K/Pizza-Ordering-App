package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaorderingapp.databinding.FragmentAdminToppingsMenuBinding


class AdminToppingsMenuFragment : Fragment(),AdminToppingHandler {
    lateinit var binding : FragmentAdminToppingsMenuBinding
    lateinit var toppings : ToppingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminToppingsMenuBinding.inflate(layoutInflater,container,false)
        val recyclerView = binding.recyclerviewToppings
        toppings = ToppingAdapter(Database.listOfToppings, ADMIN,this)
        recyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = toppings
        binding.buttonAddTopping.setOnClickListener {
            val toppingDialogFragment = ToppingDialogFragment(this, ADD_ITEM)
            toppingDialogFragment.show(parentFragmentManager, ADD_ITEM)

        }

        return binding.root
    }

    override fun refresh() {
        toppings.notifyDataSetChanged()
    }

    override fun edit(topping: Topping) {
        var addToppingDialog = ToppingDialogFragment(this, EDIT,topping)
       addToppingDialog.show(parentFragmentManager, EDIT)
    }







}