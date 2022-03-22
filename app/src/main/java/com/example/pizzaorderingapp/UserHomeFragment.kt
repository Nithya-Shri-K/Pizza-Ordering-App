package com.example.pizzaorderingapp


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaorderingapp.databinding.FragmentUserHomeBinding
import kotlin.collections.ArrayList

class UserHomeFragment : Fragment() {

    private lateinit var binding: FragmentUserHomeBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        databaseHelper = DatabaseHelper(requireContext())
        val currentUserId = arguments?.getInt(CURRENT_USER_ID)
        val menuItems = databaseHelper.getPizza()
        val recyclerView = binding.recyclerviewMenu
        val getItem =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val itemId = result.data?.getIntExtra(SELECTED_ITEM_ID, 0)
                    setFragmentResult(SELECTED_ITEM_ID, bundleOf(SELECTED_ITEM_ID to itemId))
                }

            }
        val adapterData = MenuAdapter(menuItems, context, object : UserActionListener {

            override fun startCustomizeFragment(selectedPizzaId: Int, size: Size, price: Int) {

                val intent = Intent(context, CustomizeActivity::class.java)
                intent.putExtra(SELECTED_PIZZA_ID, selectedPizzaId)
                intent.putExtra(SELECTED_SIZE, size)
                intent.putExtra(ITEM_PRICE, price)
                intent.putExtra(CURRENT_USER_ID, currentUserId)
                getItem.launch(intent)
            }
        })
        binding.defaultValues.setOnClickListener {
            databaseHelper.insertDefaultValues()
            adapterData.menu = databaseHelper.getPizza()
            binding.defaultValues.isEnabled = false
        }

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapterData


        binding.switchOnlyVeg.setOnCheckedChangeListener { _, checked ->
            showOnlyVeg(checked, adapterData)
        }
        binding.switchOnlyNonveg.setOnCheckedChangeListener { _, checked ->
            showOnlyNonVeg(checked, adapterData)
        }

        return binding.root
    }

    private fun showOnlyVeg(isChecked: Boolean, adapterData: MenuAdapter) {
        if (isChecked) {
            binding.switchOnlyNonveg.isEnabled = false
            val listOfItems = databaseHelper.getPizza()
            adapterData.menu =
                listOfItems.filter { it.category == Category.Veg } as ArrayList<Pizza>
            adapterData.notifyDataSetChanged()
        } else {
            binding.switchOnlyNonveg.isEnabled = true
            adapterData.menu = databaseHelper.getPizza()
            adapterData.notifyDataSetChanged()
        }
    }

    private fun showOnlyNonVeg(isChecked: Boolean, adapterData: MenuAdapter) {
        if (isChecked) {
            binding.switchOnlyVeg.isEnabled = false
            val listOfItems = databaseHelper.getPizza()
            adapterData.menu =
                listOfItems.filter { it.category == Category.NonVeg } as ArrayList<Pizza>
            adapterData.notifyDataSetChanged()
        } else {
            binding.switchOnlyVeg.isEnabled = true
            adapterData.menu = databaseHelper.getPizza()
            adapterData.notifyDataSetChanged()
        }
    }


}
