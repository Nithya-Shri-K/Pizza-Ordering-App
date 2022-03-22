package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaorderingapp.databinding.FragmentAddressBookBinding


class AddressBookFragment : Fragment(), AddressHandlerListener {
    lateinit var binding: FragmentAddressBookBinding
    lateinit var addressAdapter: AddressAdapter
    lateinit var databaseHelper: DatabaseHelper
    var currentUserId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddressBookBinding.inflate(layoutInflater, container, false)
        databaseHelper = DatabaseHelper(requireContext())
        currentUserId = arguments?.getInt(CURRENT_USER_ID) ?: 0

        binding.buttonAddAddress.setOnClickListener {
            val addAddress = AddressDialogFragment(currentUserId, this)
            addAddress.show(parentFragmentManager, ADD_ADDRESS)
        }

        val recyclerView = binding.recyclerviewAddressBook
        addressAdapter = AddressAdapter(
            databaseHelper.getUserAddress(currentUserId),
            false,
            requireContext(),this
        )
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = addressAdapter


        return binding.root
    }

    override fun refreshAddressList() {
        addressAdapter.addressBook = databaseHelper.getUserAddress(currentUserId)
        addressAdapter.notifyDataSetChanged()
    }


}