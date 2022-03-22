package com.example.pizzaorderingapp

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.pizzaorderingapp.databinding.FragmentItemDialogBinding

class ItemDialogFragment(
    private val listener: AdminPizzaItemsHandler,
    private val operation: String
) : DialogFragment() {
    private lateinit var selectedpizza: Pizza
    private lateinit var binding: FragmentItemDialogBinding
    var imagePath = ""

    constructor(listener: AdminPizzaItemsHandler, operation: String, pizza: Pizza) : this(
        listener,
        operation
    ) {
        this.selectedpizza = pizza
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentItemDialogBinding.inflate(layoutInflater, container, false)

        val getImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                val imageFilePath = it.data?.data
                binding.itemImage.setImageURI(imageFilePath)
                println("pppppp")
                imagePath = imageFilePath?.path.toString()


            }
        }

        with(binding) {
            textviewRegularPrice.isEnabled = false
            textviewMediumPrice.isEnabled = false
            textviewLargePrice.isEnabled = false
            checkboxRegular.setOnCheckedChangeListener { _, isChecked ->
                binding.textviewRegularPrice.isEnabled = isChecked
                if (!isChecked)
                    binding.textviewRegularPrice.text.clear()
            }
            checkboxMedium.setOnCheckedChangeListener { _, isChecked ->
                binding.textviewMediumPrice.isEnabled = isChecked
                if (!isChecked)
                    binding.textviewMediumPrice.setText("", TextView.BufferType.EDITABLE)
            }
            checkboxLarge.setOnCheckedChangeListener { _, isChecked ->
                binding.textviewLargePrice.isEnabled = isChecked
                if (!isChecked)
                    binding.textviewLargePrice.setText("", TextView.BufferType.EDITABLE)
            }

            buttonSave.setOnClickListener {
                if (itemName.text.isNotEmpty() && radiobuttonCategory.checkedRadioButtonId != -1 && ((checkboxRegular.isChecked && textviewRegularPrice.text.isNotEmpty()) || (checkboxMedium.isChecked && textviewMediumPrice.text.isNotEmpty()) || (checkboxLarge.isChecked && textviewLargePrice.text.isNotEmpty())))
                    saveItem()
                else
                    Toast.makeText(
                        context,
                        getString(R.string.save_error_message),
                        Toast.LENGTH_SHORT
                    ).show()
            }
            buttonCancel.setOnClickListener { dismiss() }
            uploadImage.setOnClickListener {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                getImage.launch(intent)

            }
        }
        if (operation == EDIT) {
            setDataToEdit(selectedpizza)
        }
        return binding.root
    }

    private fun saveItem() {

        val name = binding.itemName.text.toString()
        val categoryId = binding.radiobuttonCategory.checkedRadioButtonId
        val category = binding.root.findViewById<RadioButton>(categoryId).text.toString()
        val sizeRegular = binding.checkboxRegular
        val sizeMedium = binding.checkboxMedium
        val sizeLarge = binding.checkboxLarge
        val priceRegular = binding.textviewRegularPrice
        val priceMedium = binding.textviewMediumPrice
        val priceLarge = binding.textviewLargePrice
        val sizeAndPrice: MutableMap<Size, String> = mutableMapOf()
        if (sizeRegular.isChecked)
            sizeAndPrice[Size.valueOf(sizeRegular.text.toString())] = priceRegular.text.toString()
        if (sizeMedium.isChecked)
            sizeAndPrice[Size.valueOf(sizeMedium.text.toString())] = priceMedium.text.toString()
        if (sizeLarge.isChecked)
            sizeAndPrice[Size.valueOf(sizeLarge.text.toString())] = priceLarge.text.toString()

//        val image = BitmapFactory.decodeFile(imagePath)
//        val stream = ByteArrayOutputStream()
//        image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//        val imageInByte: ByteArray = stream.toByteArray()
        if (operation == ADD_ITEM) {
//            val pizza = AdminHandler.addPizza(
//                name,
//                R.drawable.tandooripaneer,
//                sizeAndPrice,
//                Category.valueOf(category)
//            )
            AdminHandler.addPizza(name,
                R.drawable.tandooripaneer,
                Category.valueOf(category),
                sizeAndPrice,requireContext())
            listener.refreshPizzaList()
        } else {
            AdminHandler.updatePizza(
                selectedpizza.id,
                name,
                R.drawable.tandooripaneer,
                sizeAndPrice,
                Category.valueOf(category),requireContext()
            )
            listener.refreshPizzaList()
        }
        dismiss()
    }

    private fun setDataToEdit(pizza: Pizza) {

        binding.itemName.setText(pizza.name, TextView.BufferType.EDITABLE)
        if (pizza.category == Category.Veg)
            binding.Veg.isChecked = true
        else
            binding.Nonveg.isChecked = true
        val sizeAndPrice = pizza.sizeAndPrice.entries
        for (i in sizeAndPrice) {
            when (i.key) {
                Size.Regular -> {
                    binding.checkboxRegular.isChecked = true
                    binding.textviewRegularPrice.setText(i.value, TextView.BufferType.EDITABLE)
                    binding.textviewRegularPrice.isEnabled = true
                }
                Size.Medium -> {
                    binding.checkboxMedium.isChecked = true
                    binding.textviewMediumPrice.setText(i.value, TextView.BufferType.EDITABLE)
                    binding.textviewMediumPrice.isEnabled = true
                }
                Size.Large -> {
                    binding.checkboxLarge.isChecked = true
                    binding.textviewLargePrice.setText(i.value, TextView.BufferType.EDITABLE)
                    binding.textviewLargePrice.isEnabled = true
                }
            }
        }
    }
}