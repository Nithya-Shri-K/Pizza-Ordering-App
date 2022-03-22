package com.example.pizzaorderingapp

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayInputStream

class MenuAdapter(
    var menu: ArrayList<Pizza>,
    val context: Context?,
    val listener: UserActionListener
) : RecyclerView.Adapter<MenuAdapter.MenuItemViewHolder>() {

    inner class MenuItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById<ImageView>(R.id.image_item)
        val itemTitle: TextView = view.findViewById<TextView>(R.id.text_title)
        val itemPrice: TextView = view.findViewById<TextView>(R.id.text_cost)
        val sizeSpinner: Spinner = view.findViewById<Spinner>(R.id.spinner_size)
        val addItem: Button = view.findViewById(R.id.button_add_item)

        fun setSizeSpinnerData(sizes: ArrayList<String>) {
            if (context != null) {
                val adapter =
                    ArrayAdapter<String>(
                        context,
                        R.layout.support_simple_spinner_dropdown_item,
                        sizes
                    )
                sizeSpinner.adapter = adapter
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuAdapter.MenuItemViewHolder {

        val itemViewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MenuItemViewHolder(itemViewHolder)

    }

    override fun onBindViewHolder(holder: MenuAdapter.MenuItemViewHolder, position: Int) {
//        val imageStream = ByteArrayInputStream(menu[position].image)
//        val theImage = BitmapFactory.decodeStream(imageStream)

        holder.itemImage.setImageResource(R.drawable.tandooripaneer)
        holder.itemTitle.text = menu[position].name
        val sizesAndPrice = menu[position].sizeAndPrice.entries
        val sizes = ArrayList<String>()
        for (i in sizesAndPrice) {
            sizes.add(i.key.name)
        }
        holder.setSizeSpinnerData(sizes)
        val toppings = ArrayList<String>()
        val databaseHelper = DatabaseHelper(context!!)
        for (i in databaseHelper.getToppings()) {
            toppings.add(i.name)
        }
        setPrice(holder)

        holder.addItem.setOnClickListener {
            val size = Size.valueOf(holder.sizeSpinner.selectedItem.toString())
            val price =
                (menu[holder.adapterPosition].sizeAndPrice[Size.valueOf(size.toString())])?.toInt()
                    ?: 0
            listener.startCustomizeFragment(menu[position].id, size, price)
        }

    }

    override fun getItemCount(): Int {
        println(menu.size)
        return menu.size
    }

    private fun setPrice(holder: MenuItemViewHolder) {
        holder.sizeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                selectedSize: Int,
                id: Long
            ) {
                val size: Size =
                    Size.valueOf(adapterView?.getItemAtPosition(selectedSize).toString())
                val price = menu[holder.adapterPosition].sizeAndPrice[size].toString()
                holder.itemPrice.text = context?.getString(R.string.price_prefix, price)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
}