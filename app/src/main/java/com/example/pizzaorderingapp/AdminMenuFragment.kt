package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaorderingapp.databinding.FragmentAdminMenuBinding



class AdminMenuFragment : Fragment(),AdminMenuHandler {
    lateinit var binding : FragmentAdminMenuBinding
    lateinit var menu :AdminMenuItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminMenuBinding.inflate(layoutInflater,container,false)
        binding.buttonAddItem.setOnClickListener {
            var addItemDialog = ItemDialogFragment(this,"add")
            addItemDialog.show(parentFragmentManager,"AddItem")
        }
        val recyclerView = binding.recyclerviewMenuItems
        menu = AdminMenuItemsAdapter(Database.listOfItems,activity?.applicationContext,this)
        recyclerView.layoutManager = LinearLayoutManager(activity?.application,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = menu
        return binding.root
    }
    override fun refreshMenu(){
        menu.notifyDataSetChanged()
    }

    override fun edit(item : Item) {
        var addItemDialog = ItemDialogFragment(this,"edit",item)
        addItemDialog.show(parentFragmentManager,"EditItem")
    }



}