package com.example.pizzaorderingapp

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.pizzaorderingapp.databinding.FragmentItemDialogBinding

class ItemDialogFragment(private val listener : AdminMenuHandler, private val operation : String) : DialogFragment() {
    private lateinit var item : Item
    private lateinit var binding: FragmentItemDialogBinding
    constructor(listener: AdminMenuHandler,operation: String, item : Item) : this(listener,operation)
    {
     this.item = item
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentItemDialogBinding.inflate(layoutInflater,container,false)
        if(this::item.isInitialized){
            setDataToEdit(item)
        }
        binding.buttonSave.setOnClickListener {
            saveItem()
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
            listener.refreshMenu()
            }
        else{
            AdminHandler.updateItem(item,name,R.drawable.tandooripaneer,sizeAndPrice, Category.valueOf(category))
            listener.refreshMenu()
            }

        dismiss()

    }

    private fun setDataToEdit(item : Item){
        binding.itemName.setText(item.name, TextView.BufferType.EDITABLE)
        if(item.category == Category.Veg)
            binding.Veg.isChecked = true
        else
            binding.Nonveg.isChecked = true
        val sizeAndPrice = item.sizeAndPrice.entries
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