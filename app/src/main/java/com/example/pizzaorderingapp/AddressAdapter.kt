package com.example.pizzaorderingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class AddressAdapter(val addressBook : ArrayList<Address>,val isClickable : Boolean) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {
   lateinit var listener : AddressAdapterListener
    constructor(addressBook : ArrayList<Address>,isClickable : Boolean,listener: AddressAdapterListener) : this(addressBook,isClickable)
   {
       this.listener = listener
   }
    interface AddressAdapterListener{
        fun onAddressSelected(address : Address)
    }

    inner class AddressViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title  = view.findViewById<TextView>(R.id.address_title)
        val address = view.findViewById<TextView>(R.id.address)
        val addressCard = view.findViewById<CardView>(R.id.card_address)
        init {
            if(isClickable) {
                addressCard.setOnClickListener {
                    listener.onAddressSelected(addressBook[adapterPosition])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.address,parent,false)
        return AddressViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.title.text = addressBook[position].title
        holder.address.text = addressBook[position].completeAddress
    }

    override fun getItemCount(): Int {
        return addressBook.size
    }
}