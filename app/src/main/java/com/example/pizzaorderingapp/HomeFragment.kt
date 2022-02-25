package com.example.pizzaorderingapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaorderingapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment(),ActionListener {
    private lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        val menuItems = Database.listOfItems
        val recyclerView = binding.recyclerviewMenu
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapterData = MenuAdapter(menuItems, context,this)
        recyclerView.adapter = adapterData
        binding.switchOnlyVeg.setOnCheckedChangeListener{ _,checked ->
            if(checked){
                binding.switchOnlyNonveg.isEnabled = false
                adapterData.menu = Database.listOfItems.filter { it.category == Category.Veg } as ArrayList<Items.Pizza>
                adapterData.notifyDataSetChanged()
            }
            else{
                binding.switchOnlyNonveg.isEnabled = true
                adapterData.menu = Database.listOfItems
                adapterData.notifyDataSetChanged()
            }

        }
        binding.switchOnlyNonveg.setOnCheckedChangeListener{ _,checked ->
            if(checked){
                binding.switchOnlyVeg.isEnabled = false
                adapterData.menu = Database.listOfItems.filter { it.category == Category.NonVeg } as ArrayList<Items.Pizza>
                adapterData.notifyDataSetChanged()
            }
            else{
                binding.switchOnlyVeg.isEnabled = true
                adapterData.menu = Database.listOfItems
                adapterData.notifyDataSetChanged()
            }

        }
        return binding.root
    }

    override fun customize(selectedItem : Items.Pizza,size : String,price:String) {
        val intent = Intent(context,CustomizeActivity::class.java)
        intent.putExtra("selectedItem",selectedItem)
        intent.putExtra("selectedSize",size)
        intent.putExtra("selectedPrice",price)
        startActivity(intent)

    }

}
