package com.example.pizzaorderingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.pizzaorderingapp.databinding.FragmentItemDialogBinding

class ItemDialogFragment(private val listener : AdminHandlerListener, private val operation : String) : DialogFragment() {
    private lateinit var selectedpizza : Items.Pizza
    private lateinit var binding: FragmentItemDialogBinding
    constructor(listener: AdminHandlerListener, operation: String, pizza : Items.Pizza) : this(listener,operation)
    {
        this.selectedpizza = pizza


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentItemDialogBinding.inflate(layoutInflater,container,false)
        with(binding){
            textviewRegularPrice.isEnabled = false
            textviewMediumPrice.isEnabled = false
            textviewLargePrice.isEnabled = false
            checkboxRegular.setOnCheckedChangeListener { compoundButton, isChecked ->
                binding.textviewRegularPrice.isEnabled = isChecked
            }
            checkboxMedium.setOnCheckedChangeListener{
                    compoundButton, isChecked ->
                binding.textviewMediumPrice.isEnabled = isChecked
            }
            checkboxLarge.setOnCheckedChangeListener{
                    compoundButton, isChecked ->
                binding.textviewLargePrice.isEnabled = isChecked
            }

            binding.buttonSave.setOnClickListener {
                if(itemName.text.isNotEmpty() && radiobuttonCategory.checkedRadioButtonId != -1 && ((checkboxRegular.isChecked && textviewRegularPrice.text.isNotEmpty()) || (checkboxMedium.isChecked && textviewMediumPrice.text.isNotEmpty()) || (checkboxLarge.isChecked && textviewLargePrice.text.isNotEmpty())))
                {
                    saveItem()
                }
                else
                {
                    Toast.makeText(context,"Please enter all the required Fields",Toast.LENGTH_SHORT).show()
                }

            }
            binding.buttonCancel.setOnClickListener { dismiss() }

        }

        if(operation == "edit"){
            println("inside Edit - $selectedpizza")
            setDataToEdit(selectedpizza)
        }

        return binding.root
    }
    private fun saveItem(){

        val name = binding.itemName.text.toString()
        val categoryId = binding.radiobuttonCategory.checkedRadioButtonId
        val category = binding.root.findViewById<RadioButton>(categoryId).text.toString()
        val sizeRegular = binding.checkboxRegular
        val sizeMedium = binding.checkboxMedium
        val sizeLarge = binding.checkboxLarge
        val priceRegular = binding.textviewRegularPrice
        val priceMedium = binding.textviewMediumPrice
        val priceLarge = binding.textviewLargePrice
        val sizeAndPrice : MutableMap<Size,String> = mutableMapOf()
        if(sizeRegular.isChecked)
            sizeAndPrice[Size.valueOf(sizeRegular.text.toString())] = priceRegular.text.toString()
        if(sizeMedium.isChecked)
                sizeAndPrice[Size.valueOf(sizeMedium.text.toString())] = priceMedium.text.toString()
        if(sizeLarge.isChecked)
                sizeAndPrice[Size.valueOf(sizeLarge.text.toString())] = priceLarge.text.toString()
        if(operation == "add"){
            AdminHandler.addItem(name,R.drawable.tandooripaneer,sizeAndPrice, Category.valueOf(category))
            listener.refresh()
            }
        else{
            AdminHandler.updateItem(selectedpizza,name,R.drawable.tandooripaneer,sizeAndPrice, Category.valueOf(category))
            listener.refresh()
            }

        dismiss()

    }

    private fun setDataToEdit(pizza : Items.Pizza){
        println(pizza)
        binding.itemName.setText(pizza.name, TextView.BufferType.EDITABLE)
        if(pizza.category == Category.Veg)
            binding.Veg.isChecked = true
        else
            binding.Nonveg.isChecked = true
        val sizeAndPrice = pizza.sizeAndPrice.entries
        for(i in sizeAndPrice){
            when(i.key){
                Size.Regular -> {
                    binding.checkboxRegular.isChecked = true
                    binding.textviewRegularPrice.setText( i.value,TextView.BufferType.EDITABLE)
                }
                Size.Medium -> {
                    binding.checkboxMedium.isChecked = true
                    binding.textviewMediumPrice.setText(i.value,TextView.BufferType.EDITABLE)
                }
                Size.Large -> {
                    binding.checkboxLarge.isChecked = true
                    binding.textviewLargePrice.setText(i.value,TextView.BufferType.EDITABLE)
                }

            }
        }

    }



}