package com.example.pizzaorderingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaorderingapp.databinding.CartItemBinding

class CartAdapter(
    val cartItemsList: MutableList<Item>,
    val listener: CartActionListener,
    val context: Context
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    val databaseHelper = DatabaseHelper(context)

    inner class CartViewHolder(binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemName = binding.itemName
        val itemSize = binding.itemSize
        val toppings = binding.selectedToppings
        val quantity = binding.quantity
        val price = binding.itemPrice
        val increment = binding.increment
        val decrement = binding.decrement
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartCurrentItem = cartItemsList[position]
        val pizzaId = cartCurrentItem.pizzaId
        val toppingNameList = mutableListOf<String>()
        val itemId = cartCurrentItem.itemId
        val selectedToppings = databaseHelper.getSelectedToppings(itemId)
        holder.itemName.text = databaseHelper.getPizzaName(pizzaId)
        holder.itemSize.text = cartCurrentItem.size.name

        for (toppingId in selectedToppings) {
            val toppingName = databaseHelper.getToppingName(toppingId)
            toppingNameList.add(toppingName)
        }
        if (toppingNameList.isNotEmpty())
            holder.toppings.text = toppingNameList.toString()

        holder.quantity.text = cartCurrentItem.quantity.toString()
        holder.price.text = context.getString(R.string.price_prefix,cartCurrentItem.price.toString())
        holder.increment.setOnClickListener {
            val pricePerItem = cartCurrentItem.price / cartCurrentItem.quantity
            cartCurrentItem.price += pricePerItem
            cartCurrentItem.quantity += 1
            listener.refreshCart()
        }
        holder.decrement.setOnClickListener {
            val pricePerItem = cartCurrentItem.price / cartCurrentItem.quantity
            cartCurrentItem.price -= pricePerItem
            cartCurrentItem.quantity -= 1
            if (cartCurrentItem.quantity == 0) {
                UserHandler.removeItemFromCart(cartCurrentItem.itemId,databaseHelper)
            }
            listener.refreshCart()
        }
    }

    override fun getItemCount(): Int {
        return cartItemsList.size
    }
}