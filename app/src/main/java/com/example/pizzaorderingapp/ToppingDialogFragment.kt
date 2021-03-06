package com.example.pizzaorderingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.pizzaorderingapp.databinding.FragmentToppingDialogBinding


class ToppingDialogFragment(
    private val listener: AdminToppingHandler,
    private val operation: String
) : DialogFragment() {
    lateinit var topping: Topping
    lateinit var databaseHelper: DatabaseHelper

    constructor(listener: AdminToppingHandler, operation: String, topping: Topping) : this(
        listener,
        operation
    ) {
        this.topping = topping
    }

    lateinit var binding: FragmentToppingDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToppingDialogBinding.inflate(layoutInflater, container, false)
        databaseHelper = DatabaseHelper(requireContext())
        if (operation == EDIT) {
            setDataToEdit(topping)
        }
        binding.buttonSave.setOnClickListener {
            if (binding.toppingName.text.isNotEmpty() && binding.toppingPrice.text.isNotEmpty()) {
                save()
            } else {
                Toast.makeText(context, getString(R.string.save_error_message), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    private fun setDataToEdit(topping: Topping) {
        binding.toppingName.setText(topping.name, TextView.BufferType.EDITABLE)
        binding.toppingPrice.setText(topping.price.toString(), TextView.BufferType.EDITABLE)
    }

    private fun save() {
        val toppingName = binding.toppingName.text.toString()
        val price = binding.toppingPrice.text.toString().toInt()
        if (operation == ADD_ITEM) {
            AdminHandler.addTopping(toppingName, price,databaseHelper)
        } else
            AdminHandler.updateTopping(topping.id, toppingName, price,databaseHelper)
        listener.refreshToppingList()
        dismiss()
    }
}