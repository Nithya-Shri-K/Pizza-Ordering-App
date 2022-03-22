package com.example.pizzaorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaorderingapp.databinding.ActivityCustomizeBinding

class CustomizeActivity : AppCompatActivity(), ToppingSelector {
    var selectedToppings: ArrayList<Int> = arrayListOf()
    var itemPrice: Int = 0
    var toppingPrice: Int = 0
    var totalPrice: Int = 0
    var cart: ArrayList<Item> = arrayListOf()
    var itemQuantity = 1
    lateinit var binding: ActivityCustomizeBinding
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomizeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHelper = DatabaseHelper(this)

        val selectedItemId = intent.getIntExtra(SELECTED_PIZZA_ID,0)
        val selectedSize = intent.getSerializableExtra(SELECTED_SIZE) as Size
        var price = intent.getIntExtra(ITEM_PRICE, 0)
        itemPrice = price
        setSelectedItemDetail(selectedItemId, selectedSize, price)
        showAvailableToppings()


        with(binding) {
            addToCart.setOnClickListener {
                val itemId:Int = UserHandler.createItem(
                    selectedItemId,
                    itemQuantity,
                    selectedSize,
                    totalPrice,
                    selectedToppings,
                    databaseHelper
                )
                intent.putExtra(SELECTED_ITEM_ID,itemId)
                setResult(RESULT_OK, intent)
                finish()

            }

            increment.setOnClickListener {
                onQuantityIncrement(price)
            }
            decrement.setOnClickListener {
                onQuantityDecrement(price)
            }
            backButton.setOnClickListener {
                finish()
            }
        }
    }

    private fun showAvailableToppings() {
        val databaseHelper = DatabaseHelper(this)
        val recyclerView = binding.recyclerviewToppings
        val toppings = UserToppingAdapter(databaseHelper.getToppings(), this, this)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = toppings
    }

    private fun setSelectedItemDetail(selectedItemId: Int, selectedSize: Size, price: Int) {
        val pizzaName = databaseHelper.getPizzaName(selectedItemId)
        with(binding) {
            selectedItemName.text = pizzaName
            itemPrice.text = getString(R.string.price_prefix, price.toString())
            size.text = selectedSize.name
            quantity.text = itemQuantity.toString()
        }

        setTotalPrice()

    }
   private fun onQuantityIncrement(price: Int) {
        itemQuantity +=1
        itemPrice = (price) * itemQuantity
        binding.quantity.text = itemQuantity.toString()
        setTotalPrice()
    }

    private fun onQuantityDecrement(price: Int) {
        itemQuantity -= 1
        if (itemQuantity > 0) {
            itemPrice = (price) * itemQuantity
            binding.quantity.text = itemQuantity.toString()
            setTotalPrice()
        } else {
            finish()
        }
    }

    private fun setTotalPrice() {
        val totalToppingPrice = toppingPrice * itemQuantity
        totalPrice = itemPrice + totalToppingPrice
        binding.addToCart.text = getString(R.string.add_to_cart, totalPrice.toString())

    }

    override fun onToppingSelected(topping: Topping) {
        toppingPrice += topping.price
        setTotalPrice()
        selectedToppings.add(topping.id)
    }

    override fun onToppingDeselected(topping: Topping) {
        toppingPrice -= topping.price
        setTotalPrice()
        selectedToppings.remove(topping.id)
    }
}