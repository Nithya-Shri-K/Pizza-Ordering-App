package com.example.pizzaorderingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaorderingapp.databinding.ToppingBinding

class AdminToppingAdapter(
    var toppingsList: MutableList<Topping>,
    val listener: AdminToppingHandler,
    val context: Context): RecyclerView.Adapter<AdminToppingAdapter.AdminToppingViewHolder>() {
    inner class AdminToppingViewHolder(binding: ToppingBinding):RecyclerView.ViewHolder(binding.root) {

        val toppingName: TextView = binding.name
        val toppingPrice: TextView = binding.price
        val edit: ImageView = binding.toppingEdit
        val delete: ImageView = binding.toppingDelete
        val databaseHelper = DatabaseHelper(context)

        init {
            edit.setOnClickListener {
                listener.editTopping(toppingsList[adapterPosition])
            }
            delete.setOnClickListener {
                AdminHandler.removeTopping(toppingsList[adapterPosition].id,databaseHelper)
                listener.refreshToppingList()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminToppingViewHolder {

        val binding = ToppingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdminToppingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdminToppingViewHolder, position: Int) {

        holder.toppingName.text = toppingsList[position].name
        holder.toppingPrice.text = toppingsList[position].price.toString()
    }

    override fun getItemCount(): Int {
        return toppingsList.size
    }

}