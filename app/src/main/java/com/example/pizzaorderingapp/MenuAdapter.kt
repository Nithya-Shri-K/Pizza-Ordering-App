package com.example.pizzaorderingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(var menu : ArrayList<Pizza>, val context : Context?,val listener: UserActionListener) : RecyclerView.Adapter<MenuAdapter.MenuItemViewHolder>() {

    inner class MenuItemViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById<ImageView>(R.id.image_item)
        val itemTitle: TextView = view.findViewById<TextView>(R.id.text_title)
        val itemPrice: TextView = view.findViewById<TextView>(R.id.text_cost)
        val sizeSpinner: Spinner = view.findViewById<Spinner>(R.id.spinner_size)
        val addItem: Button = view.findViewById(R.id.button_add_item)
        fun setSizeSpinnerData(sizes: ArrayList<String>) {
            if (context != null){
                val adapter =
                ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, sizes)
                sizeSpinner.adapter = adapter
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuAdapter.MenuItemViewHolder {

        val itemViewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return MenuItemViewHolder(itemViewHolder)

    }

    override fun onBindViewHolder(holder: MenuAdapter.MenuItemViewHolder,position : Int) {

        holder.itemImage.setImageResource(menu[position].image)
        holder.itemTitle.text = menu[position].name
        val sizesAndPrice = menu[position].sizeAndPrice.entries
        val sizes = ArrayList<String>()
        for (i in sizesAndPrice) {
            sizes.add(i.key.name)
        }
        holder.setSizeSpinnerData(sizes)
        val toppings = ArrayList<String>()
        for(i in Database.listOfToppings){
            toppings.add(i.name)
        }
        holder.addItem.setOnClickListener {
            val size = holder.sizeSpinner.selectedItem.toString()
            val price = holder.itemPrice.text.toString()
            listener.customize(menu[position],size,price)
        }
        setPrice(holder)
    }

    override fun getItemCount(): Int {
        println(menu.size)
        return menu.size
    }
    private fun setPrice(holder: MenuItemViewHolder){
        holder.sizeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                selectedSize: Int,
                id: Long
            ) {
                val size : Size = Size.valueOf(adapterView?.getItemAtPosition(selectedSize).toString())
               holder.itemPrice.text ="Rs. ${menu[holder.adapterPosition].sizeAndPrice[size].toString()}"
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
}