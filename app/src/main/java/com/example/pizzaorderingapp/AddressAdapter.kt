package com.example.pizzaorderingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AddressAdapter(val addressBook: ArrayList<Address>, val isClickable: Boolean) :
    RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    lateinit var selector: AddressSelectorListener
    lateinit var listener: AddressHandlerListener

    constructor(
        addressBook: ArrayList<Address>,
        isClickable: Boolean,
        listener: AddressHandlerListener
    ) : this(addressBook, isClickable) {
        this.listener = listener
    }

    constructor(
        addressBook: ArrayList<Address>,
        isClickable: Boolean,
        selector: AddressSelectorListener,
        listener: AddressHandlerListener
    ) : this(addressBook, isClickable) {
        this.selector = selector
        this.listener = listener
    }


    inner class AddressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById<TextView>(R.id.address_title)
        val address: TextView = view.findViewById<TextView>(R.id.address)
        val addressCard: CardView = view.findViewById<CardView>(R.id.card_address)
        val deleteAddress: ImageView = view.findViewById<ImageView>(R.id.delete)

        init {
            if (isClickable) {
                addressCard.setOnClickListener {
                    selector.onAddressSelected(addressBook[adapterPosition])
                }
            }
            deleteAddress.setOnClickListener {
                UserHandler.removeAddress(addressBook[adapterPosition], addressBook)
                listener.refresh()

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.address, parent, false)
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