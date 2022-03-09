package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaorderingapp.databinding.FragmentAddressBookBinding


class AddressBookFragment : Fragment(), addressHandlerListener,AddressAdapter.AddressAdapterListener {
    lateinit var binding: FragmentAddressBookBinding
    lateinit var addressBook: AddressAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddressBookBinding.inflate(layoutInflater, container, false)

        val currentUser = arguments?.getSerializable(CURRENT_USER) as User

        binding.buttonAddAddress.setOnClickListener {
            val addAddress = AddressDialogFragment(currentUser, this)
            addAddress.show(parentFragmentManager, "add Address")
        }

        val recyclerView = binding.recyclerviewAddressBook
        addressBook = AddressAdapter(currentUser.address, false,this)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = addressBook


        return binding.root
    }

    override fun onAddressSelected(address: Address) {

    }

    override fun refresh() {
        addressBook.notifyDataSetChanged()
    }


}