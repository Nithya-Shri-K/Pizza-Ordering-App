package com.example.pizzaorderingapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaorderingapp.databinding.FragmentUserHomeBinding

class UserHomeFragment : Fragment(),UserActionListener {
    private lateinit var binding : FragmentUserHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserHomeBinding.inflate(inflater,container,false)
        val menuItems = Database.listOfItems
        val recyclerView = binding.recyclerviewMenu
        val adapterData = MenuAdapter(menuItems, context,this)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapterData


        binding.switchOnlyVeg.setOnCheckedChangeListener{ _,checked ->
            showOnlyVeg(checked,adapterData)
        }
        binding.switchOnlyNonveg.setOnCheckedChangeListener{ _,checked ->
            showOnlyNonVeg(checked,adapterData)
        }
        return binding.root
    }
    private fun showOnlyVeg(isChecked : Boolean, adapterData : MenuAdapter ){
        if(isChecked){
            binding.switchOnlyNonveg.isEnabled = false
            adapterData.menu = Database.listOfItems.filter { it.category == Category.Veg } as ArrayList<Pizza>
            adapterData.notifyDataSetChanged()
        }
        else{
            binding.switchOnlyNonveg.isEnabled = true
            adapterData.menu = Database.listOfItems
            adapterData.notifyDataSetChanged()
        }
    }
    private fun showOnlyNonVeg(isChecked : Boolean, adapterData: MenuAdapter){
        if(isChecked){
            binding.switchOnlyVeg.isEnabled = false
            adapterData.menu = Database.listOfItems.filter { it.category == Category.NonVeg } as ArrayList<Pizza>
            adapterData.notifyDataSetChanged()
        }
        else{
            binding.switchOnlyVeg.isEnabled = true
            adapterData.menu = Database.listOfItems
            adapterData.notifyDataSetChanged()
        }
    }
    override fun customize(selectedItem : Pizza,size : String,price:String) {
        val intent = Intent(context,CustomizeActivity::class.java)
        intent.putExtra("selectedItem",selectedItem)
        intent.putExtra("selectedSize",size)
        intent.putExtra("selectedPrice",price)
        startActivity(intent)

    }

}
