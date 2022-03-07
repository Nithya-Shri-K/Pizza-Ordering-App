package com.example.pizzaorderingapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaorderingapp.databinding.FragmentUserHomeBinding

class UserHomeFragment : Fragment() {
    private lateinit var binding : FragmentUserHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserHomeBinding.inflate(inflater,container,false)
        val menuItems = Database.listOfItems
        val recyclerView = binding.recyclerviewMenu
        val adapterData = MenuAdapter(menuItems, context, object : UserActionListener {

            override fun startCustomizeFragment(selectedItem : Pizza, size : Size, price : Int) {
                val transaction = parentFragmentManager.beginTransaction()
                val fragment = CustomizeFragment()
                val bundle = Bundle()
                bundle.putSerializable("selectedItem",selectedItem)
                bundle.putSerializable("selectedSize",size)
                bundle.putInt("price",price)
                fragment.arguments = bundle
                transaction.replace(R.id.fragment_container,fragment).commit()
            }
        })
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


}
