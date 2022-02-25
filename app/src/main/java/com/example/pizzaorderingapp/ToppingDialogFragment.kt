package com.example.pizzaorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.pizzaorderingapp.databinding.FragmentToppingDialogBinding


class ToppingDialogFragment(val listener: AdminHandlerListener,val operation : String) : DialogFragment() {
    lateinit var topping :Items.Topping
    constructor(listener: AdminHandlerListener,operation: String,topping : Items.Topping) : this(listener,operation){
        this.topping = topping
    }
    lateinit var binding : FragmentToppingDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToppingDialogBinding.inflate(layoutInflater,container,false)
        if(operation == "edit"){
            setDataToEdit(topping)
        }
        binding.buttonSave.setOnClickListener {
            save()
        }
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
        return binding.root
    }
    private fun setDataToEdit(topping : Items.Topping) {
        binding.toppingName.setText(topping.name, TextView.BufferType.EDITABLE)
        binding.toppingPrice.setText(topping.price,TextView.BufferType.EDITABLE)
    }
    fun save(){
        val toppingName = binding.toppingName.text.toString()
        val price = binding.toppingPrice.text.toString()
        if(operation == "add") {
            AdminHandler.addTopping(toppingName, price)
        }
        else
            AdminHandler.updateTopping(topping,toppingName,price)
        listener.refresh()
        dismiss()
    }


}