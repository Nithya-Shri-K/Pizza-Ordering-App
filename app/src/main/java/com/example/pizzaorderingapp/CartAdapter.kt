package com.example.pizzaorderingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton

class CartAdapter(val cart : ArrayList<Item>,val listener:CartActionListener) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    interface CartActionListener{
        fun refresh()
    }

    inner class CartViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val itemName = view.findViewById<TextView>(R.id.text_title)
        val itemSize = view.findViewById<TextView>(R.id.text_size)
        val toppings = view.findViewById<TextView>(R.id.text_toppings)
        val quantity = view.findViewById<TextView>(R.id.quantity)
        val price = view.findViewById<TextView>(R.id.text_price)
        val increment = view.findViewById<TextView>(R.id.increment)
        val decrement = view.findViewById<TextView>(R.id.decrement)

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item,parent,false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.itemName.text = cart[position].item.name
        holder.itemSize.text = cart[position].size.name
        var topping = ""
        for(i in cart[position].toppings){
            topping = "$topping, ${i.name}"
        }
        holder.toppings.text = topping
        holder.quantity.text = cart[position].quantity.toString()
        holder.price.text = "Rs. ${cart[position].price.toString()}"
        holder.increment.setOnClickListener {
            val pricePerItem = cart[position].price / cart[position].quantity
            cart[position].price += pricePerItem
            cart[position].quantity += 1
            listener.refresh()
        }
        holder.decrement.setOnClickListener {
            val pricePerItem = cart[position].price / cart[position].quantity
            cart[position].price -= pricePerItem
            cart[position].quantity -= 1
            if(cart[position].quantity == 0){
                UserHandler.removeItemFromCart(cart,cart[position])
            }
            listener.refresh()

        }

    }

    override fun getItemCount(): Int {
        return cart.size
    }
}