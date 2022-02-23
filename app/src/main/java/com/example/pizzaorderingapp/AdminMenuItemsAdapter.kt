package com.example.pizzaorderingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class AdminMenuItemsAdapter(val menuItems : ArrayList<Item>,val context : Context?,val listener : AdminMenuHandler) : RecyclerView.Adapter<AdminMenuItemsAdapter.ViewHolder>() {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val id = view.findViewById<TextView>(R.id.item_id)
        val name = view.findViewById<TextView>(R.id.item_name)
        val size = view.findViewById<Spinner>(R.id.item_size)
        val price = view.findViewById<TextView>(R.id.item_price)
        val category = view.findViewById<TextView>(R.id.item_Category)
        val edit = view.findViewById<ImageView>(R.id.item_edit)
        init{
            edit.setOnClickListener {
                listener.edit(menuItems[adapterPosition])
            }
        }
        fun setSizesSpinnerData(sizes : ArrayList<String>){
            if(context != null){
                val adapter = ArrayAdapter<String>(context,R.layout.spinner_text,sizes)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                size.adapter = adapter
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemViewHolder = LayoutInflater.from(parent.context).inflate(R.layout.admin_menu_items,parent,false)
        return ViewHolder(itemViewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.id.text = menuItems[position].id.toString()
        holder.name.text = menuItems[position].name
        holder.category.text = menuItems[position].category.name
        val sizeAndPrice = menuItems[position].sizeAndPrice.entries
        val sizes = arrayListOf<String>()
        for(i in sizeAndPrice){
            sizes.add(i.key.name)
        }
        holder.setSizesSpinnerData(sizes)
        setPrice(holder)

    }
    private fun setPrice(holder: AdminMenuItemsAdapter.ViewHolder) {
        holder.size.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                selectedSize: Int,
                id: Long
            ) {
                val size: Size =
                    Size.valueOf(adapterView?.getItemAtPosition(selectedSize).toString())
                holder.price.text = menuItems[holder.adapterPosition].sizeAndPrice[size].toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }
}