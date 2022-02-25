package com.example.pizzaorderingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToppingAdapter(val toppings : ArrayList<Items.Topping>) : RecyclerView.Adapter<ToppingAdapter.ViewHolder>() {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val toppingName = view.findViewById<CheckBox>(R.id.topping_name)
        val toppingPrice = view.findViewById<TextView>(R.id.topping_price)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.topping_selector,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.toppingName.text = toppings[position].name
        holder.toppingPrice.text = toppings[position].price
    }

    override fun getItemCount(): Int {
        return toppings.size
    }
}