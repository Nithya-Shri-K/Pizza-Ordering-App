package com.example.pizzaorderingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(val items: ArrayList<Item>, val context: Context) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    val databaseHelper = DatabaseHelper(context)

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById<TextView>(R.id.item_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_in_order, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val pizzaName = databaseHelper.getPizzaName(items[position].pizzaId)
        holder.itemName.text = "${items[position].quantity} x $pizzaName"

    }

    override fun getItemCount(): Int {
        return items.size
    }
}