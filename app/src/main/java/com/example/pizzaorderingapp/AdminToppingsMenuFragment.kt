package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaorderingapp.databinding.FragmentAdminToppingsMenuBinding


class AdminToppingsMenuFragment : Fragment(), AdminToppingHandler {
    private lateinit var binding: FragmentAdminToppingsMenuBinding
    private lateinit var listOfToppings: ToppingAdapter
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAdminToppingsMenuBinding.inflate(layoutInflater, container, false)
        databaseHelper = DatabaseHelper(requireContext())
        val recyclerView = binding.recyclerviewToppings
        listOfToppings = ToppingAdapter(databaseHelper.getToppings(), USER_TYPE_ADMIN, this, requireContext())
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = listOfToppings
        binding.buttonAddTopping.setOnClickListener {
            val toppingDialogFragment = ToppingDialogFragment(this, ADD_ITEM)
            toppingDialogFragment.show(parentFragmentManager, ADD_ITEM)
        }

        return binding.root
    }

    override fun refreshToppingList() {
        listOfToppings.toppingsList = databaseHelper.getToppings()
        listOfToppings.notifyDataSetChanged()
    }

    override fun editTopping(topping: Topping) {
        val addToppingDialog = ToppingDialogFragment(this, EDIT, topping)
        addToppingDialog.show(parentFragmentManager, EDIT)
    }
}