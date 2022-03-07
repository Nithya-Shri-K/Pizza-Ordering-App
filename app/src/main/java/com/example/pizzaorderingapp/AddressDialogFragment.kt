package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import com.example.pizzaorderingapp.databinding.FragmentAddressDialogBinding


class AddressDialogFragment(val currentUser : Users,val listener: addressHandlerListener) : DialogFragment() {
    lateinit var binding : FragmentAddressDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddressDialogBinding.inflate(layoutInflater,container,false)

        binding.save.setOnClickListener {
            val address = binding.completeAddress.text.toString()
            val selectedTitleId = binding.addressTitle.checkedRadioButtonId
            val title = binding.root.findViewById<RadioButton>(selectedTitleId).text.toString()
            UserHandler.addAddress(currentUser,title,address)
            listener.refresh()
            dismiss()
        }


        return binding.root
    }
}