package com.example.pizzaorderingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaorderingapp.databinding.AddressBinding

class AddressAdapter(var addressBook: MutableList<Address>, val isClickable: Boolean, val context: Context, val listener: AddressHandlerListener):
    RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    lateinit var selector: AddressSelectorListener
    lateinit var binding: AddressBinding

    constructor(
        addressBook: MutableList<Address>,
        isClickable: Boolean,
        selector: AddressSelectorListener,
        listener: AddressHandlerListener,
        context: Context
    ) : this(addressBook, isClickable, context,listener) {
        this.selector = selector
    }

    inner class AddressViewHolder(binding: AddressBinding) : RecyclerView.ViewHolder(binding.root) {

        var databaseHelper: DatabaseHelper = DatabaseHelper(context)
        val title: TextView = binding.addressTag
        val address: TextView = binding.completeAddress
        private val addressCard: CardView = binding.addressCard
        private val deleteAddress: ImageView = binding.deleteAddress

        init {
            if (isClickable) {
                addressCard.setOnClickListener {
                    selector.onAddressSelected(addressBook[adapterPosition])
                }
            }

            deleteAddress.setOnClickListener {
                UserHandler.removeAddress(addressBook[adapterPosition].addressId, databaseHelper)
                listener.refreshAddressList()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {

        binding = AddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val address = addressBook[position]
        holder.title.text = address.addressTag
        holder.address.text = address.completeAddress
    }

    override fun getItemCount(): Int {
        return addressBook.size
    }
}