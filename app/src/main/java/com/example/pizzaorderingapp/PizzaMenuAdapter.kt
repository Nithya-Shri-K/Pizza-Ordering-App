package com.example.pizzaorderingapp

import android.content.ClipData
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class PizzaMenuAdapter(val items : ArrayList<Pizza>, val context : Context?, val listener : AdminPizzaItemsHandler, val menuType : String) : RecyclerView.Adapter<PizzaMenuAdapter.PizzaViewHolder>() {
    inner class PizzaViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val id: TextView = view.findViewById<TextView>(R.id.item_id)
        val name: TextView = view.findViewById<TextView>(R.id.item_name)
        val size: Spinner = view.findViewById<Spinner>(R.id.item_size)
        val price: TextView = view.findViewById<TextView>(R.id.item_price)
        val category: TextView = view.findViewById<TextView>(R.id.item_Category)
        val edit: ImageView = view.findViewById<ImageView>(R.id.item_edit)
        val delete: ImageView = view.findViewById<ImageView>(R.id.item_delete)
        init{
            edit.setOnClickListener {
                listener.edit(items[adapterPosition])
            }
            delete.setOnClickListener {
               AdminHandler.removeItem(items[adapterPosition].id)
                listener.refresh()
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder{

            val itemViewHolder = LayoutInflater.from(parent.context)
                .inflate(R.layout.admin_menu_items, parent, false)
            return PizzaViewHolder(itemViewHolder)
    }
    override fun onBindViewHolder(holder: PizzaViewHolder, position: Int) {

        holder.id.text = items[position].id.toString()
        holder.name.text = items[position].name
        holder.category.text = items[position].category.name
        val sizeAndPrice = items[position].sizeAndPrice.entries
        val sizes = arrayListOf<String>()
        for (i in sizeAndPrice) {
            sizes.add(i.key.name)
        }
        holder.setSizesSpinnerData(sizes)
        setPrice(holder)
        }

    private fun setPrice(holder: PizzaMenuAdapter.PizzaViewHolder) {
        holder.size.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                selectedSize: Int,
                id: Long
            ) {
                val size: Size =
                    Size.valueOf(adapterView?.getItemAtPosition(selectedSize).toString())
                holder.price.text = items[holder.adapterPosition].sizeAndPrice[size].toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}