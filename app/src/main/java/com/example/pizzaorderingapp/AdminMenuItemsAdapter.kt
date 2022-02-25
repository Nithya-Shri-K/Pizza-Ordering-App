package com.example.pizzaorderingapp

import android.content.ClipData
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
const val PIZZA = 1
const val TOPPING = 2
class AdminMenuItemsAdapter(val items : ArrayList<Items>, val context : Context?, val listener : AdminHandlerListener, val menuType : String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class PizzaViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val item = items as ArrayList<Items.Pizza>
        val id = view.findViewById<TextView>(R.id.item_id)
        val name = view.findViewById<TextView>(R.id.item_name)
        val size = view.findViewById<Spinner>(R.id.item_size)
        val price = view.findViewById<TextView>(R.id.item_price)
        val category = view.findViewById<TextView>(R.id.item_Category)
        val edit = view.findViewById<ImageView>(R.id.item_edit)
        val delete = view.findViewById<ImageView>(R.id.item_delete)
        init{
            edit.setOnClickListener {
                listener.editPizza(item[adapterPosition])
            }
            delete.setOnClickListener {
               AdminHandler.removeItem(item[adapterPosition].id)
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

    inner class ToppingViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val item = items as ArrayList<Items.Topping>
        val toppingName = view.findViewById<TextView>(R.id.topping_name)
        val toppingPrice = view.findViewById<TextView>(R.id.topping_price)
        val edit = view.findViewById<ImageView>(R.id.topping_edit)
        val delete = view.findViewById<ImageView>(R.id.topping_delete)
        init{
            edit.setOnClickListener {
                listener.editTopping(item[adapterPosition])
            }
            delete.setOnClickListener {
                AdminHandler.removeTopping(item[adapterPosition].id)
                listener.refresh()
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == PIZZA) {
            val itemViewHolder = LayoutInflater.from(parent.context)
                .inflate(R.layout.admin_menu_items, parent, false)
            return PizzaViewHolder(itemViewHolder)
        }
        else{
            val itemViewHolder = LayoutInflater.from(parent.context)
                .inflate(R.layout.topping, parent, false)
            return ToppingViewHolder(itemViewHolder)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position)== PIZZA) {
            val viewHolder = holder as PizzaViewHolder
            val item = items as ArrayList<Items.Pizza>
            viewHolder.id.text = item[position].id.toString()
            viewHolder.name.text = item[position].name
            viewHolder.category.text = item[position].category.name
            val sizeAndPrice = item[position].sizeAndPrice.entries
            val sizes = arrayListOf<String>()
            for (i in sizeAndPrice) {
                sizes.add(i.key.name)
            }
            holder.setSizesSpinnerData(sizes)
            setPrice(holder)
        }else
        {
            val viewHolder = holder as ToppingViewHolder
            val item = items as ArrayList<Items.Topping>
            viewHolder.toppingName.text = item[position].name
            viewHolder.toppingPrice.text = item[position].price

        }

    }
    private fun setPrice(holder: AdminMenuItemsAdapter.PizzaViewHolder) {
        val item = items as ArrayList<Items.Pizza>
        holder.size.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                selectedSize: Int,
                id: Long
            ) {
                val size: Size =
                    Size.valueOf(adapterView?.getItemAtPosition(selectedSize).toString())
                holder.price.text = item[holder.adapterPosition].sizeAndPrice[size].toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if(menuType == "pizza")
            PIZZA
        else
            TOPPING
    }

    override fun getItemCount(): Int {
        return items.size
    }
}