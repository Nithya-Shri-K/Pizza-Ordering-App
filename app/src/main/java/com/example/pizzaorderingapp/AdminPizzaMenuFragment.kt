package com.example.pizzaorderingapp

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaorderingapp.databinding.FragmentAdminPizzaMenuBinding
import com.google.android.material.navigation.NavigationView


class AdminPizzaMenuFragment : Fragment(), AdminPizzaItemsHandler {
    lateinit var binding: FragmentAdminPizzaMenuBinding
    lateinit var menu: PizzaMenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminPizzaMenuBinding.inflate(layoutInflater, container, false)
        val recyclerView = binding.recyclerviewMenuItems
        menu =
            PizzaMenuAdapter(Database.listOfItems, activity?.applicationContext, this, PIZZA)
        recyclerView.layoutManager =
            LinearLayoutManager(activity?.application, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = menu

        binding.buttonAddItem.setOnClickListener {
            var addItemDialog = ItemDialogFragment(this, ADD_ITEM)
            addItemDialog.show(parentFragmentManager, ADD_ITEM)
        }
        return binding.root
    }

    override fun refresh() {
        menu.notifyDataSetChanged()
    }

    override fun edit(pizza: Pizza) {
        var showPizzaDialog = ItemDialogFragment(this, EDIT, pizza)
        showPizzaDialog.show(parentFragmentManager, EDIT)
    }
}


