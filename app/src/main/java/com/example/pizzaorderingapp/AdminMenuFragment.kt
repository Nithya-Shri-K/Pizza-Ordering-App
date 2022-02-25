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
import com.example.pizzaorderingapp.databinding.FragmentAdminMenuBinding
import com.google.android.material.navigation.NavigationView


class AdminMenuFragment : Fragment(),AdminHandlerListener {
    lateinit var binding : FragmentAdminMenuBinding
    lateinit var menu :AdminMenuItemsAdapter
    lateinit var itemType : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminMenuBinding.inflate(layoutInflater,container,false)
        //binding.menuToolbar.inflateMenu(R.menu.items_category_menu)
        val drawerLayout : DrawerLayout = binding.drawerLayout
        val navigationView : NavigationView = binding.navigation
        val toggle : ActionBarDrawerToggle = ActionBarDrawerToggle(activity,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        activity

        val recyclerView = binding.recyclerviewMenuItems
        showPizza(recyclerView)
        binding.buttonAddItem.setOnClickListener {
            if(itemType== "pizza") {
                var addItemDialog = ItemDialogFragment(this, "add")
                addItemDialog.show(parentFragmentManager, "AddItem")
            }else
            {
                var addToppingDialog = ToppingDialogFragment(this, "add")
                addToppingDialog.show(parentFragmentManager, "AddTopping")
            }
        }
        binding.menuToolbar.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.pizza ->  showPizza(recyclerView)
                R.id.topping ->  showTopping(recyclerView)
            }
            true
        }



        return binding.root
    }
    fun showPizza(recyclerView : RecyclerView){
        itemType= "pizza"
        binding.pizzaTable.visibility = View.VISIBLE
        binding.toppingTable.visibility = View.GONE
        menu = AdminMenuItemsAdapter(Database.listOfItems as ArrayList<Items>,activity?.applicationContext,this,"pizza")
        recyclerView.layoutManager = LinearLayoutManager(activity?.application,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = menu
    }
    fun showTopping(recyclerView : RecyclerView){
        itemType= "topping"
        binding.pizzaTable.visibility = View.GONE
        binding.toppingTable.visibility = View.VISIBLE
        menu = AdminMenuItemsAdapter(Database.listOfToppings as ArrayList<Items>,activity?.applicationContext,this,"topping")
        recyclerView.layoutManager = LinearLayoutManager(activity?.application,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = menu
    }
    override fun refresh(){
        menu.notifyDataSetChanged()
    }

    override fun editPizza(pizza : Items.Pizza) {
        println(pizza)
        var showPizzaDialog = ItemDialogFragment(this,"edit",pizza)
        showPizzaDialog.show(parentFragmentManager,"EditItem")
    }

    override fun editTopping(topping: Items.Topping) {
        var addToppingDialog = ToppingDialogFragment(this,"edit",topping)
        addToppingDialog.show(parentFragmentManager,"EditItem")
    }



}