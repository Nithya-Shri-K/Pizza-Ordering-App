package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaorderingapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
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
        val adapterData = MenuAdapter(menuItems, context)
        recyclerView.adapter = adapterData
        binding.switchOnlyVeg.setOnCheckedChangeListener{ _,checked ->
            if(checked){
                adapterData.menu = Database.listOfItems.filter { it.category == Category.Veg } as ArrayList<Item>
                adapterData.notifyDataSetChanged()
            }
            else{
                adapterData.menu = Database.listOfItems
                adapterData.notifyDataSetChanged()
            }

        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        println("fragments onResume")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("fragments onCreate")
    }

    override fun onStart() {
        super.onStart()
        println("fragments onStart")
    }


}
