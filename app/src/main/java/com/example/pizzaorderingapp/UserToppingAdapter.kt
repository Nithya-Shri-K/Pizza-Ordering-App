package com.example.pizzaorderingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaorderingapp.databinding.ToppingSelectorBinding

class UserToppingAdapter(val toppingsList:MutableList<Topping>,
                         val listener: ToppingSelector,
                         val context: Context
): RecyclerView.Adapter<UserToppingAdapter.UserToppingViewHolder>() {

    inner class UserToppingViewHolder(binding: ToppingSelectorBinding): RecyclerView.ViewHolder(binding.root){
        val toppingName = binding.toppingName
        val toppingPrice = binding.toppingPrice

        init {
            toppingName.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    listener.onToppingSelected(toppingsList[adapterPosition])
                } else {
                    listener.onToppingDeselected(toppingsList[adapterPosition])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserToppingViewHolder {
        val binding = ToppingSelectorBinding.inflate(LayoutInflater.from(context),parent,false)
        return UserToppingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserToppingViewHolder, position: Int) {
        val price = toppingsList[position].price.toString()
        holder.toppingName.text = toppingsList[position].name
        holder.toppingPrice.text = context.getString(R.string.price_prefix, price)
    }

    override fun getItemCount(): Int {
        return toppingsList.size
    }
}