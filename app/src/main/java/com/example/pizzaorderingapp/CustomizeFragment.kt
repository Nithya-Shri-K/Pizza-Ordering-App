package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.example.pizzaorderingapp.databinding.FragmentCustomizeBinding


class CustomizeFragment : Fragment(),SelectedTopping {
    lateinit var binding : FragmentCustomizeBinding
    var selectedToppings : ArrayList<Topping> = arrayListOf()
    var itemPrice : Int = 0
    var toppingPrice : Int = 0
    var totalPrice : Int = 0
    var cart : ArrayList<Item> = arrayListOf()
    var quantity = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomizeBinding.inflate(layoutInflater, container, false)
        val selectedItem = arguments?.getSerializable("selectedItem") as Pizza
        val selectedSize = arguments?.getSerializable("selectedSize") as Size
        val price = arguments?.getInt("price")
        binding.selectedItemName.text = selectedItem.name
        binding.price.text = price.toString()
        binding.size.text = selectedSize.name
        itemPrice = price ?: 0

        val recyclerView = binding.recyclerviewToppings
        val toppings = ToppingAdapter(Database.listOfToppings, USER,this)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = toppings

        binding.quantity.text = quantity.toString()
        setTotalPrice()

        binding.close.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,UserHomeFragment()).commit()
        }
        binding.addToCart.setOnClickListener {
            val item = CartItemsHandler.createItem(selectedItem,quantity,selectedToppings,selectedSize,totalPrice)
            setFragmentResult("cartItem", bundleOf("item" to item))
        }
        binding.increment.setOnClickListener {
            quantity += 1
            itemPrice = (price?.toInt() ?:0) * quantity
            binding.quantity.text = quantity.toString()
            setTotalPrice()
        }
        binding.decrement.setOnClickListener {
            quantity -= 1
            if(quantity > 0) {
                itemPrice = (price?.toInt() ?: 0) * quantity
                binding.quantity.text = quantity.toString()
                setTotalPrice()
            }else{
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container,UserHomeFragment()).commit()
            }
        }

        return binding.root
    }
    private fun setTotalPrice(){
        val totalToppingPrice = toppingPrice * quantity
        totalPrice = itemPrice + totalToppingPrice
        binding.addToCart.text = "Add to Cart Rs. " + totalPrice

    }

    override fun onCheck(topping: Topping) {
        toppingPrice += topping.price.toInt()
        setTotalPrice()
        selectedToppings.add(topping)
    }

    override fun onUncheck(topping: Topping) {
        toppingPrice -= topping.price.toInt()
        setTotalPrice()
        selectedToppings.remove(topping)
    }


}