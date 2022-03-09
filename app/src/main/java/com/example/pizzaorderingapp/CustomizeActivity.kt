package com.example.pizzaorderingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pizzaorderingapp.databinding.ActivityCustomizeBinding

class CustomizeActivity : AppCompatActivity(), ToppingSelector {
    var selectedToppings: ArrayList<Topping> = arrayListOf()
    var itemPrice: Int = 0
    var toppingPrice: Int = 0
    var totalPrice: Int = 0
    var cart: ArrayList<Item> = arrayListOf()
    var itemQuantity = 1
    lateinit var binding: ActivityCustomizeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedItem = intent.getSerializableExtra(SELECTED_ITEM) as Pizza
        val selectedSize = intent.getSerializableExtra(SELECTED_SIZE) as Size
        val price = intent.getIntExtra(ITEM_PRICE, 0)
        itemPrice = price
        setSelectedItemDetail(selectedItem, selectedSize, price)
        setToppings()
        with(binding) {
            addToCart.setOnClickListener {
                val item = CartItemsHandler.createItem(
                    selectedItem,
                    itemQuantity,
                    selectedToppings,
                    selectedSize,
                    totalPrice
                )
                intent.putExtra(SELECTED_ITEM, item)
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

    private fun setToppings() {
        val recyclerView = binding.recyclerviewToppings
        val toppings = ToppingAdapter(Database.listOfToppings, USER, this, this)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = toppings
    }

    private fun setSelectedItemDetail(selectedItem: Pizza, selectedSize: Size, price: Int) {
        with(binding) {
            selectedItemName.text = selectedItem.name
            itemPrice.text = getString(R.string.price_prefix, price.toString())
            size.text = selectedSize.name
            quantity.text = itemQuantity.toString()
        }

        setTotalPrice()

    }

    private fun onQuantityIncrement(price: Int) {
        itemQuantity += 1
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
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, UserHomeFragment()).commit()
        }
    }

    private fun setTotalPrice() {
        val totalToppingPrice = toppingPrice * itemQuantity
        totalPrice = itemPrice + totalToppingPrice
        binding.addToCart.text = getString(R.string.add_to_cart, totalPrice.toString())

    }

    override fun onCheck(topping: Topping) {
        toppingPrice += topping.price
        setTotalPrice()
        selectedToppings.add(topping)
    }

    override fun onUncheck(topping: Topping) {
        toppingPrice -= topping.price
        setTotalPrice()
        selectedToppings.remove(topping)
    }
}