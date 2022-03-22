package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaorderingapp.databinding.FragmentAdminPizzaMenuBinding



class AdminPizzaMenuFragment : Fragment(), AdminPizzaItemsHandler {
    lateinit var binding: FragmentAdminPizzaMenuBinding
    lateinit var menu: PizzaMenuAdapter
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminPizzaMenuBinding.inflate(layoutInflater, container, false)
        databaseHelper = DatabaseHelper(requireContext())
        val recyclerView = binding.recyclerviewMenuItems
        menu = PizzaMenuAdapter(databaseHelper.getPizza(), activity?.applicationContext, this, PIZZA)
        recyclerView.layoutManager = LinearLayoutManager(activity?.application, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = menu

        binding.buttonAddItem.setOnClickListener {
            val addItemDialog = ItemDialogFragment(this, ADD_ITEM)
            addItemDialog.show(parentFragmentManager, ADD_ITEM)
        }
        return binding.root
    }

    override fun refreshPizzaList() {
        menu.items = databaseHelper.getPizza()
        menu.notifyDataSetChanged()
    }

    override fun editPizza(pizza: Pizza) {
        val showPizzaDialog = ItemDialogFragment(this, EDIT, pizza)
        showPizzaDialog.show(parentFragmentManager, EDIT)
    }
}


