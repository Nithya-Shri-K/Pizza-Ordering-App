package com.example.pizzaorderingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class ToppingAdapter(val toppings : ArrayList<Topping>, private val userType : Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var listener : AdminToppingHandler
    constructor(toppings : ArrayList<Topping>,userType : Int, listener : AdminToppingHandler) : this(toppings,userType){
        this.listener = listener
    }
    inner class UserToppingViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val toppingName = view.findViewById<CheckBox>(R.id.topping_name)
        val toppingPrice = view.findViewById<TextView>(R.id.topping_price)

    }
    inner class AdminToppingViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val toppingName: TextView = view.findViewById<TextView>(R.id.name)
        val toppingPrice: TextView = view.findViewById<TextView>(R.id.price)
        val edit: ImageView = view.findViewById<ImageView>(R.id.topping_edit)
        val delete: ImageView = view.findViewById<ImageView>(R.id.topping_delete)
        init{
            edit.setOnClickListener {
                listener.edit(toppings[adapterPosition])
            }
            delete.setOnClickListener {
                AdminHandler.removeTopping(toppings[adapterPosition].id)
                listener.refresh()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == ADMIN) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.topping,parent,false)
            return AdminToppingViewHolder(view)
        }
        else
        {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.topping_selector, parent, false)
            return UserToppingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ADMIN) {
            val viewHolder = holder as AdminToppingViewHolder
            viewHolder.toppingName.text = toppings[position].name
            viewHolder.toppingPrice.text = toppings[position].price

        } else {
            val viewHolder = holder as UserToppingViewHolder
            viewHolder.toppingName.text = toppings[position].name
            viewHolder.toppingPrice.text = "Rs. ${toppings[position].price}"
        }
    }

    override fun getItemCount(): Int {
        return toppings.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(userType == ADMIN)
            ADMIN
        else
            USER

    }
}